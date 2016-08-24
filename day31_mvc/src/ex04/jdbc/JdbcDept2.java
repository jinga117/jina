package ex04.jdbc;

import java.util.Scanner;

public class JdbcDept2 {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ConnectionUtil jdb = new ConnectionUtil();
		jdb.connect(); // 연결 됨...

		while (true) {
			jdb.menu();
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
	} // main end

	
}
