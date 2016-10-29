package sum.data;

import sum.util.SentenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Document {
	private String filepath;
	private String url;
	private String datetime;
	private String headline;
	
	private String title;
	HashSet<String> titleWords;
	ArrayList<Sentence> sentList;       // sentence list of the document
	HashMap<String, Double> tfMap;      // term frequency of this document
	int termCount;
	

	public Document(String title, String parsedTitle, String url, String datetime,
			ArrayList<Sentence>	sentList) {
		this.url = url;
		this.datetime = datetime;
		this.title = title;		
		this.sentList = sentList;
		
		titleWords = new HashSet<String>();
        if (SentenceUtil.isChineseSentence(title)) {
            titleWords.addAll(SentenceUtil.getChineseWords(parsedTitle));
        }
        else {
            titleWords.addAll(SentenceUtil.getEnglishWords(parsedTitle));
        }
        initTermFreq();
	}
	
	
	/**
	 * init term frequency
	 */
	private void initTermFreq() {
		termCount = 0;
		tfMap = new HashMap<String, Double>();
		
		for(Sentence sent : sentList) {
			for(String word : sent.getTermFreqMap().keySet()) {
				termCount++;
				if(!tfMap.containsKey(word))
					tfMap.put(word, new Double(1));
				else 
					tfMap.put(word, tfMap.get(word) + 1);				
			}
		}
    }
	
	////////////////////////////////////////////////////////////
	// get/set functions	
	public HashMap<String, Double> getTermFreqMap() { return tfMap; }
	public ArrayList<Sentence> getSentList() { return sentList; }
	public String getHeadline() { return headline; }
	public int getTermCount() { return termCount; }
	public void setTermCount(int count) { termCount = count; }
	public String getFilePath() { return filepath; }
	
	public HashSet<String> getTitleWords() {
		return titleWords;
	}
	
	public String getTitle() {
		return title;
	}
	
}
