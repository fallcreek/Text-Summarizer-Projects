package sum.data;

import drasys.or.matrix.SparseMatrix;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * for a document cluster
 */
public class DocSet {
	ArrayList<Document> docList;
	SparseMatrix matrix;          // term-document matrix
	ArrayList<String> vac;        // vocabulary
	
	// the index of each term in the vacabulary
	HashMap<String, Integer> termIndex; 
	
	// document frequency
	HashMap<String, Double> dfMap;
	
	public DocSet(ArrayList<Document> docList) {
		this.docList = docList;
		//dfMap = new HashMap<String, Double>();
		initMatrix();
	}
	
	// Initialize the term-document matrix. 
	public void initMatrix() {
		vac = new ArrayList<String>();
		termIndex = new HashMap<String, Integer>();
		
		// init vacabulary
		for(Document doc : docList) {
			HashMap<String, Double> docTf = doc.getTermFreqMap();
			for(String term : docTf.keySet()) {
				if(!termIndex.containsKey(term)) {
					termIndex.put(term, vac.size());
					vac.add(term);
				} 
			}
		}
		
		// init matrix
		int indc = 0;
		matrix = new SparseMatrix(vac.size(), docList.size());
		for(Document doc : docList) {
			HashMap<String, Double> docTf = doc.getTermFreqMap();
			for(String term : docTf.keySet()) {
				matrix.setElementAt(termIndex.get(term), indc, docTf.get(term));
			}
			indc++;
		}
	}
	
	public void initDf() {
		for(Document doc : docList) {
			for(String word : doc.tfMap.keySet()) {
				if(!dfMap.containsKey(word))
					dfMap.put(word, new Double(1));
				else
					dfMap.put(word, dfMap.get(word) + 1);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	// get/set functions
	public void addDocument(Document doc) { docList.add(doc); }
	public ArrayList<Document> getDocList() { return docList; }
	public double getIdfByWord(String word) { return 0; }
	public HashMap<String, Double> getDfMap() { return dfMap; }
	
	public SparseMatrix getMatrix() { return matrix; }
	public HashMap<String, Integer> getTermIndex() { return termIndex; }
	public ArrayList<String> getVacabulary() { return vac; }
	
}
