package input;

public class AdaInputData {
	private int iterationNum;
	private int itemNum;
	private double smoothNum;
	private double x[];
	private int y[];
	private double prob[];
	
	public int getIterationNum() {
		return iterationNum;
	}
	public void setIterationNum(int iterationNum) {
		this.iterationNum = iterationNum;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public double getSmoothNum() {
		return smoothNum;
	}
	public void setSmoothNum(double smoothNum) {
		this.smoothNum = smoothNum;
	}
	public double[] getX() {
		return x;
	}
	public void setX(double x[]) {
		this.x = x;
	}
	public int[] getY() {
		return y;
	}
	public void setY(int y[]) {
		this.y = y;
	}
	public double[] getProb() {
		return prob;
	}
	public void setProb(double prob[]) {
		this.prob = prob;
	}
	public double getProb(int i) {
		return prob[i];
	}
	public void setProb(int i, double p) {
		prob[i] = p;
	}
}
