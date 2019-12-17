package ncontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.MemberDao;
import vo.Member;

@Controller
@RequestMapping("/joinus/")
public class JoinController {

	private MemberDao memberdao;

	@Autowired
	public void setMemberdao(MemberDao memberdao) {
		this.memberdao = memberdao;
	}
	
	
	//회원가입 페이지(화면단)
	@RequestMapping(value="join.htm", method=RequestMethod.GET)
	public String join() {
		return "joinus.join"; //"join.jsp";
	}
	
	
	//회원가입 처리 (처리단)
	@RequestMapping(value="join.htm", method=RequestMethod.POST)
	public String join(Member member) {   //오버로딩 함수로 써봤어요!    & dto를 parameter로 받으려면 폼에서 넘어오는 name값이랑 dto 이름이 같아야함!
		System.out.println(member.toString());  //클라이언트가 넘긴 값이 잘 나오는지 toString으로 확인해보는게 중요! (나를믿지말기)
		
		try {
			memberdao.insert(member);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/index.htm"; // "redirect:/index.htm" 다른 폴더 내에 있기때문에 ★/★를 붙여줘야 함 (joinus폴더안에 묶여있기때문에)
	}
	
	   //"redirect:noticeDetail.htm?seq="+notice.getSeq();와의 차이점 (이건 같은 폴더내에 있기때문에 '/' 안해줘도 됨)  
	
}
