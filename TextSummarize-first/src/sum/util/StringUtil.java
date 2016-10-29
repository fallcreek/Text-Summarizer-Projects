package sum.util;

public class StringUtil {
	public static String removeXmlTag(String str, String startTag, String endTag) {
		str =  str.replace(startTag, "").replace(endTag, "").trim();
		
		// Remove the space in Chinese that can not be removed by the trim() method.
		return str.replaceAll("　+", "");
	}
	
	public static String deleteWhitespace(String str) {
		str = str.replaceAll(" +", "");
		str = str.replaceAll("　+", "");
		return str;
	}
}
