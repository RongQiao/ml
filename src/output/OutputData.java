package output;

import input.LabelExample;

import java.util.ArrayList;
import java.util.List;

import basicFile.InputLabelParser;

public class OutputData {
	LabelExample examples[][];

	public OutputData(int numRow, int numColumn) {
		examples = new LabelExample[numRow][numColumn];
	}

	public void addLabels(List<LabelExample> labeledData) {
		for (int i = 0; i < labeledData.size(); i++) {
			LabelExample ex = labeledData.get(i);
			int row = ex.getX2();
			int col = ex.getX1();
			examples[row][col] = ex;
		}
	}

	public List<String> outputLabels() {
		List<String> lbs = new ArrayList<String>();
		for (int i = 0; i < examples.length; i++) {
			String line = new String();
			for (int j = 0; j < examples[i].length; j++) {
				LabelExample ex = examples[i][j];
				if (ex != null) {
					line += ex.getLabel();
				}
				else {
					line += InputLabelParser.NOT_LABEL;
				}
				line += " ";
			}
			lbs.add(line);
		}
		return lbs;
	}

}
