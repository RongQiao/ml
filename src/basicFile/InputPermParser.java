package basicFile;

import java.util.ArrayList;
import java.util.List;

import input.InputData;

public class InputPermParser {
	private static final int HEAD_NUM_PERMFILE = 3;

	public static void parse(List<String> lines, InputData data) {		
		int head[] = parseHead(lines.get(0));
		if (head.length == HEAD_NUM_PERMFILE) {
			data.setNumFolds(head[0]);
			data.setNumExamples(head[1]);
			data.setNumPermutations(head[2]);
		}
		int perm[][] = new int[data.getNumPermutations()][data.getNumExamples()];
		int lineNo = 1;
		for (int i = 0; i < data.getNumPermutations(); i++) {
			parsePermutation(lines.get(lineNo++), perm[i]);
		}
		data.setPermutation(perm);
	}

	private static void parsePermutation(String content, int[] indexSequence) {
		List<String> numbers = new ArrayList<String>();
		RegularParser.putItemsToCollection(content, numbers);
		if (numbers.size() == indexSequence.length) {
			for (int i = 0; i < indexSequence.length; i++) {
				indexSequence[i] = Integer.parseInt(numbers.get(i));
			}
		}
	}

	private static int[] parseHead(String content) {
		int head[] = new int[HEAD_NUM_PERMFILE];
		List<String> numbers = new ArrayList<String>();
		RegularParser.putItemsToCollection(content, numbers);
		if (numbers.size() == HEAD_NUM_PERMFILE) {
			for (int i = 0; i < HEAD_NUM_PERMFILE; i++) {
				head[i] = Integer.parseInt(numbers.get(i));
			}
		}
		return head;
	}

}
