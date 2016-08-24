package ex04.jdbc;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.StringTokenizer;

import java.sql.*; //


public class ManagerGUI
{
	static{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
	}
	
	Frame frame;
	Panel []panel;
	Label lblTitle,lblName,lblHeight,lblSex,lblAge,lblWeight;
	TextField  tfName,tfHeight,tfAge,tfWeight;
	Button btnSave,btnSearch,btnDelete,btnReset,btnUpdate;
	
	List list;
	Checkbox chMale, chFemale;
	CheckboxGroup chGroup;

	
	public ManagerGUI()
	{
			frame = new Frame("회원 관리");
			panel = new Panel[12];
			for( int i = 0; i < panel.length ; i++)
			{
				panel[i] = new Panel();
			}
			lblTitle = new Label("회원 관리", Label.CENTER);
			Font font = new Font("dialog" , Font.BOLD, 20);
			lblTitle.setFont(font);
			lblTitle.setForeground(new Color(123,54,32));
			lblName = new Label("이 름");
			lblHeight = new Label("키 ");
			lblSex = new Label("성 별");
			lblAge = new Label("연 령");
			lblWeight = new Label("체 중");
			
			tfName = new TextField("");
			tfHeight = new TextField("");
			tfAge = new TextField("");
			tfWeight = new TextField("");
			btnSave = new Button("저장");
			btnSearch = new Button("검색");
			btnDelete  = new Button("삭제");
			btnReset = new Button("지우기");
			btnUpdate = new Button("수정");


			list = new List(7,false);
			chGroup = new CheckboxGroup();
			chMale = new Checkbox("Male",chGroup,false);
			chFemale = new Checkbox("FeMale",chGroup,false);
		
			
			frame.addWindowListener( new WindowAdapter () {
				public void windowClosing(WindowEvent e){
					System.exit(0);
				}
	
		    });	
			

		list.addItemListener( new ItemListener () {

				@Override
				public void itemStateChanged(ItemEvent e) {

					
				}
										
		});	
																	
		btnReset.addActionListener( new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
	

			}
		});
																
		btnSave.addActionListener( new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
			

			}
		});

		btnDelete.addActionListener( new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});	


		btnUpdate.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	
			
			}
			});	

   	btnSearch.addActionListener( new ActionListener () {

		@Override
		public void actionPerformed(ActionEvent e) {
		
		}
	 });
	}// end Manager 생성자	
	
	public void launchFrame()
	{
		    panel[1].setLayout( new GridLayout(2,1));
			panel[1].add(lblName);
			panel[1].add(lblHeight);
			panel[2].setLayout( new GridLayout(2,1));
			panel[2].add(tfName);
			panel[2].add(tfHeight);
			panel[3].setLayout( new BorderLayout());
			panel[3].add(panel[1], "West");
			panel[3].add(panel[2], "Center");
			panel[3].add( new Label(" "), "East");
			panel[4].setLayout( new GridLayout(2,1));
			panel[4].add(lblAge);
			panel[4].add(lblWeight);
			panel[5].setLayout( new GridLayout(2,1));
			panel[5].add(tfAge);
			panel[5].add(tfWeight);
			panel[6].setLayout( new BorderLayout());
			panel[6].add(panel[4],"West");
			panel[6].add(panel[5],"Center");
			panel[7].setLayout( new GridLayout(1,2));
			panel[7].add(panel[3]);
			panel[7].add(panel[6]);
			panel[8].add(lblSex);
			panel[8].add(chMale);
			panel[8].add(chFemale);
			panel[9].setLayout( new BorderLayout());
			panel[9].add(lblTitle, "North");
			panel[9].add(panel[7], "Center");
			panel[9].add(panel[8], "South");
			panel[10].setLayout( new GridLayout(1,5));
			panel[10].setBackground(Color.black);
			panel[10].setForeground(Color.white);
			panel[10].add( new Label("이 름     "));
			panel[10].add( new Label("나 이     "));
			panel[10].add( new Label("체 중     "));
			panel[10].add( new Label(" 키       "));
			panel[10].add( new Label("성 별     "));
			panel[11].setLayout( new BorderLayout());
			panel[11].add(panel[10], "North");
			panel[11].add(list , "Center");
			panel[0].add(btnSave);
			panel[0].add( new Label(" "));
			panel[0].add(btnSearch);
			panel[0].add( new Label(" "));
			panel[0].add(btnDelete);
			panel[0].add( new Label(" "));
			panel[0].add(btnUpdate);
			panel[0].add( new Label(" "));
			panel[0].add(btnReset);

			frame.add(panel[9], "North");
			frame.add(panel[11], "Center");
			frame.add(panel[0], "South");
		//	frame.setSize(400,300);
		    frame.pack();

			frame.setResizable(false);// 크기 조절 불가
			frame.setLocation( ((frame.getToolkit().getScreenSize()).width  -  frame.getWidth()) /2 ,
								 ((frame.getToolkit().getScreenSize()).height -  frame.getHeight())/ 2); 
		frame.setVisible(true);

		  //리스트보기 호출
		displayAll();

	}// end launchFrame()

	public void displayAll() {

	}//displayAll()


	public static void main(String [] args)
	{
		try{
			Connection con = DriverManager.getConnection(	"jdbc:oracle:thin:@127.0.0.1:1521:xe", 	"happy",		"oracle");	
			Statement stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("create table members( name varchar2(20), age number(4), ");
			sb.append("height number(4), weight number(4), gender varchar2(10) )");
			stmt.executeUpdate(sb.toString());
		
			stmt.close();
			con.close();
		}catch(SQLException ee){
			System.out.println("Connection Error");
			ee.printStackTrace();
		}

			ManagerGUI  mem = new ManagerGUI();
			mem.launchFrame();	
		
	}// end main()
}// end Manager class


			
		