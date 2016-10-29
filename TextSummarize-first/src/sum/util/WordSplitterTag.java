package sum.util;

import java.util.HashMap;

public class WordSplitterTag {
	public static String punctuationSymbol = "/w";
	
	public static void setTagValue(HashMap<String, String> tagValueMap) {

		if (tagValueMap.containsKey("punctuationSymbol")) {
			punctuationSymbol = tagValueMap.get("punctuationSymbol");
		}
	}

}
