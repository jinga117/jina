package ex03.jdbc.create;

import java.sql.*;

public class MainEntry {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String url , user, pwd;
		url = user = pwd = null;
		Connection conn = null;
		Statement stmt = null;
		
		try {
			url = "jdbc:oracle:thin:@localhost:1521:xe";
			user = "happyjina";
			pwd = "oracle";
			
			conn = DriverManager.getConnection(url, user, pwd);
			stmt = conn.createStatement();
			String sql = "CREATE TABLE KOSTATABLE2(NAME VARCHAR(20), AGE NUMBER)";
			stmt.executeUpdate(sql);
			System.out.println("Table create success!!!");
			
			sql = "SELECT * FROM KOSTATABLE";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("name : " + rs.getString(1));
				System.out.println("age : " + rs.getInt(2));
			}
			
			sql = "DROP TABLE KOSTATABLE";
			int result = stmt.executeUpdate(sql);
			System.out.println("drop table : "+ result);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
	}
}
