package sum.util;

import java.util.HashMap;

public class Score {
	public static double innerProduct(HashMap<String, Double> weight, HashMap<String, Double> sent) {
		double sum = 0;
		for(String key : weight.keySet()) 
			if(sent.containsKey(key))
				sum += weight.get(key) * sent.get(key);
		
		return sum;
	}
}
