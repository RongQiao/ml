package kNeighbors;

import input.InputData;
import input.LabelExample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import basicFile.InputLabelParser;

public class KNearestNeighbors {
	private int k=1;
	KNNStrategy knnStrategy;

	public KNearestNeighbors() {
		setKnnStrategy(new KNNStrategyRandom());
	}
	public KNNStrategy getKnnStrategy() {
		return knnStrategy;
	}

	public void setKnnStrategy(KNNStrategy knnStrategy) {
		this.knnStrategy = knnStrategy;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}	
	public class DistComparator implements Comparator<Integer> {
		Map<Integer, Double> base;
		public DistComparator(Map<Integer, Double> map) {
			this.base = map;
		}

		@Override
		//compare the distance
		public int compare(Integer k1, Integer k2) {
			if (base.get(k1) <= base.get(k2)) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
		}
		
	}

	public char calculateLabel(int x1, int x2, int k, List<LabelExample> classifier) {
		//first Integer: index of example, second Double: the distance
		Map<Integer, Double> distMap = calculateDistance(x1, x2, classifier);
		List<Character> possibleLabels = getPossibleLabels(k, distMap, classifier);		
		char label = getMostLabel(possibleLabels);
		return label;
	}

	private Map<Integer, Double> calculateDistance(int x1, int x2,
			List<LabelExample> classifier) {
		Map<Integer, Double> distMap = new HashMap<Integer, Double>();
		for (int i = 0; i < classifier.size(); i++ ) {
			LabelExample ex = classifier.get(i);
			double d = ex.calculateDistance(x1, x2);
			distMap.put(i, d);
		}
		return distMap;
	}

	/*
	 * get the K neighbors
	 */
	private List<Character> getKNearestNeighbor(int k, Map<Integer, Double> sortedMap, List<LabelExample> classifier) {
		List<Character> possibleLabels = new ArrayList<Character>();
		int cntNeighber = 0;
		Set<Entry<Integer, Double>> entries = sortedMap.entrySet();
		for (Entry<Integer, Double> en: entries) {
			if (cntNeighber < k) {
				int exampleIndex = en.getKey();
				LabelExample ex = classifier.get(exampleIndex);
				possibleLabels.add(ex.getLabel());
				cntNeighber++;
			}
			else {
				break;
			}
		}
		return possibleLabels;

	}


	private List<Character> getPossibleLabels(int k,
			Map<Integer, Double> distMap, List<LabelExample> classifier) {		
		Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(new DistComparator(distMap));
		sortedMap.putAll(distMap);
		//List<Character> possibleLabels = this.knnStrategy.getKNearestNeighbor(k, sortedMap, classifier); 
		List<Character> possibleLabels = getKNearestNeighbor(k, sortedMap, classifier);
		return possibleLabels;
	}

	private char getMostLabel(List<Character> possibleLabels) {
		int cntPos = 0, cntNeg = 0;
		char label = InputLabelParser.NOT_LABEL;
		for (int i = 0; i < possibleLabels.size(); i++) {
			char ch = possibleLabels.get(i);
			if (ch == InputLabelParser.POSITIVE) {
				cntPos++;
			}
			else {
				cntNeg++;
			}
		}
		if (cntPos > cntNeg) {
			label = InputLabelParser.POSITIVE;	
		}
		else {
			label = InputLabelParser.NEGATIVE;
		}
		return label;
	}

	public List<LabelExample> calculateLabel(List<LabelExample> testData,
			List<LabelExample> trainData) {
		List<LabelExample> retExamples = testData;
		for (LabelExample ex: retExamples) {
			char label = this.calculateLabel(ex.getX1(), ex.getX2(), k, trainData);
			ex.setLabel(label);
		}
		return retExamples;
	}

	

}
