package sum.util;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import sum.data.Sentence;
import sum.net.SummarizeServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

public class SentenceUtil {
    private static String sentenceModelFile = "data/model/en-sent.bin";

    public static void setSentenceModelFile(String s) {
        sentenceModelFile = s;
    }

	public static ArrayList<String> getChineseWords(String parsedText) {
		String [] tokens = parsedText.split(" ");
		ArrayList<String> ret = new ArrayList<String>();
		
		for(String token : tokens) {
			int pos = token.indexOf('/');
			
			if(pos < 0)
				continue;
			
			String word = token.substring(0, pos).trim();
			String tag = token.substring(pos + 1).trim();

            if (word.length() == 1) { //如果是中文标点符号，则跳过
                char c = word.charAt(0);
                if (isChinesePunctuation(c)) {
                    continue;
                }
            }
            ret.add(word);
		}
		return ret;
	}
	
	/**
	 * @author lizhengyao
	 * @param parsedText
	 * @return 有词性的分词结果
	 */
	public static ArrayList<String> getChineseWords2(String parsedText) {
		String [] tokens = parsedText.split(" ");
		ArrayList<String> ret = new ArrayList<String>();
		
		for(String token : tokens) {
			int pos = token.indexOf('/');
			
			if(pos < 0)
				continue;
			
			String word = token.substring(0, pos).trim();
			String tag = token.substring(pos + 1).trim();

            if (word.length() == 1) { //如果是中文标点符号，则跳过
                char c = word.charAt(0);
                if (isChinesePunctuation(c)) {
                    continue;
                }
            }
            ret.add(word + "/" + tag);
		}
		return ret;
	}


    public static ArrayList<String> getEnglishWords(String text) {
        text = text.replaceAll("\\p{P}" , "").trim(); //去掉英文标点
        String[] words = text.split(" ");
        ArrayList<String> ret = new ArrayList<String>();
        for (String w : words) {
            Stemmer stemmer = new Stemmer();
            stemmer.addStr(w.toLowerCase());
            stemmer.stem();
            w = stemmer.toString();
            ret.add(w);
        }
        return ret;
    }
	
	public static String getOriginalString(String splitted) {  //这个函数的作用？
		String[] words = splitted.split(" ");
		StringBuffer sb = new StringBuffer();
		for (String s : words) {
			int index = s.lastIndexOf("/");
			if (index != -1) {
				if (index == 0) { //如果切分前是空格的话
					s = " "; 
				}
				else {
				    s = s.substring(0, index);
				}
			}
			sb.append(s);
		}
		return sb.toString().trim();
	}
	
	public static ArrayList<Sentence> getChineseSentences(String content, boolean splitOnline) {
		if (splitOnline) {
			if (SummarizeServer.serverType == 1) {
				content = new WordSplitterSocket().testSegment(content);
			}
			else {
				content = new WordSplitterPost().testSegment(content);
			}
		}

		ArrayList<String> lines = segChineseSentences(content);
		ArrayList<Sentence> sentList = new ArrayList<Sentence>();
		Sentence sent;
		for (String s : lines) {
			//System.out.println(getOriginalString(s) + s.length());
			if (s.length() <= 3 || s.length() >= 400) {  //10.27 200改为400
				continue;
			}
			
//			System.out.println(s);  //Jianfa 显示分词情况
			sent = new Sentence(s, null);
			sentList.add(sent);
		}
        return sentList;
	}

    public static ArrayList<Sentence> getEnglishSentences(String content) {
        ArrayList<String> lines = segEnglishSentences(content);
        ArrayList<Sentence> sentList = new ArrayList<Sentence>();
        Sentence sent;
        for (String s : lines) {
            sent = new Sentence(s, null);
            sentList.add(sent);
        }

        return sentList;
    }

    public static ArrayList<String> segEnglishSentences(String content) {
        InputStream modelIn = null;
        SentenceModel model = null;
        try {
            modelIn = new FileInputStream(sentenceModelFile);
            model = new SentenceModel(modelIn);

        } catch (FileNotFoundException e) {
            System.err.println("Sentence Detection model files not found!");
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (modelIn != null) try {
                modelIn.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> ret = new ArrayList<String>();
        if (model != null) {
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
            String[] sentences = sentenceDetector.sentDetect(content);
            for (String s : sentences) {
                ret.add(s);
            }
        }
        return ret;
    }
	
	public static ArrayList<String> segChineseSentences(String content) {
		ArrayList<String> list = new ArrayList<String>();
		String temp = content;
		
        temp = temp.trim().replaceAll("(@)?(href=\")?(http://)?[A-Za-z]+(\\.\\w+)+(/[&\\n=?\\+\\%/\\.\\w]+)?","");  
        temp = temp.trim().replaceAll("[`^&*()+=|{}\\[\\]<>￥%（）——|__:【】‘”“’] ","");
        temp = temp.trim().replaceAll("　+", "");
        temp = temp.replaceAll("(\\d{0,4})-?(\\d{2})-(\\d{1,2}) ?\\d{2}:\\d{2}:?\\d{0,2}","")  
                .replaceAll("★|●|\\*|'+", "")  
                .replaceAll("~", "。")  
                .replaceAll("～", "。")  
                .replaceAll("！", "。")  
                .replaceAll("!", "。")  
                .replaceAll("？", "。")  
                .replaceAll("﹖", "。")  
                .replaceAll(";", "。")  
                .replaceAll("；", "。")  
                .replaceAll("。+", "。")
                .replaceAll("\\.\\.", "。")
                .replaceAll("……", "。"); 
        
		// 根据中文标点符号切分
		BreakIterator boundary = BreakIterator
				.getSentenceInstance(Locale.CHINESE);
		// 设置句子在文中的位置索引
		int index = 1;
		// 设置要处理的文本
		boundary.setText(temp);
		int start = boundary.first(); // 设置开始位置
		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary
				.next()) {
			// 输出子串
			// System.out.println(temp.substring(start, end)+":"+i);
			String str = temp.substring(start, end).trim();
			if (isChineseSentence(str)) {
				list.add(str);
			}
		}

		return list;
        
	}
	
	/**
	 * 判断切分字符串是否为中文句子
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isChineseSentence(String str) {
		boolean isSen = false;
		if (isChinese(str.charAt(0))) {
			isSen = true;
		}// 第一个字符为中文则判定该句为中文
		else {
			if (isChinese(str)) {
				isSen = true;
			}// 句子中含中文则判定该句为中文
		}
		return isSen;
	}

	/**
	 * 判断输入字符是否为中文
	 * 
	 * @param c
	 * @return boolean
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
//                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
//                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
            return true;
        } else {
            return false;
        }
	}

	/**
	 * 判断输入字符串是否为中文
	 * 
	 * @param strName
	 * @return boolean
	 */
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 利用通用编码区间来判断是否为中文,编码区间为[0x4e00-0x9fbb][\u4E00-\u9FA5]"
	 * 
	 * @param chineseStr 待判断的字符串
	 * @return boolean
	 */
	public static boolean isChineseCharacter(String chineseStr) {
		char[] charArray = chineseStr.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
				return true;
			}
		}
		return false;
	}
    // 根据UnicodeBlock方法判断中文标点符号
    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
//                || ub == Character.UnicodeBlock.VERTICAL_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args){
    	String s = "如果光从设计上看，可以说目前为止，无论是外观还是软硬件升级上，当年的iPhone 4都可以说是苹果历代产品中最为成功的一款，以至于iPhone 4S都在外观上延续了这一经典的设计。";
    	ArrayList<Sentence> a = new ArrayList<Sentence>();
    	a = SentenceUtil.getChineseSentences(s,true);
//    	System.out.println(a.get(0).toString());
    }
}
