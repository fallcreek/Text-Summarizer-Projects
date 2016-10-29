package sum.summarizer;

import sum.data.Sentence;

import java.util.ArrayList;

public interface Summarizer {
	public static final int SUMMARY_METHOD_MMR = 1;
	public static final int SUMMARY_METHOD_SUMILP = 2;
	public static final int SUMMARY_METHOD_DP = 3;
//	public static final int SUMMARY_METHOD_NEW = 4;
	
	/**
	 * Given a list of ranked (scored) sentences, a summarizer should extract a
	 * subset of salient sentences to form the summary. 
	 * @param sentList the input sentences
	 * @param length summary length
	 * @param lengthType the type of summary length, either in number of words 
	 * or in number of sentences
	 * @return a list of extracted sentences as summary
	 */
	public ArrayList<Sentence> generateSummary(
			ArrayList<Sentence> sentList, 
			int length, 
			int lengthType);
	
}
