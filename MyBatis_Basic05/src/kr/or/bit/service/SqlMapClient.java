package kr.or.bit.service;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/*
  
나쁜 냄새가 나는 코드  

//리턴하는 객체가 session객체
public class SqlMapClient {   
	private static SqlSession session;   //1. session이 static으로 있어서 공유하게됨 
	
	static {
		String resource = "SqlMapConfig.xml";
		try {
			  Reader reader =  Resources.getResourceAsReader(resource);
			  SqlSessionFactory factory=  new  SqlSessionFactoryBuilder().build(reader);
			  session = factory.openSession();
		}catch (Exception e) {
		}
	}
	public static SqlSession getSqlSession() {
		return session;
	}
}
*/

// 리던하는객체가 factory 객체 
public class SqlMapClient {
	private static SqlSessionFactory sqlsessionfactory; // 팩토리가 공유자원이됨. 팩토리가 생산하는게 session 그 session은 공유되면X 안전하지x 
	static {
		String resource = "SqlMapConfig.xml";
		try {
			 Reader reader = Resources.getResourceAsReader(resource);
			 sqlsessionfactory = new SqlSessionFactoryBuilder().build(reader);
			 
		}catch(Exception e) {
			
		}
 
	}
	 public static SqlSessionFactory getSqlSession(){
		  return sqlsessionfactory;
	  }	
}

//Static변수가 제일먼저 올라가고, 그다음에 static 초기자가 올라감 
// public static SqlSessionFactory getSqlSession(){는 static변수가 private이기때문에 사용하기위해서 static으로(그리고, 이 클래스가 new하지않고 사용하기위해서)

