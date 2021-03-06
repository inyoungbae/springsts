package ws;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {

	private Map<String, WebSocketSession> users = new ConcurrentHashMap<>(); 
	// 채팅메세지를 연결된 전체 클라이언트에게 전달할때 사용
	//users : 채팅에 참여하는 사람들
	
	@Override
	public void afterConnectionEstablished(  //웹소캣 클라이언트랑 연결이 되면, users맵에 추가 
			WebSocketSession session) throws Exception {
		log(session.getId() + " 연결 됨");
		users.put(session.getId(), session); 
		System.out.println("세션은 뭘까?" + session);  //WebSocket session id=0
		System.out.println("아이디는 뭘까?" + session.getId()); // 0

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
		log(session.getId() + "로부터 메시지 수신: " + message.getPayload());  //채팅에 메세지를 작성한 id와 메세지내용 로그로 찍어봄. 
		for (WebSocketSession s : users.values()) {    //for문 사용하여 전체 참여자에게 특정참여자가 작성한 메세지 내용을 전송 
			s.sendMessage(message);
			log(s.getId() + "에 메시지 발송: " + message.getPayload());
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
