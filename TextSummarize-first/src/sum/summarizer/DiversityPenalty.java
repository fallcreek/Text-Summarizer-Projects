package sum.summarizer;

import sum.data.Sentence;
import sum.data.SentenceCmp;
import sum.util.Scale;
import sum.util.Similarity;

import java.util.ArrayList;
import java.util.Collections;

public class DiversityPenalty implements Summarizer {
	
	/**
	 * A summary may be constrained by the number of sentences or the number of
	 * words. 
	 */
	public static int LENGTH_TYPE_SENT_NUM = 1;
	public static int LENGTH_TYPE_WORD_NUM = 2;
	
	// the penalizing factor, default value is 0.7
	private double omega = 0.7;
	
	/**
	 * Generate summary using the Diversity Penalty method.
	 * @param sentList the input sentences
	 * @param length length (number of words) constraint of the resulting summary
	 * @param lengthType whether a summary is constrained by the number of 
	 * sentences or the number of words
	 * @return a list of sentences as summary
	 */
	public ArrayList<Sentence> generateSummary(
			ArrayList<Sentence> sentList, int length, int lengthType) {
		Scale.scale(sentList);

		int currentLen = 0;
		ArrayList<Sentence> sel = new ArrayList<Sentence>();

		while (!sentList.isEmpty()) {
			Sentence sent = penalize(sentList);
			
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
		return sel;
	}

	/**
	 * Return the top ranked sentence and penalize the other sentences according
	 * to their similarity with the top sentence.
	 * @param sentList
	 * @param omegar the penalizing factor
	 * @return
	 */
	Sentence penalize(ArrayList<Sentence> sentList) {
		Collections.sort(sentList, new SentenceCmp());
		Sentence ret = sentList.get(0);
		sentList.remove(0);

		for (int i = 0; i < sentList.size(); i++) {
			Sentence s = sentList.get(i);			
			double val = s.getScore() - omega * Similarity.cosineSim(ret, s);
			s.setScore(val);
		}

		return ret;
	}
	
	public void setOmega(double omega) {
		this.omega = omega;
	}
}
