package ex03.jdbc;

/*
 * Java 에서 MySql 연동하기
 * 1.  mysql 사이트에서 JDBC 드라이버 다운 받기
 * 2. mysql-connector-java-5.1.26-bin.jar 파일 저장경로 확인
 * 3. lib 추가를 통해 참조 설정
 * 4. MySQL - com.mysql.jdbc.Driver
      오라클 - oracle.jdbc.driver.OracleDriver
                 oracle.jdbc.OracleDriver
      MS SQL 서버 - com.microsoft.sqlserver.jdbc.SQLServerDriver
   5. MySQL접속 정보 : DB명(sampledb) port:3306  계정(SampleUser) 비번(1004)
   6. Java API : java.sql.* 하위 자원들 사용
   
   기타
   주요 DBMS의 JDBC URL 구성
	MySQL : jdbc:mysql://HOST[:PORT]/DBNAME[?param=value&param1=value2&...]
	Oracle: jdbc:oracle:thin:@HOST:PORT:SID
	MS SQL : jdbc:sqlserver://HOST[:PORT];databaseName=DB

   
 */
//JDBC 무조건 따라하기
//1. 연결하고자 하는 데이터베이스 드라이버 로딩
//2. 드라이버 로딩
//3. 연결생성 (어떤 DB에서 , 어떤 port  , 어떤 사용자 , 어떤 비번 접속 (Connection String)
//4. 명령생성 (CRUD) 
//5. 명령실행 (execute ....  select (resultset) , insert , update , delete ...
//6. 명령처리 (조회 , 출력 ,,,,,)
//7. 자원해제 (연결 종료)

import java.sql.DriverManager; //드라이버
import java.sql.Connection; //연결
import java.sql.Statement; //명령
import java.sql.ResultSet; //결과처리

public class Ex01_OracleConnect {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// 1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver"); // 드라이버 클래스 이름 명시
			System.out.println("Oracle 드라이버 로드 완료");

			// 2. 연결생성(ConnectionString)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "kingsmile", "oracle");

			// 3.명령생성 (CRUD)
			stmt = conn.createStatement();
			String sql = "select empno, ename , job  from emp";

			// 4.명령실행(mysql 에게 명령 구문 실행)
			rs = stmt.executeQuery(sql);

			// 5.명령처리
			while (rs.next()) {
				System.out.printf("[%d][%s][%s]\n", rs.getInt("empno"), rs.getString("ename"), rs.getString("job"));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			// 6.자원해제
			// DB연결 종료
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
