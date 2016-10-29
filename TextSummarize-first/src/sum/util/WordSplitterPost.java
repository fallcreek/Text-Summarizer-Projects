
package sum.util;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WordSplitterPost extends PostMethod {
	private static String url = "http://218.245.4.117:8080/ThulacJni2/wordsegment.jsp";
	
	public WordSplitterPost(String url) {
		super(url);
	}
	
	public WordSplitterPost() {
		super(url);
	}
	
	public static void setUrl(String url) {
		WordSplitterPost.url = url;
	}
	
	@Override
	public String getRequestCharSet() {
		// return super.getRequestCharSet();
		return "UTF-8";
	}
	
	public String testSegment(String t){
		try{
		this.addParameter("text", t);
		HttpClient client = new HttpClient();
		client.executeMethod(this);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				this.getResponseBodyAsStream(), "UTF8"));
		StringBuffer tempSB = new StringBuffer("");
		String temp;
		while ((temp = reader.readLine()) != null) {
			tempSB.append(temp).append("\n");
		}
		reader.close();
		this.releaseConnection();
		return tempSB.toString();
	
		}catch(IOException e){
			e.printStackTrace();
		return null;
	}
}
	public static void main(String[] args) throws Exception {
		System.out.println("————————————————————————————————————————————————————————");
		String s = new WordSplitterPost().testSegment("计算机系创业导师团聘任大会暨第一次创业沙龙活动圆满结束。");
		System.out.print(s);
		System.out.println("————————————————————————————————————————————————————————");	
	}
}
