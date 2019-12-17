package inyoung;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
@Controller
public class MemberController {
	
	@RequestMapping("/memberForm")
	public String memberForm() {
		return "member/memberInput";
	}
	
	/* 폼 유효성 검사 1 */
	@RequestMapping(value="/memberSave1", method = RequestMethod.POST)
	public String memberSave1(@ModelAttribute("member") Member member, BindingResult bindingReuslt) {
		String resultPage = "member/memberOk";
		
		//Validator 생성
		MemberValidator mValidator = new MemberValidator();
		mValidator.validate(member, bindingReuslt);
		
		//오류여부 확인
		
		if(bindingReuslt.hasErrors()) {
			resultPage = "member/memberInput";
		}
		return resultPage;
	}
	
	/* 폼 유효성 검사 2 */
	@RequestMapping(value="/memgerSave2", method=RequestMethod.POST) 
	public String memberSave2(@ModelAttribute("member") @Valid Member member, BindingResult bindingResult) {
		String resultPage = "member/memberOk";
		
		return resultPage;
	}
	
	  @InitBinder
	    protected void initBinder(WebDataBinder binder) {
	        binder.setValidator(new MemberValidator());
	    }
	
}

