package ex04.jdbc;

import java.sql.*;
import java.io.*;
import java.util.*;

public class PropertiesEx {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement  pstmt = null;
		Properties  prop = null;
		FileInputStream  fis = null;
		FileOutputStream  fos = null;
		
		try {
			File profile = new File("jdbc.properties");
			prop = new Properties();
			
			prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
			prop.setProperty("url", "jdbc:oracle:thin:@127.0.0.1:1521:xe");
			prop.setProperty("id", "kingsmile");
			prop.setProperty("pwd", "oracle");
			
			//파일이 없다면 새로 만든다
			if( !profile.exists() ) profile.createNewFile();
			//프로퍼티 파일 읽기 스트림 생성
			fis = new FileInputStream(profile);
			//프로퍼티 파일을 메모리에 올린다(파일을 읽어온다)
			prop.load(new BufferedInputStream(fis));
			
			fos= new FileOutputStream(profile);
			prop.store(fos, "JDBC  Configuration Settings " );	
			
			prop.load(fis);
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String id = prop.getProperty("id");
			String pwd = prop.getProperty("pwd");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pwd);
			String sql = "create table Properties( id varchar(20) )";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			System.out.println("Properties table success!! ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}







