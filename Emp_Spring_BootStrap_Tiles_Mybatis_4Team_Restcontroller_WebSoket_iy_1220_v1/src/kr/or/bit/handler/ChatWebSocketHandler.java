package kr.or.bit.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatWebSocketHandler extends TextWebSocketHandler {
	
    ObjectMapper objectMapper = new ObjectMapper();  //java object를 json 문자열로 변환

	private Map<String, WebSocketSession> users = new ConcurrentHashMap<>(); 
	// 채팅메세지를 연결된 전체 클라이언트에게 전달할때 사용
	//users : 채팅에 참여하는 사람들
	
	private Map<String, List<WebSocketSession>> roomList = new HashMap<>();  //세션아이디 + 룸정보
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//웹소캣 클라이언트랑 연결이 되면, users맵에 추가 
		log(session.getId() + " 연결 됨");
		users.put(session.getId(), session); 
		//System.out.println("세션은 뭘까?" + session);  //WebSocket session id=0
		//System.out.println("아이디는 뭘까?" + session.getId()); // 0
		
	}

	@Override
	public void afterConnectionClosed( //웹소캣 클라이언트랑 연결을 끊으면, users맵에서 삭제
			WebSocketSession session, CloseStatus status) throws Exception {
		log(session.getId() + " 연결 종료됨");
		users.remove(session.getId());
	}

	@Override
	protected void handleTextMessage(
			WebSocketSession session, TextMessage message) throws Exception {
	
		System.out.println("메세지 내용이뭐니?" + message); //TextMessage payload= msg:99 : 8.., byteCount=11, last=true]
		
		System.out.println( "세션아이디 내용 " + session.getId()  +"/"+ message.getPayload());

		JSONObject data = new JSONObject(message.getPayload());
		System.out.println(data);
	     String cmd = data.getString("cmd");
	     if(cmd.equals("create")) {
	    	System.out.println("방생성");
	 		System.out.println("방생성데이터" + data);
	 		System.out.println("방이름" + data.get("roomname"));
	 		String roomnm = data.getString("roomname");
	 		//String roomember = data.getString("captain");
	 		
	 		List<WebSocketSession> memberlist = new ArrayList<WebSocketSession>();
	 		
	 		memberlist.add(session);
	 		roomList.put(roomnm, memberlist);
	 		
	 		for(WebSocketSession a : users.values()) {
	 			a.sendMessage(message);
	 		}
	 		
	     }else if(cmd.equals("enter")) {
	    	 System.out.println("채팅할래요");
	    	 System.out.println("채팅데이터" + data);
	    	 
			  String id = message.getPayload().split(":")[0]; 
			  String content = message.getPayload().split(":")[1]; 
			  String json = "{'id' : " + id + "," + "'msg' : '" + content + "'}"; 
			  System.out.println(json); log(session.getId() + "로부터 메시지 수신: " + message.getPayload()); //채팅에 메세지를 작성한 id와 메세지내용 로그로 찍어봄. 
			  for (WebSocketSession s : users.values()) { //for문 사용하여 전체 참여자에게 특정참여자가 작성한 메세지 내용을 전송
			  s.sendMessage(message); 
			  log(s.getId() + "에 메시지 발송: " + message.getPayload()); }
	     }
		
		

		 
	}

	@Override
	public void handleTransportError(
			WebSocketSession session, Throwable exception) throws Exception {
		log(session.getId() + " 익셉션 발생: " + exception.getMessage());
	}

	private void log(String logmsg) {
		System.out.println(new Date() + " : " + logmsg);
	}

}
