package ex03.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.jdbc.internal.OracleTypes;

/*
 CREATE OR REPLACE PROCEDURE SCOTT.usp_EmpList
(
	p_sal IN number,
  p_cursor OUT SYS_REFCURSOR --APP 사용하기 위한 타입
)
IS
	BEGIN
  			OPEN p_cursor
        FOR
        		 SELECT empno, ename, sal FROM EMP WHERE sal > p_sal;

  END; 
 */
public class Ex07_Oracle_plsql {


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
			   String sql = "{call usp_EmpList(?,?)}"; //******************
			   cstmt = conn.prepareCall(sql); //****************
			   cstmt.setInt(1, 2000);
			   cstmt.registerOutParameter(2, OracleTypes.CURSOR);  //****************
			   
			   
			   //4.명령실행(pl-sql실행)
			   cstmt.execute();
			   System.out.println("실행");
			   //5.명령처리
			   rs =  (ResultSet)cstmt.getObject(2); //************************
			   
			   while(rs.next()){
				   System.out.printf("[%d][%s][%d]\n", 
						                 rs.getInt(1) , rs.getString(2) , rs.getInt(3) );
			     }
			
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
