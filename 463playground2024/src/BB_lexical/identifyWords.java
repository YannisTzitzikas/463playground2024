package BB_lexical;

/**
 * Just shows steps of word identification
 * (tokenization, lowercase, removal of accents)
 */
import java.text.Normalizer;
import java.text.Normalizer.Form;
/*
 * It shows how to tokenize a line
 */
import java.util.StringTokenizer;

class identifyWords {
	
	
	// ongoing
	static void IdentifyWordsOf(String line) {
		 String delimiter = "\t\n\r\f.,?;: ";
		 StringTokenizer tokenizer = new StringTokenizer(line, delimiter);
		 while(tokenizer.hasMoreTokens() ) {
		 String currentToken = tokenizer.nextToken();
		 	System.out.print(currentToken);
		 	
		 	String currentTokenLowerCase = currentToken.toLowerCase();
		 	System.out.print("\t"+ currentTokenLowerCase);
		 	
		 	// removal of accents
		 	System.out.println("\t" + Normalizer.normalize(currentTokenLowerCase,	Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
		 }
    }
}

 class Test {
	 public static void main(String[] lala) {
		 String line = "Hello, MY name is John, how are you? ";
		 String lineGR = "Σήμερα έχει καλό καιρό.";
		 identifyWords.IdentifyWordsOf(line+lineGR); 
	 }
 }
 