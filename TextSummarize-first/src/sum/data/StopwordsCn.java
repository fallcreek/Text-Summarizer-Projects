package sum.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * Class that can test whether a given string is a stop word.
 *
 * @author Feng Jin (jinfengfeng@126.com)
 */
public class StopwordsCn {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static String fileStopword = "data/stopwords/cn.txt";
	
	/** The hashmap containing the list of stopwords */
	static HashSet<String> m_Stopwords = null;
	
	public static void setStopWordsFile (String file) {
		StopwordsCn.fileStopword = file;
	}
	
	public static void loadStopwords (String filename_stopwords){
		
		m_Stopwords = new HashSet<String>();
		File txt = new File(filename_stopwords);	
		InputStreamReader is;
		String sw = null;
		try {
			is = new InputStreamReader(new FileInputStream(txt), "UTF-8");
			BufferedReader br = new BufferedReader(is);				
			while ((sw=br.readLine()) != null)  {
				sw = sw.trim();
				m_Stopwords.add(sw);   
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	/** 
	 * Returns true if the given string is a stop word.
	 */
	public  static boolean isStopword(String str) {
		if(m_Stopwords == null)
			loadStopwords(fileStopword);
		return m_Stopwords.contains(str.toLowerCase());
	}
	
	// test
	public static void main(String [] args) {
		System.out.println(StopwordsCn.isStopword("的"));
		for(String key : StopwordsCn.m_Stopwords)
			System.out.printf("%s|\n", key);
	}
}