package ex04.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ConnectionUtil {
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private String id = "kingsmile", pwd = "oracle";

	Scanner sc = new Scanner(System.in);
	PreparedStatement pstmt = null;
	public Connection conn = null;
	ResultSet rs = null;
	String sql = null;

	public void connect() {  //연결
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // connect() end

	public void close() {  //닫기
		try {
			if (rs != null)				rs.close();
			if (pstmt != null)		pstmt.close();
			if (conn != null)			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // close()  end
	
	public static void menu() {
		System.out.println("\n\n-=-=-=-=- JDBC Query -=-=-=-=-");
		System.out.println("\t 1. 레코드 삽입 ");
		System.out.println("\t 2. 레코드 수정 ");
		System.out.println("\t 3. 전체보기 ");
		System.out.println("\t 0. 프로그램 종료 ");
		System.out.print("원하는 메뉴 숫자 입력 : ");
	} //menu() end
	
	public void select() {
		try {
			pstmt = conn.prepareStatement("select * from dept2");
			rs = pstmt.executeQuery();
			System.out.println("\n\n DCODE \t DNAME \t PDEPT \t AREA ");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t\t ");
				System.out.print(rs.getString(2) + "\t\t");
				System.out.print(rs.getString(3) + "\t\t");
				System.out.print(rs.getString(4) + "\n");
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		}
	} //select end

	public void update() {
		System.out.print("수정할 Dcode? : ");		String dcode = sc.next();
		System.out.print("Dcode 입력 : ");			String dcode2 = sc.next();
		System.out.print("Dname 입력 : ");			String dname = sc.next();
		System.out.print("Pdept 입력 : ");				String pdept = sc.next();
		System.out.print("Area 입력 : ");				String area = sc.next();

		try {
			sql = "update dept2 set dcode = ? , dname =?,  pdept = ?, area = ? where dcode = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dcode2);
			pstmt.setString(2, dname);
			pstmt.setString(3, pdept);
			pstmt.setString(4, area);
			pstmt.setString(5, dcode);
			int result = pstmt.executeUpdate(); //
			System.out.println(result + " 데이터 수정 성공~!");
			// pstmt.close();
			// pstmt = null;
			conn.commit();

		} catch (Exception e3) {
			e3.printStackTrace();
		}
	} // update end

	public void insert() {
		System.out.print("Dcode : ");		String dcode = sc.next();
		System.out.print("Dname : ");		String dname = sc.next();
		System.out.print("Pdept : ");		String pdept = sc.next();
		System.out.print("Area : ");			String area = sc.next();

		try {
			sql = "insert into dept2 values(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dcode);
			pstmt.setString(2, dname);
			pstmt.setString(3, pdept);
			pstmt.setString(4, area);
			int result = pstmt.executeUpdate();  //
			System.out.println(result + "데이터가 추가되었습니다");

			sql = "select * from dept2";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(); //
			System.out.println("\n\n DCODE \t DNAME \t PDEPT \t AREA ");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t\t ");
				System.out.print(rs.getString(2) + "\t\t");
				System.out.print(rs.getString(3) + "\t\t");
				System.out.print(rs.getString(4) + "\n");
			}
			conn.commit();
			pstmt = null;
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	} // insert end
}
