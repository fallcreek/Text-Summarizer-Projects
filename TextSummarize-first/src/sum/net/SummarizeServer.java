package sum.net;

import org.json.JSONArray;
import org.json.JSONObject;
import sum.data.Document;
import sum.data.NetJson;
import sum.data.Stopwords;
import sum.summarizer.ChineseSum;
import sum.util.SentenceUtil;
import sum.util.WordSplitterPost;
import sum.util.WordSplitterSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Leon
 * @changed by fun 这个类要重新 实现，是服务端的 监听程序
 */
public class SummarizeServer {
    public static final String EOF = "__EOF__"; //定义为终止时发送的控制符
    public static int serverType = 1; // 1表示socket类型的分词服务器，2表示post类型
    public static String configFileName = null;

    /**
     * 输入Json格式的数据，并且解析获得摘要结果。
     *
     * @param input      输入字符串（json格式）
     * @param configFile 配置文件名称
     * @return 摘要结果，以Json格式返回，详见文档。
     */
    public static String getSummarize(String input, String configFile) {
        HashMap<String, String> argMap = readConfig(configFile);
        if (argMap.containsKey("stopword")) {
            //System.out.println("Stopword file is " + argMap.get("stopword"));
            Stopwords.setStopWordsFile(argMap.get("stopword"));
        }

        if (argMap.containsKey("model")) {
            SentenceUtil.setSentenceModelFile(argMap.get("model"));
        }

        if (argMap.containsKey("servertype") && argMap.get("servertype").equals("socket")) {
            SummarizeServer.serverType = 1;
            //System.out.println("Word split server type is socket");
            if (argMap.containsKey("ip") && argMap.containsKey("port")) {
                WordSplitterSocket.setIpPort(argMap.get("ip"), Integer.parseInt(argMap.get("port")));
                //System.out.println(argMap.get("ip") + ":" + argMap.get("port"));
            } else {
                System.err.println("can't find ip and/or port config!");
                System.exit(-1);
            }
        }

        if (argMap.containsKey("servertype") && argMap.get("servertype").equals("post")) {
            SummarizeServer.serverType = 2;
            //System.out.println("Word split server type is post");
            if (argMap.containsKey("url")) {
                //System.out.println(argMap.get("url"));
                WordSplitterPost.setUrl(argMap.get("url"));
            } else {
                System.err.println("can't find url config!");
                System.exit(-1);
            }
        }

        //////////////////////////////////////////////////////
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(input);
        } catch (Exception e) {
            SumLog.log(e.getMessage());
            return null;
        }
        JSONArray docs = jsonObj.getJSONArray("docs");
        int lengthLimit = jsonObj.getInt("lengthLimit");
        int limitType = jsonObj.getInt("limitType");
        boolean splitOnline = jsonObj.getBoolean("splitOnline");
        int summaryMethod = 1; //1:MMR  2:SumILP  3:DiversityPenalty
        if (jsonObj.has("summaryMethod")) {
            summaryMethod = jsonObj.getInt("summaryMethod");
        }
        int selectMethod = 1; //1: first 5 sentences  2: first 1 sentence  3: all sentences
        if (jsonObj.has("selectMethod")) {
            selectMethod = jsonObj.getInt("selectMethod");
        }

        ArrayList<Document> docList = NetJson.getDocumentList(docs, splitOnline);
        String result;
        ChineseSum cs = new ChineseSum();
        if (docList.size() == 1) {
            result = cs.genSDS(docList.get(0), lengthLimit, limitType, summaryMethod);
        } else {
            result = cs.genMDS(docList, lengthLimit, limitType, summaryMethod, selectMethod);
        }

        JSONObject o = new JSONObject();
        o.put("abstract", result);
        return o.toString();
    }

    static public HashMap<String, String> readArgs(String args[]) {
        HashMap<String, String> result = new HashMap<String, String>();
        for (int i = 0; i < args.length; i = i + 2) {
            if (i == args.length - 1)
                break;
            String argName = args[i];
            if (argName.startsWith("-")) {
                argName = argName.substring(1);
            }
            String argValue = args[i + 1];
            result.put(argName, argValue);
        }
        return result;
    }

    static public HashMap<String, String> readConfig(String filePath) {
        HashMap<String, String> result = new HashMap<String, String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String line;
            String[] argPair;
            String argName;
            String argValue;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.trim().length() == 0) {
                    continue; //skip the comment
                }
                if (line.contains("#")) {
                    line = line.substring(0, line.indexOf("#")).trim();
                }
                argPair = line.split("=");
                argName = argPair[0].trim();
                argValue = argPair[1].trim();
                result.put(argName, argValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    static public void help() {
        System.err.println("Usage: java -jar SummarizeServer.jar -config CONFIGFILE");
    }

    static public void main(String[] args) {
        if (args.length != 2 || !args[0].equals("-config")) {
            help();
            System.exit(-1);
        }

        configFileName = args[1];
        File configFile = new File(configFileName);
        if (!configFile.exists()) {
            System.err.println("CONFIGFILE: \"" + configFileName + "\" does NOT exists!");
            System.exit(-1);
        }
        HashMap<String, String> argMap = readConfig(configFileName);
        int port = 0;
        String logFile = null;

        if (argMap.containsKey("p")) {
            System.out.println("Listening on port " + argMap.get("p"));
            port = Integer.parseInt(argMap.get("p"));
        } else {
            System.err.println("No port config!");
            System.exit(-1);
        }

        if (argMap.containsKey("logfile")) {
            System.out.println("Log file is " + argMap.get("logfile"));
            logFile = argMap.get("logfile");
        } else {
            System.err.println("No logfile config!");
            System.exit(-1);
        }

        SumLog.setLogfile(logFile);
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            while (true) {
                Socket client = server.accept();

                SumLog.log("[SummarizeServer] client comes from " + client.getInetAddress());

                CreateServer newServer = new CreateServer(client);
                newServer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SumLog {
    private static PrintWriter writer = null;
    private static FileOutputStream fos = null;
    private static String logFile = "SummarizeServer.log";

    public static void setLogfile(String logFile) {
        SumLog.logFile = logFile;
    }

    public static void log(String logString) {
        if (writer == null) {
            try {
                fos = new FileOutputStream(logFile, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writer = new PrintWriter(fos, true);
        }
        writer.println(logString);
    }
}

class CreateServer extends Thread {
    static int allServer = 0;
    Socket client;
    PrintWriter out;
    BufferedReader in;
    int id;

    public CreateServer(Socket s) throws IOException {
        client = s;
        id = allServer++;
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                client.getOutputStream(), "UTF-8")));
        in = new BufferedReader(new InputStreamReader(client.getInputStream(),
                "UTF-8"));
    }

    /**
     * Note: in JSON string, "{}" indicates a json object,
     * and "[]" indicates a json array
     */
    public void run() {
        try {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                if (line.equals(SummarizeServer.EOF)) {
                    break;
                }
                sb.append(line);
            }

            // read all content from socket
            String res = sb.toString().trim();

            // System.out.println("server received: " + res);
            SumLog.log("[Summarizer:" + id + "] server received " + res.length() + " bytes");

            out.println(SummarizeServer.getSummarize(res, SummarizeServer.configFileName));
            out.println(SummarizeServer.EOF);
            out.flush();
            SumLog.log("[Summarizer:" + id + "] client disconnected");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            try {
                if (in != null)
                    in.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            try {
                client.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}