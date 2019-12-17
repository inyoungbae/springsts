package com.kosta.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kosta.Config.SqlSessionFactoryService;
import com.kosta.DTO.GuestDTO;

public class GuestDAO {
	//CRUD
	private SqlSessionFactory factory;
	public GuestDAO(){
		factory = SqlSessionFactoryService.getSqlSession();
	}
	
	public int insert(GuestDTO guestobj){   //session객체를 메소드 내에서 만들고, 닫아버림
		SqlSession session = null;
		try{
			//factory.openSession(true); autocommit          //true 넣으면 autocommit        
			//factory.openSession(); app (commit ,rollback)  //()를 비워놓으면 commit, rollback을 제어해야함
					
			session = factory.openSession();//작업을 method 단위   //factory.openSession()하면 commit/ rollback제어 
			
			int result = session.insert("GUEST.insertGuest",guestobj);
			session.commit();   //commit 설정
			return result;
		}catch(Exception e){
			session.rollback(); //rollback 설정(롤백은 예외에서)
			return 0;
		}finally{
			if(session != null){session.close();}
		}
		
	}
	public int delete(int num){
		SqlSession session = null;
		try{
			//factory.openSession(true); autocommit
			//factory.openSession(); app (commit ,rollback)
			
			session = factory.openSession(true);//작업을 method 단위
			int result = session.delete("GUEST.deleteGuest",num);
			//session.commit();
			return result;
		}catch(Exception e){
			//session.rollback();
			return 0;
		}finally{
			if(session != null){session.close();}
		}
		
	}
	public int update(GuestDTO guestObj){
		//코드 완성하세요 ******************************
		SqlSession session=null;
		try{
			session = factory.openSession();
			int rowcount= session.update("GUEST.updateGuest", guestObj);
			session.commit();
			return rowcount;
		}catch(Exception e){
			return 0;
		}
		finally{
			if(session != null){session.close();}
		}
	}
	//전체 조회
	public List<GuestDTO> getList(){
		//코드 완성하세요 ******************************
		SqlSession session=null;
		try{
			session = factory.openSession();
			//Mybatis > selectList (여러개 Row)
			return session.selectList("GUEST.getAllList");
		}catch(Exception e){
			return null;
		}
		finally{
			if(session != null){session.close();}
		}
	}
	//게시글 상세조회
	public GuestDTO DetailList(int no){
		//코드 완성하세요
		SqlSession session=null;
		try{
			session = factory.openSession();
			//한건 (하나의 ROW) >> selectOne
			GuestDTO dto = session.selectOne("GUEST.getDetailByno",no);
			return dto;
		}catch(Exception e){
			return null;
		}
		finally{
			if(session != null){session.close();}
		}
	}
	
	//전체 검색 조회_1
	public List<GuestDTO> getSearchList(Map<String, String> map){
		SqlSession session=null;
		try{
			 /*for(Map.Entry<String, String> m : map.entrySet()){
				 System.out.println(m.getKey() + "/" + m.getValue() +"-");
			 }*/
			 session = factory.openSession();
			 return session.selectList("GUEST.selectSearch" ,map);
		}catch(Exception e){
			 return null;
		}finally{
			if(session != null){session.close();}
		}
	}
	
	//전체 검색 조회_2
	public List<GuestDTO> getSearchList2(Map<String, String> map){
		SqlSession session=null;
		try{
			 for(Map.Entry<String, String> m : map.entrySet()){
				 System.out.println(m.getKey() + "/" + m.getValue() +"-");
			 }
			 session = factory.openSession();
			 return session.selectList("GUEST.selectSearch2",map);
		}catch(Exception e){
			 return null;
		}finally{
			if(session != null){session.close();}
		}
	}
}







