package sum.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MatrixUtil {
	public static void writeTable(String filepath, double [][] data) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(filepath));
            for(int i = 0; i < data.length; i++) {
                for(int j = 0; j < data[0].length; j++)
                    out.print("" + data[i][j] + "\t");
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) out.close();
        }
	}
	
	public static double [][] transpose(double [][] mat) {
		double [][] tr = new double[mat[0].length][mat.length];
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat[0].length; j++) {
				tr[j][i] = mat[i][j];
			}
		}
		
		return tr;
	}
}
