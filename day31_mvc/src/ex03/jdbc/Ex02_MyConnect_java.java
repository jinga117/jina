package ex03.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Ex02_MyConnect_java {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
				//1. 드라이버 등록
			   Class.forName("com.mysql.jdbc.Driver"); //드라이버 클래스 이름 명시 
			   System.out.println("My SQL 드라이버 로드 완료");
			   
			   //2. 연결생성(ConnectionString)
			   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb","kingsmile","mysql");
			   
			   //3.명령생성 (CRUD)
			   stmt = conn.createStatement();
			   String sql = "select empno, ename , job  from emp";
			   
			   //4.명령실행(mysql 에게 명령 구문 실행)
			    rs = stmt.executeQuery(sql);
			   
			   //5.명령처리
			   while(rs.next()){
				   System.out.printf("[%d][%s][%s]\n", 
						                 rs.getInt(1) , rs.getString(2) , rs.getString(3) );
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
