package sum.data;

import org.json.JSONArray;
import org.json.JSONObject;
import sum.net.SummarizeServer;
import sum.util.SentenceUtil;
import sum.util.WordSplitterPost;
import sum.util.WordSplitterSocket;

import java.util.ArrayList;

/**
 * Read from a json array list and return a list of documents
 * @author Naitong
 *
 */
public class NetJson {
	public static ArrayList<Document> getDocumentList(JSONArray array, boolean splitOnline) {
		ArrayList<Document> documents = new ArrayList<Document>();

		for (int i = 0; i< array.length(); i++) {
            JSONObject doc = array.getJSONObject(i);

            String test = doc.getString("title"); //使用title来判断语言，如果title中含中文则认为是中文，否则英文

            if (SentenceUtil.isChineseSentence(test)) { //文档为中文
                String title, parsedTitle;
                if (splitOnline) {
                    title = doc.getString("title");
                    if (SummarizeServer.serverType == 1) {
                        parsedTitle = new WordSplitterSocket().testSegment(title);
                    }
                    else {
                        parsedTitle = new WordSplitterPost().testSegment(title);
                    }
                }
                else {
                    parsedTitle = doc.getString("title");
                    title = SentenceUtil.getOriginalString(parsedTitle);
                }
                ArrayList<Sentence> sentList = SentenceUtil.getChineseSentences(doc.getString("body"), splitOnline);
                Document tmp = new Document(title, parsedTitle, null, null, sentList);
                documents.add(tmp);
            }
            else { //文档为英文，则忽略splitOnline信息
                String title = doc.getString("title");
                ArrayList<Sentence> sentList = SentenceUtil.getEnglishSentences(doc.getString("body"));
                Document tmp = new Document(title, title, null, null, sentList);
                documents.add(tmp);
            }

		}
		return documents;
		
	}
	
}
