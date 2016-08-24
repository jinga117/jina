package ex03.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;

import oracle.jdbc.internal.OracleTypes;

/*
 CREATE OR REPLACE PROCEDURE usp_delete_emp
  (
  	vempno IN emp.empno%TYPE,
    p_outmsg OUT VARCHAR2
   )
   IS
   		BEGIN
      		DELETE FROM EMP WHERE empno=vempno;
          COMMIT;
      	  p_outmsg := 'success';
          EXCEPTION WHEN OTHERS THEN
          p_outmsg := SQLERRM;	
          ROLLBACK;
      END;
 */


public class Ex08_Oracle_plsql_DML {
	public static void main(String[] args) {
		Connection conn = null;
		//Statement stmt = null;
		CallableStatement cstmt = null; //pl-sql 사용******************
		ResultSet rs = null;
		
		try{
				//1. 드라이버 등록
			   Class.forName("oracle.jdbc.OracleDriver"); //드라이버 클래스 이름 명시 
			   System.out.println("Oracle 드라이버 로드 완료");
			   
			   //2. 연결생성(ConnectionString)
			   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kingsmile","oracle");
			   
			   //3.명령생성 (CRUD)
			   //pl-sql 사용시 =>prepareCall
			   //String sql = "{call usp_delete_emp(?,?)}"; //******************
			   String sql = "{call usp_insert_emp(?,?)}"; //******************
			   cstmt = conn.prepareCall(sql); //****************
			   cstmt.setInt(1, 8888);
			   cstmt.registerOutParameter(2, Types.VARCHAR);  //****************
			   
			   
			   //4.명령실행(pl-sql실행)
			   cstmt.execute();
			   System.out.println("실행");
			   //5.명령처리
			   String msg =  cstmt.getString(2); //************************
			   
			   System.out.println("처리결과 : " + msg);
			   
		}catch(Exception e){
			System.out.println(e.getMessage());
			
		}finally{
			//6.자원해제
			//DB연결 종료
			if(rs != null){try{rs.close();}catch(Exception e){}}
			if(cstmt != null){try{cstmt.close();}catch(Exception e){}}
			if(conn != null){try{conn.close();}catch(Exception e){}}
		}
		
	}

}
