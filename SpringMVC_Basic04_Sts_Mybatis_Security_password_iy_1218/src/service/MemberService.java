package service;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.MemberDao;
import vo.Member;

@Service
public class MemberService {

	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}	

	
		//회원등록
		public int insertMember(Member member) {
			MemberDao dao = sqlsession.getMapper(MemberDao.class);
			int result = 0;
			try {			
				result = dao.insertMember(member);
				if(result >0) {
					System.out.println("서비스회원가입성공");
				}else {
					System.out.println("서비스회원가입실패");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
	};
		
		//회원수정위한 불러오기 
		public Member getMember(String uid) {
			MemberDao dao = sqlsession.getMapper(MemberDao.class);
			Member member = null;
			try {
				member = dao.getMember(uid);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return member;
		};
		
		public int updateMember(Member member) {
			MemberDao dao = sqlsession.getMapper(MemberDao.class);
			int result = 0;
			try {
				result = dao.updateMember(member);
				if(result > 0){
					System.out.println("서비스단 회원 업데이트 성공");
				}else{
					System.out.println("서비스단 회원 업데이트 실패");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;

		};
		
		//로그인 체크하기 
		public int loginCheck(String username, String password) {
			MemberDao dao = sqlsession.getMapper(MemberDao.class);
			int result = 0;
			try {
				result = dao.loginCheck(username, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			return result;
		};
		
		//id체크하기 
		public int idCheck(String userid) {
			MemberDao dao = sqlsession.getMapper(MemberDao.class);
			int result = 0;
			try {
				result = dao.idCheck(userid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		
}
