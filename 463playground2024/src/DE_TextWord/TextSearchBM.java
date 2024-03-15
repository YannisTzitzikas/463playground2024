/**
 * 
 */
package DE_TextWord;

//todo: experiments on very big txt

class TextSearchBM{
	
	static int NO_OF_CHARS = 256;

	static int max (int a, int b) { return (a > b)? a: b; }      //returns max of two numbers

	static void badCharHeuristic( char []str, int size,int badchar[])
	{

	// Initialize all occurrences as -1
	for (int i = 0; i < NO_OF_CHARS; i++)
		badchar[i] = -1;

	// Fill the actual value of last occurrence of a character
	for (int i = 0; i < size; i++)
		badchar[(int) str[i]] = i;
	}

	static void search( char txt[], char pat[])
	{
	int m = pat.length;
	int n = txt.length;

	int badchar[] = new int[NO_OF_CHARS];

	badCharHeuristic(pat, m, badchar);

	int s = 0; 
	while(s <= (n - m))
	{
		int j = m-1;

		/* Keep reducing index j of pattern while
			characters of pattern and text are
			matching at this shift s */
		while(j >= 0 && pat[j] == txt[s+j])
			j--;

		/* If the pattern is present at current
			shift, then index j will become -1 after
			the above loop */
		if (j < 0)
		{
			System.out.println("Patterns occur at shift = " + s); // This is the resutls of search
			s += (s+m < n)? m-badchar[txt[s+m]] : 1;
		}
		else
			s += max(1, j - badchar[txt[s+j]]);
	}
}

	public static void main(String []args) {
		
		// Plain text search using Java String contains
		String text ="Today is Friday and it is one great day (just before the weekend) ";
		String pattern = "Friday and";
		System.out.println(text.contains(pattern));
		
		
		//BM search
		char txt[] = text.toCharArray();
		char pat[] = pattern.toCharArray();
		
		search(txt, pat);
		
		
		/*
		int numOfExperiments = 1000000;
		long t = java.lang.System.currentTimeMillis();
		
		for (int i=0; i<numOfExperiments;i++) {
			text.contains(pattern);
		}
		//System.out.println(text.contains(pattern));
		
		System.out.println(
				"Cost of Brute Force="+ (java.lang.System.currentTimeMillis()- (long) t)); 
		
		
		
		//experiment
		t = java.lang.System.currentTimeMillis();
		for (int i=0; i<numOfExperiments;i++) {
			search(txt, pat);
		}
		System.out.println(
				"Cost of MB: "+ (java.lang.System.currentTimeMillis()- (long) t)); 
		//System.out.println(java.lang.System.currentTimeMillis()- (long) t); 
		*/
	
	}
}
