package sum.summarizer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;

import database.insertDatasets;
import sum.data.Article;
import sum.data.Sentence;
import sum.data.WaitingSentences;
import sum.data.WordDict;


public class SingleText {
	static int AspectNum = 14;
	static boolean TestOpen = false;  //true: 测试不考虑标题关键词的情况  false: 标题考虑关键词
	static boolean GetAllSentence = false;
	static boolean GetAspect = false;
	static boolean GetStructuralSentence = false;
	static WordDict wd = new WordDict();
	/*static HashMap<Integer, List<String>> WordDict = new HashMap<Integer, List<String>>();
	
	static {  //初始化匹配词集
		File file = new File("conf/aspect/cellphone.aspect");
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if(tempString.equals("")||tempString.charAt(0)=='#')//跳过注释
                	continue;
                else{
                	String key=tempString.split(" +")[0].split("@")[1];  //key为代表某一aspect的数字
                	List<String> value= new ArrayList<String>();  //value为aspect对应的词
                	int num = tempString.split(" +").length - 1;
                	for(int k = 1; k <= num; k++)
                		value.add(tempString.split(" +")[k]);

                	WordDict.put(Integer.parseInt(key), value);
                }
            }
            
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
	
	/**
	 * 对编号为id的产品进行摘要生成，生成方式是将每个产品在所有sources里的
	 * 所有文章，先按照不同aspect抽取出句子，再分别对不同aspect的句子生成
	 * 摘要的字符串数组。
	 * 
	 * @param id
	 * @param sources
	 * @return 摘要内容的字符串数组
	 */
	public static String[] sumEvaluations(String id, List<String> sources) {
		JSONObject evals = new JSONObject();
		evals.put("limitType", 1);
		evals.put("lengthLimit", 5);
		evals.put("summaryMethod", 1);
		evals.put("selectMethod", 1);
		evals.put("splitOnline", true);
		
		
//		String body[] = new String[17];  //body用来存储
//		for(int i=0; i<17; i++)  //初始化
//			body[i] = "";
		WaitingSentences selectedSent = new WaitingSentences();  //挑选出的句子
		
		String title = "";  //所有标题
		for(int i=0; i < sources.size(); i++)  //遍历所有source中的数据
		{
			String path = "data/" + sources.get(i) + "/" + id + ".xml";  //获得每个产品每篇文章的地址 
			File file = new File(path);
			SAXReader reader = new SAXReader();
					
			try{
				Document doc = reader.read(file);
				Element root = doc.getRootElement();
				String source = root.attributeValue("source");
				
				int en = 0; //文章篇数				
				for(Iterator it = root.elementIterator(); it.hasNext();)
				{
					Element evaluation = (Element)it.next();
					en++;
					if(en == 5)
						System.out.println("here");
					
					ArrayList<Integer> aspects = new ArrayList<Integer>();  //记录标题包含的aspect
					String Title = en + "@" + source + "^" + evaluation.attributeValue("title") + "。";  //每篇文章标题
					System.out.println(Title);
					String content = evaluation.getText();
					if(!content.isEmpty()){
						content = replaceText(content);
						Article article = new Article(Title, content);
						ArrayList<String> subtitles = article.getSubTitles();
//						if(hasKeyWord(Title, aspects))  //如果文章标题包含关键词，那么全文只与该关键词所在aspects有关
//						{
//							for(int aspect: aspects)  //遍历不同aspect的关键词
//							{
//								ArrayList<Sentence> sentences = new ArrayList<Sentence>();
//								for(String SubTitle: subtitles)
//								{
//									ArrayList<Sentence> temp = article.getSection().get(SubTitle);
//									sentences.addAll(temp);
//								}
//								selectSentences(id, sentences, selectedSent, aspect, en, source);
//							}
//						}
//						
//						else  //如果文章标题不包含关键词，那就看文章各section的小标题是否包含关键词
//						{
						
						for(String SubTitle: subtitles)
						{
							ArrayList<Sentence> sentences = article.getSection().get(SubTitle);
							for(int j=1; j<=AspectNum; j++)
							{
								selectSentences(id, sentences, selectedSent, j, en, source,SubTitle);
							}
							
							if(GetAspect)
							{
								
								insertDatasets insert = new insertDatasets();
								insert.insertAspect(sentences);
							}
							if(GetAllSentence)
							{
								insertDatasets insert = new insertDatasets();
								insert.insert(sentences);
//								String fileName = "data/result2.txt";
//								File file2 = new File(fileName);		
//								try{
//									if(!file2.exists())
//									{
//										file2.createNewFile();  //创建文件
//									}
//									BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));	
//									
//									for(Sentence s : sentences)
//									{
//										writer.write(s.getText());
//										writer.newLine();
//										writer.write("knumber:"+s.getkNumber()+"anumber:"+s.getaNumber()+"nnumber:"+s.getnNumber()+"position:"+s.isTitleWithKW()+"aspectnumber:"+s.getAspectNumber()+"hasProductname:"+s.isHasProductName()+"hasOthername"+s.isHasOtherProduct());
//										writer.newLine();
//									}
//									
//									writer.flush();
//									writer.close();
//								}catch(IOException e)
//								{
//									e.printStackTrace();
//								}
							}
						}
						
							/*for(String SubTitle: subtitles)
							{
								if(hasKeyWord(SubTitle, aspects) && aspects.size() == 1)  //如果小标题包含关键词，选取只提到一个aspect的section
								{
								
									ArrayList<Sentence> sentences = article.getSection().get(SubTitle);
									for(int k=0; k<sentences.size(); k++)
										sentences.get(k).setTitleWithKW(true);  //给句子标注，表示小标题包含关键词
									
									for(int j=1; j<=AspectNum; j++)
									{
										selectSentences(id, sentences, selectedSent, j, en, source);
									}
								}
								else  //小标题不包含关键词，每一句都遍历所有aspect
								{
									ArrayList<Sentence> sentences = article.getSection().get(SubTitle);
									for(int k=0; k<sentences.size(); k++)
										sentences.get(k).setTitleWithKW(false);
									
									for(int j=1; j<=AspectNum; j++)
									{
										selectSentences(id, sentences, selectedSent, j, en, source);
									}
								}
							}*/
//						}
					}
					
						
//					String currentTitle = en + "@" + source + "^" + evaluation.attributeValue("title") + "。";  //每篇文章标题
//					title = title + currentTitle;
//					String content = evaluation.getText();
//					content = replaceText(content);  //去除正文中的{}[]#等自加符号
//					ArrayList<String> sentences = SentenceUtil.segChineseSentences(content);  //给文章断句
//					
//					selectSentences(sentences, body, en, source);
				}		
								
			}catch(Exception e){
				System.err.println(e.toString());
			}
			
		}
		
		if(GetAllSentence || GetAspect)
			return null;
		
		//modify by lizhengyao 0420
		if(GetStructuralSentence)
		{	
			insertDatasets insert = new insertDatasets();
			insert.insertStructuralData(id,selectedSent);
			
			return null;
		}
		/**
		 * 计算每个句子的分数
		 * @modify Jianfa 1.13 17改成AspectNum
		 */
		for(int asp=1; asp<=AspectNum; asp++)
		{
			if(selectedSent.hasSentence(asp))
			{
//				ArrayList<Sentence> tmp = selectedSent.
				selectedSent.show(asp);
			}
		}
		selectedSent.sortList();
		
		//调用NewAlgorithm()
		/**
		 * @modify Jianfa 1.8 删除下方		
		 */
		/*ArrayList<Sentence> summarization = new ArrayList<Sentence>();
		NewAlgorithm summarizer = new NewAlgorithm();
		summarization = summarizer.generateSummary(selectedSent.getSentList().get(1), 5, 1);
		
		
		for(Sentence s: summarization)
		{
			System.out.println(s.getText() + " " + summarization.indexOf(s));
		}*/
		
		/**
		 * 实现所有产品摘要生成
		 * @add Jianfa 1.8
		 */
		String[] summarization = new String[AspectNum + 1];
		System.out.println(id + "的aspect数量：" + selectedSent.getSize());
		for(int j=1; j<=AspectNum; j++)  //设定17个aspect
		{
			System.out.println("aspect: "+ j);
			if(selectedSent.hasSentence(j))
			{
				summarization[j] = "";
				ArrayList<Sentence> resultList = new ArrayList<Sentence>();
				//调用NewAlgorithm
				NewAlgorithm summarizer = new NewAlgorithm();
				resultList = summarizer.generateSummary(selectedSent.getSentList().get(j), 5, 1);
				for(Sentence s: resultList)
				{
					summarization[j] = summarization[j] + s.getText();  //生成摘要字符串
				}
				//System.out.println("@" + j + ":" + summarization[j]);
			}
			else{
				System.out.println(id + "的aspect[" + (j) + "]相关句为空");  //显示产品哪一个aspect为空
			}
			
		}
		
		return summarization;
		
		/*String[] summarization = new String[AspectNum+1];  //用来存放摘要结果
		System.out.println("selectedSent.getSize() = " + selectedSent.getSize());
		for(int j=1; j<=AspectNum; j++)  //设定17个aspect
		{
			//实现对同一产品不同aspect的摘要生成
//			if(!body[j].equals("")){
			if(selectedSent.hasSentence(j)){  //如果备选句子中有第j个aspect相关的句子
				JSONArray docs = new JSONArray();  //"docs"对应的JSONArray
				JSONObject json = new JSONObject();
				
				json.put("title", "标题");
//				json.put("body", body[j]);
				json.put("body", selectedSent.getSentence(j));
				docs.put(json);
				evals.put("docs", docs);  //所有评测文章合成一个json
				
				JSONObject result = new JSONObject(SummarizeServer.getSummarize(evals.toString(), "conf/config"));
				summarization[j] = result.get("abstract").toString();
				System.out.println("@" + j + ":" + summarization[j]);
				
				evals.remove("docs");
			}
			else{
				System.out.println(id + "的aspect[" + (j) + "]相关句为空");  //显示产品哪一个aspect为空
			}
		}
		
		return summarization;*/
	}
	
	/**
	 * 判断标题或小标题是否包含关键词
	 * @param title
	 * @param aspects
	 * @return hasWord
	 */
	public static boolean hasKeyWord(String title, ArrayList<Integer> aspects)
	{
		if(TestOpen)  //测试不考虑标题关键词的情况
			return false;
		else
		{
			boolean hasWord = false;
			aspects.clear();
			for(int i=0; i<wd.getWordDict().size(); i++)  //body为某一个aspect相关的句子
			{
				List<String> words = wd.getWordDict().get(i+1);  //该aspect相关的一些词		
				for(String word: words)  //遍历words中的每一个词进行判断
				{
					if(title.contains(word))
					{
						hasWord = true;
						aspects.add(i+1);
						break;
					}					
				}		
			}
			return hasWord;
		}
	}
	
	/**
	 * 选择含有aspect词集中的词语的句子，存入body中
	 * @param sentences 所有句子
	 * @param SentList 备选句子
	 * @param aspect
	 * @param en
	 * @param source
	 * @modify lizhengyao judgeHasProductName(id)
	 */
	public static void selectSentences(String id, ArrayList<Sentence> sentences, WaitingSentences SentList, int aspect, int en, String source,String SubTitle)
	{
		ArrayList<Sentence> temp = new ArrayList<Sentence>();  //存放挑出来的备选句子
		List<String> words = wd.getWordDict().get(aspect);  //该aspect相关的一些词
		
		boolean hasKeyWord = false;
		for(String word: words)  //遍历words中的每一个词进行判断
		{	
			if(SubTitle.contains(word))
			{
				hasKeyWord = true;
			}
		}
		
		for(Sentence sent: sentences){
			
//			boolean hasWord = false;
			int count = 0;  //number of key words included
			for(String word: words)  //遍历words中的每一个词进行判断
			{			
				if(sent.getText().contains(word)) //如果句子包含关键词
				{
					count++;
//					hasWord = true;
				}					
			}
			if(!GetAllSentence && !GetAspect)
			{
				if(count > 0)  //如果句子含有aspect的关键词
				{
					if(sent.getText().charAt(1)!='@' && sent.getText().charAt(2)!='@')  //如果句子还未被标注
					{
						String newSent = en + "@" + source + "^" + sent.getText();
						sent.modifyText(newSent);  //给句子加上标签
					}
					
					Sentence newsent = (Sentence)sent.clone(); //xinjia 克隆新的Sentence对象
					
					newsent.kNumberCount(count);  //给句子的kNumber赋值为count
					newsent.judgeHasProductName(id);  //1.5 modify
					
					if(hasKeyWord)			
						newsent.setTitleWithKW(true);
					else
						newsent.setTitleWithKW(false);
					
					temp.add(newsent);			
				}
			}		
			else if(GetAllSentence || GetAspect)
			{
				if(sent.getText().length() <= 2 || sent.getText().charAt(1)!='@' && sent.getText().charAt(2)!='@')  //如果句子还未被标注
				{
					String newSent = en + "@" + source + "^" + sent.getText();
					sent.modifyText(newSent);  //给句子加上标签
				}
				
				//Sentence newsent = (Sentence)sent.clone(); //xinjia 克隆新的Sentence对象
				
				if(count > 0)
				{
					sent.setAspectNumber(sent.getAspectNumber() + 1);
				}
				
				sent.kNumberCount(count + sent.getKNumber());  //给句子的kNumber赋值为count
				sent.judgeHasProductName(id);  //1.5 modify
				
				if(hasKeyWord)			
					sent.setTitleWithKW(true);
				else if(!sent.isTitleWithKW())
					sent.setTitleWithKW(false);
			}
		}
		if(!temp.isEmpty())
			SentList.insertSentence(aspect, temp);
	}
	
	/**
	 * 将句子中的自加识别符号去除。
	 * @param text
	 * @return text
	 */
	public static String replaceText(String text)
	{
		text = text.replace("[", "");
		text = text.replace("]", "。");  //将图片注释按照一句话处理
//		text = text.replace("{", "");
//		text = text.replace("}", "。");  //将小标题按照一句话处理
//		text = text.replace("#", "");
		
		String pat="@\\d+-\\d+";
		Pattern p=Pattern.compile(pat);
		Matcher m=p.matcher(text);
		text = m.replaceAll("");
		
//		text = text.replaceAll(" +", "");  //普通空格字符  11.05
//		text = text.replaceAll(" +", "");  //类似于zol第二篇iPhone 6中间的空格  11.05
		return text;
	}

/*	public static void main(String[] args) {  //测试用
		// TODO Auto-generated method stub
		String path = "data/it168/00001001.xml";
		File file = new File(path);
		SAXReader reader = new SAXReader();
		try{
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			String source = root.attributeValue("source");
			
			int en = 0; //文章篇数
			JSONObject evals = new JSONObject();
			evals.put("limitType", 1);
			evals.put("lengthLimit", 5);
			evals.put("summaryMethod", 1);
			evals.put("selectMethod", 1);
			evals.put("splitOnline", false);
			JSONArray docs = new JSONArray();  //"docs"对应的JSONArray
			
			String s = "";  //所有文章句子
			String title = "";
			for(Iterator<Element> it = root.elementIterator(); it.hasNext();)
			{
				Element evaluation = it.next();
				en++;
				
				String currentTitle = en + "@" + source + "^" + evaluation.attributeValue("title") + "。";  //每篇文章标题
				title = title + currentTitle;
				String content = evaluation.getText();
				content = replaceText(content);  //去除正文中的{}[]#等自加符号
				ArrayList<String> sentences = SentenceUtil.segChineseSentences(content);  //给文章断句
				
				for(String sent: sentences){
					if(sent.contains("外观") || sent.contains("外形") || sent.contains("设计") || sent.contains("外型") || sent.contains("外壳") || sent.contains("外表"))
					{
						sent = sent + "[" + source + " " + en + "]";
						s = s + sent;
						System.out.println(sent);
					}					
				}

			}
			
			JSONObject json = new JSONObject();
			json.put("title", title);
			json.put("body", s);
			docs.put(json);
			evals.put("docs", docs);
//			System.out.println(docs.toString());
//			System.out.println(evals.toString());
			
			JSONObject result = new JSONObject(SummarizeServer.getSummarize(evals.toString(), "conf/config"));
			System.out.println(result.toString());
			
		}catch(Exception e){
			e.toString();
		}
		
	} */
	
}


