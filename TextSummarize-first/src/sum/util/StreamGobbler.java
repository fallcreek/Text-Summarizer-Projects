package sum.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class is used for reading the console output of a subprocess. When 
 * running command from java code, this class is useful since on some platform
 * the output of subprocess may block the process. 
 * @author jinfeng
 *
 */
public class StreamGobbler extends Thread {
	InputStream is;
	String type;
	String output;

	public StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
		output = "";
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (type.equals("Error"))
					System.err.println(line);
				else
					output += line + "\n";
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public String getOutput() { return output; }
	
}