package validation;

import input.InputData;
import input.LabelExample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kNeighbors.KNearestNeighbors;

public class CrossValidation {
	private int kFold;
	
	public CrossValidation() {
		setKfold(1);
	}

	public int getKfold() {
		return this.kFold;
	}
	
	public void setKfold(int k) {
		this.kFold = k;
	}

	public double estimate(List<LabelExample> examples, KNearestNeighbors kn) {
		List<Map<Integer, LabelExample>> folds = this.divideFolds(this.getKfold(), examples);
		int errorSum = 0;
		for (int i = 1; i <= this.kFold; i++) {
			Map<Integer, LabelExample> trainExamples = getTrainExamples(folds, i);
			Map<Integer, LabelExample> classifier = train(trainExamples, kn);
			Map<Integer, LabelExample> testExamples = folds.get(i-1);
			Map<Integer, LabelExample> testResult = test(testExamples, classifier, kn);
			int numError = getNumError(testResult, testExamples);
			errorSum += numError; 
		}
		double E = (double) errorSum / (double)examples.size();
		return E;
	}

	private int getNumError(Map<Integer, LabelExample> testResult,
			Map<Integer, LabelExample> testRefResult) {
		int numErr = 0;
		for (Entry<Integer, LabelExample> en: testResult.entrySet()) {
			LabelExample result = en.getValue();
			LabelExample real = testRefResult.get(en.getKey());
			if (result.notEqualLabel(real)) {
				numErr++;
			}
		}
		return numErr;
	}

	private Map<Integer, LabelExample> test(Map<Integer, LabelExample> testExamples,
			Map<Integer, LabelExample> classifier, KNearestNeighbors kn) {
		Map<Integer, LabelExample> result = new HashMap<Integer, LabelExample>();
		List<LabelExample> trainData = new ArrayList<LabelExample>();
		for (LabelExample ex: classifier.values()) {
			trainData.add(ex);
		}
		for (Entry<Integer, LabelExample> en: testExamples.entrySet()) {
			LabelExample ex = en.getValue();
			LabelExample exRst = new LabelExample(ex);
			char lbl = kn.calculateLabel(ex.getX1(), ex.getX2(), kn.getK(), trainData);
			exRst.setLabel(lbl);
			result.put(en.getKey(), exRst);
		}
		return result;
	}

	//not sure the train process, now just use the trainExamples as the classifier
	private Map<Integer, LabelExample> train(Map<Integer, LabelExample> trainExamples, KNearestNeighbors kn) {
		Map<Integer, LabelExample> classifier = new HashMap<Integer, LabelExample>();
		classifier.putAll(trainExamples);
		return classifier;
	}

	private Map<Integer, LabelExample> getTrainExamples(
			List<Map<Integer, LabelExample>> folds, int testFold) {
		Map<Integer, LabelExample> trainExamples = new HashMap<Integer, LabelExample>();	
		for (int i = 1; i <= this.kFold; i++) {
			if (i != testFold) {
				trainExamples.putAll(folds.get(i-1));
			}
		}
		return trainExamples;
	}

	private List<Map<Integer, LabelExample>> divideFolds(int k,
			List<LabelExample> examples) {
		List<Map<Integer, LabelExample>> folds = new ArrayList<Map<Integer, LabelExample>>();
		int cnt = examples.size() / k;
		int cntAddition = examples.size() % k;
		int j = 1;
		int limit = cnt * j;
		Map<Integer, LabelExample> map = new HashMap<Integer, LabelExample>();
		for (int i = 0; i < examples.size(); i++) {			
			if (i < limit) {
				map.put(i, examples.get(i));	
			}
			else {
				folds.add(map);
				map = new HashMap<Integer, LabelExample>();
				j++;
				if (j < k) {
					limit = cnt * j;
				}
				else {
					limit = cnt * j + cntAddition;
				}			
				map.put(i, examples.get(i));
			}
		}
		folds.add(map);
		return folds;
	}

	public AccuracyEstimate estimate(InputData data, KNearestNeighbors kn) {
		AccuracyEstimate ae = new AccuracyEstimate();
		int t = data.getNumPermutations();
		double E[] = new double[t];
		double sumE = 0.0;		
		for (int i = 0; i < t; i++) {			
			List<LabelExample> examples = data.getExamples(i);
			E[i] = this.estimate(examples, kn);
			//test
			System.out.println("E" + i + ": " + E[i]);
			sumE += E[i];			
		}
		ae.setE(sumE/t);
		double sumES = 0.0;
		for (int i = 0; i < t; i++) {
			sumES += Math.pow(E[i] - ae.getE(), 2);
		}
		ae.setV(sumES/(t-1));
		ae.setSigma(Math.sqrt(ae.getV()));
		return ae;
	}

}
