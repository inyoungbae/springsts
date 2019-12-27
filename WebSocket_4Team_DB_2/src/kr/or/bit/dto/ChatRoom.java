package kr.or.bit.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

public class ChatRoom {
	private int ref = 0;
	private String owner;
	private String name;
	private int max;
	private Map<String, WebSocketSession> users;
	
	
	public ChatRoom() {
		this.users = new HashMap<>();
	
	}

	public ChatRoom(String owner, String name, int max, int ref) {
		this.owner = owner;
		this.name = name;
		this.max = max;
		this.ref = ref;
		this.users = new HashMap<>();
	}


	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getMax() {
		return max;
	}


	public void setMax(int max) {
		this.max = max;
	}


	public Map<String, WebSocketSession> getUsers() {
		return users;
	}
	
	public List<String> getUserName() {
		System.out.println("in getUserName");
		System.out.println(users.size());
		return new ArrayList<String>(users.keySet());
	}


	public void setUsers(Map<String, WebSocketSession> users) {
		this.users = users;
	}


	public void addUser(String user, WebSocketSession session) {
		System.out.println("chartroom in");
		System.out.println(users.keySet().toString());
		users.put(user, session);
	}
	
	public void removeUser(String user) {
		users.remove(user);
	}
}
