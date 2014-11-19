package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kNeighbors.KNearestNeighbors;
import input.InputData;
import input.LabelExample;

import org.junit.Test;

import validation.AccuracyEstimate;
import validation.CrossValidation;

public class ValidationTest {

	@Test
	public void testFold() {
		InputData data = Hw1InputTest.getInputData();
		KNearestNeighbors kn = new KNearestNeighbors();
		kn.setK(1);
		CrossValidation cv = new CrossValidation();
		cv.setKfold(data.getNumFolds());
		List<LabelExample> examples = data.getExamples(1);
		Double E = cv.estimate(examples, kn);
		AccuracyEstimate ae = cv.estimate(data, kn);
		System.out.println("k = " + kn.getK());
		System.out.println("E = " + E);
		System.out.println("e = " + ae.getE());
		System.out.println("V = " + ae.getV());
		System.out.println("sigma = " + ae.getSigma());
	}

}
