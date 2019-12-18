package dao;

import java.sql.SQLException;
import vo.Member;

public interface MemberDao {
	
	//회원정보 얻기
	public Member getMember(String uid) throws ClassNotFoundException, SQLException;
	
	//회원가입
	public int insertMember(Member member) throws ClassNotFoundException, SQLException;

	 //회원수정하기 
	public int updateMember(Member member) throws ClassNotFoundException, SQLException;
	
	//로그인 체크하기 
	public int loginCheck(String username, String password)throws ClassNotFoundException, SQLException;
	
	//회원아이디 검증하기 - 비동기

	public int idCheck(String userid) throws ClassNotFoundException, SQLException;
	
}
