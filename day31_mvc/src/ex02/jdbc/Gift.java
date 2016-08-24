package ex02.jdbc;

public class Gift {
	
	public final String ClassName = "Gift";
	
	//gno, gname, g_start, g_end
	public int num;
	public String gname;
	public int g_start;
	public int g_end;
	
	
	public String getClassName() { // get method¸¸,....
		return ClassName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public int getG_start() {
		return g_start;
	}
	public void setG_start(int g_start) {
		this.g_start = g_start;
	}
	public int getG_end() {
		return g_end;
	}
	public void setG_end(int g_end) {
		this.g_end = g_end;
	}
	
	
}
