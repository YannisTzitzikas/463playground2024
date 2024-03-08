/**
 * 
 */
package AA_fileMgmt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *
 */
public class ConcatenateFiles {
	
	/**
	 * concatenates files in a stream mode (by loading only n bytes in main memory).
	 * Useful for merging partial indexes
	 * @param files
	 * @param outFile
	 */
	static void concatenateFiles(String[] files, String outFile) {
		int n=1; // reads the files in chunks of n bytes
		try {
		OutputStream out = new FileOutputStream(outFile);
	    byte[] buf = new byte[n];
	    for (String file : files) {
	        InputStream in = new FileInputStream(file);
	        int b = 0;
	        while ( (b = in.read(buf)) >= 0)
	            out.write(buf, 0, b);
	        in.close();
	    }
	    out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] lala) {
		
		String pathPrefix = "C:\\Users\\tzitzik\\Desktop\\temp\\";
		
		String[] files = {	pathPrefix + "file1.txt", 
							pathPrefix + "file2.txt"};
		String outFile = 	pathPrefix+"lala.txt";
		concatenateFiles(files,  outFile) ;
		System.out.println("Done");
		
	}
}
