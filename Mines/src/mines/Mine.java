package mines;

public class Mine {
	private char mine;
	private boolean m;
	
	public Mine() {
		this.mine = '.';
		this.m=false;
	}
	
	public Mine(boolean m) {
		this.mine = '.';
		this.m=m;
	}
		
	public boolean getMine() {
		if(m==true) {
			return true;
		}
		return false;
	}
	
	public void setMine() {
		this.m=true;
	}
	
	public char getView() {
		return mine;
	}
	public void setView(char c) {
		this.mine=c;
	}

}