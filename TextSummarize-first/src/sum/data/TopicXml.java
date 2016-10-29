package sum.data;

import org.json.JSONArray;
import org.json.JSONObject;
import sum.summarizer.SumILP;
import sum.summarizer.Summarizer;
import sum.util.FileIO;
import sum.util.StringUtil;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class TopicXml {
	/**
	 * Parse the topic (xml) file. Each topic file contains a group of documents.  
	 * @param topicfile the xml file
	 * @return a list of document elements
	 */
	/*public static NodeList getDocList(String topicfile) 
	{
		DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(topicfile);
			Element root = doc.getDocumentElement();
			NodeList docList = root.getElementsByTagName("document");
			
			return docList;		
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}	
	}*/
	
	/**
	 * Read xml input file and return a json object containing data for testing if the server work properly.
	 * @param filename the name of the file
	 * @return a json object
	 */
	public static JSONObject getJsonObjectFromXml(String filename) {
		JSONObject toReturn = new JSONObject();
		toReturn.put("lengthLimit", 2);
		toReturn.put("limitType", SumILP.LENGTH_TYPE_SENT_NUM);
		toReturn.put("splitOnline", true);
		toReturn.put("summaryMethod", Summarizer.SUMMARY_METHOD_DP);
		
		JSONArray docs = new JSONArray();
		ArrayList<String> lines = FileIO.readLines(filename);
		int flag = 0; 
		
		String title = null;
		String content = null;
		
		JSONObject doc = null;
		
		for(String line : lines) {
			if(line.startsWith("<title>"))
				flag = 1;
			else if(line.startsWith("<content>"))
				flag = 2;
			
			if(line.startsWith("<document>")) {
				doc = new JSONObject();
			}
			else if(line.startsWith("<original>")) {
				if(flag == 1)
					title = StringUtil.removeXmlTag(line, "<original>", "</original>");
				else if (flag == 2){
					flag = 3;
				}
			} else if(line.startsWith("</document>")) {
                if (doc != null) {
                    doc.put("title", title);
                    doc.put("body", content);
                    docs.put(doc);
                }
			} else if (flag == 3) {
				content = line;
				flag = 0;
			}
		}
	
		toReturn.put("docs", docs);
		return toReturn;
	}
	
	
	/**
	 * Read the xml input file and return a list of documents.
	 */
	public static ArrayList<Document> getDocumentList(String filename) {
		ArrayList<Document> docList = new ArrayList<Document>();
		ArrayList<String> lines = FileIO.readLines(filename);
		int flag = 0; 
		
		String title = null;
		String url = null;
		String datetime = null;
		String parsedTitle = null;
		
		ArrayList<Sentence> sentList = null;
		
		for(String line : lines) {
			if(line.startsWith("<title>"))
				flag = 1;
			else if(line.startsWith("<content>"))
				flag = 2;
			
			if(line.startsWith("<document>")) {
				sentList = new ArrayList<Sentence>();
			}
			else if(line.startsWith("<original>")) {
				if(flag == 1)
					title = StringUtil.removeXmlTag(line, "<original>", "</original>");
			} else if(line.startsWith("<parsed>")) {
				parsedTitle = StringUtil.removeXmlTag(line, "<parsed>", "</parsed>");
			} else if(line.startsWith("<fromURL>")) {
				url = StringUtil.removeXmlTag(line, "<fromURL>", "</fromURL>");
			} else if(line.startsWith("<date>")) {
				datetime = StringUtil.removeXmlTag(line, "<date>", "</date>");
			} else if(line.startsWith("<sen>")) {
				line = StringUtil.removeXmlTag(line, "<sen>", "</sen>");
				Sentence sent = new Sentence(line, null);
                if (sentList != null) sentList.add(sent);
			} else if(line.startsWith("</document>")) {
				Document doc = new Document(title, parsedTitle, url, datetime, sentList);
				docList.add(doc);
			}
		}
	
		return docList;
	}
	
	
	// for test
	public static void main(String [] args) throws Exception {
		System.setOut(new PrintStream(new FileOutputStream("data/log.txt")));
		//ArrayList<Document> docList = TopicXml.getDocumentList("data/mlh.xml");
		
		JSONObject object = getJsonObjectFromXml("data/mlh.xml");
		System.out.println(object.toString());
		
//		System.out.println(docList.size());
//		
//		for(Document doc : docList) {
//			System.out.printf("================ %s ===============\r\n", doc.getTitle());
//			for(Sentence sent : doc.getSentList()) {
//				System.out.println(sent.getText());
//			}
//			System.out.println();
//		}
	}
}
