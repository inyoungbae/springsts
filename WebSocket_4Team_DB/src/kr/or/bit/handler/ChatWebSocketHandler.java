package kr.or.bit.handler;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.bit.dao.ChatDao;
import kr.or.bit.dto.ChatRoom;
import kr.or.bit.helper.ChatInfoHepler;

public class ChatWebSocketHandler extends TextWebSocketHandler {

	private SqlSession sqlSession;

	@Autowired
	public void setSqlsession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 접속한 전체 유저 관리
	private Map<String, WebSocketSession> users = ChatInfoHepler.users;
	
	// 채팅방 관리
	private Map<String, ChatRoom> roomInfos = ChatInfoHepler.roomInfos;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log(session.getId() + " 연결 됨");
		String cmd = getAttribute(session, "cmd");
		String user = getAttribute(session, "user");
		// 채팅 접속
		if(cmd.equals("on")) {
			System.out.println("세션내용은?" + session);
			users.put(user, session);	
			sendChatRoomInfoMessage();
		}
		// 채팅방 접속
		else if(cmd.equals("join")) { 
			System.out.println("조인in");
			joinChatRoom(session, user, getAttribute(session, "room"));
			System.out.println(user+ "/" + session.getId() );
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log(session.getId() + "로부터 메시지 수신: " + message.getPayload());
		JSONObject data = new JSONObject(message.getPayload());
		String cmd = data.getString("cmd");

		if (cmd.equals("message")) {
			sendMessage(session, data);
		} else if (cmd.equals("createChatRoom")) {
			System.out.println("create chat");
			createChatRoom(session, data);
		}
			
			
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log(session.getId() + " 연결 종료");
		String cmd = getAttribute(session, "cmd");
		String user = getAttribute(session, "user");
		System.out.println(cmd+user);
		// 접속 유저 종료
		if(cmd.equals("on")) { 
			//System.out.println("창 종료");
			users.remove(user);
		}
		// 채팅창 종료
		else if(cmd.equals("join")){ 
			System.out.println("채팅 종료");
			String roomName = getAttribute(session, "room");

			ChatRoom room = roomInfos.get(roomName);
			room.removeUser(user);
			sendMemberInfoMessage(room, user + "님이 나가셨습니다.");
			sendChatRoomInfoMessage();
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log(session.getId() + " 익셉션 발생: " + exception.getMessage());
	}

	private void log(String logmsg) {
		System.out.println(new Date() + " : " + logmsg);
	}

	private void sendMessage(WebSocketSession session, JSONObject data) throws Exception {
		System.out.println("sendMessage in");
		System.out.println(data);
		System.out.println(session);
		String sendUser = getAttribute(session, "user");
		System.out.println(sendUser);
		JSONObject json = new JSONObject().put("message", data.getString("message"))
																.put("sender", sendUser);

		ChatRoom room = roomInfos.get(data.get("room"));
		for (Map.Entry<String, WebSocketSession> userInfo : room.getUsers().entrySet()) {
			String type = sendUser.equals(userInfo.getKey()) ? "my" : "other";
			System.out.println("타입은?");
			System.out.println(type);
			System.out.println(userInfo);
			System.out.println(roomInfos);
			System.out.println("방  인원사이즈★"+ roomInfos.size());
		
			json.put("type", type);
		

			userInfo.getValue().sendMessage(new TextMessage(json.toString()));
			System.out.println("--------------");
			System.out.println(userInfo);
			System.out.println(userInfo.getValue());
			log(userInfo.getValue() +"에 메세지 발송");
			System.out.println("------------------");
		}
	}

	private void createChatRoom(WebSocketSession session, JSONObject data) throws Exception {
		System.out.println("데이터가뭐니?" + data);
	
		ChatRoom room = new ChatRoom(getAttribute(session, "user")
														, data.getString("name")
														, Integer.parseInt(data.getString("max"))
														, Integer.parseInt(data.getString("ref")));

		System.out.println("룸은뭐니?" + room );
		roomInfos.put(room.getName(), room);
		sendChatRoomInfoMessage();
		
		System.out.println("room" + room);   //kr.or.bit.dto.ChatRoom@1ef97e65
		ChatDao dao = sqlSession.getMapper(ChatDao.class);
		dao.insertRoomInfo(room);
	}

	private void joinChatRoom(WebSocketSession session, String user, String room) throws Exception {
		System.out.println("join1");
		System.out.println("roomInfos start");
		for(String name : roomInfos.keySet()) {
			System.out.println(name);
		}
		System.out.println("roomInfos end" + room);
		ChatRoom chatRoom = roomInfos.get(room);
		System.out.println("join2");

		System.out.println(user +"/"+ session.getId());
		System.out.println("chartroom name1 ");
		System.out.println(chatRoom.getName());
		System.out.println("chartroom name 2");
		chatRoom.addUser(user, session);
		
		System.out.println("join3");

		sendMemberInfoMessage(chatRoom, user + "님이 들어오셨습니다.");

		sendChatRoomInfoMessage();
	}

	private void sendMemberInfoMessage(ChatRoom room, String message) throws Exception {
		String jsonString = new JSONObject().put("message", message)
																.put("type", "memberInfo")
																.put("owner", room.getOwner())
																.put("users", new JSONArray(room.getUserName())).toString();

		for (WebSocketSession s : room.getUsers().values())
			s.sendMessage(new TextMessage(jsonString));
	}

	private void sendChatRoomInfoMessage() throws Exception {
		System.out.println("여기오나요?");
		
		JSONArray array = new JSONArray();
		for(ChatRoom room : roomInfos.values()) {
			System.out.println("룸이름");
			System.out.println(room.getName());
			array.put(new JSONObject().put("owner", room.getOwner())
													.put("max", room.getMax())
													.put("name", room.getName())
													.put("users", new JSONArray(room.getUserName())));
		}
		
		System.out.println("오나요?" + array);
		
		
		String jsonString = new JSONObject().put("type", "chatRoomInfo")
																.put("rooms", array).toString();
		System.out.println("여기는요?");
		for (WebSocketSession s : users.values())
			s.sendMessage(new TextMessage(jsonString));
	}
	
	public String getAttribute(WebSocketSession session, String parameter) {
		return (String) session.getAttributes().get(parameter);
	}
}
