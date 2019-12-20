package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HandlerController {

	@RequestMapping("/Chat-ws.do")
	public String chatview() {
		return "/WEB-INF/views/chat/chat-ws.jsp";
	}
}
