package kr.or.bit.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.bit.dao.EmpDao;
import kr.or.bit.dto.Emp;

@Controller
public class RegisterController {

	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	@RequestMapping(value="/Register.do", method = RequestMethod.GET)
	public String register() { //회원가입 화면단
		
		return "/WEB-INF/views/register/Register.jsp";
	}
	
	@RequestMapping(value="/Register.do", method = RequestMethod.POST)
	public String register(Emp emp, Model model, HttpServletRequest request) throws Exception {  //회원가입 처리단 
		System.out.println("왜안오니?");
		emp.setHiredate(new Date());
		
	    String uploadpath = request.getServletContext().getRealPath("upload");
        String imagefilename = emp.getFile().getOriginalFilename();
        String fpath = uploadpath + "\\" + imagefilename;

        if (!imagefilename.equals("")) { // 실 파일 업로드
            FileOutputStream fs = new FileOutputStream(fpath);
            fs.write(emp.getFile().getBytes());
            fs.close();
         }
         emp.setImagefilename(imagefilename);


		  
		EmpDao empdao = sqlsession.getMapper(EmpDao.class);  //연결시킨 인터페이스dao의 정보를 넘기면 됨
		int result = empdao.insertEmp(emp);
		
		String msg = "";
		String url = "";
		if (result>0) {
			msg = "등록 성공! 로그인 페이지로 이동합니다.";
			url = "Login.do";
		} else {
			msg = "등록 실패! 회원 가입 페이지로 재 이동합니다.";
			url = "Register.do";
		}
		
		model.addAttribute("board_msg", msg);
		model.addAttribute("board_url", url);
		
		
		return "/common/redirect.jsp";
	}
}
