package ncontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vo.Member;
import dao.MemberDao;
import validate.JoinValidator;

@Controller
@RequestMapping("/joinus/")
public class JoinController {
	
	private MemberDao memberdao;
	@Autowired
	public void setMemberdao(MemberDao memberdao) {
		this.memberdao = memberdao;
	}
	
	//회원가입페이지
	@RequestMapping(value="join.htm" , method=RequestMethod.GET)
	public String join() {
		
		return  "joinus.join"; //"join.jsp"; 
	}
	
	//회원가입처리
	@RequestMapping(value="join.htm" , method=RequestMethod.POST)
	public String join(Member member, BindingResult bindingResult) {
		System.out.println(member.toString());
		new JoinValidator().validate(member, bindingResult);
		
		System.out.println(bindingResult.hasErrors());
		System.out.println(bindingResult.getErrorCount());
		System.out.println(bindingResult.hasGlobalErrors());
		System.out.println(bindingResult.getGlobalErrorCount());
		
		try {
			memberdao.insert(member);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/index.htm";  // /index.htm  
		//주의사항
		//요청 주소 ...아래처럼 ..
		//http://localhost:8090/SpringMVC_Basic06_WebSite_Annotation_JdbcTemplate/index.htm
		//return "redirect:noticeDetail.htm?seq="+n.getSeq();
	}
}











