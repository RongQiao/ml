package kNeighbors;

import input.LabelExample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public abstract class KNNStrategy {
	/*
	 * if the distance is same, pick up the one making most error
	 */
	public List<Character> getKNearestNeighbor(int k, Map<Integer, Double> sortedMap, List<LabelExample> classifier) {
		List<Character> possibleLabels = new ArrayList<Character>();		
		
		Collection<Double> dis = sortedMap.values();
		List<List<Integer>> disExamples = new ArrayList<List<Integer>>();
		splitDistances(sortedMap, dis, disExamples);
				
		int cntNeighber = 0;
		for (int i = 0; i < dis.size(); i++) {
			List<Integer> exampleIndex = disExamples.get(i);
			cntNeighber += exampleIndex.size();
			if (cntNeighber <= k) {	//add all for the current distance
				for (Integer eI: exampleIndex) {
					LabelExample ex = classifier.get(eI);
					possibleLabels.add(ex.getLabel());
				}				
			}
			else {	//pick some of example for the same distance
				cntNeighber -= exampleIndex.size();
				Integer eI = pollExample(exampleIndex, classifier);
				LabelExample ex = classifier.get(eI);
				possibleLabels.add(ex.getLabel());
				cntNeighber++;
			}
		}
		
		return possibleLabels;
	}
	
	public abstract Integer pollExample(List<Integer> exampleIndex, List<LabelExample> classifier);

	public void splitDistances(Map<Integer, Double> sortedMap,
			Collection<Double> dis, List<List<Integer>> disExamples) {
		Set<Entry<Integer, Double>> entries = sortedMap.entrySet();
		for (double d: dis) {
			List<Integer> exampleIndex = new ArrayList<Integer>();
			boolean begin = false;
			for (Entry<Integer, Double> en: entries) {
				double ed = en.getValue();
				if (Double.compare(d, ed) == 0) {
					if (!begin) {
						begin = true;
					}
					exampleIndex.add(en.getKey());
				}
				else {
					if (begin) {
						break;
					}
				}
			}
			disExamples.add(exampleIndex);
		}
	}
}
