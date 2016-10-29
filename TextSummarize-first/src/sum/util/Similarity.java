package sum.util;

import sum.data.Sentence;

import java.util.HashMap;

public class Similarity {
	
	/**
	 * Computer the cosine similarity. 
	 * @param m1
	 * @param m2
	 * @return
	 * modify by lizhengyao 0309
	 */
	public static double cosineSim(HashMap<String, Double> m1, HashMap<String, Double> m2) {
		double product = 0;
		double len1 = 0; 
		double len2 = 0;
		
		
		for(String word : m1.keySet()) {
			
			if(word.length() < 1)
			{
				continue;
			}
			
			if(m2.containsKey(word))
				product += m1.get(word).doubleValue() * m2.get(word).doubleValue();
			len1 += m1.get(word).doubleValue() * m1.get(word).doubleValue();
		}
		
		
		for(String word : m2.keySet())
		{
			if(word.length() < 1)
			{
				continue;
			}
			
			len2 += m2.get(word).doubleValue() * m2.get(word).doubleValue();
		}
			
		
		if(Math.abs(product) < 1e-8)
			return 0;
		
		//double result = product / Math.sqrt(len1 * len2);
		
		return product / Math.sqrt(len1 * len2);
	}
	
	
	/**
	 * m1 and m2 must have the same set of keys
	 */
	public static double euclidDist(HashMap<String, Double> m1, HashMap<String, Double> m2) {
		double dist = 0;		
		
		for(String key : m1.keySet()) {
			dist += (m1.get(key) - m2.get(key)) * (m1.get(key) - m2.get(key));
		}
				
		return Math.sqrt(dist);
	}
	
	/**
	 * cosine similarity between two sentences
	 */
	public static double cosineSim(Sentence s1, Sentence s2) {				
		return cosineSim(s1.getTermFreqMap(), s2.getTermFreqMap());
	}
	
	/**
	 * The dice similarity of two sentences. 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static double diceSim(Sentence s1, Sentence s2) {
		double sum = 0;
		for(String word : s1.getTermFreqMap().keySet())
			if(s2.getTermFreqMap().containsKey(word))
				sum++;
		return 2 * sum / (s1.getTermFreqMap().size() + s2.getTermFreqMap().size());
	}
	
	/**
	 * Normalize the given vector 
	 */
	public static void normalize(HashMap<String, Double> vec) {
		double norm = 0;
		
		for(String key : vec.keySet())
			norm += vec.get(key) * vec.get(key);
		
		norm = Math.sqrt(norm);
		for(String key : vec.keySet())
			vec.put(key, vec.get(key) / norm);		
	}
	
	/**
	 * Compute the second norm of a given vector.
	 */
	public static double norm2(HashMap<String, Double> vec) {
		double norm = 0;
		
		for(String key : vec.keySet())
			norm += vec.get(key) * vec.get(key);
		
		return Math.sqrt(norm);
	}
}
