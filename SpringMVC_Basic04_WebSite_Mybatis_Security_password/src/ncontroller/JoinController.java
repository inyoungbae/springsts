package ncontroller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import dao.MemberDao;
import service.JoinService;
import vo.Member;

@Controller
@RequestMapping("/joinus/")
public class JoinController {

/*	@Autowired
	private MemberDao memberdao;*/

	//비동기
	@Autowired
	private View jsonview;
	
	@Autowired
	private JoinService service;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping(value="join.do",method=RequestMethod.GET)
	public String join() {
		//return "join.jsp";
		return "joinus.join"; //폴더명.파일명
	}
	
	@RequestMapping(value="join.do",method=RequestMethod.POST)
	public String join(Member member) throws ClassNotFoundException, SQLException {
		System.out.println(member.toString());
		
		int result = 0;
		String viewpage="";
		
		member.setPwd(this.bCryptPasswordEncoder.encode(member.getPwd()));
		result = service.insertMember(member);
		
		if(result > 0) {
			System.out.println("가입성공");
			viewpage = "redirect:/index.do";
		}else {
			System.out.println("가입실패");
			viewpage = "join.do";
		}
		
		return viewpage; //주의 (website/index.htm
		
	}
	
	//로그인 페이지
	@RequestMapping(value="login.do",method=RequestMethod.GET)
	public String login() {
		//return "join.jsp";
		return "joinus.login"; //폴더명.파일명
	}
	
	//비동기 JSONVIEW
	@RequestMapping(value = "idcheck.do", method = RequestMethod.POST)
	public View idCheck(@RequestParam("userid") String userid, Model model) {
		int result = service.idCheck(userid);
		if (result > 0) {
			System.out.println("아이디 중복");
			model.addAttribute("result", "fail");
		} else {
			System.out.println("아이디 사용가능");
			model.addAttribute("result", "success");
		}
		return jsonview; //{"result":"fail"}
	}
	
	@RequestMapping(value="accessDenied.do",method=RequestMethod.GET)
	public String Denied() {
		//return "join.jsp";
		return "joinus.accessDenied"; //폴더명.파일명
	}
}
