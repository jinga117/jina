package DBConn.Util;

import java.sql.Connection;
import java.sql.DriverManager;

//ConnectionHelper Ŭ������ ����
/*�Ź� ����̹� �ε�...
        connection ����...
        
 ������ �ϳ��� ���μ������� �ϳ��� ���� �����ϸ� ���ٵ�....
 �ذ� ����� > �����ڿ� �̱���
 */
public class ConnectionSingletonHelper {
	//�ϳ��� ���μ������� �������� ����� �� �ִ� �����ڿ�(static)
	private static Connection conn;
	private ConnectionSingletonHelper() {  }

	public static Connection getConnection( String dsn ) {  //
		//Connection conn = null;
		if( conn != null ){
			return conn;
		}
		
		try {
			if( dsn.equals("mysql")) {
				Class.forName("com.mysql.jdbc.Driver"); //����̹� Ŭ���� �̸� ��� 
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb ","kingsmile","mysql");
			
			} else if( dsn.equals("oracle")) {
				Class.forName("oracle.jdbc.OracleDriver"); //����̹� Ŭ���� �̸� ��� 
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kingsmile","oracle");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return conn;
		}
	}
	
	
}
