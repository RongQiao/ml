package basicFile;

import input.AdaInputData;

import java.util.List;

public class AdaInputParser {

	public static void parse(List<String> lines, AdaInputData data) {
		parseHead(lines.get(0), data);
		int cnt = data.getItemNum();
		data.setX(parseRealNumber(lines.get(1), cnt));
		data.setY(parseIntNumber(lines.get(2), cnt));
		data.setProb(parseRealNumber(lines.get(3), cnt));
	}

	private static int[] parseIntNumber(String str, int itemCnt) {
		int ret[] = new int[itemCnt];
		int cnt = 0;
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (isSpace(ch)) {
				ret[cnt++] = Integer.parseInt(s.toString());
				s = new StringBuilder();
			}			
			else {
				s.append(ch);
			}
		}
		ret[cnt] = Integer.parseInt(s.toString());
		return ret;
	}

	private static double[] parseRealNumber(String str, int itemCnt) {
		double ret[] = new double[itemCnt];
		int cnt = 0;
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (isSpace(ch)) {
				ret[cnt++] = Double.parseDouble(s.toString());
				s = new StringBuilder();
			}			
			else {
				s.append(ch);
			}
		}
		ret[cnt] = Double.parseDouble(s.toString());
		return ret;
	}


	private static void parseHead(String str, AdaInputData data) {
		int begin = 0, cnt = 0;
		String num[] = new String[3];
		for (int i = 0; i < str.length(); i++) {
			if (isSpace(str.charAt(i))) {
				num[cnt] = str.substring(begin, i);
				begin = i + 1;
				cnt++;
			}
			else if (cnt == 2) {
				num[cnt] = str.substring(begin);
			}
		}
		data.setIterationNum(Integer.parseInt(num[0]));
		data.setItemNum(Integer.parseInt(num[1]));
		data.setSmoothNum(Double.parseDouble(num[2]));
	}

	private static boolean isSpace(char ch) {		
		return (ch == ' ') ? true : false;
	}

}
