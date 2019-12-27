package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.dto.ChatRoom;

public interface ChatDao {
	
	public List<ChatRoom> getRoomInfo();
	
	public int insertRoomInfo(ChatRoom chatroom);

}
