/**
 * 
 */
package II_System;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import BB_lexical.Tokenizer;

import java.net.URL;
import HH_similarity.SimUtilities;


/**
 * 
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *
 */

/**
 * 
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *  Class for document, where each doc created gets a new id, it has a URI and a textual content
 */
class Document {
	static int curID=1;
	int id;
	URI  uri;
	String contents = "EMPTY";
	public String toString() {
		return "Doc"+id +" : " + contents  ;
	}
	Document(File file){
		id =curID++;
		uri = file.toURI();
		try {
			contents = new Scanner(file).useDelimiter("\\Z").next();
			System.out.println("Doc"+id+ ":" + contents);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

/**
 * This class models a document corpus. Each has a root folder in the filesystem (where the documents reside),
 * and a set that holds these documents.
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *
 */
class DocumentCorpus {
	private  String docFolderStr ; //the relative path of the folder that stores the documents
	private  File rootfolder ;  // the root folder
	private  static Set<Document> docs = new HashSet();
	
	public Set<Document> getDocs()  {
		return docs;
	}
	
	DocumentCorpus(String docFolder) {
		setDocumentsFolder(docFolder);
	}
	/**
	 * It sets the roots folder and reads all files
	 * @param docFolder
	 */
	public void setDocumentsFolder(String docFolder) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL u = classloader.getResource(docFolder);
		rootfolder = new File(u.getPath());
		System.out.println("Corpus with root folder " + rootfolder);
		readAllFiles(rootfolder);
		System.out.println("==CORPUS LOADED==" +  " (with "+ docs.size() +" files)");
	}
	
	/**
	 * It recursively reads all files under the root folder
	 * and updates the set that holdes these documents
	 * @param folder
	 */
	void readAllFiles(File folder) {	
		 if (folder==null)   {
			  System.out.println("parameter folder is null");
			  return;
		 }	 
	     for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	//numOfFolders++;
	        	readAllFiles(fileEntry);
	        } else {
	        	docs.add(new Document(fileEntry));
	        }
	    }
	   }
}

/**
 * This class models a toy information retrieval system
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *
 */

public class IRSystem {
   private  static Set<Document> docs = new HashSet(); // holding the documents
   
   void setCorpus(DocumentCorpus dc) {
	   docs=dc.getDocs();
   }
   
   /**
    * It receives a query and returns the answer
    * @param q
    * @return
    */
   public Map getAnswer(String q) {
	   List<String> tokensOfDoc;  
	   List<String> tokensOfQuery;  
	   Set<String> wordsOfDoc;  
	   Set<String> wordsOfQuery; 
	   Set<String> commonwords = null; 
	   Map<Document,Double> scores = new HashMap<Document,Double>();
	   
	   
	   //A finding the docs that have something in common with the query
	   Set<Document> relatedDocs = new HashSet<>();
	   for (Document d: docs) {
		   // A.1 Finding the docs to return
			   
		   // Option 1: a document is retrieved in it contains (exactly) the query (i.e. like plan Ctrl-F)
		   /*
		   if (d.contents.contains(q) ) { //
			     relatedDocs.add(d);
			     scores.put(d, (double) 1); 
		    }
		    */
		   
		   
		   // Option 2: a document is retrieved in it contains (exactly) at least one word of the query
		   tokensOfDoc 		=   Tokenizer.getTokens(d.contents);
		   tokensOfQuery 	=  	Tokenizer.getTokens(q);
		   wordsOfDoc 		=   new HashSet(tokensOfDoc);
		   wordsOfQuery		= 	new HashSet(tokensOfQuery);
		   
		   commonwords 		= new HashSet(wordsOfDoc);
		   commonwords.retainAll(wordsOfQuery); //intersection
		   
		   if (! commonwords.isEmpty()) {
			   relatedDocs.add(d);
		   }
		   
		   //A.2  Scoring each doc that will be returned 
		   if (! commonwords.isEmpty()) {
			   scores.put(d, (double) commonwords.size()); //  similarity = number of common words
		   }
		   
		   
	   }
	   
	   //B. Grouping the found docs wrt their score
	   Map<Double,List<Document>> answerSet =  invertMapUsingGroupingBy(scores);
	   
	   //C. Returning the answer
	    return answerSet;
	}
	 
   
   /**
    * inverses a map (k1,v1)(k2,v2)(k3,v1) to   (v1,{k1,k3})(v2,{k2})
    * @param <V>
    * @param <K>
    * @param map
    * @return
    */
   private static <V, K> Map<V, List<K>> invertMapUsingGroupingBy(Map<K, V> map) {
	    Map<V, List<K>> inversedMap = map.entrySet()
	        .stream()
	        .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
	    return inversedMap;
	}
   
   /**
    * 
    * @param answer
    */
   void showAnswer(Map<Double,List<Document>> answer ) {
	   if (!answer.isEmpty()) {
		   
		   TreeMap<Double,List<Document>> a = new TreeMap<>(Collections.reverseOrder());
		   a.putAll(answer);
		   System.out.println(" Score| Document ");
		   for (Double score: a.keySet()) {
			   //System.out.printf("%4.2f | ",score);
			   for (Document d: a.get(score)) {
				   System.out.printf(" %4.2f | ",score);  
				   System.out.println(" "+ d);
			   }
		   }
	   } else {
		   System.out.println("Nothing found");
	   }
   }
   
}


/**
 * An example of using the Information Retrieval System
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *
 */
class IRSystemClient {
	public static void main (String[] lala) {
	   System.out.println("==SYSTEM STARTS==");	
	   
	   IRSystem irs = new IRSystem();
	   DocumentCorpus corpus = new DocumentCorpus("Collection1");
	   irs.setCorpus(corpus);
	   
	   // Keyword search
	   String[] queries = {"test", "Πέμπτη είναι", "Παρασκευή", "test second words σήμερα is"};
	   for (String q: queries) {
		   System.out.println("Query : " + q);
		   System.out.println("Answer:");
		   irs.showAnswer(irs.getAnswer(q));
		   System.out.println();
	   }
	   System.out.println("==BYE BYE==");
	}
}

