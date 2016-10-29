package sum.util;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class WordSplitterSocket {

	private static String ip = "118.192.65.79";
	private static int port = 12131;
	private String _ip;
	private int _port;
	final String CONFIG_EOF = "#END#";
	public WordSplitterSocket() {
		this._ip = ip;
		this._port = port;
	}
	public WordSplitterSocket(String ip, int port) {
		this._ip = ip;
		this._port = port;
	}
	public static void setIpPort(String ip, int port) {
		WordSplitterSocket.ip = ip;
		WordSplitterSocket.port = port;
	}
	
	public String testSegment(String toSend) {
		Socket client = null;
        PrintWriter out = null;
        BufferedReader in = null;
		try {
			client = new Socket(_ip, _port);
			out = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream(), "UTF-8"));
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream(), "UTF-8"));
			out.println(toSend);
			out.println(CONFIG_EOF);
			out.flush();
			
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				if (line.equals(CONFIG_EOF)) {
					break;
				}
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString().trim();
			
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
	
	public static void main(String[] args) {
//		String content = "hello,world. This is Dr. Hu. I'm happy! So do you.";
		String content = "外观很美。外形很漂亮！设计很精致，但是外型像是动物。外壳太硬了。这个外表我不喜欢。";
//        ArrayList<String> sentences = SentenceUtil.segChineseSentences(content);
		WordSplitterSocket test = new WordSplitterSocket();
		String sentences = test.testSegment(content);
		System.out.println(sentences);

    }
}
