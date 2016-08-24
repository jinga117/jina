package ex01.jdbc.employee;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class JDBCProjectEx9 extends JFrame implements ActionListener {

   // component 객체 선언
   JPanel panWest, panSouth; // 왼쪽텍스트필드, 아래쪽 버튼
   JPanel p1, p2, p3, p4, p5;
   JTextField txtNo, txtName, txtEmail, txtPhone;
   JButton btnTotal, btnAdd, btnDel, btnSearch, btnCancel;

   JTable table; // 검색과 전체 보기를 위한 테이블 객체 생성
   // 상태변화를 위한 변수 선언
   private static final int NONE = 0;
   private static final int ADD = 1;
   private static final int DELETE = 2;
   private static final int SEARCH = 3;
   private static final int TOTAL = 4;
   int cmd = NONE;
   
   MyModel model;   

   // db 관련 객체 선언
   Connection conn;
   Statement stmt;
   PreparedStatement pstmtInsert, pstmtDelete;
   PreparedStatement pstmtTotal, pstmtTotalScroll;
   PreparedStatement pstmtSearch, pstmtSearchScroll;

   private String driver = "oracle.jdbc.OracleDriver";
   private String url = "jdbc:oracle:thin:@localhost:1521:xe";
   private String user = "happyjina";
   private String pwd = "oracle";

   private String sqlInsert = "insert into Customers values(? , ? , ? ,?)";
   private String sqlDelete = "delete from Customers where name =? ";
   private String sqlTotal = "select * from Customers";
   private String sqlSearch = "select * from Customers where name =? ";

   public void dbConnect() {
      try {
         Class.forName(driver);
         conn = DriverManager.getConnection(url, user, pwd);

         pstmtInsert = conn.prepareStatement(sqlInsert);
         pstmtDelete = conn.prepareStatement(sqlDelete);
         pstmtTotal = conn.prepareStatement(sqlTotal);
         pstmtSearch = conn.prepareStatement(sqlSearch);

         pstmtTotalScroll = conn.prepareStatement(sqlTotal, ResultSet.TYPE_SCROLL_SENSITIVE,
               ResultSet.CONCUR_UPDATABLE);
         // 커서 이동을 자유롭게 하고 업데이트 내용 반영
         // resultset의 변경 가능 <==> concur_read_only 읽기 전용
         pstmtSearchScroll = conn.prepareStatement(sqlSearch, ResultSet.TYPE_SCROLL_SENSITIVE,
               ResultSet.CONCUR_UPDATABLE);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public JDBCProjectEx9() { // 생성자함수
      // component 등록
      panWest = new JPanel(new GridLayout(5, 0));
      p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p1.add(new JLabel("번    호"));
      p1.add(txtNo = new JTextField(12));
      panWest.add(p1);

      p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p2.add(new JLabel("이    름"));
      p2.add(txtName = new JTextField(12));
      panWest.add(p2);

      p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p3.add(new JLabel("이  메  일"));
      p3.add(txtEmail = new JTextField(12));
      panWest.add(p3);

      p4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p4.add(new JLabel("전화번호"));
      p4.add(txtPhone = new JTextField(12));
      panWest.add(p4);

      p5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p5.add(new JLabel(""));
      panWest.add(p5);

      add(panWest, "West");

      // button 화면 아래 등록
      panSouth = new JPanel();
      panSouth.add(btnTotal = new JButton("전체보기"));
      panSouth.add(btnAdd = new JButton("추     가"));
      panSouth.add(btnDel = new JButton("삭     제"));
      panSouth.add(btnSearch = new JButton("검     색"));
      panSouth.add(btnCancel = new JButton("취     소"));
      add(panSouth, "South");

      // event 처리
      btnTotal.addActionListener(this);
      btnAdd.addActionListener(this);
      btnDel.addActionListener(this);
      btnSearch.addActionListener(this);
      btnCancel.addActionListener(this);

      // 테이블 객체 생성
      add(new JScrollPane(table = new JTable()), "Center");
      // close
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // 메인 창 출력
      setBounds(100, 100, 700, 300); // setSize(W,H); pack();
      setVisible(true);
      
      dbConnect();
   } // constuctor end

   @Override
   public void actionPerformed(ActionEvent e) { // 버튼 이벤트처리
      Object obj = e.getSource();
      if (obj == btnAdd) {
         if (cmd != ADD) { // if(0 != 1){ }
            setText(ADD); // user method
            return;
         } // if in
         setTitle(e.getActionCommand());
         add();

      } else if (obj == btnDel) {
         if (cmd != DELETE) {
            setText(DELETE); // user method
            return;
         } // if in
         setTitle(e.getActionCommand());
         del();

      } else if (obj == btnSearch) {
         if (cmd != SEARCH) {
            setText(SEARCH); // user method
            return;
         } // if in
         setTitle(e.getActionCommand());
         // search(); 검색

      } else if (obj == btnTotal) {
         setTitle(e.getActionCommand());
         // total(); 전체보기
      }
      setText(NONE);
      init(); // 초기화 메소드, user method
   }// actionPerformed end

   /////////////////////// event method append////////////////
   private void add() {
      try {
         String strNo = txtNo.getText();
         String strName = txtName.getText();
         String strMail = txtEmail.getText();
         String strPhone = txtPhone.getText();

         if (strNo.equals("") || strName.equals("")) {
            JOptionPane.showMessageDialog(null, "전화번호 이름 넣어라 좋은말 할때!");
            return;
         }
         
         pstmtInsert.setInt(1, Integer.parseInt(strNo));
         pstmtInsert.setString(2,  strName);
         pstmtInsert.setString(3,  strMail);
         pstmtInsert.setString(4,  strPhone);
         
         pstmtInsert.executeUpdate();
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      JOptionPane.showMessageDialog(null, "추가됬습니다");
   }

   private void del() {
      
   }

   private void init() { // 초기화 메소드
      txtNo.setText("");
      txtNo.setEditable(false);
      txtName.setText("");
      txtName.setEditable(false);
      txtEmail.setText("");
      txtEmail.setEditable(false);
      txtPhone.setText("");
      txtPhone.setEditable(false);
   }// init() end

   private void setText(int command) {
      switch (command) {
      case ADD:
         txtNo.setEditable(true);
         txtName.setEditable(true);
         txtEmail.setEditable(true);
         txtPhone.setEditable(true);
         break;
      case DELETE:
      case SEARCH:
         txtName.setEditable(true);
         break;
      }// switch end

      setButton(command); // user method
   }// setText() end

   private void setButton(int command) {
      // cancel button 제외하고 어떤 버튼이 눌리더라도 모든 버튼이 비활성화
      btnTotal.setEnabled(false);
      btnAdd.setEnabled(false);
      btnDel.setEnabled(false);
      btnSearch.setEnabled(false);

      switch (command) {
      case ADD:
         btnAdd.setEnabled(true);
         cmd = ADD;
         break;
      case DELETE:
         btnDel.setEnabled(true);
         cmd = DELETE;
         break;

      case SEARCH:
         btnSearch.setEnabled(true);
         cmd = SEARCH;
         break;
      case TOTAL:
         btnTotal.setEnabled(true);
         cmd = TOTAL;
         break;
      case NONE:
         btnTotal.setEnabled(true);
         btnAdd.setEnabled(true);
         btnDel.setEnabled(true);
         btnSearch.setEnabled(true);
         btnCancel.setEnabled(true); //
         cmd = NONE;
         break;
      }// switch end
   }// setButton end

   public static void main(String[] args) {
      new JDBCProjectEx9();
   } // end main
}