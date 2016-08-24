package ex02.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JdbcDept2 {

	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "happyjina", pwd = "oracle";

	Scanner sc = new Scanner(System.in);
	PreparedStatement pstmt = null;
	public Connection conn = null;
	ResultSet rs = null;
	String sql = null;

	public void connect() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (rs != null)			rs.close();
			if (pstmt != null)	pstmt.close();
			if (conn != null)		conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void menu() {

	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		JdbcDept2 jdb = new JdbcDept2();
		jdb.connect(); // 연결 됨...
	
		while (true) {
			System.out.println("\n\n-=-=-=-=- JDBC Query -=-=-=-=-");
			System.out.println("\t 1. 삽입 ");
			System.out.println("\t 2. 수정 ");
			System.out.println("\t 3. 검색 ");
			System.out.println("\t 0. 종료 ");
			System.out.print("숫자 입력 : ");

			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				jdb.close();
				System.exit(0);
				break;
			case 1:
				jdb.insert();
				break;
			case 2:
				jdb.update();
				break;
			case 3:
				jdb.select();
				break;
			default:
				System.out.println("==메뉴에 숫자 입력하세요== ");
				break;
			} // switch end
		} // while end
	}

	public void select() {
		try {
			sql = "select * from dept2";
			pstmt = conn.prepareStatement(sql);
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
	}

	public void update() {
		System.out.print("수정할 Dcode? : ");
		String dcode = sc.next();
		System.out.print("Dcode 입력 : ");
		String dcode2 = sc.next();
		System.out.print("Dname 입력 : ");
		String dname = sc.next();
		System.out.print("Pdept 입력 : ");
		String pdept = sc.next();
		System.out.print("Area 입력 : ");
		String area = sc.next();

		try {
			sql = "update dept2 set dcode = ? , dname =?,  pdept = ?, area = ? where dcode = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dcode2);
			pstmt.setString(2, dname);
			pstmt.setString(3, pdept);
			pstmt.setString(4, area);
			pstmt.setString(5, dcode);
			int result = pstmt.executeUpdate();
			System.out.println(result + " 데이터 수정 성공~!");
			// pstmt.close();
			// pstmt = null;
			conn.commit();

		} catch (Exception e3) {
			e3.printStackTrace();
		}
	}

	public void insert() {
		System.out.print("Dcode : ");
		String dcode = sc.next();
		System.out.print("Dname : ");
		String dname = sc.next();
		System.out.print("Pdept : ");
		String pdept = sc.next();
		System.out.print("Area : ");
		String area = sc.next();

		try {
			sql = "insert into dept2 values(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dcode);
			pstmt.setString(2, dname);
			pstmt.setString(3, pdept);
			pstmt.setString(4, area);
			int result = pstmt.executeUpdate();
			System.out.println(result + "데이터가 추가되었습니다");

			sql = "select * from dept2";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
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
		}
	}
}
