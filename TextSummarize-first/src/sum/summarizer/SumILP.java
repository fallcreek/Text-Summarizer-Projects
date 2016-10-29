package sum.summarizer;

import lpsolve.LpSolve;
import sum.data.Sentence;
import sum.util.Similarity;

import java.util.ArrayList;
import java.util.HashMap;

public class SumILP implements Summarizer, ConceptBasedSummarizer {
	
	/**
	 * A summary may be constrained by the number of sentences or the number of
	 * words. 
	 */
	public static int LENGTH_TYPE_SENT_NUM = 1;
	public static int LENGTH_TYPE_WORD_NUM = 2;
	
	private double optimum;
	private double [] variables;
	private int numConcept;
	
	/**
	 * If the similarity between sentences is great than sentSimThresh, then 
	 * at most one of them should be included in the resulting summary. This
	 * parameter is used in the sentence-level ILP. Default value is 0.5;
	 */
	private double sentSimThresh = 0.5;
	
	
	public SumILP() {		
	}	
	
	/**
	 * Generate a summary using the concept-level ILP. 
	 * @param sentList the input sentence list
	 * @param conceptScore the score of each concept
	 * @param length the length limit of the resulting summary
	 * @param lengthType the type of length constraint (either number of words
	 * or number of sentences)
	 * @return
	 */
	public ArrayList<Sentence> generateSummary(ArrayList<Sentence> sentList, 
			HashMap<String, Double> conceptScore, int length, int lengthType) {
		ArrayList<Sentence> summary = new ArrayList<Sentence>();
		
		try {
			solveConcept(sentList, conceptScore, length, lengthType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int vi = numConcept; vi < variables.length; vi++) {
			if (variables[vi] == 1) {
				summary.add(sentList.get(vi - numConcept));
			}
		}
		
		return summary;
	}
	
	/**
	 * Solve the concept-level ILP. 
	 */
	public void solveConcept(ArrayList<Sentence> sentList, 
			HashMap<String, Double> conceptScore, int length, int lengthType) 
		throws Exception 
	{
		// begin ILP
		LpSolve lp;
		this.numConcept = conceptScore.size();
		int numSent = sentList.size();
		int ncol = numConcept + numSent;
		double [] row = new double[ncol + 1];
		double [] rowConst = new double[ncol + 1];
		
		lp = LpSolve.makeLp(0, ncol);
        if(lp.getLp() == 0) {
        	System.err.println("Error when getLP.");
        	return;
        }
        
        lp.setAddRowmode(true);
        for(int i = 0; i < ncol; i++)
        	lp.setBinary(i+1, true);
        
        // add constraints, length constraint
        setZero(row);
        
        // length limit constraint	
        if(lengthType == LENGTH_TYPE_SENT_NUM) {
	        // notice that index of variables start from 1
	        for(int i = numConcept + 1; i <= ncol; i++)
	        	row[i] = 1;
        } else if(lengthType == LENGTH_TYPE_WORD_NUM) {
        	// notice that index of variables start from 1
        	for(int i = numConcept + 1; i <= ncol; i++)
            	row[i] = sentList.get(i - numConcept - 1).getNumWords();  
        }
        lp.addConstraint(row, LpSolve.LE, length); 
        
        // add constraints, concept-sentence consistency constraints
        int iconcept = 1;
        for(String term : conceptScore.keySet()) {
        	setZero(rowConst);
        	rowConst[iconcept] = -1;
        	
        	for(int j = 0; j < sentList.size(); j++) {
        		setZero(row);
        		row[iconcept] = -1;
        		if(sentList.get(j).getTermFreqMap().containsKey(term)) {
        			row[numConcept + j + 1] = 1;
        			rowConst[numConcept + j + 1] = 1;
        		}
        		else {
        			rowConst[numConcept + j + 1] = 0;
        		}
        		
        		lp.addConstraint(row, LpSolve.LE, 0); // constraint (1) 		
        	} // end j
        	
        	lp.addConstraint(rowConst, LpSolve.GE, 0);
        	iconcept++;
        }
              
        lp.setAddRowmode(false);
        
        // set object function
        setZero(rowConst);
        iconcept = 1;
        for(String term : conceptScore.keySet()) {
        	rowConst[iconcept] = conceptScore.get(term);
        	iconcept++;
        }        
        lp.setObjFn(rowConst);    
        lp.setMaxim();       
        
        
        //lp.writeLp("f:\\model.lp");
        lp.setVerbose(LpSolve.IMPORTANT);
        
        if(lp.solve() == LpSolve.OPTIMAL) {
        	this.optimum = lp.getObjective();
        	this.variables = new double[ncol];
        	lp.getVariables(variables);
        } else {
        	System.err.println("ILP cannot find optimal solution. ");
        	lp.getVariables(variables);
        }
        
        // free memory
        if(lp.getLp() != 0)
            lp.deleteLp();
	}
	
	/**
	 * Generate a summary using the sentence-level ILP. 
	 * @param sentList the input sentence list
	 * @param length the length limit of the resulting summary
	 * @param lengthType the type of length constraint (either number of words
	 * or number of sentences)
	 * @return
	 */
	public ArrayList<Sentence> generateSummary(ArrayList<Sentence> sentList, 
			int length, int lengthType) {
		ArrayList<Sentence> summary = new ArrayList<Sentence>();
		
		try {
			solve(sentList, length, lengthType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int vi = 0; vi < variables.length; vi++) {
			if (variables[vi] == 1) {
				summary.add(sentList.get(vi));
			}
		}
		
		return summary;
	}
	
	/**
	 * Solve the sentence-level ILP. 
	 */
	public void solve(ArrayList<Sentence> sentList, int length, 
			int lengthType) 
	throws Exception {
		LpSolve lp;
		int ncol = sentList.size();
		double [] row = new double[ncol + 1];
		
		lp = LpSolve.makeLp(0, ncol);
        if(lp.getLp() == 0) {
        	System.err.println("Error when getLP.");
        	return;
        }
        
        lp.setAddRowmode(true);
        for(int i = 0; i < ncol; i++)
        	lp.setBinary(i+1, true);
        
        // add constraints
        
        // the length constraint
        if(lengthType == LENGTH_TYPE_WORD_NUM) {
        	// notice that index of variables start from 1
	        for(int i = 0; i < ncol; i++)
	        	row[i + 1] = sentList.get(i).getNumWords(); 	          
        } else if (lengthType == LENGTH_TYPE_SENT_NUM){
        	for(int i = 0; i < ncol; i++)
	        	row[i + 1] = 1;	         
        }        
        lp.addConstraint(row, LpSolve.LE, length);  
        
        
        for(int i = 0; i < ncol - 1; i++) {
        	Sentence si = sentList.get(i);
        	for(int j = i+1; j < ncol; j++) {
        		Sentence sj = sentList.get(j);
        		double sim = Similarity.cosineSim(si, sj);   		
        		       		
        		for(int ri = 0; ri < ncol; ri++)
                	row[ri + 1] = 0;
        		
        		row[i+1] = sim;
        		row[j+1] = sim;
        		lp.addConstraint(row, LpSolve.LE, 2 * sentSimThresh);
        	}
        } // end for i
        
        lp.setAddRowmode(false);
        
        // set object function
        for(int i = 0; i < ncol; i++)
        	row[i + 1] = sentList.get(i).getScore();        
        lp.setObjFn(row);        
        lp.setMaxim();       
        
        
        //lp.writeLp("f:\\model.lp");
        lp.setVerbose(LpSolve.IMPORTANT);
        
        if(lp.solve() == LpSolve.OPTIMAL) {
        	this.optimum = lp.getObjective();
        	this.variables = new double[ncol];
        	lp.getVariables(variables);
        } else {
        	System.err.println("ILP cannot find optimal solution. ");
        	lp.getVariables(variables);
        }
        
        // free memory
        if(lp.getLp() != 0)
            lp.deleteLp();
	}
	
	void setZero(double [] arr) {
		for(int i = 0; i < arr.length; i++)
			arr[i] = 0;
	}
	
	public void setSentSimThresh(double val) {
		this.sentSimThresh = val;
	}
}
