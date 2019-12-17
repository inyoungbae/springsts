package kr.or.bit.controller;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

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
public class MemberController {
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	@RequestMapping(value="/MemberList.do")  //emplist 가져오는 거 
	public String memberList(HttpServletRequest request) {
		
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);  //연결시킨 인터페이스dao의 정보를 넘기면 됨
			List<Emp> emplist = empdao.getEmps();
			request.setAttribute("emplist", emplist);

		return "/WEB-INF/views/admin/MemberList.jsp";
	}
	
	@RequestMapping(value="/MemberEdit.do", method = RequestMethod.GET)
	public String memberEdit(int empno, Model model) {  //사원 수정 
	//int no = Integer.parseInt(request.getParameter("empno"));
	//EmpDao dao = new EmpDao();
		EmpDao empdao = sqlsession.getMapper(EmpDao.class);
		Emp emp =empdao.getEmpByEmpno(empno);
		
		model.addAttribute("emp", emp);


	return "/WEB-INF/views/admin/MemberEdit.jsp";
	}
	
	@RequestMapping(value="/MemberEdit.do", method = RequestMethod.POST)
	public String memberEditOk(Emp emp, HttpServletRequest request) throws Exception {
	System.out.println("수정하기완료부분 오니?");
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
	 empdao.updateEmp(emp); //굳이?
     
		return "/MemberList.do";
	}
	
	@RequestMapping(value="/MemberDetail.do")
	public String memberDetail(int empno, HttpServletRequest request) {
		EmpDao empdao = sqlsession.getMapper(EmpDao.class);
		Emp emp = empdao.getEmpByEmpno(empno);
		request.setAttribute("emp", emp);

		return "/WEB-INF/views/admin/MemberDetail.jsp";
	}
	
	
	@RequestMapping(value="/MemberDelete.do")
	public String memberDelete(int empno) {
		System.out.println("삭제컨트롤러 오니?"+ empno);
		EmpDao empdao = sqlsession.getMapper(EmpDao.class);
		empdao.deleteEmpByEmpno(empno);
		
		return "redirect:/MemberList.do";
	}
}
