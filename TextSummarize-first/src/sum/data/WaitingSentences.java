package sum.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class WaitingSentences {
	static HashMap<Integer, ArrayList<Sentence>> SentList;
	static int AspectNum = 17;
	
	public WaitingSentences()
	{
		SentList = new HashMap<Integer, ArrayList<Sentence>>();
	}
	
	public void insertSentence(int aspect, ArrayList<Sentence> sentences)
	{
		if(!SentList.containsKey(aspect))
			SentList.put(aspect, sentences);  //如果还没有aspect的键存在，说明是第一次加入sentences
		else
			SentList.get(aspect).addAll(sentences);  //如果已经存在aspect键，那么在原来对应的sentences值中加入新值
	}
	
	public static void deleteSentence()
	{
		
	}
	
	/**
	 * @author lizhengyao
	 * @modify
	 * @version 1 lizhengyao
	 * @return
	 * @function 排列arraylist，按照得分大小降序排列
	 */
	public void sortList()
	{
		for(int asp = 1; asp <= 17; asp++)
		{
			List<Sentence> Sents = SentList.get(asp);
			
			if(Sents == null)
				continue;
			
			double allScore = 0;
			int allLength = 0;
			int number = 0;
			for(Sentence sent : Sents)
			{
				if(sent.getSentenceScore() >= 0 && number < 15)	
				{
					allScore += sent.getSentenceScore();
					allLength += sent.getNumWords();
					number++;
				}	
			}
			
			double averageScore = allScore / number;
			double averageLength = allLength / number;
			
			double fl = averageScore * 0.3 / averageLength;
			
			for(Sentence sent : Sents)
			{
				sent.modifySentenceScore(fl);
			}
			
			Collections.sort(Sents,new Comparator<Sentence>(){
				public int compare(Sentence s1, Sentence s2) 
				   {
					 if(s1.sentenceScore - s2.sentenceScore > 0)
						 return -1; //jdk1.7中必须返回一对相反值
					 else if(s1.sentenceScore - s2.sentenceScore < 0)
						 return 1;
					 else
						 return 0;
				  }
			});  
			
			//modify by lizhengyao 0407
//			double maxScore = Sents.get(0).getSentenceScore();
//			int wordNum = Sents.get(0).getNumWords();
//			double fl = maxScore * 0.2 / wordNum;
//			
//			for(Sentence sent : Sents)
//			{
//				sent.modifySentenceScore(fl);
//			}
//			
//			Collections.sort(Sents,new Comparator<Sentence>(){
//				public int compare(Sentence s1, Sentence s2) 
//				   {
//					 if(s1.sentenceScore - s2.sentenceScore > 0)
//						 return -1; //jdk1.7中必须返回一对相反值
//					 else if(s1.sentenceScore - s2.sentenceScore < 0)
//						 return 1;
//					 else
//						 return 0;
//				  }
//			}); 
			
			//System.out.println("-------------------(aspect:" + asp + ")--------------------");
			
			String fileName = "data/result.txt";
			File file = new File(fileName);		
			try{
				if(!file.exists())
				{
					file.createNewFile();  //创建文件
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));	
				
				writer.write("-------------------(aspect:" + asp + ")--------------------");
				writer.newLine();
				
				writer.flush();
				writer.close();
			}catch(IOException e)
			{
				e.printStackTrace();
			}
			
			for(Sentence sent : Sents)
			{
				sent.showScore();
			}
		}
		
	}
	
	public int getSize()
	{
		return SentList.size();
	}
	
	public boolean hasSentence(int aspect)
	{
		ArrayList<Sentence> Sents = SentList.get(aspect);
		if(Sents == null){  //如果aspect相关的句子为空
			return false;
		}
		else{
			return true;
		}
	}
	public String getSentence(int aspect)
	{
		String text = "";
		for(Sentence temp: SentList.get(aspect))
		{
			text = text + temp.getText();
		}
		return text;
	}
	
	/**
	 * @author Jianfa
	 * @modify
	 * @version 1
	 */
	public void kNumberCount() {
		for(int i=1; i<=AspectNum; i++)
		{
			if(hasSentence(i))
			{
				ArrayList<Sentence> temp = SentList.get(i);
				for(Sentence st: temp)
				{
					
				}
			}
		}
	}

	public void show(int aspect)
	{
//		System.out.println("-------------------(aspect:" + aspect + ")--------------------");
		for(Sentence temp: SentList.get(aspect))
		{		
			temp.countSentenceScore();
		}	
	}
	/*public void show(int aspect)
	{
		for(Sentence temp: SentList.get(aspect))
		{
			System.out.println("(aspect:" + aspect + ")" + temp.getText());
			temp.countSentenceScore();
		}	
	}*/
	
	
	//add 1.8 Jianfa test
	public HashMap<Integer, ArrayList<Sentence>> getSentList() {
		return SentList;
	}
	
	public void setSentList(HashMap<Integer, ArrayList<Sentence>> s)
	{
		SentList = s;
	}
	
}
