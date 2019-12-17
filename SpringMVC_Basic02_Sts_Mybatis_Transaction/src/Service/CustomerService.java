package Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dao.NoticeDao;
import vo.Notice;

@Service
public class CustomerService {

	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	// 글 목록보기
	public List<Notice> notices(String pg, String f, String q) {

		// default
		int page = 1;
		String field = "TITLE";
		String query = "%%";

		// 조건처리
		if (pg != null && !pg.equals("")) {
			page = Integer.parseInt(pg);
		}

		if (f != null && !f.equals("")) {
			field = f;
		}

		if (q != null && !q.equals("")) {
			query = q;
		}

		// DAO 데이터 받아오기
		List<Notice> list = null;
		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			list = noticedao.getNotices(page, field, query);
			// list = noticedao.getNotices(page, field, query);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 글 상세보기 서비스
	public Notice noticeDetail(String seq) {
		Notice notice = null;
		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			notice = noticedao.getNotice(seq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return notice;
	}

	// 글쓰기 서비스
	@Transactional //<tx:annotation-driven transaction-manager="transactionManager"/> 이게 필요해요.
	public String noticeReg(Notice n, HttpServletRequest request)
			throws IOException, ClassNotFoundException, SQLException {

		List<CommonsMultipartFile> files = n.getFiles();
		List<String> filenames = new ArrayList<String>(); // 파일명

		if (files != null && files.size() > 0) { // 1 개라도 업로드
			for (CommonsMultipartFile multifile : files) {
				String filename = multifile.getOriginalFilename();
				String path = request.getServletContext().getRealPath("/customer/upload");

				String fpath = path + "\\" + filename;

				if (!filename.equals("")) { // 실 파일 업로드
					FileOutputStream fs = new FileOutputStream(fpath);
					fs.write(multifile.getBytes());
					fs.close();
				}
				filenames.add(filename); // 파일명을 별도로 ... DB insert
			}
		}

		// 실 DB insert
		n.setFileSrc(filenames.get(0));
		n.setFileSrc2(filenames.get(1));

		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);

		// noticedao.insert(n); 기존 방법
		
		// 트랜잭션 적용하기 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		/*
		  논리적인 단위 
		 [ 시작 > 글쓰기 insert > 회원 point증가  > 끝  ]
		 
		 
		 @Transactional의 기본설정 
		   : 하다가 예외가 발생하면 기본 'ROLLBACK' 
		   
		   트랜잭션은 CATCH로 빠진지 모르기때문에 MANAGER에게 예외가 발생했다는 사실을 알려줘야함 
		   -> catch 부분에 throw e; 사용하면 transaction manager에게 예외발생사실을 알림 (그럼 manager가 Rollback처리함)
		 */

		try {
			noticedao.insert(n);
			noticedao.updateOfMemberPoint("iy");
			System.out.println("notice쪽에는 : insert, member쪽에는  : update");
		} catch (Exception e) {
			System.out.println("Trans 예외 발생 : " + e.getMessage());
			throw e; // 이렇게 쓰면 transaction manager가 예외가 발생했다는 사실을 인지함. 인지하고 ROLLBACK처리함 
		}

		return "redirect:notice.htm"; // 문자열 리턴
	}

	// 글 삭제하기
	public String noticeDel(String seq) throws ClassNotFoundException, SQLException {
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		noticedao.delete(seq);
		return "redirect:notice.htm";
	}

	// 글 수정하기 (화면 처리)
	public Notice noticeEdit(String seq) throws ClassNotFoundException, SQLException {
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		Notice notice = noticedao.getNotice(seq);
		return notice;
	}

	// 글 수정하기 (처리)
	public String noticeEdit(Notice n, HttpServletRequest request)
			throws IOException, ClassNotFoundException, SQLException {

		List<CommonsMultipartFile> files = n.getFiles();
		List<String> filenames = new ArrayList<String>(); // 파일명

		if (files != null && files.size() > 0) { // 1 개라도 업로드
			for (CommonsMultipartFile multifile : files) {
				String filename = multifile.getOriginalFilename();
				String path = request.getServletContext().getRealPath("/customer/upload");

				String fpath = path + "\\" + filename;

				if (!filename.equals("")) { // 실 파일 업로드
					FileOutputStream fs = new FileOutputStream(fpath);
					fs.write(multifile.getBytes());
					fs.close();
				}
				filenames.add(filename); // 파일명을 별도로 ... DB insert
			}
		}

		n.setFileSrc(filenames.get(0));
		n.setFileSrc2(filenames.get(1));

		// DB 파일명 저장
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		noticedao.update(n);
		return "redirect:noticeDetail.htm?seq=" + n.getSeq();
	}

	// 파일 다운로드
	public void download(String p, String f, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String fname = new String(f.getBytes("euc-kr"), "8859_1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");

		String fullpath = request.getServletContext().getRealPath("/customer/" + p + "/" + f);
		System.out.println(fullpath);
		FileInputStream fin = new FileInputStream(fullpath);

		ServletOutputStream sout = response.getOutputStream();
		byte[] buf = new byte[1024]; // 전체를 다읽지 않고 1204byte씩 읽어서
		int size = 0;
		while ((size = fin.read(buf, 0, buf.length)) != -1) {
			sout.write(buf, 0, size);
		}
		fin.close();
		sout.close();
	}

}
