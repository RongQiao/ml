package boosting;

import input.AdaInputData;

import java.util.ArrayList;
import java.util.Arrays;

public class RealAdaBoosting extends AbstractBoosting{
	private static final int rPlus = 0;
	private static final int rMinus = 1;
	private static final int wPlus = 2;
	private static final int wMinus = 3;
	
	private testTempData tmpData;		//it's used for calculating, shared by several mthod
	private testTempData savedTmpData;	//it's only used for store tmp data for current selected best classifier, shared by saveTmpData() and retrivalTmpData();
	private class testTempData{
		double G;
		double P[];
		double gx[];
		int y[];
		testTempData() {}
		testTempData(testTempData src) {
			this.G = src.G;
			this.P = Arrays.copyOf(src.P, src.P.length);
			this.gx = Arrays.copyOf(src.gx, src.gx.length);
			this.y = Arrays.copyOf(src.y, src.y.length);
		}
	}
	
	public RealAdaBoosting() {
		super();
		tmpData = new testTempData();
	}
	
	@Override
	public void init(AdaInputData inputData) {
		super.init(inputData);
		tmpData.y = new int[data.getItemNum()];
		tmpData.gx = new double[data.getItemNum()];
		tmpData.G = 1.0;
	}
	/*
	 * c: '<' :if x < v, y = -1(or 1), otherwise 1(or -1)
	 * c: '>' :if x > v, y = -1(or 1), otherwise 1(or -1)
	 * compare the new Y with input Y, error cnt = doesn't matched number	 * 
	 */
	@Override
	public int testClassifier(double v, char c) {
		int errCnt = 0;
		double x[] = data.getX();
		int y[] = data.getY();
		double p[] = Arrays.copyOf(data.getProb(), x.length);		
				
		for (int i = 0; i < x.length; i++) {
			tmpData.y[i] = calculateIFunction(x[i], v, c);
			double tmpPrw[] = calculateP4(y, tmpData.y, p);
			tmpData.P = tmpPrw;
			tmpData.G = calculateG(tmpPrw);
		}
		
		return errCnt;
	}

	@Override
	public double getClassifyComparator() {
		return  tmpData.G;		
	}
	
	@Override
	public void saveTmpData() {
		savedTmpData = new testTempData(tmpData);
	}
	
	@Override
	public void retriveTmpData() {
		iterationData.setCurrentY(savedTmpData.y);
		iterationData.setGvalue4X(savedTmpData.gx);
	}
	
	@Override
	public void calculateItData() {
		double Prw[] = savedTmpData.P;
		double sm = data.getSmoothNum();
		double value = (Prw[rPlus] + sm) / (Prw[wMinus] + sm);
		double cPlus = Math.log(value) / 2;
		value = (Prw[wPlus] + sm) / (Prw[rMinus] + sm);
		double cMinus = Math.log(value) / 2;
		iterationData.setQRight(cPlus);
		iterationData.setQWrong(cMinus);		//
		double Z = calculateZ(cPlus, cMinus, data.getY(), tmpData.y);
		iterationData.setNormalizationFactor(Z);
	}
	
	@Override
	public double calculatePreNomFactor(int xIndex, int yIn, int yCurrent) {
		double cPlus = iterationData.getQRight();
		double cMinus = iterationData.getQWrong();
		double g = (yCurrent == 1) ? cPlus : cMinus;
		tmpData.gx[xIndex] = g;
		double factor = Math.exp(-1 * yIn * g);
		return factor;
	}
	
	@Override
	public void updateIterationData() {
		iterationData.setGvalue4X(Arrays.copyOf(tmpData.gx, data.getItemNum()));
	}

	@Override
	public int verifyClassifier() {
		int errCnt = 0;
		double x[] = data.getX();
		int y[] = data.getY();
				
		for (int i = 0; i < x.length; i++) {
			tmpData.y[i] = calculateFFunction(i);
			if (tmpData.y[i] != y[i]) {
				errCnt++;
			}
		}
		
		return errCnt;
	}
	
	public int calculateFFunction(int index) {
		double y = 0.0;		
		for (int i = itHistory.size() - 1; i >= 0; i--) {
			IterationData itData = itHistory.get(i);
			double[] g = itData.getGvalue4X();
			y += g[index];
		}
		int yI = (Double.compare(y, 0.0) > 0) ? 1 : -1;
		return yI;
	}
	
	private double calculateG(double[] P) {
		double g = Math.sqrt(P[rPlus] * P[wMinus]) + Math.sqrt(P[wPlus] * P[rMinus]);
		return g;
	}

	private double calculateZ(double cPlus, double cMinus, int[] y, int[] yH) {
		double z = 0.0;
		double q = savedTmpData.P[rPlus] * Math.exp(-1 * cPlus);
		z += q;
		q = savedTmpData.P[rMinus] * Math.exp(cMinus);
		z += q;
		q = savedTmpData.P[wPlus] * Math.exp(-1 * cMinus);
		z += q;
		q = savedTmpData.P[wMinus] * Math.exp(cPlus);
		z += q;
		return z;
	}


	private double[] calculateP4(int[] y, int[] yH, double[] pInput) {
		double P[] = new double[4];
		
		for (int i = 0; i < y.length; i++) {
			int sum = y[i] + yH[i];
			double prob = pInput[i];
			switch (sum) {
			case 2:
				P[rPlus] += prob;
				break;
			case -2:
				P[rMinus] += prob;
				break;
			case 0:
				if (y[i] == 1) {
					P[wPlus] += prob;
				}
				else {
					P[wMinus] += prob;
				}
				break;
			default:
				break;
			}
		}
		return P;
	}


}
