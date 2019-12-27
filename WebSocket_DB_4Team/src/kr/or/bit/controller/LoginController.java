package kr.or.bit.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.EmpDao;

@Controller
public class LoginController {

	private SqlSession sqlSession;

	@Autowired
	public void setSqlsession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@RequestMapping("/Login.do")
	public String showView() {
		return "/WEB-INF/views/login/Login.jsp";
	}

	@RequestMapping("/LoginOk.do")
	public String loginOk(String userid, String pwd, HttpServletRequest request) {

			if (userid != null && !userid.isEmpty() && pwd != null && !pwd.isEmpty()) {
				request.getSession().setAttribute("userid", userid);
			} 
				return "Index.do";
		}


	@RequestMapping("/Logout.do")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "Index.do";
	}
}
