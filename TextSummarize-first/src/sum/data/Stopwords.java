package sum.data;

import java.io.*;
import java.util.HashSet;

/**
 * Class that can test whether a given string is a stop word.
 *
 * @author Feng Jin (jinfengfeng@126.com)
 */
public class Stopwords {

	private static final long serialVersionUID = 1L;
	
	static String fileStopword = "data/stopwords/cn+en.txt";
	
	/** The hashmap containing the list of stopwords */
	static HashSet<String> m_Stopwords = null;
	
	public static void setStopWordsFile (String file) {
		Stopwords.fileStopword = file;
	}
	
	public static void loadStopwords (String filename_stopwords){
		
		m_Stopwords = new HashSet<String>();
        BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename_stopwords), "UTF-8"));
            String sw;
			while ((sw=br.readLine()) != null)  {
				sw = sw.trim();
				m_Stopwords.add(sw);   
			}

		} catch (IOException e) {
            System.err.println("stopword file not found!");
            System.exit(-1);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/** 
	 * Returns true if the given string is a stop word.
	 */
	public static boolean isStopword(String str) {
		if(m_Stopwords == null)
			loadStopwords(fileStopword);
		return m_Stopwords.contains(str.toLowerCase());
	}
	
	// test
	public static void main(String [] args) {
		System.out.println(Stopwords.isStopword("的"));
		for(String key : Stopwords.m_Stopwords)
			System.out.printf("%s|\n", key);
	}

}