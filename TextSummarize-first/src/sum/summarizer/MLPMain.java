package sum.summarizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import sum.data.Sentence;
import sum.data.WaitingSentences;
import sum.util.FileIO;
import sum.util.SentenceUtil;
import sum.summarizer.MMR;

public class MLPMain {

	
	
	
	public static void main(String[] args) {
		
		
		HashMap<Integer, ArrayList<Sentence>> SentList = new HashMap<Integer, ArrayList<Sentence>> ();
		
		int product = 12;
		for(int i = product; i <= product; i++)
		{
			String filename = "data/output/" + i + ".txt";
			ArrayList<String> line = FileIO.readLines(filename);
			String content = "";
			for(String l : line)
			{
				content += l;
			}
			//System.out.println(content);
			ArrayList<Sentence> sentListTemp = SentenceUtil.getChineseSentences(content, true);
			
			int asp = 0;
			for(Sentence s : sentListTemp)
			{			
				
				if(s.getText().charAt(0) == '方')
				{
					System.out.println(s);
					System.out.println("asp+"+asp);
					asp++;
					ArrayList<Sentence> temp = new ArrayList<Sentence>();
					SentList.put(asp, temp);
				}
				else
				{
					System.out.println(s);
					SentList.get(asp).add(s);
				}
			}	
			
			ILP_MLP summarizer = new ILP_MLP();
			String fileName = "data/result/" + product + ".txt";
			
			File file = new File(fileName);	
			
			try {
				if(!file.exists())
				{
					file.createNewFile();  //创建文件
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));	
				
				for(int j = 1; j <= 14; j++)
				{		
					System.out.println("aspect:"+j);
					System.out.println(SentList.get(j).size());
					writer.write("aspect:"+j);
					writer.newLine();
					
					if(SentList.get(j).size() == 0)
						continue;
					//if(SentList.get(j).size() > 10)
						
					SentList.put(j, summarizer.generateSummary(SentList.get(j), 0, 0));		
					for(Sentence s : SentList.get(j))
					{
						System.out.println(s.getText());
						writer.write(s.getText());
						writer.newLine();
					}
				}
				writer.flush();
				writer.close();
			}
			catch(Exception e)		
			{
				e.printStackTrace();
			}
			
			
			
			
			
		}	
		
		
			
//			for(String l : line)
//			{		
//				if(l.charAt(0) == 'a' && !content.equals(""))
//				{
//					
//					SentListTemp.put(i, SentenceUtil.getChineseSentences(content, true));
//				}
//				else if(l.charAt(0) != 'a')
//				{
//					content += l.substring(l.indexOf("^") + 1);
//				}
//			}
//			
//		}
//		sentList.setSentList(SentListTemp);
		
	}

	
}
