package ex01.jdbc.insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SelectMain {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {	// ���� ����
		// 1. Driver load
		Class.forName("oracle.jdbc.OracleDriver");
		System.out.println("����̹� �ε� ����!");
		
		// 2. Connection & open
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:xe", "happyjina", "oracle");
		System.out.println("���� �����߽��ϴ�.");
		
	}
}
