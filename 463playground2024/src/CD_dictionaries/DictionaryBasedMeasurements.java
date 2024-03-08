/**
 * 
 */
package CD_dictionaries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


/**
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *
 * It loads and performs a few basic measurements over a Greek dictionary
 */
public class DictionaryBasedMeasurements {
	static private String		  		placeDict = null;  	// the path for locating the dictionary in the Resources
	static private BufferedReader 		readerDict = null; 	// it is defined in this way for locating the dictionary within the IDE and in a packaged jar
	private static Set<String >			wordsSet = null;	// the set of words of the dictionary if loaded
	
		
	/**
	 * Sets the location of the dictionary in a way that ensures readability (within an IDE and in a packaged jar)
	 * @param resourcePlace the resource place
	 */
	static public void setDictionaryLocation(String resourcePlace) {
		placeDict=resourcePlace;
		InputStream inDict = DictionaryBasedMeasurements.class.getResourceAsStream(placeDict); 
		try {
			readerDict = new BufferedReader(new InputStreamReader(inDict,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * It performs a few statistical measurements over the dictionary (about the distribution of codes).
	 * @param bfr  the BuffferedReader  of the dictionary
	 */
	public static void performMeasurements(BufferedReader bfr) {
			int counter = 0;  		// word counter
			int totalCharNum = 0;
			int wordMinSize = Integer.MAX_VALUE;
			int wordMaxSize = 0;
			int curWordSize = 0;
	        String line;
	         
			try {        
		        while ((line = bfr.readLine()) != null) {
		        	counter++; 						// counting words
		        	curWordSize = line.length(); 	// size of current word
		        	totalCharNum+=curWordSize;  	// adding chars of current word
		        	if (curWordSize <wordMinSize ) wordMinSize=curWordSize;  // for min/max word sizes
		        	if (curWordSize >wordMaxSize ) wordMaxSize =curWordSize;
		        }
	        } catch (Exception e) {
	        	System.out.println(e);
	        }
			System.out.println("Number of words read      : " + counter);
	        System.out.println("Total number of chars read: " + totalCharNum);
	        System.out.println("Avg word size             : " + (totalCharNum+0.0)/counter);
	        System.out.println("Min word size             : " + wordMinSize);
	        System.out.println("Max word size             : " + wordMaxSize);
	}
	
	
	/**
	 * It looks up a word in the dictionary. If the dictionary is not loaded, it loads it.
	 * @param word
	 * @return
	 */
	public static boolean lookup(String word) {
		
		if  (wordsSet==null) { // if the dictionary has not been processed
			wordsSet = new HashSet<>(); // for keeping the words of the dictionary    
			String line;
				try {
					InputStream inDict = DictionaryBasedMeasurements.class.getResourceAsStream(placeDict); 
					BufferedReader bfr = new BufferedReader(new InputStreamReader(inDict,"UTF-8"));
					//	FileReader fl = new FileReader("Resources/dictionaries/EN-winedt/gr.dic");	BufferedReader bfr = new BufferedReader(fl);	
					while ((line = bfr.readLine()) != null) {
						wordsSet.add(line);	
					}
					//bfr.close(); // TODO to check if ok
				} catch (Exception e) {
					System.out.println(e);
				}
				//System.out.println(codesToWords);
				//System.out.println("Dictionary was read, number of keys = " + codesToWords.keySet().size());
		}		
		return wordsSet.contains(word);
	}
	
		
	public static void main(String[] args) {
		System.out.println("[DictionaryBasedMeasurements]-start");
		
		// Setting the place of the dictionary (for being accessible from the IDE and in a package jar)
		String dictResourcePlace = "/Dictionaries/GR-winedt/gr.dic";    
		setDictionaryLocation(dictResourcePlace);

		performMeasurements(readerDict);   // for general measurements over the dictionary
		
		
		// EXAMPLE OF LOOKUP
		String[] ws = {"Γιάννης", "Γιάνης", "Ηράκλειο" };
		for (String w: ws) {
			System.out.println(w +" " +  lookup(w));
		}
	}




}
