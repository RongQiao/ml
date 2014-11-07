package kNeighbors;

import input.LabelExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

public class KNNStrategyRandom extends KNNStrategy{

	
	/*
	 * if the distance is same, pick up one ramdomly
	 */
	@Override
	public Integer pollExample(List<Integer> exampleIndex, List<LabelExample> classifier) {
		System.out.println("random");
		int r = random(1, exampleIndex.size());
		r = r - 1;
		
		int index = exampleIndex.get(0);
		exampleIndex.remove(r);
		
		
		return index;
	}

	private static int random(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}


}
