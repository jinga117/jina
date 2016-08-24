package ex01.jdbc.insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SelectMain {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {	// 예외 위임
		// 1. Driver load
		Class.forName("oracle.jdbc.OracleDriver");
		System.out.println("드라이버 로드 성공!");
		
		// 2. Connection & open
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:xe", "happyjina", "oracle");
		System.out.println("연결 성공했습니다.");
		
	}
}
