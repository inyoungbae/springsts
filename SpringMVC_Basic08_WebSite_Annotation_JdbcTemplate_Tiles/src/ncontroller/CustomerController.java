package ncontroller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
/*SpringMVC_Basic02_WebSite 컨트롤러 2개 합치는 연습 해봄 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dao.NoticeDao;
import vo.Notice;

@Controller
@RequestMapping("/customer/")
public class CustomerController {

	private NoticeDao noticedao;  
	
	@Autowired
	public void setNoticedao(NoticeDao noticedao) {
		this.noticedao = noticedao;
	}
	
	/*
              메소드의 리턴 타입이 [String]이면 [리턴 값]은 [뷰 이름]으로 사용된다
       View 에 데이터 전달 위해 Model interface 사용
       Model model > 함수의 parameter 사용시 자동 객체 생성  > Spring ....

    */
	
	@RequestMapping("notice.htm")  // 글목록보기
	public String notices(String pg, String f, String q, Model model) {   
		                  //함수를 만들면 Model(Interface) 자동으로 제공 *******
		//public String notices(@RequestParam(value="pg", defaultValue="1") String pg, ) {  ..이런식으로도 할 수 있음

		//default (값이 안들어올 수 잇으니까. default값 처리해주기)
		int page = 1;
		String field = "TITLE";
		String query = "%%";
		
		//조건처리 
		if(pg != null && pg.equals("")) {
			page = Integer.parseInt(pg);
		}
		
		if(f != null && f.equals("")) {
			field = f;
		}
		
		if(q != null && q.equals("")) {
			query = q;
		}
		
		
		//DAO  객체 데이터 받아오기 
		List<Notice> list = null;
		
		try {
			list = noticedao.getNotices(page, field, query);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		model.addAttribute("list", list);    //view까지 전달됨 (forward가 됩니다)
		
		return "customer.notice"; //"notice.jsp";
	}
	
	
	@RequestMapping("noticeDetail.htm")  //글 상세보기 
	public String NoticeDetail(String seq, Model model)  {
		Notice notice = null;
		
		try {
			notice = noticedao.getNotice(seq);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("notice", notice);
		
		return "customer.noticeDetail"; //"noticeDetail.jsp";
	}
	
	
	//글쓰기 화면(noticeReg.htm)
	//@RequestMapping(value="noticeReg.htm", method=RequestMethod.GET)
	@RequestMapping(value="noticeReg.htm", method=RequestMethod.GET)
	public String writeForm() {

		return "customer.noticeReg"; //"/customer/noticeReg.jsp";
	}
	
	
	//글쓰기 처리(noticeReg.htm)(파일업로드)
	//@RequestMapping(value="noticeReg.htm", method=RequestMethod.POST)
	//return "redirect:notice.htm"
	@RequestMapping(value="noticeReg.htm", method=RequestMethod.POST)
	public String writeSubmit(Notice notice, HttpServletRequest request) throws IOException {  
		
		//Notice DTO
		//private List<CommonsMultipartFile> files;
		//files[0] = new CommonsMultipartFile();  
		//files[1] = new CommonsMultipartFile();
		
		List<CommonsMultipartFile> imagefile = notice.getFiles();
		List<String> filenames = new ArrayList<String>();  // 파일명만 따로 관리 
		
		if(imagefile != null && imagefile.size() > 0) {  //최소 1개의 업로드가 있다면 
			for(CommonsMultipartFile multifile : imagefile) {
				String filename = multifile.getOriginalFilename();
				String path = request.getServletContext().getRealPath("/customer/upload");
				
				String fpath = path +"\\" + filename;
				
				if(!filename.equals("")) { //실 파일 업로드는 여기서 
					FileOutputStream fs = new FileOutputStream(fpath);
					fs.write(multifile.getBytes());
					fs.close();
				}
				filenames.add(filename); // 파일명을 별도관리 (DB insert)
				
			}
			
		}
		notice.setFileSrc(filenames.get(0));
		notice.setFileSrc2(filenames.get(1));

		
		try {
			noticedao.insert(notice);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:notice.htm";  //들어오는 주소이기때문에 바꾸면 안돼요!!!!!!!!! 
	}

	
	//글삭제하기 (noticeDel.htm) : parameter로 seq받아야함  
	//hint) location.href 
	//return "redirect:notice.htm"   --이거안쓰면 f5누르고 계속 글쓰기 나옴 
	@RequestMapping("noticeDel.htm")
	public String writeDelete(String seq) { 
		
		try {
			noticedao.delete(seq);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:notice.htm";  ////들어오는 주소이기때문에 바꾸면 안돼요!!!!!!!!! 
	}
	
	
	//글수정하기(화면처리 : select *from where seq=?) : GET : parameter로 seq받아야함 
	//noticedao.getNotice(seq)
	//parameter로 Model model 받기 > 화면에 데이터 출력 > noticeEdit.jsp
	@RequestMapping(value="noticeEdit.htm", method=RequestMethod.GET)
	public String writeUpdateForm(String seq, Model model) { 
		Notice notice = null;
		try {
			notice = noticedao.getNotice(seq);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		model.addAttribute("notice", notice);
		
		return "customer.noticeEdit"; //"noticeEdit.jsp";
		
	}
	
	//글수정하기 (처리단/ 실업데이트 updata.. where seq=?) : POST
	//parameter Notice notice >> insert 동일 >> 현재는 무조건 파일 첨부하는 형태로
	//return "redirect:noticeDetail.htm?seq="+notice.getSeq();
	@RequestMapping(value="noticeEdit.htm", method=RequestMethod.POST)
	public String writeUpdate(Notice notice, HttpServletRequest request) throws IOException {
		
		List<CommonsMultipartFile> imagefile = notice.getFiles();
		List<String> filenames = new ArrayList<String>();  // 파일명만 따로 관리 
		
		if(imagefile != null && imagefile.size() > 0) {  //최소 1개의 업로드가 있다면 
			for(CommonsMultipartFile multifile : imagefile) {
				String filename = multifile.getOriginalFilename();
				String path = request.getServletContext().getRealPath("/customer/upload");
				
				String fpath = path +"\\" + filename;
				
				if(!filename.equals("")) { //실 파일 업로드는 여기서 
					FileOutputStream fs = new FileOutputStream(fpath);
					fs.write(multifile.getBytes());
					fs.close();
				}
				filenames.add(filename); // 파일명을 별도관리 (DB insert)
				
			}
			
		}
		notice.setFileSrc(filenames.get(0));
		notice.setFileSrc(filenames.get(1));
		


	
		System.out.println("뭘까?" + notice.getSeq());
		return "redirect:noticeDetail.htm?seq="+notice.getSeq();
	}
	
}
