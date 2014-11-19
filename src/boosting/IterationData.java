package boosting;

import java.util.ArrayList;
import java.util.List;

public class IterationData {
	private int currentItNum;
	private double classifier;
	private double weightedErr;
	private int errCnt;			//error cnt during iteration
	private int boostedErrCnt;	//error cnt after interation, verify the boosted functon
	private double qWrong;
	private double qRight;
	private double alpha;
	private int currentY[];
	private double Z;
	
//	private List<Double> alphaHistory;
//	private List<Double> ZHistory;
//	private List<Double> hHistory;
	private char chCompare;
private double[] gValue4X;
	
	public IterationData() {
		currentItNum = 0;
//		alphaHistory = new ArrayList<Double>();
//		alphaHistory.add(1.0);
//		ZHistory = new ArrayList<Double>();
//		ZHistory.add(1.0);
//		hHistory = new ArrayList<Double>();
//		hHistory.add(0.0);
	}

	public double getBoostComparator() {
		return weightedErr;
	}

	public void setBoostComparator(double weightedErr) {
		this.weightedErr = weightedErr;
	}

	/**
	 * @return the classifier
	 */
	public double getClassifier() {
		return classifier;
	}

	/**
	 * @param classifier the classifier to set
	 */
	public void setClassifier(double classifier) {
		this.classifier = classifier;
//		hHistory.add(classifier);
	}

	public void setErrCnt(int errCnt) {
		this.errCnt = errCnt;
	}
	
	public int getErrCnt() {
		return errCnt;
	}

	/**
	 * @return the qWrong
	 */
	public double getQWrong() {
		return qWrong;
	}

	/**
	 * @param qWrong the qWrong to set
	 */
	public void setQWrong(double qWrong) {
		this.qWrong = qWrong;
	}

	/**
	 * @return the qRight
	 */
	public double getQRight() {
		return qRight;
	}

	/**
	 * @param qRight the qRight to set
	 */
	public void setQRight(double qRight) {
		this.qRight = qRight;
	}

	public void setCurrentY(int y[]) {
		currentY = y;
	}

	public int[] getCurrentY() {
		return currentY;
	}

	public double getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
//		alphaHistory.add(alpha);
	}

	public double getNormalizationFactor() {
		return this.Z;
	}
	
	public void setNormalizationFactor(double z) {
		this.Z = z;
//		ZHistory.add(z);
	}

	/**
	 * @return the currentIt
	 */
	public int getCurrentItNum() {
		return currentItNum;
	}

	/**
	 * @param currentIt the currentIt to set
	 */
	public void setCurrentItNum(int currentIt) {
		this.currentItNum = currentIt;
	}

//	public List<Double> getAlphaHistory() {
////		return alphaHistory;
//	}
//
//	public List<Double> getZHistory() {
//		return ZHistory;
//	}

	public int getBoostedErrCnt() {
		return boostedErrCnt;
	}
	
	public void setBoostedErrCnt(int errCnt) {
		this.boostedErrCnt = errCnt;
	}

//	/**
//	 * @return the hHistory
//	 */
//	public List<Double> gethHistory() {
//		return hHistory;
//	}

	public char getChCompare() {
		return chCompare;
	}
	
	public void setChCompare(char chCompare) {
		this.chCompare = chCompare;
	}

	public double[] getGvalue4X() {
		return gValue4X;
	}
	public void setGvalue4X(double[] gValue) {
		gValue4X = gValue;
	}

}
