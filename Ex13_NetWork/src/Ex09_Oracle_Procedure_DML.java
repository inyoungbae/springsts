import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
/*
create table usp_emp
as
    select * from emp;

alter table usp_emp
add constraint pk_usp_emp_empno primary key(empno);

select * from SYS.USER_CONSTRAINTS where table_name='USP_EMP';
 
CREATE OR REPLACE PROCEDURE usp_insert_emp
(
 vempno IN emp.empno%TYPE,
 vename IN emp.ename%TYPE,
 vjob   IN emp.job%TYPE,
 p_outmsg OUT VARCHAR2
 )
 IS
   BEGIN
      INSERT INTO USP_EMP(empno , ename, job) VALUES(vempno ,vename , vjob);
      COMMIT;
        p_outmsg := 'success';
        EXCEPTION WHEN OTHERS THEN
        p_outmsg := SQLERRM;
        ROLLBACK;
    END;
    
  제약에러 확인을 위해서 EMP 제약 추가
  alter table emp
  add constraint pk_emp_empno primary key(empno);
*/
public class Ex09_Oracle_Procedure_DML {

	public static void main(String[] args) {
		 Connection conn = null;
		  //명령객체
		  CallableStatement cstmt= null; //변경
		  try{
		   Class.forName("oracle.jdbc.OracleDriver");
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "bituser", "1004"); 
		      
		   String sql = "{call usp_insert_emp(?,?,?,?)}";
		      cstmt = conn.prepareCall(sql);
		      
		      //usp_insert_emp(?,?) input , output
		      cstmt.setInt(1, 9000);
		      cstmt.setString(2, "hong");
		      cstmt.setString(3, "IT");
		      cstmt.registerOutParameter(4, Types.VARCHAR);
		      
		   cstmt.execute();
		      
		   String Oracle_msg = (String)cstmt.getObject(4); //네번째 ? 표 output
		   
		   System.out.println("Oracle_Msg : " + Oracle_msg);
		   
		   
		  }catch(Exception e){
		   
		  }finally{
		   if(cstmt != null){try{cstmt.close();}catch(Exception e){}};
		   if(conn != null){try{conn.close();}catch(Exception e){}};
		  }
		  

	}

}
