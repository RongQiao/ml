package input;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InputData {
	private int numFolds;
	private int numExamples;
	private int numPermutations;
	private int permutation[][];
	private InputDataLabel examples;
	private Random rand;
	
	public InputData(int k, int e, int t) {
		this.setNumFolds(k);
		this.setNumExamples(e);
		this.setNumPermutations(t);
		createPermutations();
	}

	public InputData() {
		// TODO Auto-generated constructor stub
	}

	private int indexForRandom = 0;
	private void createPermutations() {
		int numP = this.getNumPermutations();
		int numE = this.getNumExamples();
		permutation = new int[numP][numE];
		rand = new Random();
		for (int i = 0; i < numP; i++) {
			int p[] = permutation[i]; 
			//rand.setSeed(rand.nextInt());
			indexForRandom = 0;
			createRandomPermutation(0, numE-1, p);			
		}
	}

	//inclusive min, exclusive max
	private void createRandomPermutation(int min, int max, int[] p) {
		int r = 0;
		if (min > max) {
			return;
		}
		else if (max == min) {
			r = min;
		}
		else {
			r = randomInt(min, max);
			createRandomPermutation(min, r-1, p);
			createRandomPermutation(r+1, max, p);
		}
		p[indexForRandom++] = r;
	}

	private int randomInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

	public int getNumFolds() {
		return numFolds;
	}
	
	public void setNumFolds(int k) {
		this.numFolds = k;
	}
	
	public int getNumExamples() {
		return numExamples;
	}
	
	public void setNumExamples(int numExamples) {
		this.numExamples = numExamples;
	}
	
	public int getNumPermutations() {
		return numPermutations;
	}
	
	public void setNumPermutations(int numPermutations) {
		this.numPermutations = numPermutations;
	}
	
	public int[][] getPermutation() {
		return permutation;
	}
	
	public void setPermutation(int permutation[][]) {
		this.permutation = permutation;
	}

	public void setExample(InputDataLabel labels) {
		examples = labels;
	}

	//get the no.j example of the no.i permutation
	public LabelExample getExample(int i, int j) {
		int exampleIndex = this.permutation[i][j];
		return examples.getExample(exampleIndex);
	}

	//get all examples of the no.i permutation
	public List<LabelExample> getExamples(int i) {
		List<LabelExample> all = new ArrayList<LabelExample>();		
		for (int j = 0; j < this.getNumExamples(); j++) {
			LabelExample ex = this.getExample(i, j);
			all.add(ex);
		}
		return all;
	}

	//get all examples of the input data, the sequence order doesn't matter
	public List<LabelExample> getExamples() {
		return this.examples.getExamples();
	}

	//for the given input label file, there are some points labeled 
	public List<LabelExample> getEmptyPoints() {
		List<LabelExample> ex = new ArrayList<LabelExample>();
		for (int i = 0; i < examples.getNumColumn(); i++) {
			for (int j = 0; j < examples.getNumRow(); j++) {
				if (examples.getExample(i, j) == null) {
					LabelExample emptyPoint = new LabelExample(i, j);
					ex.add(emptyPoint);
				}
			}
		}
		return ex;
	}

	public int getNumColumn() {
		return examples.getNumColumn();
	}

	public int getNumRow() {
		return examples.getNumRow();
	}

}
