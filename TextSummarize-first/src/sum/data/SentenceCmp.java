package sum.data;

import java.util.Comparator;

public class SentenceCmp implements Comparator<Sentence>{

	public int compare(Sentence s1, Sentence s2) {
		if(s1.getScore() < s2.getScore())
			return 1;
		else if(s1.getScore() > s2.getScore())
			return -1;
		else
			return 0;
	}

	
	
}
