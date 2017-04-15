
public class Word {
	
	/* ----------------------------------------- ATTRIBUTES ----------------------------------------- */
	
	private String name = null;					/* Keep word name */
	private int counter = 0;					/* Keep the number of word */
	private int initialCounter = 0;					/* Keep the number of initial word */
	private double emissionProbability = 0.0;			/* Keep emission probability value */
	
	/* ------------------------------------- GETTERS AND SETTERS ------------------------------------- */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public int getInitialCounter() {
		return initialCounter;
	}

	public void setInitialCounter(int initialCounter) {
		this.initialCounter = initialCounter;
	}

	public double getEmissionProbability() {
		return emissionProbability;
	}

	public void setEmissionProbability(double emissionProbability) {
		this.emissionProbability = emissionProbability;
	}
	
	/* ------------------------------------ WORD ADDDING FUNCTION ------------------------------------- */

	/* In this function, the word is added to the specified tag. */
	public void add(Tag tag, String word, boolean initial) {
		
		if(!tag.getWords().containsKey(word)) {			/* Check tag contain word */
			
			tag.getWords().put(word, new Word());		/* Word added the tag */
			
		}
		
		if(initial)						/* Check head word of sentence */
			tag.getWords().get(word).initialCounter++;	/* Increase number of initial word */
		
		tag.getWords().get(word).counter++;			/* Increase number of word */
		
	}
	
	/* ------------------------------------------------------------------------------------------------ */
	
}

