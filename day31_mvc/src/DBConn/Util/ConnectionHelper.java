package DBConn.Util;

import java.sql.*;

/*DB���� ���� �ݺ������� �ڵ� �ذ�
�ٸ� Ŭ�������� �Ʒ� �ڵ� ������ ���� �ʵ��� ����
Class.forName("oracle.jdbc.OracleDriver"); //����̹� Ŭ���� �̸� ���� 
conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","happyjina","oracle");

�̷� ������ ���
ConnectionHelper.getConnection("mssql")  or ("oracle")
dsn ==> data source name
*/
public class ConnectionHelper {
	
	public static Connection getConnection( String dsn ) {  //
		Connection conn = null;
		
		try {
			if( dsn.equals("mysql")) {
				Class.forName("com.mysql.jdbc.Driver"); //����̹� Ŭ���� �̸� ���� 
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb ","kingsmile","mysql");
			
			} else if( dsn.equals("oracle")) {
				Class.forName("oracle.jdbc.OracleDriver"); //����̹� Ŭ���� �̸� ���� 
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kingsmile","oracle");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return conn;
		}
	}
	
	public static Connection getConnection(String dsn, String userid, String pwd) {
Connection conn = null;
		
		try {
			if( dsn.equals("mysql")) {
				Class.forName("com.mysql.jdbc.Driver"); //����̹� Ŭ���� �̸� ���� 
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb", userid, pwd);
			
			} else if( dsn.equals("oracle")) {
				Class.forName("oracle.jdbc.OracleDriver"); //����̹� Ŭ���� �̸� ���� 
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", userid, pwd );
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return conn;
		}
	}
}





