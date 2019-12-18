package ncontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import vo.Member;
import dao.MemberDao;

@Controller
@RequestMapping("/joinus/")
public class JoinController {
	

	@Autowired
	private service.MemberService service;
	

	///////////////////////////////////////////////////////
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    /////////////////////////////////////////////////////// 
	
	
	//회원가입페이지
	@RequestMapping(value="join.htm" , method=RequestMethod.GET)
	public String join() {
		
		return  "joinus.join"; //"join.jsp"; 
	}
	
	//회원가입처리
	@RequestMapping(value="join.htm" , method=RequestMethod.POST)
	public String join(Member member) {
		int result = 0;
		String viewpage ="";
		member.setPwd(this.bCryptPasswordEncoder.encode(member.getPwd()));

		result	= service.insertMember(member);		
		
		if(result > 0) {
			System.out.println("컨트롤러 회원가입성공");
			viewpage = "redirect:/index.htm";
		}else {
			viewpage = "joinus.join";
		}
		return viewpage;  // /index.htm  
	}
	
	@RequestMapping(value="login.htm", method=RequestMethod.GET)
	public String login() {
		return "joinus.login"; //Tiles 적용 
	}
	
	//id체크하기 
	@RequestMapping(value="ajax_idcheck.htm", method=RequestMethod.POST)
	public @ResponseBody String idcheck(String userid) {
		System.out.println("유저아이디 넘어오니? ajax : " + userid);
		int result = service.idCheck(userid);
		System.out.println("결과는?   " + result + "   true사용가능/false 사용불가능");
		String data = "";
		if(result > 0) {
			System.out.println("있는 아이디입니다.");
			data = "false";
		}else {
			System.out.println("없는 아이디입니다.");
			data = "true";
		}
		return data;
	}

}











