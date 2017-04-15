import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

public class Tag {
	
	/* ----------------------------------------- ATTRIBUTES ----------------------------------------- */
	
	private String name = null;					/* Keep tag name*/
	private int totalWord = 0;					/* Keep total word of tag */
	private int initialCounter = 0;					/* Keep the number of initial tag */
	private int initialTotalTag = 0;				/* Keep initial total tag */
	private Hashtable<String, Word> words = null;			/* Keep words of tag */
	private Hashtable<String, String> afterTagTypes = null; 	/* Keep type of after tags of the tag */
	private ArrayList<String> afterTags = null;			/* Keep after tags of the tag */
	private double initialTagProbability = 0.0;			/* Keep initial tag probability */
	private double transitionProbability = 0.0;			/* Keep transition probability */
	
	/* ------------------------------------- GETTERS AND SETTERS ------------------------------------- */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getTotalWord() {
		return totalWord;
	}
	
	public int getInitialCounter() {
		return initialCounter;
	}
	
	public int getInitialTotalTag() {
		return initialTotalTag;
	}

	public void setInitialTotalTag(int initialTotalTag) {
		this.initialTotalTag = initialTotalTag;
	}

	public Hashtable<String, Word> getWords() {
		return words;
	}

	public Hashtable<String, String> getAfterTagTypes() {
		return afterTagTypes;
	}

	public ArrayList<String> getAfterTags() {
		return afterTags;
	}

	public double getInitialTagProbability() {
		return initialTagProbability;
	}

	public void setInitialTagProbability(double initialTagProbability) {
		this.initialTagProbability = initialTagProbability;
	}

	public double getTransitionProbability() {
		return transitionProbability;
	}

	public void setTransitionProbability(double transitionProbability) {
		this.transitionProbability = transitionProbability;
	}

	/* ------------------------------------ TAG ADDDING FUNCTION ------------------------------------- */
	
	/* This function reads the data structure on Task 1 and adds the words to the desired tag. */
	public void add(Tag tg, Hashtable<String, Tag> ht, String[] tagWord) {
		
		boolean initial = true;						/* For check head of sentence */
		String previous = null;						/* For previous tag */
		
		for(String tw : tagWord) {					/* The tag and words of the tag turning up */
			
			String[] words = separation(tw);			/* Send for edit line */
			
			if(words[0] != null && words[1] != null) {		/* Check tag and word is empty */
				
				if(!ht.containsKey(words[1])) {			/* Check data structure contain tag */
					
					/* Tag add operations */
					ht.put(words[1], new Tag());
					ht.get(words[1]).name = words[1];
					ht.get(words[1]).afterTagTypes = new Hashtable<String, String>();
					ht.get(words[1]).afterTags = new ArrayList<String>();
					ht.get(words[1]).words = new Hashtable<String, Word>();
					
				}
		
				if(initial) {					/* Check head of sentence */
					
					tg.initialTotalTag++;			/* Increase initial total tag */
					ht.get(words[1]).initialCounter++;	/* Increase initial tag */
					
				}
				else {
					
					/* Check after tag types of the tag contain key */
					if(!ht.get(previous).afterTagTypes.containsKey(words[1]))
						ht.get(previous).afterTagTypes.put(words[1], words[1]);
					
					/* After tags of the tag add operation */
					ht.get(previous).afterTags.add(words[1]);
					
				}
				
				/* Send function of the tag to add the tag */
				new Word().add(ht.get(words[1]), words[0], initial);
				
				ht.get(words[1]).totalWord++;			/* Increase word number of the tag */						
				previous = words[1];				/* Assigning tag to previous */
				initial = false;				/* Do not initial tag */
				
			}
			
		}
		
	}
	
	/* ------------------------------------- SEPARATION FUNCTION -------------------------------------  */

	private String[] separation(String tw) {
		
		/* The translation of the words into lower case is done according to this locale. */
		Locale locale =  new Locale.Builder().setLanguageTag("en-US").build();
		
		tw = tw.trim();							/* Delete space of line */
		String [] tagsAndWord = new String[2];				/* Keep tag and word of the tag */
		
		/* Check word contains '/' and controlling word size */
		if(tw.contains("/") && tw.length() > 2) {
			
			int counter = 0;					/* Keep number of '/' */
			
			for(char ch : tw.toCharArray()) {			/* The letters of the word turning up */
				if(ch == '/')					/* Check letter is or not '/' */
				  counter++;					/* Increase counter of '/' */
			}
			
			if(counter > 1) {					/* Check number of '/' */
				
				for(int i = tw.length() - 1; i >= 0; i--) {	/* The letters of the line turning up */
					
					if(tw.charAt(i) == '/') {		/* Controlling letter */
						
						if(i == tw.length() - 1) {	/* Check the last letter of line */
							
							/* Word convert to lower case */
							tagsAndWord[0] = tw.substring(0, i - 1).toLowerCase(locale);
							
							/* Tag convert to lower case */
							tagsAndWord[1] = tw.substring(i, i + 1).toLowerCase(locale);
							
						}
						else {				/* Check the last letter of line */		
							
							/* Word convert to lower case */
							tagsAndWord[0] = tw.substring(0, i).toLowerCase(locale);
							
							/* Tag convert to lower case */
							tagsAndWord[1] = tw.substring(i + 1, tw.length()).toLowerCase(locale);
							
						}
						
						break;		
					}
					
				}
				
			}
			else {
				
				/* Tag convert to lower case */
				tagsAndWord[0] = tw.split("/")[0].toLowerCase(locale);
				
				/* Tag convert to lower case */
				tagsAndWord[1] = tw.split("/")[1].toLowerCase(locale);

			}
			
		}
		else {
			tagsAndWord[0] = null;			/* Assigning the word is null */
			tagsAndWord[1] = null;			/* Assigning the tag is null */
		}
		
		return tagsAndWord;				/* Return tag and words of the tag */
		
	}
	
	/* ------------------------------------------------------------------------------------------------ */

}

