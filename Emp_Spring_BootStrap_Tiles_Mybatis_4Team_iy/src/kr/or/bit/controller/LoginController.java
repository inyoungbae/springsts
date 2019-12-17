package kr.or.bit.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bit.dao.EmpDao;

@Controller
public class LoginController {
	
		private SqlSession sqlsession;
		
		@Autowired
		public void setSqlsession(SqlSession sqlsession) {
			this.sqlsession = sqlsession;
		}

		@RequestMapping(value="/Login.do", method=RequestMethod.GET)
		public String login() { //로그인 화면단
			return "/WEB-INF/views/login/Login.jsp";
		}
		
		@RequestMapping(value="/Login.do", method=RequestMethod.POST)
		public String login(String userid, String pwd, HttpServletRequest request) {
			System.out.println("들어왔니?" + userid + "/" + pwd);
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);  //연결시킨 인터페이스dao의 정보를 넘기면 됨
			System.out.println("정보 받아오니?" + empdao);
			String isLogin = empdao.checkAdminLogin(userid, pwd);
			
			System.out.println("여기는 오니?");
			String view = null;
			if (isLogin != null) {
				request.getSession().setAttribute("userid", userid);
				view = "index.jsp";
			}
			else {
				view = "Login.do";
			}
			return view;
		} 
		
		@RequestMapping(value="/Logout.do")
		public String Logout(HttpServletRequest request) {   //redirect 붙여야하나? session객체는 ? 
			request.getSession().invalidate();

			return "redirect:index.jsp";
		}
}
