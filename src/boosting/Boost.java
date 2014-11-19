package boosting;

import input.AdaInputData;

import java.util.List;

import basicFile.AdaInputParser;
import basicFile.TextFile;
import output.AdaOutputData;
import output.RealAdaOutputData;

public class Boost {

	public static void main(String[] args) {
		if (args.length >= 3) {
			String fileName = args[1];
			int Y = Integer.parseInt(args[2]);
			if (args[0].compareToIgnoreCase("-r")==0) {
				realAdaBoost(fileName, Y);
			}
			else {
				adaBoost(fileName, Y);
			}
				
		}
		else {
			System.out.println("please provide parameters");
		}
	}

	private static void realAdaBoost(String fileName, int y) {
		TextFile file = new TextFile(fileName);
		AdaInputData data = new AdaInputData();
		AdaInputParser.parse(file.readLines(), data);
		
		RealAdaBoosting ada = new RealAdaBoosting();
		ada.init(data);
		ada.setY4Classifier(y);
		for (int i = 1; i <= data.getIterationNum(); i++) {
			ada.iterate(i);
			List<IterationData> itData = ada.getAllIterationData();
			RealAdaOutputData outData = new RealAdaOutputData(data, itData);
			outData.print();			
		}
	}

	private static void adaBoost(String fileName, int y) {
		TextFile file = new TextFile(fileName);
		AdaInputData data = new AdaInputData();
		AdaInputParser.parse(file.readLines(), data);

		AdaBoosting ada = new AdaBoosting();
		ada.init(data);
		ada.setY4Classifier(y);
		for (int i = 1; i <= data.getIterationNum(); i++) {
			ada.iterate(i);
			List<IterationData> itData = ada.getAllIterationData();
			AdaOutputData outData = new AdaOutputData(data, itData);
			outData.print();
		}
	}
	

}
