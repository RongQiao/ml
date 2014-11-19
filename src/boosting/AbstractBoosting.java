package boosting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import input.AdaInputData;

public abstract class AbstractBoosting {
	protected AdaInputData data;
	protected IterationData iterationData;
	protected List<IterationData> itHistory;
	protected int y4Classifier;	
	
	public IterationData getIterationData() {
		return iterationData;
	}
	
	public List<IterationData> getAllIterationData() {
		return itHistory;
	}

	public void setIterationData(IterationData iterationData) {
		this.iterationData = iterationData;
	}	

	public void setY4Classifier(int y) {
		y4Classifier = y;
	}

	public int getY4Classifier() {
		return y4Classifier;
	}


	public AbstractBoosting() {		
		iterationData = new IterationData();
	}
	
	public void init(AdaInputData inputData) {
		data = inputData;
		itHistory = new ArrayList<IterationData>();
	}

	public double selectBestThreshold() {
		double comparatorMin = 1.0;
		
		for (int i = 0; i < data.getItemNum()-1; i++) {	//classifier number = cnt - 1
			double classifier = selectClassifier(i);
			for (int j = 0; j < 2; j++) {
				char chCompare = (j == 0) ? '<' : '>';	//same number, try twice: < and >
				int errCnt = testClassifier(classifier, chCompare);
				double comparator = getClassifyComparator();	//ada boosting compare the err probability, real ada boosting compare G()			
				if (comparator < comparatorMin) {
					comparatorMin = comparator;
					iterationData.setClassifier(classifier);
					iterationData.setChCompare(chCompare);
					iterationData.setBoostComparator(comparator);
					iterationData.setErrCnt(errCnt);
					//save tmpData
					saveTmpData();
				}
			}
		}
		retriveTmpData();
		return iterationData.getClassifier();
	}

	abstract public void retriveTmpData();

	abstract public void saveTmpData();

	abstract public double getClassifyComparator();

	abstract public int testClassifier(double v, char c);

	protected int calculateIFunction(double x, double v, char c) {
		int yI = 0;
		if (c == '<') {
			yI = calculateIFunctionLess(x, v);
		}
		else {
			yI = calculateIFunctionGreat(x, v);
		}
		return yI;
	}
	
	public int calculateIFunctionLess(double x, double v) {
		int y = 0 - this.getY4Classifier();
		if (x < v) {
			y = this.getY4Classifier();
		}
		return y;
	}
	
	public int calculateIFunctionGreat(double x, double v) {
		int y = 0 - this.getY4Classifier();
		if (x > v) {
			y = this.getY4Classifier();
		}
		return y;
	}

	abstract public int verifyClassifier(); 
//	{
//		tmpData.errCnt = 0;
//		tmpData.errProb = 0.0;
//		double x[] = data.getX();
//		int y[] = data.getY();
//		double p[] = data.getProb();	
//				
//		for (int i = 0; i < x.length; i++) {
//			tmpData.y[i] = calculateFFunction(x[i]);
//			if (tmpData.y[i] != y[i]) {
//				tmpData.errCnt++;
//				tmpData.errProb += p[i];
//			}
//		}
//		
//		return tmpData.errCnt;
//	}




	private double selectClassifier(int lowIndex) {
		double ret = 0.0;
		if (lowIndex < (data.getItemNum()-1)) {
			double x[] = data.getX();
			ret = Calculator.mid(x[lowIndex], x[lowIndex+1]);
		}
		return ret;
	}

	/*
	 * after decided Hypothesis
	 */
	public void calculateItData() {
		double errProb = iterationData.getBoostComparator();
		double alpha = Math.log((1-errProb)/errProb);
		alpha /= 2;
		iterationData.setAlpha(alpha);
		iterationData.setQWrong(Math.exp(alpha));		//
		iterationData.setQRight(Math.exp(0-alpha));
		double Z = Math.sqrt(errProb * (1 - errProb)) * 2;
		iterationData.setNormalizationFactor(Z);
	}

	public void updateProb() {
		double Z = iterationData.getNormalizationFactor();
		int inputY[] = data.getY();
		int currentY[] = iterationData.getCurrentY();
		for (int i = 0; i < inputY.length; i++) {
			double p = data.getProb(i);
			double preNomFactor = calculatePreNomFactor(i, inputY[i], currentY[i]);			
			p = (p * preNomFactor) / Z;
			data.setProb(i, p);
		}
		//
		updateIterationData();
	}

	public void updateIterationData() {
		// TODO Auto-generated method stub
		
	}

	public double calculatePreNomFactor(int xIndex, int yIn, int yCurrent) {
		double factor = iterationData.getQWrong();
		if (yIn == yCurrent) {
			factor = iterationData.getQRight();
		}
		return factor;
	}

	public void iterate(int t) {
		int currentItNum = iterationData.getCurrentItNum();
		if (currentItNum < t) {
			for (int i = currentItNum; i < t; i++) {				
				selectBestThreshold();
				calculateItData();
				updateProb();
				itHistory.add(iterationData);
				int errCnt = verifyClassifier();
				iterationData.setBoostedErrCnt(errCnt);
				iterationData.setCurrentItNum(t);
				//prepare for next iteration
				iterationData = new IterationData();
				iterationData.setCurrentItNum(t);
			}
		}
	}


}