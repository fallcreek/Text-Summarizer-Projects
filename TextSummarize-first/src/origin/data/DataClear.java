package origin.data;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class DataClear {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "data/zol/000010011.xml";
//		String[] source = {"it168", "pconline", "sina", "zol"};

		File file = new File(path);
		SAXReader reader = new SAXReader();
		
		System.out.println("haa");
		try{
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			List<Element> evaluations = root.elements();
			
			String title;
			for(Element evaluation: evaluations)
			{
				title = evaluation.attributeValue("title");
				if(title != "")
				{
					String temp = evaluation.getText();
					temp = temp.replaceAll(" +", " ");
					evaluation.setText(temp);
				}
			}
			
			OutputFormat format = OutputFormat.createPrettyPrint(); 
			
			format.setNewLineAfterDeclaration(false);  
            format.setEncoding("UTF-8");  
            format.setExpandEmptyElements(true);  
              
            // 输出  
            FileOutputStream output = new FileOutputStream(new File(path));  
            XMLWriter writer = new XMLWriter(output, format);  
            writer.write(doc);  
            writer.flush();  
            writer.close();
			
		}catch(Exception e){
			System.err.println(e.toString());
		}
	}

}
