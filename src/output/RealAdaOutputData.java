package output;

import input.AdaInputData;

import java.util.List;

import boosting.IterationData;

public class RealAdaOutputData extends AdaOutputData{

	public RealAdaOutputData(AdaInputData inData, List<IterationData> itData) {
		super(inData, itData);
	}
	
	@Override
	public void print() {
		if (itData.size() > 0) {
			IterationData srcData = itData.get(itData.size() - 1);
			System.out.println("Iteration " + srcData.getCurrentItNum() + ":");
			System.out.println("h: " + parseHypothesis(srcData.getClassifier(), srcData.getChCompare()));
			System.out.println("G: " + f2Str(srcData.getBoostComparator(), 1));
			System.out.print("c+: " + f2Str(srcData.getQRight()) + ",\t");
			System.out.println("c-: " + f2Str(srcData.getQWrong()));
			System.out.println("Z: " + f2Str(srcData.getNormalizationFactor()));
			System.out.println("p: " + parseProb(data.getProb()));
			System.out.println("f: " + parseClassifier());
			System.out.println("EF: " + parseBoostedErr(srcData));
			System.out.println("EF bound: " + parseErrBound());
			System.out.println("");
		}
	}

	/*
	 * use saved history g
	 */
	@Override
	protected String parseClassifier() {
		double g[] = new double[data.getItemNum()];
		for (int i = 0; i < itData.size(); i++) {
			IterationData srcData = itData.get(i);
			double gIt[] = srcData.getGvalue4X();
			for (int j = 0; j < g.length; j++) {
				g[j] += gIt[j];
			}
		}
		
		StringBuilder s = new StringBuilder();
		for (int j = 0; j < g.length; j++) {
			s.append(f2Str(g[j]) + " ");
		}
		return s.toString();
	}
}
