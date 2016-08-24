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

   // component ��ü ����
   JPanel panWest, panSouth; // �����ؽ�Ʈ�ʵ�, �Ʒ��� ��ư
   JPanel p1, p2, p3, p4, p5;
   JTextField txtNo, txtName, txtEmail, txtPhone;
   JButton btnTotal, btnAdd, btnDel, btnSearch, btnCancel;

   JTable table; // �˻��� ��ü ���⸦ ���� ���̺� ��ü ����
   // ���º�ȭ�� ���� ���� ����
   private static final int NONE = 0;
   private static final int ADD = 1;
   private static final int DELETE = 2;
   private static final int SEARCH = 3;
   private static final int TOTAL = 4;
   int cmd = NONE;
   
   MyModel model;   

   // db ���� ��ü ����
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
         // Ŀ�� �̵��� �����Ӱ� �ϰ� ������Ʈ ���� �ݿ�
         // resultset�� ���� ���� <==> concur_read_only �б� ����
         pstmtSearchScroll = conn.prepareStatement(sqlSearch, ResultSet.TYPE_SCROLL_SENSITIVE,
               ResultSet.CONCUR_UPDATABLE);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public JDBCProjectEx9() { // �������Լ�
      // component ���
      panWest = new JPanel(new GridLayout(5, 0));
      p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p1.add(new JLabel("��    ȣ"));
      p1.add(txtNo = new JTextField(12));
      panWest.add(p1);

      p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p2.add(new JLabel("��    ��"));
      p2.add(txtName = new JTextField(12));
      panWest.add(p2);

      p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p3.add(new JLabel("��  ��  ��"));
      p3.add(txtEmail = new JTextField(12));
      panWest.add(p3);

      p4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p4.add(new JLabel("��ȭ��ȣ"));
      p4.add(txtPhone = new JTextField(12));
      panWest.add(p4);

      p5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p5.add(new JLabel(""));
      panWest.add(p5);

      add(panWest, "West");

      // button ȭ�� �Ʒ� ���
      panSouth = new JPanel();
      panSouth.add(btnTotal = new JButton("��ü����"));
      panSouth.add(btnAdd = new JButton("��     ��"));
      panSouth.add(btnDel = new JButton("��     ��"));
      panSouth.add(btnSearch = new JButton("��     ��"));
      panSouth.add(btnCancel = new JButton("��     ��"));
      add(panSouth, "South");

      // event ó��
      btnTotal.addActionListener(this);
      btnAdd.addActionListener(this);
      btnDel.addActionListener(this);
      btnSearch.addActionListener(this);
      btnCancel.addActionListener(this);

      // ���̺� ��ü ����
      add(new JScrollPane(table = new JTable()), "Center");
      // close
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // ���� â ���
      setBounds(100, 100, 700, 300); // setSize(W,H); pack();
      setVisible(true);
      
      dbConnect();
   } // constuctor end

   @Override
   public void actionPerformed(ActionEvent e) { // ��ư �̺�Ʈó��
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
         // search(); �˻�

      } else if (obj == btnTotal) {
         setTitle(e.getActionCommand());
         // total(); ��ü����
      }
      setText(NONE);
      init(); // �ʱ�ȭ �޼ҵ�, user method
   }// actionPerformed end

   /////////////////////// event method append////////////////
   private void add() {
      try {
         String strNo = txtNo.getText();
         String strName = txtName.getText();
         String strMail = txtEmail.getText();
         String strPhone = txtPhone.getText();

         if (strNo.equals("") || strName.equals("")) {
            JOptionPane.showMessageDialog(null, "��ȭ��ȣ �̸� �־�� ������ �Ҷ�!");
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
      
      JOptionPane.showMessageDialog(null, "�߰�����ϴ�");
   }

   private void del() {
      
   }

   private void init() { // �ʱ�ȭ �޼ҵ�
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
      // cancel button �����ϰ� � ��ư�� �������� ��� ��ư�� ��Ȱ��ȭ
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