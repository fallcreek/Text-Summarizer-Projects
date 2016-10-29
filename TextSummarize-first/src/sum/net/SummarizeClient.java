package sum.net;

import opennlp.tools.stemmer.Stemmer;
import org.json.JSONObject;
import sum.util.FileIO;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SummarizeClient  {
	String ip;
	int port;

	public SummarizeClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getSummary(JSONObject toSend) {
		Socket client = null;
        PrintWriter out = null;
        BufferedReader in = null;
		try {
			client = new Socket(ip, port);
			out = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream(), "UTF-8"));
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream(), "UTF-8"));
			out.println(toSend.toString());
			out.println(SummarizeServer.EOF);
			out.flush();
			
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				if (line.equals(SummarizeServer.EOF)) {
					break;
				}
				sb.append(line);
				sb.append("\n");
			}
			//System.out.println(sb.toString());
			JSONObject o = new JSONObject(sb.toString());
			return o.getString("abstract");
			//return sb.toString();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (out != null) out.close();
            if (in != null) try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (client != null) try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

	}
	
	public static void main(String[] args) throws Exception {
//		String content = FileIO.readFile("data/test.json"); //需要在线分词的测试例子
//		JSONObject test1 = new JSONObject(content);
//		
//		//Server端部署在远程服务器上的情况下的使用方法
//		SummarizeClient client = new SummarizeClient("118.192.65.79", 12321);
//		System.out.println(client.getSummary(test1));
//		
//		
//		JSONObject test2 = new JSONObject(FileIO.readFile("data/test2.json")); //不需要在线分词的测试例子
//		//本地调用摘要服务的方法
//		JSONObject result = new JSONObject(SummarizeServer.getSummarize(test2.toString(), "config"));
//		System.out.println(result.get("abstract"));
		
		JSONObject note3 = new JSONObject(FileIO.readFile("data/test 2.json"));
		JSONObject result = new JSONObject(SummarizeServer.getSummarize(note3.toString(), "config"));

        //SummarizeClient client = new SummarizeClient("127.0.0.1", 12321);
		
		System.out.println(result.get("abstract"));
	}

}
