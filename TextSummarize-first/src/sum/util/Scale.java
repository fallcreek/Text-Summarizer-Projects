package sum.util;

import sum.data.Sentence;

import java.util.ArrayList;

public class Scale {

	/**
	 * Scale the score of the input sentences to interval [0, 1]
	 * @param sentList
	 */
	public static void scale(ArrayList<Sentence> sentList) {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;

		for (Sentence sent : sentList) {
			double val = sent.getScore();
			if (val < min)
				min = val;
			if (val > max)
				max = val;
		}

		for (Sentence sent : sentList) {
			double newScore = (sent.getScore() - min) / (max - min);
			sent.setScore(newScore);
		}
	}
}
