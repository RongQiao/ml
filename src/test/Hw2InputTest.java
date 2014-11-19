package test;

import static org.junit.Assert.*;
import input.AdaInputData;
import input.InputData;
import input.InputDataLabel;

import org.junit.Test;

import basicFile.AdaInputParser;
import basicFile.InputLabelParser;
import basicFile.InputPermParser;
import basicFile.TextFile;

public class Hw2InputTest {

	@Test
	public void readInputFileTest() {
		TextFile file = new TextFile("testfile/ada.txt");
		AdaInputData data = new AdaInputData();
		AdaInputParser.parse(file.readLines(), data);
		printData(data);
	}

	private void printData(AdaInputData data) {
		String s = data.getIterationNum() + " " + data.getItemNum() + " " + data.getSmoothNum();
		System.out.println(s);
		StringBuilder sX = new StringBuilder();
		StringBuilder sY = new StringBuilder();
		StringBuilder sP = new StringBuilder();
		double x[] = data.getX();
		int y[] = data.getY();
		double p[] = data.getProb();
		for (int i = 0; i < data.getItemNum(); i++) {
			sX.append(x[i]);
			sX.append(" ");
			sY.append(y[i]);
			sY.append(" ");
			sP.append(p[i]);
			sP.append(" ");
		}
		System.out.println(sX.toString());
		System.out.println(sY.toString());
		System.out.println(sP.toString());
	}

}
