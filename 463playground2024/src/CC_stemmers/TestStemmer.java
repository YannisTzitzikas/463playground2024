package CC_stemmers;

/**
 * This code justs  tests the stemmer of mitos.
 * 
 * @author  Yannis Tzitzikas
 * @since   2017-01-08
 *
 */

import java.util.Scanner;

import mitos.stemmer.Stemmer;


public class TestStemmer {

	/* just tests some words */
	static void testSomeWords() {
		String[] words =
			{"άνθρωποι", "φυτά", "φυτόν","λουλουδιών", "πράττω", "πράξη", "έπραξα",
			 "persons", "cats","reading","police","policing","polical","computational","environmental"};
		for (String word: words) {
			System.out.println(word +"->" + Stemmer.Stem(word));	
		}
	}
	
	static void typeAndStem() {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Γράψε μια λέξη (0 για τερματισμό):");
		String nextLine ="";
		while (!nextLine.equals("0")) {
			
			nextLine = in.next(); 
			System.out.println(nextLine + " ->  " + Stemmer.Stem(nextLine));
		}
		System.out.println(".End.");
	}
	
	
	public static void main(String[] args){
		
		Stemmer.Initialize();
		testSomeWords();
		typeAndStem();	
	}
}
