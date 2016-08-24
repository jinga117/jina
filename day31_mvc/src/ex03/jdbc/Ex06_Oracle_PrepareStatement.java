package ex03.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import DBConn.Util.*;
import DBClose.Util.*;

public class Ex06_Oracle_PrepareStatement {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			 //conn = ConnectionHelper.getConnection("oracle");
			 //싱글톤 패턴을 사용한 연결
			 conn = ConnectionSingletonHelper.getConnection("oracle");
			
			// pstmt = conn.prepareStatement("select empno,ename from emp where empno=?");
			// pstmt.setInt(1, 7788);
			 
			//rs = pstmt.executeQuery();
			// while(rs.next()){
			//	System.out.println("empno : " + rs.getInt(1) + " ename : " + rs.getString(2));
			// }
			 
			 //Insert  처리
			 String sql = "insert into emp(empno,ename,sal) values(?,?,?)";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, 8888);
			 pstmt.setString(2, "nothing");
			 pstmt.setInt(3, 5555);
			 
			 int row =  pstmt.executeUpdate(); // statement 라면  pstmt.executeUpdate(쿼리문);
			 System.out.println("반영된 행의 수 : " + row);
		
		}catch(Exception e){
				e.printStackTrace();
		}finally{
			ConnectionCloseHelper.close(pstmt);
			ConnectionCloseHelper.close(conn);
		}

	}

}
