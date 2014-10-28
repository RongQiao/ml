package basicFile;

import input.InputDataLabel;
import input.LabelExample;

import java.util.ArrayList;
import java.util.List;

public class InputLabelParser {
	public static final char NOT_LABEL = '.';
	private static final char SPACE = ' ';
	public static final char POSITIVE = '+';
	public static final char NEGATIVE = '-';

	public static boolean parseHead(String str, InputDataLabel label) {
		boolean ret = false;
		if (str.length() >= 3) {
			String c1 = str.substring(0, 1);
			label.setNumRow(Integer.parseInt(c1));
			String c2 = str.substring(2, 3);
			label.setNumColumn(Integer.parseInt(c2));
			ret = true;
		}
		return ret;
	}

	public static List<LabelExample> parse(int rowIndex, String line) {
		List<LabelExample> exs = new ArrayList<LabelExample>();
		int columnIndex = 0;
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if (ch != SPACE) {
				if ((ch == POSITIVE) || (ch == NEGATIVE)) {				
					LabelExample ex = new LabelExample(columnIndex, rowIndex, ch);
					exs.add(ex);
				}
				columnIndex++;
			}

		}
		return exs;
	}

	public static void parse(List<String> labelSrc, InputDataLabel label) {
		String numbers = labelSrc.get(0);
		parseHead(numbers, label);
		List<LabelExample> examples = new ArrayList<LabelExample>();
		for (int i = 1; i < labelSrc.size(); i++) {
			String line = labelSrc.get(i);
			List<LabelExample> exs = InputLabelParser.parse(i-1, line);
			examples.addAll(exs);
		}
		label.setExamples(examples);
	}

}
