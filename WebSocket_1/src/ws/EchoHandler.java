package ws;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class EchoHandler extends TextWebSocketHandler{
	//EchoHandler 클래스는 클라이언트로부터 메시지를 받았을 때, 응답으로 Echo 메시지를 보내기 위한 처리를 하는 헨들러
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception { 
		//웹소캣 클라이언트와 연결될때 호출
		//WebSocketSession : 클라이언트와의 세션을 관리하는 객체
		System.out.printf("%s 연결 됨\n ", session.getId());
	};
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message)throws Exception {
		//웹소켓 클라이언트가 텍스트 메세지를 전송할때 호출되는 메서드 
		//TextMessage : 클라이언트가 전송하는 텍스트데이터 담음 
		//message.getPayload() : 이 함수를 통해 텍스트 데이터를 구함 
		//session.sendMessage() : 웹소켓 클라이언트에 데이터를 전송 
		System.out.printf("%s로부터 [%s]받음\n", session.getId(),message.getPayload());
		session.sendMessage(new TextMessage("echo:" + message.getPayload()));
	};
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//웹소캣 클라이언트와 연결이 종료될때 호출
		System.out.printf("%s 연결끊김\n", session.getId());
	}
}
