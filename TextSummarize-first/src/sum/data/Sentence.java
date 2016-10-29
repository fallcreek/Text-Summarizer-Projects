package sum.data;

import sum.util.SentenceUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Sentence implements Cloneable{
	static int sentNum2 = 0;  //nNumber为2的句子数
	static int sentNum3 = 0;  //nNumber为3的句子数
	static int sentNum4 = 0;  //nNumber为4的句子数
	static int sentNum5andLarger = 0;  //nNumber为5或以上的句子数
	static int PsentNum2 = 0;  //nNumber为2且为参数型的句子数
	static int PsentNum3 = 0;  //nNumber为3且为参数型的句子数
	static int PsentNum4 = 0;  //nNumber为4且为参数型的句子数
	static int PsentNum5andLarger = 0;  //nNumber为5或以上且为参数型的句子数
//	static int Count = 0;  //统计参数时用，可删
	
	/* sentence score
	 * add by lizhengyao
	 * modify:
	 * version:1 lizhengyao
	*/
	static double ft = 1.8; //title factor
	static double fp = 1.5; //product name factor
	static double fk = 1.5; //key word factor
	static double fn = 1.2; //number factor
	static double fa = 1.2; //adjective factor
	//static double fl = 0.1; //length factor   modify by lizhengyao 0314
	
	 
	double titleValue = 1; 
	double nameValue = 1;
	
	private int kNumber = 0; //number of key word
	private int nNumber = 0; //number of number
	private int aNumber = 0; //number of adjective
	

	private int aspectNumber = 0;//number of aspect
	
	int sentenceType; 
	boolean hasProductName = false;
	boolean hasOtherProduct = false;
	boolean TitleWithKW;  //Title has key word;
	
	double sentenceScore;
	double sentenceScore_ori;
	/*end*/
	
	/** 
	 * 统计句子中关键词的个数
	 * @author Jianfa 
	 * @modify
	 * @version 1 
	 */
	public void kNumberCount(int count) {
		kNumber = count;
	}
	
	/** 
	 * 统计句子中数词的个数（仅统计阿拉伯数字）
	 * @author Jianfa 
	 * @modify
	 * @version 1 
	 */
	public void nNumberCount(String parsedText) {
		String originalString = SentenceUtil.getOriginalString(parsedText);
		
		String allNumber = "[0-9.]+[:：]*[0-9]*";
		Pattern allNumberParten = Pattern.compile(allNumber);
		
		String productName = "[a-zA-Z |荣耀|小米|红米|一加|骁龙]+[0-9.]+|[0-9]+年|[0-9]+月|[0-9]日|[0-9]G[^a-zA-Z]|[0-9]/[0-9]|\\([0-9]\\)|[0-9]+[:：][0-9]+";
		Pattern productNamePattern = Pattern.compile(productName);
		
		Matcher allNumberMatcher = allNumberParten.matcher(originalString);
		Matcher productNameMatcher = productNamePattern.matcher(originalString);
		
		int allCount = 0;
		
		ArrayList<String> allNumberStr = new ArrayList<String>();
        while (allNumberMatcher.find()) {
        	allCount ++;
        	allNumberStr.add(allNumberMatcher.group(0));            
        } 
        
//        for (String s : allNumberStr){
//            System.out.println(s);
//        }  
//        
            
        ArrayList<String> productNameStr = new ArrayList<String>();
        while (productNameMatcher.find()) {
        	allCount --;
        	productNameStr.add(productNameMatcher.group(0));            
        } 
        
//        for (String s : productNameStr){
//            System.out.println(s);
//        }  
        
        nNumber = allCount;
       // System.out.println("nNumber: " + nNumber + " Sentence: " + originalString);
		
		
		
//		String[] tokens = parsedText.split(" ", 0);
//		ArrayList<String> ret = new ArrayList<String>();
//		
//		int count = 0;
//		String preword = "";
//		String pretag = "";
//		int prepos = 0;
//		for(String token : tokens) {
//			int pos = token.indexOf('/');
////			System.out.println("token=" + token + " pos=" + pos);  //Jianfa 1.5
//			if(pos < 0)
//				continue;
//			
//			String word = token.substring(0, pos).trim();  
//			String tag = token.substring(pos + 1).trim();
//			if(tag.equals("m") && prepos!=0 && !pretag.contains("n"))  //排除数字属于产品名的情况
//			{
//				if(hasDigit(word))
//					count++;
//			}
//			if(tag.equals("t") && hasDigit(preword) && count!=0)  //排除数字属于时间的情况
//			{
//				count--;
//			}
//			preword = word;
//			pretag = tag;
//			prepos = pos;
//				
//		}
//		nNumber = count;
//		System.out.println("nNumber: " + nNumber + " Sentence: 当然");
		
		/*
		 * 以下这部分仅用于统计参数情况，统计完可删。
		 */
		/*if(nNumber > 1 && Count<50)
//			Count++;
//		if(nNumber > 1 && Count>=100 && Count<200)
		{
			Count++;
			System.out.println(Count + "nNumber: " + nNumber + " Sentence: " + SentenceUtil.getOriginalString(parsedText));
//			System.out.println(Count + "nNumber: " + nNumber + " Sentence: " + parsedText);
			Scanner input = new Scanner(System. in);
            int Judge = input.nextInt();

        	if(nNumber == 2){  sentNum2++;}
        	else if(nNumber == 3){  sentNum3++;}
        	else if(nNumber == 4){  sentNum4++;}
        	else{  sentNum5andLarger++;}
        	
            if(Judge == 1)
            {
            	if(nNumber == 2){  PsentNum2++;}
            	else if(nNumber == 3){  PsentNum3++;}
            	else if(nNumber == 4){  PsentNum4++;}
            	else{  PsentNum5andLarger++;}
            }
		}
		
		if(Count >= 50)
		{
			System.out.println("sentNum2=" + sentNum2 + "; 其中参数型句子数：" + PsentNum2);
			System.out.println("sentNum3=" + sentNum3 + "; 其中参数型句子数：" + PsentNum3);
			System.out.println("sentNum4=" + sentNum4 + "; 其中参数型句子数：" + PsentNum4);
			System.out.println("sentNum5andLarger=" + sentNum5andLarger + "; 其中参数型句子数：" + PsentNum5andLarger);
			System.exit(0);
		}*/
	}
	
	/**
	 * @author lizhengyao
	 * @version:1 lizhengyao
	 * @function: 计算形容词个数
	 */
	public void aNumberCount(String parsedText) {
		
		int count = 0;
		int startPosition = 0;
		int resultPosition = 0;
		
		while(resultPosition != -1)
		{
			resultPosition = parsedText.indexOf("/a", startPosition);
			if(resultPosition != -1)
			{
				count++;
				startPosition = resultPosition + 1;
			}						
		}
		
		aNumber = count;
		//System.out.println("anumber=" + aNumber);	
	}
	
	/** 
	 * 判断句子类型是参数型还是感觉型
	 * @author Jianfa 
	 * @modify
	 * @version:1 
	 */
	public void judgeSentenceType() { 
		if(nNumber >= 3)
			sentenceType = 1;  //数词数为3个以上时，认为是参数型句子
		else
			sentenceType = 0;  //数词数小于3时，认为是感觉型句子
	}
	
	/** judgeUnderTitle
	 * @author Jianfa 
	 * @modify
	 * @version 1 
	 */
	public void setTitleWithKW(boolean a) {
		TitleWithKW = a;
		
		if(TitleWithKW)  //modified by Jianfa
			titleValue = ft;
		else
			titleValue = 1;
	}
	
	/**
	 * @author lizhengyao
	 * @version 2 lizhengyao
	 * @modify version 2:2016/1/8 lizhengyao, 增加判断是否包含其他产品名称。
	 * @param id
	 * @function 判断这句话里面是否有产品名称，并将hasProductName置为相应的值
	 */
	public void judgeHasProductName(String id) {
		
		String path = "conf/productName.xml";
		File file = new File(path);
		SAXReader reader = new SAXReader();
		try{
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			List<Element> items = root.elements();
			
			for(Element item: items)
			{
				String idTemp = item.attributeValue("id");
				
				List<String> nameList = new ArrayList();  //一个产品所有名称
				Iterator it = item.elementIterator();
				while(it.hasNext())
				{
					Element productName = (Element)it.next();
					//System.out.println("name=" + productName.getText());
					nameList.add(productName.getText());				
				}
				
				
				if(hasOtherProduct == false && !idTemp.equals(id))
				{
					for(String tempName : nameList)
					{
						if(text.contains(tempName))
						{
							hasOtherProduct = true;							
							break;
						}	
					}
				}
				else if(idTemp.equals(id))
				{				
					for(String tempName : nameList)
					{						
						if(text.contains(tempName))
						{							
							hasProductName = true;	
							if(hasOtherProduct == true)
							{
								setNameValue();
								return;
							}				
							else
								break;
						}						
					}
				}				
			}
			setNameValue();
		}
		catch(Exception e)
		{
			System.err.println(e.toString());
		}
				
								
	}

	
	/* sentence score
	 * add by lizhengyao
	 * modify: Jianfa
	 * version:1 lizhengyao
	*/
	public void setTitleValue() {
		if(this.TitleWithKW)  //modified by Jianfa
			this.titleValue = ft;
		else
			this.titleValue = 1;
	}
	
	/**
	 * @author lizhengyao
	 * @version 2
	 * @modify:version 2:2016/1/8 lizhengyao 增加了对是否含有其他产品名称的判断
	 */
	public void setNameValue() {
		if(hasProductName)
		{
			if(!hasOtherProduct)
				nameValue = fp;
			else
				nameValue = 1;
		}
		else
		{
			if(hasOtherProduct)
				nameValue = 0;
			else
				nameValue = 1;
		}			
		//System.out.println(hasProductName+"value:"+nameValue);
	}
	
	/**
	 * @author lizhengyao
	 * @version 1 lizhengyao
	 */
	public void countSentenceScore() {
		if(sentenceType == 1)
			sentenceScore = titleValue * nameValue * (kNumber * fk + nNumber * fn * 1.2 + aNumber * fa);	
		else 
			sentenceScore = titleValue * nameValue * (kNumber * fk + nNumber * fn + aNumber * fa * 1.2);	
		
		//modify by lizhengyao 0314: 得分除以单词总数
//		sentenceScore_ori = sentenceScore;
//		sentenceScore = sentenceScore / getNumWords();
		
		
		sentenceScore_ori = sentenceScore;		
		//modify by lizhengyao 0407：换到排序时候再做
//		sentenceScore = sentenceScore - fl * getNumWords();
		
		//System.out.println("sentenceType=" + sentenceType + ";score="+ sentenceScore + ";T=" + titleValue + ";N=" + nameValue + ";kNumber=" + kNumber + ";aNumber=" + aNumber + ";nNumber=" + nNumber + "name=" + hasProductName);
		//System.out.println(kNumber);
//		if(sentenceScore == 0)
//		{
//			System.out.println(text);
//			System.out.println("sentenceType=" + sentenceType + ";score="+ sentenceScore + ";T=" + titleValue + ";N=" + nameValue + ";kNumber=" + kNumber + ";aNumber=" + aNumber + ";nNumber=" + nNumber + "name=" + hasProductName);
//			
//		}
				
	}
	
	/*
	 * add by lizhengyao 0407
	 * 
	 */
	void modifySentenceScore(double fl)
	{
		sentenceScore = sentenceScore - fl * getNumWords();
	}

	/**
	 * @author lizhengyao
	 * @function 打印分数信息
	 */
	public void showScore()
	{
//		System.out.println(text);
//		System.out.println("sentenceType=" + sentenceType + ";score="+ sentenceScore + ";T=" + titleValue + ";N=" + nameValue + ";kNumber=" + kNumber + ";aNumber=" + aNumber + ";nNumber=" + nNumber + "name=" + hasProductName);
	
		
		String fileName = "data/result.txt";
		File file = new File(fileName);		
		try{
			if(!file.exists())
			{
				file.createNewFile();  //创建文件
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));	
			
			writer.write(text);
			writer.newLine();
			writer.write("sentenceType=" + sentenceType + ";score=" + sentenceScore +  ";wordnumber:" + getNumWords() + ";oriscore="+ sentenceScore_ori + ";T=" + titleValue + ";N=" + nameValue + ";kNumber=" + kNumber + ";aNumber=" + aNumber + ";nNumber=" + nNumber + "name=" + hasProductName);
			writer.newLine();
			
			writer.flush();
			writer.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	Document doc;                   // the document that contains this sentence
	
	String text;
	private ArrayList<String> wordList;
	private ArrayList<String> wordList2;
	HashMap<String, Double> tfMap;  // word term frequency of this sentence
	
	int numWords;
	HashMap<String, Double> fv;     // feature vector
	double score;                   // used for sorting (ranking)

	public Sentence(String parsedText, Document doc) {
		this.doc = doc;		
		init(parsedText);
	}

	/**
	 * @modify 2016/1/6 lizhengyao 增加judgeSentenceType()函数的调用。
	 * @param parsedText
	 */
	public void init(String parsedText) {		
		tfMap = new HashMap<String, Double>();
		nNumberCount(parsedText);
		aNumberCount(parsedText);
		judgeSentenceType();
		
        if (SentenceUtil.isChineseSentence(parsedText)) {
            wordList = SentenceUtil.getChineseWords(parsedText);
//            wordList2 = SentenceUtil.getChineseWords2(parsedText);
            text = SentenceUtil.getOriginalString(parsedText);
        }
        else {
            wordList = SentenceUtil.getEnglishWords(parsedText);
            text = parsedText;
        }
        numWords = wordList.size();

		// compute tf
		for(String word : wordList) {
			// discard stop words
			if(Stopwords.isStopword(word))
				continue;
			
			if(tfMap.containsKey(word))
				tfMap.put(word, tfMap.get(word) + 1);
			else
				tfMap.put(word, 1.0);
		}

	}
	
	public Object clone() {  
        Sentence sentence = null;  
        try{  
            sentence = (Sentence)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return sentence;  
    }
	
	public boolean isInSentenceList(ArrayList<Sentence> sentList) {
		for (Sentence s : sentList) {
			if (s.getText().equals(this.text)) {
				return true;
			}
		}
		return false;
	}

    @Override
    public String toString() {
        return text;
    }
    
    public void modifyText(String newText)
    {
    	text = newText;
    }
    
    /**
     * 判断是否含有数字
     * @author Jianfa
     * @param str
     * @return
     */
    public static boolean hasDigit(String str) {
        for (int i = str.length(); --i >= 0;) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 返回Sentence的字符串长度
     * @author Jianfa
     * @return
     */
    public int getLength()
    {
    	return text.length();
    }
    
    /**
     * 返回句子类型
     * @author Jianfa
     * @return
     */
    public int getSentenceType()
    {
    	return sentenceType;
    }
    
    public double getSentenceScore() {
		return sentenceScore;
	}

	////////////////////////////////////////////////////////////////////////////
	// get/set functions
	//public Document getDocument() { return doc; }
	public String getText() { return text; }
	
	public HashMap<String, Double> getTermFreqMap() { return tfMap; }
	public void setTermFreqMap(HashMap<String, Double> map) { tfMap = map; }
	public HashMap<String, Double> getFeatureVector() { return fv; }
	
	public int getNumWords() { return numWords; }
	public ArrayList<String> getWordList() {return wordList;}
	public ArrayList<String> getWordList2() {return wordList2;}
	
	public double getScore() { return score; }
	public void setScore(double val) { score = val; }
	public void setFeatureVector(HashMap<String, Double> fv) { this.fv = fv; }
	
	public int getKNumber()
	{
		return kNumber;
	}

	public int getAspectNumber() {
		return aspectNumber;
	}

	public void setAspectNumber(int aspectNumber) {
		this.aspectNumber = aspectNumber;
	}
	
	public int getkNumber() {
		return kNumber;
	}

	public void setkNumber(int kNumber) {
		this.kNumber = kNumber;
	}

	public int getnNumber() {
		return nNumber;
	}

	public void setnNumber(int nNumber) {
		this.nNumber = nNumber;
	}

	public int getaNumber() {
		return aNumber;
	}

	public void setaNumber(int aNumber) {
		this.aNumber = aNumber;
	}

	public boolean isHasProductName() {
		return hasProductName;
	}

	public void setHasProductName(boolean hasProductName) {
		this.hasProductName = hasProductName;
	}

	public boolean isHasOtherProduct() {
		return hasOtherProduct;
	}

	public void setHasOtherProduct(boolean hasOtherProduct) {
		this.hasOtherProduct = hasOtherProduct;
	}

	public boolean isTitleWithKW() {
		return TitleWithKW;
	}
}
