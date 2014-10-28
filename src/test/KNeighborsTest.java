package test;

import static org.junit.Assert.*;

import java.util.List;

import kNeighbors.KNearestNeighbors;
import input.InputData;
import input.LabelExample;

import org.junit.Test;

import basicFile.TextFile;
import output.OutputData;

public class KNeighborsTest {

	@Test
	public void testKneighbor() {
		KNearestNeighbors kn = new KNearestNeighbors();
		InputData data = InputTest.getInputData();
		List<LabelExample> trainData = data.getExamples(1);
		char l = kn.calculateLabel(3,2,1,trainData);
		assertTrue(l == '-');
		l = kn.calculateLabel(0, 2, 1, trainData);
		assertTrue(l == '+');
		l = kn.calculateLabel(2, 1, 4, trainData);
		assertTrue(l == '+');
	}

	
	@Test
	public void testLabelEmptyPoint() {
		KNearestNeighbors kn = new KNearestNeighbors();
		kn.setK(1);
		InputData data = InputTest.getInputData();
		List<LabelExample> trainData = data.getExamples();
		List<LabelExample> testData = data.getEmptyPoints();
		List<LabelExample> labeledData = kn.calculateLabel(testData, trainData);
		OutputData outData = new OutputData(data.getNumRow(), data.getNumColumn());
		outData.addLabels(labeledData);
		outData.addLabels(trainData);
		List<String> labels = outData.outputLabels();
		for (int i = 0; i < labels.size(); i++) {
			System.out.println(labels.get(i));
		}
		TextFile outFile = new TextFile("testfile/o1.txt");
		outFile.write(labels);
	}
	
}
