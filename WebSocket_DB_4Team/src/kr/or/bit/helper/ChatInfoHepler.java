package kr.or.bit.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

import kr.or.bit.dto.ChatRoom;

public class ChatInfoHepler {
	// 접속 유저 관리
	public static Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

	// 채팅방 관리
	public static Map<String, ChatRoom> roomInfos = new HashMap<>();
}
