package sum.summarizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import sum.data.Sentence;
import sum.util.Similarity;
import lpsolve.*;

public class ILP_MLP implements Summarizer{
	/*
	 * A summary may be constrained by the number of sentences or the number of
	 * words. 
	 */
	public static int LENGTH_TYPE_SENT_NUM = 1;
	public static int LENGTH_TYPE_WORD_NUM = 2;
	
	
	public int L = 500;  //maximum length of all summarizations  //modify by lizhengyao 0309
	
	public ArrayList<Sentence> generateSummary(ArrayList<Sentence> WaitingList, int length, int lengthType)
	{
		
		try{
			LpSolve lp;
			int numSentence = WaitingList.size();
			int nCol = numSentence;
			int sCol = (nCol * nCol - nCol) / 2;  //modify by lizhengyao 0309   
			System.out.println("sCol="+ sCol);
			
			double[] sim = countSim(WaitingList,sCol);	//modify by lizhengyao 0309
			double[] row = new double[nCol + sCol + 1]; //modify by lizhengyao 0309
			lp = LpSolve.makeLp(0, nCol + sCol); //modify by lizhengyao 0309
			
			if(lp.getLp() == 0)
			{
				System.err.println("Error when getLP");
				System.exit(-1);
			}
			
			lp.setAddRowmode(true);
			
			//set binary mode
			for(int i=0; i< nCol + sCol; i++) //modify by lizhengyao 0310
			{
				lp.setBinary(i+1, true);
			}
			
//			//add sentence length constraint
//			modify by lizhengyao 0407 删除了判断句子是否小于L的条件
			setZero(row);
			for(int i = 0; i < numSentence; i++)
			{
				row[i+1] = WaitingList.get(i).getLength();			
			}
			lp.addConstraint(row, LpSolve.LE, L);
			
			
			
//			//add summarization length constraint
//			if(lengthType == LENGTH_TYPE_SENT_NUM)
//			{
//				for(int i = 0; i < numSentence; i++)
//					row[i+1] = 1;
//				lp.addConstraint(row, LpSolve.LE, length);
//			}
			
			setZero(row);
			int index = nCol + 1;
			for(int i = 1; i <= nCol; i++)
			{
				for(int j = i + 1; j <= nCol; j++)
				{
					row[index] = 1;
					row[i] = -1;
					lp.addConstraint(row, LpSolve.LE, 0);
					row[i] = 0;
					
					row[j] = -1;
					lp.addConstraint(row, LpSolve.LE, 0);
					row[j] = 0;
					row[index] = 0;
					
					row[i] = 1;
					row[j] = 1;
					row[index] = -1;
					lp.addConstraint(row, LpSolve.LE, 1);
					row[i] = 0;
					row[j] = 0;
					row[index] = 0;
					
					index++;
				}
			}	
			
			lp.setAddRowmode(false);
			//set objective function
			setZero(row);
			for(int i = 0; i < numSentence; i++)
			{
				row[i+1] = 50;
			}
						
			//modify by lizhengyao 0309
			for(int i = nCol, j = 0; i < nCol + sCol; i++,j++)
			{
				double x = sim[j];
				
				if(x >= 0.9)
					row[i+1] = -1000;
				else
					row[i+1] = -1 * 
							(13.81 * x * x * x * x 
						   - 3.873 * x * x * x 
						  - 0.3397 * x * x
						  + 0.3309 * x 
						  - 0.002098);
				
				if(row[i+1] > 0)
					row[i+1] = 0;
				
				if(row[i+1] < - 10)
				{
					System.out.println("dd");
				}
			}
						
			lp.setObjFn(row);
			lp.setMaxim();
//			lp.setMinim();
			
			lp.setVerbose(LpSolve.IMPORTANT);  //Only important messages are reported. Warnings and Errors.
						
			double[] variables = new double[nCol + sCol]; //modify by lizhengyao 0309
	        if (lp.solve() == LpSolve.OPTIMAL) {
	        	double result = lp.getObjective();
	        	System.out.println("optimal=" + result);
	            lp.getVariables(variables);
	           
	        } else {
	            System.err.println("ILP can not find optimal solution.");
	            lp.getVariables(variables);
	        }
	
	        if (lp.getLp() != 0) {
	            lp.deleteLp();
	        }
	
	        ArrayList<Sentence> results = new ArrayList<Sentence>();
	        for (int i = 0; i < numSentence; i++) {
	            if (variables[i] == 1.0) {
	            	//System.out.println(i);
	                results.add(WaitingList.get(i));
	                //System.out.println("variables[" + i + "] = " + variables[i]);
	            }
//	            else
//	            	System.out.println("variables[" + i + "] = " + variables[i]);
	        }
	        for(int i = 0; i < nCol + sCol; i++)
	        {
	        	if (variables[i] == 1.0) {
	        		//System.out.println("variables[" + i + "]");
	        	}
	        }
	        
	        return results;
		}
		catch(LpSolveException e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	double [] countSim(ArrayList<Sentence> WaitingList,int number)
	{
		double [] sim = new double[number];
		
		int numberOfLine = 1;	
		int thisLineNumber = 0;
		
		int oriCol = WaitingList.size() - 1;
		int col = oriCol;
		int row = col - 1;
		
		String fileName = "data/8.txt";
		File file = new File(fileName);	
		
		try {
			if(!file.exists())
			{
				file.createNewFile();  //创建文件
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));	
			
			for(int i = number - 1; i >= 0; i--)
			{
				sim[i] = Similarity.cosineSim(WaitingList.get(row).getTermFreqMap(), WaitingList.get(col).getTermFreqMap());

//				if(sim[i]>0.9)
//				{
//					System.out.println(sim[i]);
//					System.out.println(WaitingList.get(row).getText());
//					System.out.println(WaitingList.get(col).getText());
//				}
				
//				if(sim[i] == 0)
//				{
//					System.out.println("hh");
//				}
				
//				writer.write(String.valueOf(sim[i]));
//				writer.newLine();
//				writer.write(row + ":" + WaitingList.get(row).getText());
//				writer.newLine();
//				writer.write(col + ":" + WaitingList.get(col).getText());
//				writer.newLine();
				
				
				
				thisLineNumber++;
				
				if(thisLineNumber == numberOfLine)
				{
					col = oriCol;
					row--;
					numberOfLine++;
					thisLineNumber = 0;
					continue;
				}
				else
				{
					col--;
				}
			}
			
			writer.flush();
			writer.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		return sim;
		
		
	}
		
	void setZero(double[] row)
	{
		for(int i = 0; i < row.length; i++)
			row[i] = 0;
	}

}
