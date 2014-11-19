package boosting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import input.AdaInputData;

public class AdaBoosting extends AbstractBoosting{
	private testTempData tmpData;		//it's used for calculating, shared by several mthod
	private testTempData savedTmpData;	//it's only used for store tmp data for current selected best classifier, shared by saveTmpData() and retrivalTmpData();

	private class testTempData{
		//int errCnt;
		double errProb;
		int y[];
		public testTempData() {}
		public testTempData(testTempData src) {
			//this.errCnt = src.errCnt;
			this.errProb = src.errProb;
			this.y = Arrays.copyOf(src.y, src.y.length);
		}
	}

	public AdaBoosting() {		
		super();
		tmpData = new testTempData();
	}
	
	public void init(AdaInputData inputData) {
		super.init(inputData);
		tmpData.y = new int[data.getItemNum()];
	}
//
//	public double selectBestThreshold() {
//		double comparatorMin = 1.0;
////		int errCntMin = data.getItemNum();
////		double bestClassifier = 0.0; 
////		char bestChCompare = ' ';
//		int bestY[] = null;
//		
//		for (int i = 0; i < data.getItemNum()-1; i++) {	//classifier number = cnt - 1
//			double classifier = selectClassifier(i);
//			for (int j = 0; j < 2; j++) {
//				char chCompare = (j == 0) ? '<' : '>';	//same number, try twice: < and >
//				testClassifier(classifier, chCompare);
//				double comparator = getClassifyComparator();	//ada boosting compare the err probability, real ada boosting compare G()			
//				if (comparator < comparatorMin) {
//					comparatorMin = comparator;
////					errCntMin = tmpData.errCnt;
////					bestClassifier = classifier;
////					bestChCompare = chCompare;
//					bestY = Arrays.copyOf(tmpData.y, data.getItemNum());
//					//
//					iterationData.setClassifier(classifier);
//					iterationData.setChCompare(chCompare);
//					iterationData.setErrCnt(tmpData.errCnt);
//					iterationData.setBoostComparator(comparator);
//					//save tmpData
//					saveTmpData();
//				}
//			}
//		}
////		iterationData.setClassifier(bestClassifier);
////		iterationData.setChCompare(bestChCompare);
////		iterationData.setErrCnt(errCntMin);
////		iterationData.setBoostComparator(comparatorMin);
//		iterationData.setCurrentY(bestY);
//		return iterationData.getClassifier();
//	}

	@Override
	public void saveTmpData() {
		savedTmpData = new testTempData(tmpData);
	}

	@Override
	public void retriveTmpData() {
		iterationData.setCurrentY(savedTmpData.y);
	}
	
	@Override
	public double getClassifyComparator() {
		return  tmpData.errProb;		
	}

	/*
	 * c: '<' :if x < v, y = -1(or 1), otherwise 1(or -1)
	 * c: '>' :if x > v, y = -1(or 1), otherwise 1(or -1)
	 * compare the new Y with input Y, error cnt = doesn't matched number
	 */
	@Override
	public int testClassifier(double v, char c) {
		int errCnt = 0;
		tmpData.errProb = 0.0;
		double x[] = data.getX();
		int y[] = data.getY();
		double p[] = Arrays.copyOf(data.getProb(), x.length);		
				
		for (int i = 0; i < x.length; i++) {
		tmpData.y[i] = calculateIFunction(x[i], v, c);
		if (tmpData.y[i] != y[i]) {
				errCnt++;
				tmpData.errProb += p[i];
			}
		}
		
		return errCnt;
	}

	@Override
	public int verifyClassifier() {
		int errCnt = 0;
		tmpData.errProb = 0.0;
		double x[] = data.getX();
		int y[] = data.getY();
		double p[] = data.getProb();	
				
		for (int i = 0; i < x.length; i++) {
			tmpData.y[i] = calculateFFunction(x[i]);
			if (tmpData.y[i] != y[i]) {
				errCnt++;
				tmpData.errProb += p[i];
			}
		}
		
		return errCnt;
	}

	public int calculateFFunction(double x) {
		double y = 0.0;		
		for (int i = itHistory.size() - 1; i >= 0; i--) {
			IterationData itData = itHistory.get(i);
			double alpha = itData.getAlpha();	
			double yI = (double)calculateIFunction(x, itData.getClassifier(), itData.getChCompare());
			y += alpha * yI;
		}
		int yI = (Double.compare(y, 0.0) > 0) ? 1 : -1;
		return yI;
	}
	/*
	 * after decided Hypothesis
	 */
	@Override
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

	@Override
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

	@Override
	public double calculatePreNomFactor(int xIndex, int yIn, int yCurrent) {
		double factor = iterationData.getQWrong();
		if (yIn == yCurrent) {
			factor = iterationData.getQRight();
		}
		return factor;
	}

}
