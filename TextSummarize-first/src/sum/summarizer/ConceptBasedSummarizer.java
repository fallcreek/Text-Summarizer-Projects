package sum.summarizer;

import sum.data.Sentence;

import java.util.ArrayList;
import java.util.HashMap;

public interface ConceptBasedSummarizer {
	/**
	 * Given the sentences and the scored concepts (text units), a concept based
	 * summarizer extracts a subset of sentences that cover as many important
	 * (high score) concepts as to form a summary. A typical example of such
	 * summarizer is based on concept-level Integer Linear Programming.
	 * @param sentList the input sentence list
	 * @param conceptScore the score of each concept
	 * @param length the length limit of the resulting summary
	 * @param lengthType the type of length constraint (either number of words
	 * or number of sentences)
	 * @return
	 */
	public ArrayList<Sentence> generateSummary(
			ArrayList<Sentence> sentList, 
			HashMap<String, Double> conceptScore, 
			int length, 
			int lengthType);
}
