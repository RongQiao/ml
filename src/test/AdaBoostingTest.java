package test;

import static org.junit.Assert.*;

import java.util.List;

import input.AdaInputData;

import org.junit.Test;

import output.AdaOutputData;
import basicFile.AdaInputParser;
import basicFile.TextFile;
import boosting.AdaBoosting;
import boosting.Calculator;
import boosting.IterationData;

public class AdaBoostingTest {

	/*
	 * 10 4 1.0E-7
		1.0 2.0 3.5 4.5 
		1 -1 1 1 
		0.25 0.25 0.25 0.25 
	 */
	AdaInputData createInput1() {
		TextFile file = new TextFile("testfile/ada.txt");
		AdaInputData data = new AdaInputData();
		AdaInputParser.parse(file.readLines(), data);
		return data;
	}
	
	@Test
	public void IterationCase1_1() {
		AdaInputData data = createInput1();
		AdaBoosting ada = new AdaBoosting();
		ada.init(data);
		ada.setY4Classifier(-1);
		double threshold = ada.selectBestThreshold();
		assertTrue(Double.compare(threshold, Calculator.mid(2.0, 3.5)) == 0);
		IterationData itData = ada.getIterationData();
		assertTrue(itData.getErrCnt() == 1);
		assertTrue(Double.compare(itData.getBoostComparator(), 0.25) == 0);
	}

	/*
	 * 10 10 1.0E-7
		0 1 2 3 4 5 6 7 8 9
		1 1 1 -1 -1 -1 1 1 1 -1 
		0.1 0.1 0.1 0.1 0.1 0.1 0.1 0.1 0.1 0.1  
	 */
	AdaInputData createInput2() {
		TextFile file = new TextFile("testfile/ada2.txt");
		AdaInputData data = new AdaInputData();
		AdaInputParser.parse(file.readLines(), data);
		return data;
	}
	
	@Test
	public void IterationCase2_1() {
		AdaInputData data = createInput2();
		AdaBoosting ada = new AdaBoosting();
		ada.init(data);
		ada.setY4Classifier(1);
		double threshold = ada.selectBestThreshold();
		assertTrue(Calculator.isEqual(threshold, Calculator.mid(2.0, 3.0)));
		IterationData itData = ada.getIterationData();
		assertTrue(itData.getErrCnt() == 3);
		assertTrue(Calculator.isEqual(itData.getBoostComparator(), 0.3));
		ada.calculateItData();
		assertTrue(Calculator.isEqual(itData.getAlpha(), 0.423649));
		assertTrue(Calculator.isEqual(itData.getQWrong(), 1.52753));
		assertTrue(Calculator.isEqual(itData.getQRight(), 0.654654));
		ada.updateProb();
		assertTrue(Calculator.isEqual(data.getProb(0), 0.07143));
		assertTrue(Calculator.isEqual(data.getProb(5), 0.07143));
		assertTrue(Calculator.isEqual(data.getProb(7), 0.16667));
		assertTrue(Calculator.isEqual(data.getProb(9), 0.07143));		
	}

	@Test
	public void IterationCase2_it1() {
		AdaInputData data = createInput2();
		AdaBoosting ada = new AdaBoosting();
		ada.init(data);
		ada.setY4Classifier(1);
		ada.iterate(1);
		List<IterationData> itData = ada.getAllIterationData();
		AdaOutputData outData = new AdaOutputData(data, itData);
		outData.print();
	}
	
	@Test
	public void IterationCase2_it2() {
		AdaInputData data = createInput2();
		AdaBoosting ada = new AdaBoosting();
		ada.init(data);
		ada.setY4Classifier(1);
		ada.iterate(2);
		List<IterationData> itData = ada.getAllIterationData();
		AdaOutputData outData = new AdaOutputData(data, itData);
		outData.print();
	}
	
	@Test
	public void IterationCase2_it3() {
		AdaInputData data = createInput2();
		AdaBoosting ada = new AdaBoosting();
		ada.init(data);
		ada.setY4Classifier(1);
		ada.iterate(3);
		List<IterationData> itData = ada.getAllIterationData();
		AdaOutputData outData = new AdaOutputData(data, itData);
		outData.print();
	}
	
	@Test
	public void IterationCase2_itmulti() {
		AdaInputData data = createInput2();
		AdaBoosting ada = new AdaBoosting();
		ada.init(data);
		ada.setY4Classifier(1);
		for (int i = 1; i <= 4; i++) {
			ada.iterate(i);
			List<IterationData> itData = ada.getAllIterationData();
			AdaOutputData outData = new AdaOutputData(data, itData);
			outData.print();
		}
	}
}
