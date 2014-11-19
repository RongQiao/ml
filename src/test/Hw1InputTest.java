package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import input.InputData;
import input.InputDataLabel;
import input.LabelExample;

import org.junit.Test;

import basicFile.InputLabelParser;
import basicFile.InputPermParser;
import basicFile.TextFile;

public class Hw1InputTest {

	@Test
	public void testCreateInput() {
		InputData data = new InputData(2, 9, 3);
		assertTrue(data.getNumFolds() == 2);
		assertTrue(data.getNumExamples() == 9);
		assertTrue(data.getNumPermutations() == 3);
		int p[][] = data.getPermutation();
		for (int i = 0; i < data.getNumPermutations(); i++) {
			for (int j = 0; j < data.getNumExamples(); j++) {
				System.out.print(p[i][j] + ",");
			}
			System.out.println("");
		}
	}
	
	public static InputDataLabel getLabels() {
		List<String> labelSrc = new ArrayList<String>();
		labelSrc.add("4 5");
		labelSrc.add(". + . . -");
		labelSrc.add(". + . - -");
		labelSrc.add(". + . . -");
		labelSrc.add(". + . . -");
		InputDataLabel labels = new InputDataLabel(labelSrc);
		return labels;
	}
	
	@Test
	public void testCreateInputLabel() {
		InputDataLabel labels = getLabels();
		for (int i = 0; i < labels.getNumExamples(); i++) {
			LabelExample ex = labels.getExample(i);
			System.out.println(ex.getX1() + "," + ex.getX2() + "," + ex.getLabel());
		}
		assertTrue(labels.getNumExamples() == 9);
		assertTrue(labels.getExampleLabel(3) == '-');
	}
	
	public static InputData getInputData() {
		InputData data = new InputData(2, 9, 3);
		int perm[][] = {{0,1,2,3,4,5,6,7,8},
						{8,1,2,3,4,5,6,7,0},
						{4,0,8,2,6,3,7,1,5}
		};
		data.setPermutation(perm);
		data.setExample(getLabels());	
		return data;
	}
	
	private void printData(InputData data) {
		for (int i = 0; i < data.getNumPermutations(); i++) {
			for (int j = 0; j < data.getNumExamples(); j++) {
				System.out.print(data.getPermutation()[i][j]);
				System.out.print(data.getExample(i, j).getLabel() + " ");
			}
			System.out.println("");
		}
	}
	
	@Test
	public void testInputDataWithLabel() {
		InputData data = getInputData();			
		printData(data);
	}
	
	@Test
	public void readInputFileTest() {
		TextFile filePerm = new TextFile("testfile/p1.txt");
		TextFile fileLabel = new TextFile("testfile/l1.txt");
		InputData data = new InputData();
		InputPermParser.parse(filePerm.readLines(), data);
		InputDataLabel label = new InputDataLabel();
		InputLabelParser.parse(fileLabel.readLines(), label);
		data.setExample(label);
		printData(data);
	}

}
