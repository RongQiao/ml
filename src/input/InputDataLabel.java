package input;

import java.util.ArrayList;
import java.util.List;

import basicFile.InputLabelParser;

public class InputDataLabel {
	private int numRow;
	private int numColumn;	
	private List<LabelExample> examples;

	public InputDataLabel(List<String> labelSrc) {
		String numbers = labelSrc.get(0);
		InputLabelParser.parseHead(numbers, this);
		examples = new ArrayList<LabelExample>();
		for (int i = 1; i < labelSrc.size(); i++) {
			String line = labelSrc.get(i);
			List<LabelExample> exs = InputLabelParser.parse(i-1, line);
			examples.addAll(exs);
		}
	}

	public InputDataLabel() {
		// TODO Auto-generated constructor stub
	}

	public List<LabelExample> getExamples() {
		return examples;
	}

	public void setExamples(List<LabelExample> examples) {
		this.examples = examples;
	}

	public int getNumRow() {
		return numRow;
	}

	public void setNumRow(int numRow) {
		this.numRow = numRow;
	}

	public int getNumColumn() {
		return numColumn;
	}

	public void setNumColumn(int numColumn) {
		this.numColumn = numColumn;
	}


	public int getNumExamples() {
		return examples.size();
	}

	public char getExampleLabel(int index) {
		return examples.get(index).getLabel();
	}

	//index is the index in the example sequence
	public LabelExample getExample(int index) {
		return examples.get(index);
	}

	//x1, x2 is the coordinate 
	public LabelExample getExample(int x1, int x2) {
		LabelExample ex = null;
		for (int i = 0; i < examples.size(); i++) {
			LabelExample cE = examples.get(i);
			if ((cE.getX1() == x1) && (cE.getX2() == x2)) {
				ex = cE;
				break;
			}
		}
		return ex;
	}

}
