package output;

import java.text.DecimalFormat;
import java.util.List;

import input.AdaInputData;
import boosting.IterationData;

public class AdaOutputData {
	protected AdaInputData data;
	protected List<IterationData> itData;

	public AdaOutputData(AdaInputData inData, List<IterationData> itData) {
		this.data = inData;
		this.itData = itData;
	}

	public void print() {
		if (itData.size() > 0) {
			IterationData srcData = itData.get(itData.size() - 1);
			System.out.println("Iteration " + srcData.getCurrentItNum() + ":");
			System.out.println("h: " + parseHypothesis(srcData.getClassifier(), srcData.getChCompare()));
			System.out.println("E: " + f2Str(srcData.getBoostComparator(), 1));
			System.out.println("alpha: " + f2Str(srcData.getAlpha()));
			System.out.println("Z: " + f2Str(srcData.getNormalizationFactor()));
			System.out.println("p: " + parseProb(data.getProb()));
			System.out.println("f: " + parseClassifier());
			System.out.println("EF: " + parseBoostedErr(srcData));
			System.out.println("EF bound: " + parseErrBound());
			System.out.println("");
		}
	}

	protected String parseBoostedErr(IterationData srcData) {
		int errCnt = srcData.getBoostedErrCnt();
		double errWeight = (double)errCnt / (double)data.getItemNum();
		String s = f2Str(errWeight, 3);
		return s;
	}

	protected String parseErrBound() {
		double EB = 1.0;
		for (IterationData srcData: itData) {
			EB *= srcData.getNormalizationFactor();
		}
		String s = String.format("%.6f", EB);
		return s;
	}

	protected String f2Str(double num) {
		return f2Str(num, 6);
	}
	
	protected String f2Str(double num, int cnt) {
		String s = "";
		if (cnt < 6) {
			DecimalFormat df = new DecimalFormat("#.####");
			s = df.format(num); 
		}
		else {
			s = String.format("%.6f", num);
		}
		return s;
	}

	/*
	 * use saved history alpha and hypothesis classifier
	 */
	protected String parseClassifier() {
		StringBuilder s = new StringBuilder();
		s.append("f(x) = ");
		for (int i = 0; i < itData.size(); i++) {
			IterationData srcData = itData.get(i);
			double alpha = srcData.getAlpha();
			s.append(f2Str(alpha) + " ");
			s.append(parseHypothesis(srcData.getClassifier(), srcData.getChCompare()));
			if (i != (itData.size()-1)) {
				s.append(" + ");
			}
		}
		return s.toString();
	}

	protected String parseHypothesis(double threshold, char ch) {
		String h = "I(x " + ch + " " + threshold + ")";
		return h;
	}

	protected String parseProb(double[] prob) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < prob.length; i++) {
			s.append(f2Str(prob[i]) + " ");
		}
		return s.toString();
	}

}
