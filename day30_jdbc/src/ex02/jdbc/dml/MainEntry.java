package ex02.jdbc.dml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

public class MainEntry {
	public static void main(String[] args) throws Exception {
		int num, gno, g_start, g_end, modiNum;
		num = gno = g_start = g_end = modiNum = 0;
		String gname = null;
		Scanner sc = new Scanner(System.in);
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user ="happyjina";   	String pwd = "oracle";
		String driver = "oracle.jdbc.driver.OracleDriver" ;
		
		ResultSet rs = null;
		Statement  stmt = null;
		PreparedStatement  pstmt = null;
				
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, pwd);
		conn.setAutoCommit(false);  // 자동 커밋 끄기 
		
		stmt = conn.createStatement();
		while( true ) {
			System.out.print("원하는 기능을 선택해 주세요  \n");
			System.out.println("1. Gift select,  2. Gift insert,  3. Gift update,  4. Gift delete, 5. Gift rollback, 6. exit  ");
			num = sc.nextInt();
			
			switch( num ){
			// select 
			case 1 :  rs = stmt.executeQuery("SELECT * FROM GIFT");
						printdata(rs);
						break; 
			//insert
			case 2 : 
				System.out.print("gno = "); 		 	gno = sc.nextInt();
				System.out.print("gname = ");  		gname = sc.next(); 
				System.out.print("g_start = ");  		g_start = sc.nextInt();
				System.out.print("g_end = ");  			g_end = sc.nextInt();
				try {
					stmt.executeQuery("INSERT INTO GIFT VALUES(" + gno + ", '" 
												+ new String( gname.getBytes("utf-8"), "utf-8" ) + "' ," + g_start + ", " + g_end + ")");
				} catch (Exception e) {
					e.printStackTrace();
				}
				conn.commit();   //추가 완료....
				System.out.println(gname + " 추가 되었습니다.");
				break;
				
			case 3 : 	
				rs = stmt.executeQuery("SELECT * FROM GIFT");
				printdata(rs);
				System.out.print("수정할 데이터의 gno를 선택하세요 :  ");
				modiNum = sc.nextInt();
				System.out.println("수정할 데이터는 무엇 ? ");
				System.out.println("1. gno,  2. gname,  3. 상품가격");
				num = sc.nextInt();
				switch( num ){
						case 1 :
							System.out.println("현재 gno : ");
							rs = stmt.executeQuery("SELECT GNO FROM GIFT WHERE GNO = " + modiNum );
							while( rs.next() ){
								System.out.println(rs.getInt(1) + " 입니다.");
							} // in while end
							System.out.println("몇 번으로 바꾸시겠습니까? ");
							gno = sc.nextInt();
							
							pstmt = conn.prepareStatement("UPDATE GIFT SET GNO = ?  WHERE  GNO = " + modiNum);
							pstmt.setInt(1, gno);
							pstmt.executeUpdate();  // !!!!! 
							System.out.println("수정이 완료 되었습니다.");
							break;
							
						case 2 :
							System.out.println("현재 상품 이름은 : ");
							rs = stmt.executeQuery("SELECT GNAME FROM GIFT WHERE GNO = " + modiNum );
							while( rs.next() ){
								System.out.println(rs.getString(1) + " 입니다.");
							} // in while end
							System.out.println("무엇으로 바꾸시겠습니까? ");
							gname = sc.next();
							
							pstmt = conn.prepareStatement("UPDATE GIFT SET GNAME = ?   WHERE  GNO = " + modiNum );
							pstmt.setString(1, gname);
							pstmt.executeUpdate();  // !!!!! 
							System.out.println("상품명이 수정 완료 되었습니다.");
							break;
							
						case 3 :
							System.out.println("현재 g_start 가격은?  ");
							rs = stmt.executeQuery("SELECT G_START  FROM GIFT WHERE GNO = " + modiNum );
							while( rs.next() ){
								System.out.println(rs.getInt(1) + " 입니다.");
							} // in while end
							System.out.println("g_start 얼마로 변경하시겠습니까? ");
							g_start  = sc.nextInt();
							
							pstmt = conn.prepareStatement("UPDATE GIFT SET G_START = ?  WHERE  GNO = " + modiNum);
							pstmt.setInt(1, g_start);
							pstmt.executeUpdate();  // !!!!! 
							System.out.println("시작 가격이 수정 완료 되었습니다.");
							/////////////////////////////////////////////////////////////////////////////
							
							System.out.println("현재 g_end 가격은?  ");
							rs = stmt.executeQuery("SELECT G_END  FROM GIFT WHERE GNO = " + modiNum );
							while( rs.next() ){
								System.out.println(rs.getInt(1) + " 입니다.");
							} // in while end
							System.out.println("g_start 얼마로 변경하시겠습니까? ");
							g_end  = sc.nextInt();
							
							pstmt = conn.prepareStatement("UPDATE GIFT SET G_END = ?  WHERE  GNO = " + modiNum);
							pstmt.setInt(1, g_end);
							pstmt.executeUpdate();  // !!!!! 
							System.out.println("최고 가격이 수정 완료 되었습니다.");
							break;
							
							default : System.out.println("잘못 입력 하셨습니다."); 
									break;
				} // update switch end
				break;
				
			case 4 :
				rs = stmt.executeQuery("SELECT * FROM GIFT");
				printdata(rs);
				System.out.print("삭제할 선물의 이름을 입력 하세요 ");
				gname = sc.next();
				
				//stmt.executeUpdate("delete from gift where gname = '" + gname + " ' " );
				pstmt = conn.prepareStatement("DELETE FROM GIFT WHERE GNAME = ? " );
				pstmt.setString(1, gname);
				pstmt.executeUpdate();
				System.out.println( gname + " 에 해당하는 상품이 삭제 되었습니다.");
				break;
				
			case 5 :
				conn.rollback();
				System.out.println("롤백 했습니다.");
				break;
				
			case 6 :
				pstmt.close();
				stmt.close();
				conn.close();
				System.exit(0);
				break;
				
				default :
					System.out.println("다시 입력해 주세요");
					break;
				
			} // out switch end
			
		} // while end	
	}

	public  static void printdata(ResultSet rs) {
		 int num = 1;
		 try {
			ResultSetMetaData  rsmd = null;  //테이블의 정보를 얻어 올 수 있는 인터페이스
			// resultset의 데이터를 MetaData로 구조를 파악한다.
			// MetaData = 속성 정보 라고 할 수 있다.
			rsmd = rs.getMetaData();
			
			int colCount = rsmd.getColumnCount(); //컬럼 수를 구한다.
			
			while( rs.next() ) {
				System.out.print( num + " 번  --> ");
				num++;
				
				for (int i = 1; i <= colCount ; i++ ) {
					
					switch( rsmd.getColumnType(i) ) {  //각 타입별로 출력 한다.
							case  Types.NUMERIC :
							case  Types.INTEGER :
								System.out.print( rsmd.getColumnName(i) + " : " + rs.getInt(i) );
								// System.out.printf( " %d ",   rs.getInt(i) );  //(Object[])rs.getObject(i) );
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
								//System.out.println("defult ");
								System.out.print( rsmd.getColumnName(i) + " : " + rs.getString(i) );
					} // switch end
					System.out.println();
				} // for end
				
			} //while end
			
		} catch (Exception e) {
			System.err.println("SQLException : " + e.getMessage() );
		} // try end
		 
	} // printdata method end
}
