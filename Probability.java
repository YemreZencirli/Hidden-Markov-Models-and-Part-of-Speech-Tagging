import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

public class Probability {
	
	/* ------------------------------- INITIAL TAG PROBABILITY FUNCTION ------------------------------- */
	
	/* The function calculate initial tag probability and return */
	public double calculateInitialTagProbability(Tag tg, Tag tag) {
		
		/* Calculate the initial probability by formula and return */
		return ((double) tag.getInitialCounter() / (double) tg.getInitialTotalTag());

	}

	/* -------------------------------- EMISSION PROBABILITY FUNCTION ---------------------------------- */
	
	/* The function calculate emission probability and return */
	public double calculateEmissionProbability(Tag tag, Word word) {

		/* Calculate the emission probability by formula and return */
		return ((double) word.getCounter() / (double) tag.getTotalWord());

	}
	
	/* ------------------------------- TRANSITION PROBABILITY FUNCTION --------------------------------- */
	
	/* The function calculate transition probability and return */
	public double calculateTransitionProbability(Tag previousTag, Tag currentTag) {
		
		int counter = 0;						/* Keep the number of tag */
		
		for(String key : previousTag.getAfterTags()) {			/* The tags of the tag until the end */
			
			if(key.equals(currentTag.getName())) {			/* Check is equal or not name tag */
				counter++;					/* Increase counter */
			}
			
		}

		/* Calculate the transition probability by formula and return */
		return ((double) counter / (double) previousTag.getAfterTags().size());	
		
	}
	
	/* -------------------------------- VITERBI PROBABILITY FUNCTION ----------------------------------- */
	/* This function calculates the probabilities of incoming paths and compares the probabilities 
	   of these calculated viterbi paths and returns which is the best possible path. */
	public int viterbiProbability(Hashtable<String, Tag> ht, ArrayList<String> paths, 
									String[] words, Locale locale) {

		/* The possibilities of the paths are kept on this array */
		ArrayList<Double> probabilities = new ArrayList<Double>();

		/* The paths of the array until the end */
		for(String path : paths) {
			
			double probability = 0.0;				/* Keep probability value */
			double initialProbability = 0.0;			/* Keep initial probability value */
				
			String [] tags = path.split(" ");			/* Keep tags of path */
			
			/* The logarithm of the 2's base of the initial probability 
			   of the tag is equalized to the probability after it is received. */
			initialProbability = ht.get(tags[0]).getInitialTagProbability();
			initialProbability = Math.log(initialProbability) / Math.log(2);
			probability = initialProbability;
			
			/* Check tags array size */
			if(tags.length != 0) {
				
				/* The tags of the array until the end */
				for(int i = 0; i < tags.length - 1; i++) {
					
					/* Transition probability is sent to be calculated and the logarithm of the incoming 
					result 2 base is added and added to the probability. */
					double transitionProbability = calculateTransitionProbability(ht.get(tags[i]), 
													ht.get(tags[i + 1]));
					transitionProbability = Math.log(transitionProbability) / Math.log(2);
					probability += transitionProbability;
					
					/* Emission probability is sent to be calculated and the logarithm of the incoming 
					result 2 base is added and added to the probability. */
					String lcWord = words[i + 1].toLowerCase(locale);
					double emissionProbability = calculateEmissionProbability(ht.get(tags[i + 1]), 
										ht.get(tags[i + 1]).getWords().get(lcWord));
					emissionProbability = Math.log(emissionProbability) / Math.log(2);
					probability += emissionProbability;
					
				}	
				
			}	
			
			/* Probability added to probabilities array */
			probabilities.add(probability);
			
		}
		
		double max = probabilities.get(0);		/* Assigning first probability of probabilities array */
		int counter = 0;				/* Probabilities holds index of array */
		
		for(int i = 0; i < probabilities.size(); i++) { /* The probabilities of the array until the end */
			
			if(max < probabilities.get(i)) {	/* Compare max and probability */
				
				counter = i;			/* Assigning index */
				max = probabilities.get(i);	/* Assigning probability */
				
			}
			
		}

		return counter;					/* Return max probability index of probabilities array */
		
	}
	
	/* ------------------------------------------------------------------------------------------------ */
	
}

