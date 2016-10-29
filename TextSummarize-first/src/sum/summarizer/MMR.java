package sum.summarizer;

import sum.data.Sentence;
import sum.util.Scale;
import sum.util.Similarity;

import java.util.ArrayList;

public class MMR implements Summarizer {
	
	/**
	 * A summary may be constrained by the number of sentences or the number of
	 * words. 
	 */
	public static int LENGTH_TYPE_SENT_NUM = 1;
	public static int LENGTH_TYPE_WORD_NUM = 2;
	
	// the parameter that balance sentence relevance (score) and
	// redundancy (similarity between sentences), default is 0.6
	private double lambda = 0.4;
	
	/**
	 * Generate summary using the Maximal Marginal Relevance method. 
	 * @param sentList the input sentences
	 * @param length length (number of words) constraint of the resulting summary
	 * @param lengthType whether a summary is constrained by the number of 
	 * sentences or the number of words
	 * @return a list of sentences as summary
	 */
	public ArrayList<Sentence> generateSummary(
			ArrayList<Sentence> sentList, int length, int lengthType) 
	{
		Scale.scale(sentList);
		
		int currentLen = 0;
		ArrayList<Sentence> sel = new ArrayList<Sentence>();
		sel.add(sentList.get(0));
		
		if (lengthType == LENGTH_TYPE_WORD_NUM) {
			currentLen = sentList.get(0).getNumWords();
		}
		else if(lengthType == LENGTH_TYPE_SENT_NUM){
			currentLen++;
		}
		
		sentList.remove(0);
		while (!sentList.isEmpty()) {
			Sentence sent = getMmrSent(sel, sentList, 1);

			if(lengthType == LENGTH_TYPE_WORD_NUM) {
				if (currentLen + sent.getNumWords() <= length) {
					sel.add(sent);
					currentLen += sent.getNumWords();
				}
			} else if(lengthType == LENGTH_TYPE_SENT_NUM) {
				if(currentLen < length) {
					sel.add(sent);
					currentLen++;
				}
			}
		}
		return sel;  //sel为已选择的句子
	}

	public ArrayList<Sentence> generateSummaryForMLP(
			ArrayList<Sentence> sentList, int length, int lengthType) 
	{	
		int currentLen = 0;
		ArrayList<Sentence> sel = new ArrayList<Sentence>();
		sel.add(sentList.get(0));
		
		if (lengthType == LENGTH_TYPE_WORD_NUM) {
			currentLen = sentList.get(0).getNumWords();
		}
		else if(lengthType == LENGTH_TYPE_SENT_NUM){
			currentLen++;
		}
		
		sentList.remove(0);
		while (!sentList.isEmpty()) {
			Sentence sent = getMmrSent(sel, sentList, 2);

			if(lengthType == LENGTH_TYPE_WORD_NUM) {
				if (currentLen + sent.getNumWords() <= length) {
					sel.add(sent);
					currentLen += sent.getNumWords();
				}
			} else if(lengthType == LENGTH_TYPE_SENT_NUM) {
				if(currentLen < length) {
					sel.add(sent);
					currentLen++;
				}
			}
		}
		return sel;  //sel为已选择的句子
	}
	
	/**
	 * Select the sentence that has maximal marginal relevance.
	 * @param sel
	 * @param sentList
	 * @return
	 */
	Sentence getMmrSent(ArrayList<Sentence> sel,
			ArrayList<Sentence> sentList, int type) {
		double maxRel;
		double maxMmr;
		int maxPos = 0;
		maxMmr = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < sentList.size(); i++) {
			Sentence sent = sentList.get(i);
			Sentence maxRelSent = null;
			maxRel = Double.NEGATIVE_INFINITY;

			for (Sentence selSent : sel) {
				double rel = Similarity.cosineSim(sent.getTermFreqMap(),
						selSent.getTermFreqMap());  //rel 相关性？
//				System.out.println("相关:"+rel);
//				System.out.println(sent.getText());
//				System.out.println(selSent.getText());
				
				if (rel > maxRel) {
					maxRel = rel;
					maxRelSent = selSent;
				}
			}

			double mmr = 0;
			if(type == 1)
			{
				mmr = lambda * sent.getScore()
						- (1 - lambda)
						* Similarity.cosineSim(sent.getTermFreqMap(), maxRelSent
								.getTermFreqMap());
			}
			else if(type == 2)
			{
				//System.out.println(sent.getScore());
				mmr = -1 * Similarity.cosineSim(sent.getTermFreqMap(), maxRelSent
								.getTermFreqMap());
			}
			
			if (mmr > maxMmr) {
				maxMmr = mmr;
				maxPos = i;
				
			}
		}
		Sentence ret = sentList.get(maxPos);
		sentList.remove(maxPos);

		return ret;
	}
	
	public void setLambda(double lambda) {
		this.lambda = lambda;
	}
}
