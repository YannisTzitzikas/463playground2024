/**
 * 
 */
package AA_fileMgmt;

import java.io.EOFException;
import java.io.RandomAccessFile;

/**
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 * An example of merging Random Access Files
 */
public class MergeFiles {
	
	/**
	 * 
	 * @param files
	 */
	static void writeDataTo(String[] files) {
		RandomAccessFile rafile = null;
		int K = 10;
		try {
			int j=0;
			for (String file: files) {
				
				rafile = new RandomAccessFile(file,"rw");
				
				for (int i=0;i<K;i++) {
					rafile.writeInt(i);
					rafile.writeChar('A'+j);
				}
				j++;
				rafile.close();
			}
			
		} catch(Exception e) {
			System.out.println(e);
		
		}
				
	}
	
	static void  readFiles(String[] files){
		RandomAccessFile rafile = null;
		try {
			for (String file: files) {
				rafile = new RandomAccessFile(file,"rw");
				int 	intRead;
				char    charRead;
				while(true){
					   try{
						   intRead = rafile.readInt();
							charRead = rafile.readChar();
							System.out.println(intRead + " " + charRead);
					   } catch(EOFException e){
					      break;
					   }
					}
				rafile.close();
			}
			
		} catch(Exception e) {
			System.out.println(e);
		
		}
	}
	
	
	static void  mergeFiles(String file1, String file2, String outfile){
	  try {
		RandomAccessFile rafile1 =  new RandomAccessFile(file1,"r");
		RandomAccessFile rafile2 =  new RandomAccessFile(file2,"r");
		RandomAccessFile rafileo =  new RandomAccessFile(outfile,"rw");
		
		int intRead1, intRead2;
		char charRead1, charRead2;
		
		while(true) {
			
			try{
				    intRead1 = rafile1.readInt();
					charRead1 = rafile1.readChar();
					intRead2 = rafile2.readInt();
					charRead2 = rafile2.readChar();
					
					rafileo.writeInt(intRead1);
					rafileo.writeChar(charRead1);
					rafileo.writeInt(intRead2);
					rafileo.writeChar(charRead1);
					
					System.out.println(intRead1 + " " + charRead1 + "  " + intRead2 + " " + charRead2);
		    } catch(EOFException e){
			      break;
		    }
			
		}
		rafile1.close();
		rafile2.close();
		rafileo.close();
		
	  	} catch(Exception e){
	       System.out.println(e);
	  	}
	}

    public static void main(String[] lala) {
    	String pathPrefix = "C:\\Users\\tzitzik\\Desktop\\temp\\";
		
		String[] files = {	pathPrefix + "fileNumbersA.txt", 
							pathPrefix + "fileNumbersB.txt"};
		String outFile = 	pathPrefix + "fileNumbersAB.txt";
		
		writeDataTo(files) ;
		readFiles(files);
		mergeFiles(files[0],files[1], outFile);
		
		System.out.println("Done");
    }

}
