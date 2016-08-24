package ex03.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Ex03_MySql_Select {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
				//1. 드라이버 등록
			   Class.forName("com.mysql.jdbc.Driver"); //드라이버 클래스 이름 명시 
			   System.out.println("My SQL 드라이버 로드 완료");
			   
			   //2. 연결생성(ConnectionString)
			   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb","sampleUser","1004");
			   
			   //3.명령생성 (CRUD)
			   stmt = conn.createStatement();
			   String sql = "select empno, ename , job  from emp where empno=1004";
			   
			   //4.명령실행(mysql 에게 명령 구문 실행)
			    rs = stmt.executeQuery(sql);
			   
			   //5.명령처리
			   // 반환된 row 가 1건 이상인 경우
			   // if(rs.next()){ do{}while(rs.next())}
			   //while(rs.next()){  
			   //   System.out.printf("[%d][%s][%s]\n", 
			   //			                 rs.getInt(1) , rs.getString(2) , rs.getString(3) );
			   //}
			   //***************반환된 row 가 1건인 경우 처리*****************************************
			    if(rs.next()){
			    	 System.out.printf("[%d][%s][%s]\n", 
			  			  			                 rs.getInt(1) , rs.getString(2) , rs.getString(3) );
			    }else{
			    	 System.out.println("반환된 행이 없습니다");
			    }
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			
		}finally{
			//6.자원해제
			//DB연결 종료
			if(rs != null){try{rs.close();}catch(Exception e){}}
			if(stmt != null){try{stmt.close();}catch(Exception e){}}
			if(conn != null){try{conn.close();}catch(Exception e){}}
		}
		
	}

}