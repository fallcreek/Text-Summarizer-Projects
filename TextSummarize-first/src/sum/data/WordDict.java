package sum.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 关键词词典
 * @author Jianfa
 * 
 */
public class WordDict {
	static HashMap<Integer, List<String>> WordDict = new HashMap<Integer, List<String>>();
	
	public WordDict(){
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
	}
	
	public HashMap<Integer, List<String>> getWordDict()
	{
		return WordDict;
	}

}
