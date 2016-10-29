package sum.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sum.util.SentenceUtil;

public class Article {
	String Title;
	static HashMap<String, ArrayList<Sentence>> Section; 
	static ArrayList<String> subtitles;  //子标题
	
	public Article(String title, String content)
	{
		Title = title;
		init(content);
	}
	
	public void init(String content)  //初始化Article
	{
		Section = new HashMap<String, ArrayList<Sentence>>();
		subtitles = new ArrayList<String>();
		
		String[] sec = content.split("\\{", 0);  //把正文部分用小标题隔开
		String SubTitle;  //小标题
		String SubContent;  //小标题下方的段落
		ArrayList<Sentence> SubSent = new ArrayList<Sentence>();
		boolean splitOnline = true;
		
		if(sec.length > 1)  //section数目大于1说明文中一定有小标题（主要是pconline）
		{
			for(int i=0; i<sec.length; i++)
			{
				if(sec[i].isEmpty())  //正文开头就是小标题，则第一个sec为空
				{
					continue;
				}
				
				else if(isPara(sec[i].charAt(0)))  //正文开头部分，往往没有小标题
				{
					SubTitle = "开篇";
					subtitles.add(SubTitle);
					SubContent = sec[i];
					SubContent = TextReplace(SubContent);
					SubSent = SentenceUtil.getChineseSentences(SubContent, splitOnline);
					Section.put(SubTitle, SubSent);
				}

				else
				{
					String[] temp = sec[i].split("}", 0);
					if(temp.length > 1)  //避免出现两种情况：1.最后一段被归类成小标题，这样就直接以 } 结束；2.小标题下没有正文
					{
						SubTitle = sec[i].split("}")[0];
						subtitles.add(SubTitle);
						SubContent = sec[i].split("}")[1];
						SubContent = TextReplace(SubContent);
						SubSent = SentenceUtil.getChineseSentences(SubContent, splitOnline);
						Section.put(SubTitle, SubSent);
					}
				}			
			}
		}
		else  //文章中没有小标题的情况
		{
			SubTitle = Title;
			subtitles.add(SubTitle);
			SubContent = content;
			SubContent = TextReplace(SubContent);
			SubSent = SentenceUtil.getChineseSentences(SubContent, splitOnline);
			Section.put(SubTitle, SubSent);
		}
		
	}
	
	private static boolean isPara(char first)  //判断是否是自然段
	{
		if(first == '#')
		{
			return true;
		}
		return false;
	}
	
	public static String TextReplace(String text)  //把自然段标识去掉
	{
		text = text.replace("#", "");

		return text;
	}
	
	public HashMap<String, ArrayList<Sentence>> getSection()
	{
		return Section;
	}
	
	public static ArrayList<String> getSubTitles()
	{
		return subtitles;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
