import input.InputData;
import input.InputDataLabel;
import input.LabelExample;

import java.util.ArrayList;
import java.util.List;

import output.OutputData;
import basicFile.InputLabelParser;
import basicFile.InputPermParser;
import basicFile.TextFile;
import kNeighbors.KNearestNeighbors;
import validation.AccuracyEstimate;
import validation.CrossValidation;


public class CvKnn {

	public static void main(String[] args) {
		int argN = args.length;
		if (argN >= 5) {
			String inFileNameP = args[1] + ".txt";
			String inFileNameL = args[2] + ".txt";
			String outFileName = args[4];
			
			TextFile filePerm = new TextFile(inFileNameP);
			TextFile fileLabel = new TextFile(inFileNameL);
			InputDataLabel label = new InputDataLabel();
			InputLabelParser.parse(fileLabel.readLines(), label);
			InputData data = new InputData();
			InputPermParser.parse(filePerm.readLines(), data);
			data.setExample(label);
			
			CrossValidation cv = new CrossValidation();
			cv.setKfold(data.getNumFolds());
			KNearestNeighbors kn = new KNearestNeighbors();
			List<LabelExample> trainData = data.getExamples();
			List<LabelExample> testData = data.getEmptyPoints();
			
			List<String> outText = new ArrayList<String>();
			for (int i = 1; i <= 5; i++) {
				kn.setK(i);
				
				AccuracyEstimate ae = cv.estimate(data, kn);
				String cvOut = "k=" + kn.getK();
				cvOut += " e=" + ae.getE();
				cvOut += " sigma=" + ae.getSigma();
				
				List<LabelExample> labeledData = kn.calculateLabel(testData, trainData);
				OutputData outData = new OutputData(data.getNumRow(), data.getNumColumn());
				outData.addLabels(labeledData);
				outData.addLabels(trainData);
				List<String> labels = outData.outputLabels();
								
				outText.add(cvOut);
				outText.addAll(labels);
				outText.add("");
			}
			String ofn = outFileName + ".txt";
			TextFile outFile = new TextFile(ofn);	
			outFile.clear();
			outFile.write(outText);
		}
		else {
			System.out.println("error: no enough parameters.");
		}	
	}

}
