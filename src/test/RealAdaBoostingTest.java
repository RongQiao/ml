package test;

import static org.junit.Assert.*;
import input.AdaInputData;

import java.util.List;

import org.junit.Test;

import output.AdaOutputData;
import output.RealAdaOutputData;
import basicFile.AdaInputParser;
import basicFile.TextFile;
import boosting.AdaBoosting;
import boosting.Calculator;
import boosting.IterationData;
import boosting.RealAdaBoosting;

public class RealAdaBoostingTest {

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
		RealAdaBoosting ada = new RealAdaBoosting();
		ada.init(data);
		ada.setY4Classifier(1);
		double threshold = ada.selectBestThreshold();
		assertTrue(Calculator.isEqual(threshold, Calculator.mid(2.0, 3.0)));
		IterationData itData = ada.getIterationData();
		ada.calculateItData();
		ada.updateProb();
		assertTrue(Calculator.isEqual(itData.getNormalizationFactor(), 0.6946));
		assertTrue(Calculator.isEqual(data.getProb(0), 0.001));
//		assertTrue(Calculator.isEqual(data.getProb(5), 0.07143));
//		assertTrue(Calculator.isEqual(data.getProb(7), 0.16667));
//		assertTrue(Calculator.isEqual(data.getProb(9), 0.07143));		
	}

	@Test
	public void IterationCase2_it1() {
		AdaInputData data = createInput2();
		RealAdaBoosting ada = new RealAdaBoosting();
		ada.init(data);
		ada.setY4Classifier(1);
		ada.iterate(1);
		List<IterationData> itData = ada.getAllIterationData();
		RealAdaOutputData outData = new RealAdaOutputData(data, itData);
		outData.print();
	}
	
	@Test
	public void IterationCase2_itmulti() {
		AdaInputData data = createInput2();
		RealAdaBoosting ada = new RealAdaBoosting();
		ada.init(data);
		ada.setY4Classifier(1);
		for (int i = 1; i <= 5; i++) {
			ada.iterate(i);
			List<IterationData> itData = ada.getAllIterationData();
			RealAdaOutputData outData = new RealAdaOutputData(data, itData);
			outData.print();			
		}
	}

}
