package owl.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

import owl.dto.Member;
import owl.service.KaKaoService;
import owl.service.NaverService;



@Controller
public class LoginController {

	@Autowired
	private KaKaoService  kaKaoService;
	
	@Autowired
	private NaverService  naverService;
	 
	@RequestMapping(value = "Login.do", method = RequestMethod.GET)
	public String showView() {
		return "login/modal/login";
	}
	
	@RequestMapping(value = "Login.do", method = RequestMethod.POST)
	public String login() {
		
		System.out.println("login");
		return "login/main";
	}
	
	@RequestMapping(value = "kakaoLogin.do", produces = "application/json")
	public String kakaoLogin(@RequestParam("code") String code , HttpSession session) throws Exception{
		System.out.println("kakaoLoign");		
		System.out.println(naverService.getAuthorizationUrl(session));
		String accessToken=kaKaoService.getAccessToken(code);
		Member member = kaKaoService.getUserInfo(accessToken);
		
		// id 체크 후 db에 없으면 insert
		
		session.setAttribute("memberName", member.getName());
		return "login/main";
	}
	
	//kakaoLoginCallback.do
	@RequestMapping("naverLogin.do")
	public String naverLogin(@RequestParam String code, @RequestParam String state, HttpSession session,Model model)
			throws IOException {
		System.out.println("naverLogin");
		System.out.println("naverLogin : "+code);
		System.out.println("naverLogin : "+state);
		OAuth2AccessToken oauthToken;
        oauthToken = naverService.getAccessToken(session, code, state);
        //로그인 사용자 정보를 읽어온다.
	    String apiResult = naverService.getUserProfile(oauthToken);
	    System.out.println("apiResult"+apiResult);
		model.addAttribute("result", apiResult);

        /* 네이버 로그인 성공 페이지 View 호출 */
		return "login/main";
	}
}
