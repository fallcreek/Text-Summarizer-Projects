package sum.summarizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;

import sum.net.SummarizeServer;
import sum.util.*;

public class SumMain {
	static int AspectNum = 14;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String path = "conf/crawl.conf.xml";
		File file = new File(path);
		SAXReader reader = new SAXReader();
		try{
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			List<Element> items = root.elements();
			
			for(Element item: items)
			{
				String id = item.attributeValue("id");
				List<String> sources = new ArrayList<String>();  //一个产品所有source的List
				Iterator it = item.elementIterator();
				while(it.hasNext())
				{
					Element source = (Element)it.next();
//					if(source.attributeValue("source").equals("pconline"))
						sources.add(source.attributeValue("source"));
				}
				System.out.println(id + ": " + sources.toString());
				
//				if(id.charAt(6) == '0')
//					continue;
				//只生成第21号产品的数据
//				if(!id.equals("00001021"))
//					continue;
				
				String[] sum = SingleText.sumEvaluations(id, sources); //摘要结果（注：这里sum的长度设为18）
				if(sum == null)
					return;
				
				for(int i=1; i<=AspectNum; i++)  //遍历产品每个aspect的摘要并存入文件中
				{
					if(sum[i] != null)
					{
						storeSummarizaion(id, sum[i], i);
					}
				}
			}
			
		}catch(Exception e){
			System.err.println(e.toString());
		}
		
	}
	
	public static void storeSummarizaion(String id, String Summarization, int aspect)  //存储摘要数据
	{
		String Directory = "data/path";
		File fileBox = new File(Directory);
		if(!fileBox.isDirectory()){  //创建文件夹
			fileBox.mkdirs();
			if (fileBox.exists()) {  
	            System.out.println("创建目录" + Directory + "成功！");  
	        } 
			else {  
	            System.out.println("创建目录" + Directory + "失败！");  
	        }
		}
		
		String fileName = Directory + "/" + id + "_sum.txt";
		File file = new File(fileName);		
		try{
			if(!file.exists())
			{
				file.createNewFile();  //创建文件
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));	
			
			//格式化输出
//			String[] lines = Summarization.split("。|？|\\?|！|!", 0);
//			writer.write("@" + aspect + ":\n");
//			for(String line: lines)
//			{
//				writer.write("  " + line + "。");
//				writer.newLine();
//			}
			
			writer.write("@" + aspect + " " + Summarization);
			writer.newLine();
			writer.flush();
			writer.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}

}

