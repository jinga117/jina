package ex01.jdbc.insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MainEntry {
	public static void main(String[] args) throws Exception {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user ="happyjina";   	String pwd = "oracle";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(url, user, pwd);
		Statement stmt = conn.createStatement();
		// INSERT INTO gift(gno, gname, g_start, g_end)  VALUES(100, '����', 100, 200);
		String sql = "INSERT INTO gift(gno, gname, g_start, g_end)  VALUES( "
								+ args[0] +",'" + args[1] + "'," + args[2] + ", " + args[3] + ")";
		
		int result = stmt.executeUpdate(sql);  // ��ȯ ���� ���� ��� - insert, update, delete
		System.out.println(result + " ������ �߰� ����");
		
		conn.commit();
		conn.close();		
	}
}
