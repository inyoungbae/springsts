package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.ChatDao;
import kr.or.bit.dao.EmpDao;
import kr.or.bit.dto.ChatRoom;
import kr.or.bit.dto.Emp;
import kr.or.bit.helper.ChatInfoHepler;

@Controller
public class ChatController {

	@RequestMapping("/ChatHome.do")  
	public String showView(Model model) {
		return "/WEB-INF/views/chat/ChatHome.jsp";
	}
	
	@RequestMapping("/Chat.do")
	public String showView(String room, Model model) {  
		model.addAttribute("room", room);
		return "/WEB-INF/views/chat/Chat.jsp";
	}
}