package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HandlerController {

	@RequestMapping("/Chat-list.do")
	public String chatList() {
		return "/WEB-INF/views/chat/chat-list.jsp";
	}
	
	
	@RequestMapping("/Chat-ws.do")
	public String chatRoomEnter() {
		return "/WEB-INF/views/chat/chat-room.jsp";
	}
}
