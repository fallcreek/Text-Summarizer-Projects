package sum.summarizer;

import sum.data.Document;
import sum.data.Sentence;
import sum.data.SentenceCmp;
import sum.data.TopicXml;
import sum.util.Similarity;

import java.util.*;

public class ChineseSum {
	
	static int SENT_THRESH_ILP = 60;
	static int LEN_LIMIT = 100;
	static double DF_THRESH = 0.2;
	static double SENT_DUPLICATE_THRESH = 0.85;
	
	// multiplier factors to enhance the impact of words occurs in document 
	// title and words in the first sentence
	static double TITLE_WORD_MULTIPLIER = 5;
	static double FIRST_SENTENCE_WORD_MULTIPLIER = 2.5;
	
	public String genSDS(Document doc, int lengthLimit, int limitType, int summaryMethod) {
		HashMap<String, Double> sf = new HashMap<String, Double>();

		// prepare the sf of each word
		for (Sentence sent : doc.getSentList()) {
			for (String key : sent.getTermFreqMap().keySet()) {
				if (sf.containsKey(key))
					sf.put(key, sf.get(key) + 1);
				else
					sf.put(key, 1.0);
			}
		}

		for (String key : sf.keySet())
			sf.put(key, sf.get(key) / doc.getSentList().size());

		// enhance the impact of title words
		for (String key : sf.keySet()) {
			if (doc.getTitleWords().contains(key))
				sf.put(key, sf.get(key) * TITLE_WORD_MULTIPLIER); // fixed
																	// parameter
		}

		// enhance the impact of words in first sentence
		HashMap<String, Double> map = doc.getSentList().get(0).getTermFreqMap();
		for (String key : sf.keySet()) {
			if (map.containsKey(key))
				sf.put(key, sf.get(key) * FIRST_SENTENCE_WORD_MULTIPLIER); // fixed
																			// parameter
		}

		// prepare sentList, remove low score sentences
		ArrayList<Sentence> sentList = new ArrayList<Sentence>();
		for (Sentence sent : doc.getSentList()) {
			double score = 0;
			for (String key : sent.getTermFreqMap().keySet()) {
				if (sf.containsKey(key))
					score += sf.get(key);
			}

			sent.setScore(score);
			sentList.add(sent);
		}

		Collections.sort(sentList, new SentenceCmp());
		for (int n = sentList.size() - 1; n >= SENT_THRESH_ILP; n--)
			sentList.remove(n);

		// generate summary
		HashSet<Sentence> summary = new HashSet<Sentence>();
		Summarizer summarizer;
		if (summaryMethod == Summarizer.SUMMARY_METHOD_MMR) {
			summarizer = new MMR();
			summary.addAll(summarizer.generateSummary(sentList, lengthLimit, limitType));
		}
		else if (summaryMethod == Summarizer.SUMMARY_METHOD_SUMILP) {
			SumILP sumILP = new SumILP();
			summary.addAll(sumILP.generateSummary(sentList, sf, lengthLimit, limitType));
			
		}
		else if (summaryMethod == Summarizer.SUMMARY_METHOD_DP) {
			summarizer = new DiversityPenalty();
			summary.addAll(summarizer.generateSummary(sentList, lengthLimit, limitType));
		}
		else {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		// print summary, the sentence order in the summary is the same as
		// in the original document
		for (Sentence sent : doc.getSentList()) {
			if (summary.contains(sent))
				sb.append(sent.getText());  //11.4 change by jianfa. 删除括号内的 + "\n"
		}
		return sb.toString();
	}
	
	public void genSDS(Document doc) {		
		HashMap<String, Double> sf = new HashMap<String, Double>();
		
		// prepare the sf of each word
		for(Sentence sent : doc.getSentList()) {
			for(String key : sent.getTermFreqMap().keySet()) {
				if(sf.containsKey(key))
					sf.put(key, sf.get(key) + 1);
				else
					sf.put(key, 1.0);
			}				
		}
		
		for(String key : sf.keySet())
			sf.put(key, sf.get(key) / doc.getSentList().size());
		
		// enhance the impact of title words
		for(String key : sf.keySet()) {
			if(doc.getTitleWords().contains(key))
				sf.put(key, sf.get(key) * TITLE_WORD_MULTIPLIER);  // fixed parameter
		}
		
		// enhance the impact of words in first sentence
		HashMap<String, Double> map = doc.getSentList().get(0).getTermFreqMap();
		for(String key : sf.keySet()) {
			if(map.containsKey(key))
				sf.put(key, sf.get(key) * FIRST_SENTENCE_WORD_MULTIPLIER); // fixed parameter
		}
		
		// prepare sentList, remove low score sentences
		ArrayList<Sentence> sentList = new ArrayList<Sentence>();
		for(Sentence sent : doc.getSentList()) {
			double score = 0;
			for(String key : sent.getTermFreqMap().keySet()) {
				if(sf.containsKey(key))
					score += sf.get(key);
			}
			
			sent.setScore(score);
			sentList.add(sent);
		}
		
		Collections.sort(sentList, new SentenceCmp());
		for(int n = sentList.size() - 1; n >= SENT_THRESH_ILP; n--)
			sentList.remove(n);
	
		// generate summary
		HashSet<Sentence> summary = new HashSet<Sentence>();
		SumILP ilp = new SumILP();
		
		summary.addAll(ilp.generateSummary(sentList, sf, 4, SumILP.LENGTH_TYPE_SENT_NUM));
					
		// print summary, the sentence order in the summary is the same as 
		// in the original document
		for(Sentence sent : doc.getSentList()) {
			if(summary.contains(sent))
				System.out.println(sent.getText());
		}
		System.out.println();
	}
	
	public String genMDS(ArrayList<Document> docList, int lengthLimit,
			int limitType, int summaryMethod, int selectMethod) {
		// prepare df
		HashMap<String, Double> df = prepareConcept(docList, DF_THRESH);

		// prepare sentList
        ArrayList<Sentence> sentList;
        if (selectMethod == 1) {
            sentList = prepareSentences5(docList, df);
        }
        else if (selectMethod == 2) {
            sentList = prepareSentences1(docList, df);
        }
        else {
            sentList = prepareSentences(docList, df);
        }

		// generate summary
		ArrayList<Sentence> summary;
		Summarizer summarizer;
		if (summaryMethod == Summarizer.SUMMARY_METHOD_MMR) {
			summarizer = new MMR();
			summary = summarizer.generateSummary(sentList, lengthLimit, limitType);
		}
		else if (summaryMethod == Summarizer.SUMMARY_METHOD_SUMILP) {
			SumILP sumILP = new SumILP();
			summary = sumILP.generateSummary(sentList, df, lengthLimit, limitType);
			
		}
		else if (summaryMethod == Summarizer.SUMMARY_METHOD_DP) {
			summarizer = new DiversityPenalty();
			summary = summarizer.generateSummary(sentList, lengthLimit, limitType);
		}
		else {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		// output the summary for debug
		for (Sentence sent : summary)
			sb.append(sent.getText()).append("\n");
		return sb.toString();
	}
	
	public void genMDS(ArrayList<Document> docList) {
		// prepare df
		 HashMap<String, Double> df = prepareConcept(docList, DF_THRESH);
		
		// prepare sentList
		 ArrayList<Sentence> sentList = prepareSentences1(docList, df);
		
		// generate summary
		SumILP ilp = new SumILP();
		ArrayList<Sentence> summary = ilp.generateSummary(sentList, df, LEN_LIMIT, 
				SumILP.LENGTH_TYPE_WORD_NUM);
		
		// output the summary for debug
		for(Sentence sent : summary)
			System.out.println(sent.getText());
	}
	
	HashMap<String, Double> prepareConcept(ArrayList<Document> docList, 
			double discardThresh) {
		HashMap<String, Double> df = new HashMap<String, Double>();
		for(Document doc : docList) {
			HashMap<String, Double> map = doc.getTermFreqMap();
			for(String key : map.keySet()) {
				if(df.containsKey(key))
					df.put(key, df.get(key) + 1);
				else
					df.put(key, 1.0);
			}
		}
		for(String key : df.keySet())
			df.put(key, df.get(key) / docList.size());
		
		for(Iterator<String> it = df.keySet().iterator(); it.hasNext(); ) {
			String key = it.next();
			if(df.get(key) < discardThresh || key.length() < 2)
				it.remove();
		}
		
		return df;
	}
	
	ArrayList<Sentence> prepareSentences(ArrayList<Document> docList, 
			HashMap<String, Double> df) {
		ArrayList<Sentence> sentList = new ArrayList<Sentence>();
		for(Document doc : docList) {

			for(Sentence sent : doc.getSentList()) {
				double score = 0;
				for(String key : sent.getTermFreqMap().keySet()) {
					if(df.containsKey(key))
						score += df.get(key);
				}
				
				sent.setScore(score);
				sentList.add(sent);
			}
		}

        // remove duplicate sentences
        for(int i = sentList.size() - 1; i > 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                if(Similarity.cosineSim(sentList.get(i), sentList.get(j))
                        >= SENT_DUPLICATE_THRESH) {
                    sentList.remove(i);
                    break;
                }
            }
        }
		
		Collections.sort(sentList, new SentenceCmp());
		for(int n = sentList.size() - 1; n >= SENT_THRESH_ILP; n--)
			sentList.remove(n);
		
		return sentList;
	}
	
	/**
	 * Keep only the first sentence of each document and discard duplicate sentences.
	 * @param docList document list
	 * @param df df hash map
	 * @return sentences list
	 */
	ArrayList<Sentence> prepareSentences1(ArrayList<Document> docList,
                                          HashMap<String, Double> df) {
		ArrayList<Sentence> sentList = new ArrayList<Sentence>();
		for(Document doc : docList) {

			Sentence sent = doc.getSentList().get(0);
			double score = 0;
			for(String key : sent.getTermFreqMap().keySet()) {
				if(df.containsKey(key))
					score += df.get(key);
			}
			
			sent.setScore(score);
			sentList.add(sent);
		}
		
		//System.out.println("#sentences: " + sentList.size());
		
		// remove duplicate sentences
		for(int i = sentList.size() - 1; i > 0; i--) {
			for(int j = i - 1; j >= 0; j--) {
				if(Similarity.cosineSim(sentList.get(i), sentList.get(j))
						>= SENT_DUPLICATE_THRESH) {
					sentList.remove(i);
					break;
				}
			}
		}
		
		//System.out.println("# left sentences: " + sentList.size());
		
		Collections.sort(sentList, new SentenceCmp());
		for(int n = sentList.size() - 1; n >= SENT_THRESH_ILP; n--)
			sentList.remove(n);
		
		return sentList;
	}

    /**
     * Keep only the five sentence of each document and discard duplicate sentences.
     * @param docList document list
     * @param df hash map
     * @return array list
     */
    ArrayList<Sentence> prepareSentences5(ArrayList<Document> docList,
                                          HashMap<String, Double> df) {
        ArrayList<Sentence> sentList = new ArrayList<Sentence>();
        for(Document doc : docList) {
            int size = doc.getSentList().size() < 5 ? doc.getSentList().size() : 5;
            for(int i=0; i<size; i++) {
                Sentence sent = doc.getSentList().get(i);
                double score = 0;
                for(String key : sent.getTermFreqMap().keySet()) {
                    if(df.containsKey(key))
                        score += df.get(key);
                }
                sent.setScore(score);
                sentList.add(sent);
            }
        }

        //System.out.println("#sentences: " + sentList.size());

        // remove duplicate sentences
        for(int i = sentList.size() - 1; i > 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                if(Similarity.cosineSim(sentList.get(i), sentList.get(j))
                        >= SENT_DUPLICATE_THRESH) {
                    sentList.remove(i);
                    break;
                }
            }
        }

        //System.out.println("# left sentences: " + sentList.size());

        Collections.sort(sentList, new SentenceCmp());
        for(int n = sentList.size() - 1; n >= SENT_THRESH_ILP; n--)
            sentList.remove(n);

        return sentList;
    }
	
	public static void main(String[] args) {
		ChineseSum cs = new ChineseSum();
		
		System.out.println("======= 多文档摘要 =======");
		cs.genMDS(TopicXml.getDocumentList("data/mrg.xml"));
		
		System.out.println("\n======= 第一篇文档的摘要 ========");
		cs.genSDS(TopicXml.getDocumentList("data/mrg.xml").get(0));
	}

}
