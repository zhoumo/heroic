package mine.mythos.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static String upperFirstLetter(String targetStr) {
		String firstLetter = targetStr.substring(0, 1);
		return targetStr.replaceFirst(firstLetter, firstLetter.toUpperCase());
	}

	public static String lowerFirstLetter(String targetStr) {
		String firstLetter = targetStr.substring(0, 1);
		return targetStr.replaceFirst(firstLetter, firstLetter.toLowerCase());
	}

	public static String convertCommaSeparated(String targetStr, String sign) {
		String resultString = "";
		for (String value : targetStr.split(sign)) {
			if (StringUtils.isEmpty(value)) {
				continue;
			}
			resultString += value + ",";
		}
		if (resultString.endsWith(",")) {
			resultString = resultString.substring(0, resultString.length() - 1);
		}
		return resultString;
	}
}
