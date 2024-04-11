/**
 * 
 */
package JJ_Indexes;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import DD_phonemic.SoundexGRExtra;
import mitos.stemmer.Stemmer;

/**
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *  Simple verson of an  Inverted File (everything is in main mem)
 *  Future todo: the postings are stored in a file
 */



class VocabularyEntry implements Comparable {
	String word;
	// String normalizedWord   // lowercase, not accents
	String stem 				="noStemyet" ;
	String phonemicCode 		="nocodeYet" ; // soundex
	String phonemicTranscirption="noCodeYet";
	//Double[] wordvector = new Double[300]; // word2vec, BERT
	int    df =0; // document frequency
	Set<Integer> docIds = new TreeSet<>(); // the ids of the docs that contain the word
	@Override
	public int compareTo(Object arg0) {  // ordering wrt word
		if (arg0 instanceof VocabularyEntry) {
			VocabularyEntry v2 = (VocabularyEntry)arg0;
			return word.compareTo(v2.word);
		}
		return 0;
	}	
	public String toString() {
		String ret=String.format(" |%14s |%14s |%7s  |%10s |%4d |-> %s", word, stem, phonemicCode, phonemicTranscirption, df, docIds );  
		return ret;
//		return word + "\t" + stem + "\t" + phonemicCode + "\t" + df;
	}
}

/** The vocabulary of an inverted file
 * 
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *
 */
class Vocabulary {
	TreeSet<VocabularyEntry> vocabulary = new TreeSet<>();
	
	/**
	 * adds or updates
	 * @param s
	 * @param docId
	 * @return true if the word was already
	 */
	
	boolean addWord(String s, int docId ) {
		boolean found=false;
		for (VocabularyEntry ve: vocabulary) {
			if (ve.word.equals(s)) {
				ve.df = ve.df+1; //increases the df
				ve.docIds.add(docId);
				found=true;
			}
		}
		if (!found) {
			VocabularyEntry ve = new VocabularyEntry();
			ve.word=s;
			ve.stem = Stemmer.Stem(s);
			ve.phonemicCode = SoundexGRExtra.encode(s);
			ve.phonemicTranscirption =  SoundexGRExtra.phoneticTrascription(s);
			
			ve.df =1;
			ve.docIds.add(docId);
			vocabulary.add(ve);
		}
		return found;
	}
	
	public String toString() {
   	 String s="\nVocabulary (word,stem,phonemic,phonemicTranscr, df,docIds) with " + vocabulary.size() + " words:\n"; 
   	 
   	 for (VocabularyEntry ve:vocabulary )
   		 s+=ve+"\n";
   	 return s;
    }
	Vocabulary() {
		Stemmer.Initialize();
	}
}

public class InvertedFile {
	  Vocabulary vocab = new Vocabulary();
	  int numOfCallsToAddAWord=0;
      //Object postingLists;
      void addDocument(int id, String contents) {
    	  for (String s:  BB_lexical.Tokenizer.getTokens(contents)) {
    		  // s -> toLowerCase, remove Accents
    		  // if GRSF.contains(s)
    		  vocab.addWord(s, id);
    		  numOfCallsToAddAWord++;
    	  }
  	 }
     public String toString() {
    	 return vocab.toString() +
    			 "\nNumber of word additions = " + numOfCallsToAddAWord;
     }
}


class IFtest {
	/**
	 * Reads all files from a folder and created an IF for that collection
	 */
	static void voidReadCollectionAndMakeIndex() {
		//
		System.out.println("==Inverted File==");
		InvertedFile IF = new InvertedFile();
		
		
		//Reading files from a folder and createdin the IV
		int docId=1;
		String collectionFolder = "Collection2Real/greek";
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL u = classloader.getResource(collectionFolder);
		File rootfolder = new File(u.getPath());
		System.out.println("Corpus with root folder " + rootfolder);
		for (File fileEntry : rootfolder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	//numOfFolders++;
	        	//readAllFiles(fileEntry);
	        } else {
	        	// read the contents of the file
	        	String contents="";
	    		try {
	    			 contents = new Scanner(fileEntry).useDelimiter("\\Z").next();
	    		} catch (FileNotFoundException e) {
	    			e.printStackTrace();
	    		}
	    		
	        	IF.addDocument(docId, contents);
	        	docId++;
	        }
	    }
		
		// printing the IF
		System.out.println(IF);
	}
	
	
	public static void main(String[] ala) {
		
		
		
		System.out.println("==TOY Inverted File==");
		InvertedFile IF = new InvertedFile();
		IF.addDocument(2, "Καλημέρα Κααλλειμαίρραα");
		IF.addDocument(3, "Καλημέρες και καλή εβδομάδα");
		IF.addDocument(4, "Καλά κρασιά");
		IF.addDocument(5, "Καλή Καλημέρα");
		System.out.println(IF);
		
		
		voidReadCollectionAndMakeIndex();
		
		
	}
}
