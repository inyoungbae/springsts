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
	
	private SqlSession sqlSession;

	@Autowired
	public void setSqlsession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@RequestMapping("/ChatHome.do")  //챗홈 : 첫화면
	public String showView(Model model) {

		ChatDao dao = sqlSession.getMapper(ChatDao.class);
		List<ChatRoom> roomlist = dao.getRoomInfo();
		
		ChatInfoHepler.roomInfos.clear();
		for(ChatRoom room : roomlist)
			ChatInfoHepler.roomInfos.put(room.getName(), room);

		model.addAttribute("roomlist", roomlist);
		
		return "/WEB-INF/views/chat/ChatHome.jsp";
	}
	
	@RequestMapping("/Chat.do")
	public String showView(String room, Model model) {   // 채팅방 들어가서 보이는 화면
		model.addAttribute("room", room);
		return "/WEB-INF/views/chat/Chat.jsp";
	}
}