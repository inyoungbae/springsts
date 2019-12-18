package ncontroller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import service.MemberService;
import vo.Member;

@Controller
@RequestMapping("/joinus/")
public class MemberController {

	@Autowired
	private MemberService service;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = "memberConfirm.htm", method = RequestMethod.GET)
	public String memberConfirm() {
		System.out.println("in");
		return "joinus.memberConfirm";
	};

	@RequestMapping(value = "memberConfirm.htm", method = RequestMethod.POST)
	public String memberConfirm(@RequestParam("password") String rawPassword, Principal principal) {
		System.out.println("여기는 확인컨트롤러 " + rawPassword +"/" +  principal);
		String viewpage = "";
		Member member = service.getMember(principal.getName());

		String encodedPassword = member.getPwd();

		System.out.println("rowPassword : " + rawPassword); // 사용자가 입력한 값
		System.out.println("encodepassword : " + encodedPassword); // db에서 가져온 값

		Boolean result = bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
		System.out.println("결과나오니?" + result);
		if (result) {
			viewpage = "redirect:memberupdate.htm";
		} else {
			viewpage = "redirect:memberConfirm.htm";
		}
		return viewpage;
	};

	// 회원수정
	@RequestMapping(value = "memberupdate.htm", method = RequestMethod.GET)
	public String memberUpdate(Model model, Principal principal) {
		Member member = service.getMember(principal.getName());
		model.addAttribute("member", member);
		return "joinus.memberEdit";
	};

	@RequestMapping(value = "memberupdate.htm", method = RequestMethod.POST)
	public String memberUpdate(Member member) {
		member.setPwd(bCryptPasswordEncoder.encode(member.getPwd()));
		int result = service.updateMember(member);
		String viewpage = "";
		if (result > 0) {
			System.out.println("컨트롤러 수정성공");
		} else {
			System.out.println("컨트롤러 수정실패");
		}
		return "redirect:/index.htm";
	}
	

}
