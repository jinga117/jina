package ex02.jdbc;

import java.sql.*;
import java.util.*;

public class JDBCTotal {
	
	static Scanner sc = new Scanner(System.in);
	
	static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static String user ="happyjina";   	static String pwd = "oracle";
	static String driver = "oracle.jdbc.driver.OracleDriver" ;
	
	static Connection conn = null;
	static ResultSet rs = null;
	static Statement  stmt = null;
	
	public static void main(String[] args) throws Exception {
		connect();
		choice();
	}

	public static void connect() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			
			stmt = conn.createStatement();
			conn.setAutoCommit(false);		
		} catch (Exception e) {		e.printStackTrace();		}
	} //connect() end
	
	public void close() {
		try {
			rs.close();			stmt.close();			conn.close();
		} catch (Exception e) {		e.printStackTrace();		}
	}
	
	public static void choice() throws SQLException {
		Gift  gift = new Gift();
		
		while( true ) {
			System.out.println("�� �޴��� �����ϼ���....");
			System.out.println("1.����\t2.����\t3.����\t4.����\t5.�ѹ�\t6.����");
			
			switch( sc.nextInt() ) {
				case 1 :
					select(gift.getClassName());
					break;
				case 2 :
					select(gift.getClassName());
					insert();
					select(gift.getClassName());
					break;
				case 3 :
					select(gift.getClassName());
					update();
					select(gift.getClassName());
					break;
				case 4 :
					select(gift.getClassName());
					delete();
					select(gift.getClassName());
					break;
				case 5 :
					conn.rollback();
					break;
				case 6 :
					System.out.println("�����մϴ�.");
					System.exit(0);
			
			} // switch end			
		} // while end
	} //choice() end - �޴� ���ÿ� ���Ѱ�....

	public  static void select(String ClassName) throws SQLException {
			rs = stmt.executeQuery("select * from " + ClassName );	
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			
			while( rs.next() ) {	
				for (int i = 1; i <= count ; i++ ) {				
					switch( rsmd.getColumnType(i) ) {  //�� Ÿ�Ժ��� ��� �Ѵ�.
							case  Types.NUMERIC :
							case  Types.INTEGER :
								System.out.print( rsmd.getColumnName(i) + " : " + rs.getInt(i) );
								break;
								
							case Types.FLOAT :
								System.out.print( rsmd.getColumnName(i) + " : " + rs.getFloat(i) );
								break;
								
							case Types.DOUBLE :
								System.out.print( rsmd.getColumnName(i) + " : " + rs.getDouble(i) );
								break;
								
							case Types.CHAR :
								System.out.print( rsmd.getColumnName(i) + " : " + rs.getString(i) );
								break;
								
							case Types.DATE :
								System.out.print( rsmd.getColumnName(i) + " : " + rs.getDate(i) );
								break;
								
							default :
								System.out.print( rsmd.getColumnName(i) + " : " + rs.getString(i) );
					} // switch end
					System.out.println();
				} // for end
			} // while end
	} //select() end

	public static void insert() throws SQLException {
		System.out.println("��ǰ��ȣ : ");		int num = sc.nextInt();
		System.out.println("��ǰ�� : ");		String name = sc.next();
		System.out.println("��ǰ���� ���� : ");		int g_s = sc.nextInt();
		System.out.println("��ǰ�ְ� ���� : ");		int g_e = sc.nextInt();
		
		String sql = "insert into gift(gno, gname, g_start, g_end) values(" + num + ", '" 
				+ name + "' ," + g_s + ", " + g_e + ")";
		
		int result = stmt.executeUpdate(sql);
		System.out.println(result + " ������ �߰� ����");
		
	} //insert() end
	
	public static void update() throws SQLException {
		System.out.println("������ ����Ʈ ��ȣ ? ");
		int num = sc.nextInt();
		
		System.out.println("1.��ǰ��ȣ\t2.��ǰ��\t3.�ּҰ���\t4.�ְ�����\t5.�ڷΰ���");
		
		switch( sc.nextInt() ) {
		case 1 :
			System.out.println("������ ��ǰ ��ȣ : ");
			String sqlNo = "update gift set gno = " + sc.nextInt() + "  where gno = " + num ;
			int result_no = stmt.executeUpdate(sqlNo);
			System.out.println(result_no + " ��ǰ��ȣ�� ���� �Ǿ����ϴ�");
			break;
		
		case 2 :
			System.out.println("������ ��ǰ�� : ");
			String sqlName = "update gift set gname = '" + sc.next() + "'  where gno = " + num ;
			int result_name = stmt.executeUpdate(sqlName);
			System.out.println(result_name + " ��ǰ���� ���� �Ǿ����ϴ�");
			break;
			
		case 3 :
			System.out.println("������ ��ǰ �ּ� ���� : ");
			String sql_start = "update gift set g_start = " + sc.nextInt() + "  where gno = " + num ;
			int result_start = stmt.executeUpdate(sql_start);
			System.out.println(result_start + " ���� ���� �Ǿ����ϴ�");
			break;
			
		case 4 :
			System.out.println("������ ��ǰ �ְ� ���� : ");
			String sql_end = "update gift set g_end = " + sc.nextInt() + "  where gno = " + num ;
			int result_end = stmt.executeUpdate(sql_end);
			System.out.println(result_end + " ���� ���� �Ǿ����ϴ�");
			break;
			
		case 5 :
			choice();
			break;
		} //switch end			
	} //update() end
	
	public static void delete() throws SQLException {  //
		System.out.println("��Ͽ��� ������ ��ȣ �����ϼ��� : ");
		int num = sc.nextInt();
		
		String sql = "delete gift where gno = " + num;
		int result = stmt.executeUpdate(sql);
		System.out.println(result + " ������ ���� ����...");
	}// delete() end
}





