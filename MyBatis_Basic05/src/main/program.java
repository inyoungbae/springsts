package main;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.bit.dto.User;
import kr.or.bit.service.SqlMapClient;

public class program {
	public static void main(String[] args) {
		//method scope
	    SqlSessionFactory sqlsession=SqlMapClient.getSqlSession(); //팩토리로부터 session객체를 얻어냄 
	    
	    SqlSession session= sqlsession.openSession();//메인도 하나의 쓰레드 이기때문에 지금 session객체는 쓰레드안에 안전하게 있음. 
	    User user = (User)session.selectOne("Emp.getone", "ALLEN"); //Emp라는 네임스페이스를 가지는 getone을 호출할겁니다 / selectOne 결과 하나 가져와라 
	    System.out.println(user.getEmpno());
	    
	    session.close(); //꼭 닫아줘야함 
	    
	    //클라이언트가 요청했을때 session받고, session없애세요.!!!
	    

	}

}



