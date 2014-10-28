package input;

public class LabelExample {
	private int x1;		//column index	
	private int x2;		//row index
	private char label;
	
	public LabelExample(int x1, int x2, char label) {
		this.setX1(x1);
		this.setX2(x2);
		this.setLabel(label);
	}
	
	public LabelExample(int x1, int x2) {
		this.setX1(x1);
		this.setX2(x2);
	}	
	
	public LabelExample(LabelExample ex) {
		this.setX1(ex.getX1());
		this.setX2(ex.getX2());
		this.setLabel(ex.getLabel());
	}


	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public char getLabel() {
		return label;
	}
	public void setLabel(char label) {
		this.label = label;
	}
	
	public double calculateDistance(int x1Target, int x2Target) {
		int x = Math.abs(this.getX1() - x1Target);
		int y = Math.abs(this.getX2() - x2Target);
		double d = Math.sqrt(x * x + y * y);
		return d;
	}
	
	public boolean notEqualLabel(LabelExample target) {
		boolean ret = false;
		if ((this.getX1() == target.getX1())
				&& (this.getX2() == target.getX2())
				&& (this.getLabel() != target.getLabel())) {
			ret = true;
		}
		return ret;
	}
}
