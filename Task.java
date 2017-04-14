import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

public class Task {
	
	/* -------------------------------------- CONSTRUCTOR -------------------------------------------- */
	
	/* This constructor distributes the data structure to the 
	   locations needed to perform the given tasks */
	public Task(Tag tg, Hashtable<String, Tag> ht) {
		
		/* The task functions */
		readCorpus(tg, ht);							/* Send to perform Task 1 */
		buildHMM(tg, ht);						/* Send to perform Task 2 */
		assignTheMostProbableTag(ht);			/* Send to perform Task 3 */
		implementTheViterbiAlgorithm(ht);		/* Send to perform Task 4 */
		
	}
	
	/* ----------------------------------------- TASK 1 ----------------------------------------------- */
	
	/* The Brown corpus takes training data and adds it to the main data structure. */
	private void readCorpus(Tag tg, Hashtable<String, Tag> ht) {

		/* All files in the Brown folder are handled */
		File[] files  = new File("brown").listFiles();

		/* The files are turning until the end */
		for(File file : files) {
			
			Scanner sc = null;					/* Create scanner for read file */
			sc = controlInputFile(sc, file);	/* Send to input file open */
			
			if(sc == null)						/* Control file is exit or not */
				return;							/* Exit function */
						
			while(sc.hasNext()) {				/* The words in the file are turning up */
				String line = sc.nextLine();	/* Read file line by line */
				tg.add(tg, ht, line.split(" "));/* Send to words of line add the data structure. */
			}
			
			sc.close();							/* File close */
			
		}
		
	}
	
	/* ----------------------------------------- TASK 2 ----------------------------------------------- */
	
	/* This function build Hidden Markov Model */
	private void buildHMM(Tag tg, Hashtable<String, Tag> ht) {
		
		Probability pro = new Probability();	/* Probability class object */
		String previous = null;					/* Previous tag */
		
		/* The tags in the data structure continue until the end */
		for(String key : ht.keySet()) {
		
			/* Send to calculate the probability that a sentence begins with tag  */
			double initialProbability = pro.calculateInitialTagProbability(tg, ht.get(key));
				
			/* Assigning incoming initial probability */
			ht.get(key).setInitialTagProbability(initialProbability);		
		
			/* Check whether the head of the sentence is or not */
			if(previous != null) {
				
				/* Send to calculate the transition probability */
				double probability = pro.calculateTransitionProbability(ht.get(previous), ht.get(key));
				
				/* Assigning incoming transition probability */
				ht.get(previous).setTransitionProbability(probability);
				
			}
			
			/* The words of that tag are turning up */
			for(String word : ht.get(key).getWords().keySet()) {
				
				/* Send to calculate the emission probability */
				double emissionProbability = pro.calculateEmissionProbability(ht.get(key), 
											 ht.get(key).getWords().get(word));
				
				/* Assigning incoming emission probability */
				ht.get(key).getWords().get(word).setEmissionProbability(emissionProbability);
			}

			previous = key;						/* Assignment tag */
			
		}
		
	}

	/* ----------------------------------------- TASK 3 ----------------------------------------------- */
	
	/* This function reads the words in the file input_tokens.txt and finds out which tag has the
	   highest likelihood of passing that tag. The result is printed to output_tokens.txt file. */
	private void assignTheMostProbableTag(Hashtable<String, Tag> ht) {
		
		Scanner sc = null;					   							/* Create Scanner for read file */
		PrintWriter pw = null;											/* Create PrintWriter for write file */									 
		sc = controlInputFile(sc, new File("input_tokens.txt"));		/* Send to input file open */
		pw = controlOutputFile(pw, new File("output_tokens.txt"));		/* Send to output file open */
		
		/* The translation of the words into lower case is done according to this locale. */
		Locale locale =  new Locale.Builder().setLanguageTag("en-US").build();
		
		/* Print screen and write output file */
		System.out.println("\t\t\t------------------ TASK 3 ------------------\n");
		pw.println("\t\t\t\t\t\t------------------ TASK 3 ------------------");
		pw.println();
		
		while(sc.hasNext()) {											/* The words in the file are turning up */
			
			System.out.print("Sentence : ");							/* Print screen */
			pw.write("Sentence : ");									/* Write output file */
			String line = sc.nextLine();								/* Read file line by line */
			String lcWord = null;										/* Lower case word */
			
			for(String word : line.split(" ")) {						/* The words of line are turning up */
				
				lcWord = word.toLowerCase(locale);						/* Convert lower case */
				double probability = 0.0;								/* Keep probability value */
				String maxProbabilityTag = null;						/* Keep max probability tag value */
				
				for(String key : ht.keySet()) {							/* The tags of data structure are turning up */
					
					/* Control data structure contains tag */
					if(ht.get(key).getWords().containsKey(lcWord)) {
						
						/* Compared to two possibilities are getting bigger */
						if(probability < ht.get(key).getWords().get(lcWord).getEmissionProbability()) {
							
							/* Assignment max probability tag */
							maxProbabilityTag = key;
							
							/* Assignment emission probability of word of tag */
							probability = ht.get(key).getWords().get(lcWord).getEmissionProbability();
							
						}
						
					}	
					
				}
				
				/* Control max probability value */
				if(maxProbabilityTag != null) {
					
					/* Print screen and write output file */
					System.out.print(word + "/" + maxProbabilityTag + " ");
					pw.write(word + "/" + maxProbabilityTag + " ");
					
				}
				
			}
			
			
			System.out.println();	/* Print to screen */
			System.out.println();	/* Print to screen */
			pw.println();			/* Write output file */
			pw.println();			/* Write output file */
			
		}
		
		
		sc.close();					/* Close input file */
		pw.close();					/* Close output file */
		
	}

	
	/* ----------------------------------------- TASK 4 ----------------------------------------------- */
	
	/* This function reads the test_set.txt file. According to these codes, the viterbi algorithm is 
	   implemented. These algorithms calculate the probability of the tags the best way to tag. 
	   These results are print screen and write output_set.txt file. */
	private void implementTheViterbiAlgorithm(Hashtable<String, Tag> ht) {
			
		Scanner sc = null;											/* Create Scanner for read file */
		PrintWriter pw = null;										/* Create PrintWriter for output file */
		sc = controlInputFile(sc, new File("test_set.txt"));		/* Send to input file open */
		pw = controlOutputFile(pw, new File("output_set.txt"));		/* Send to output file open*/
		
		/* The translation of the words into lower case is done according to this locale. */
		Locale locale =  new Locale.Builder().setLanguageTag("en-US").build();
		
		/* Print screen and write output file */
		System.out.println("\n\n\n\t\t\t------------------ TASK 4 ------------------\n");
		pw.println("\t\t\t\t\t\t------------------ TASK 4 ------------------");
		pw.println();
		
		while(sc.hasNext()) {										/* The words in the file are turning up */
			
			ArrayList<String> paths = new ArrayList<String>();		/* For keep paths */
			String [] words = sc.nextLine().trim().split(" ");		/* Sentence trimmed and split words */
			boolean initial = true;									/* For check head of sentence */
			String lcWord = null;									/* Keep lower case word */
			
			if(words.length != 0) {									/* Controlling word size */
				lcWord = words[0].toLowerCase(locale);				/* Convert lower case */
			}
			else
				return;									
			
			if(initial) {											/* Check head of sentence */	
				
				for(String key : ht.keySet()) {						/* The tags of data structure are turning up */
					
					if(ht.get(key).getWords().containsKey(lcWord)){	/* Control data structure contains tag */

						/* Check word initial counter */
						if(ht.get(key).getWords().get(lcWord).getInitialCounter() > 0) {
							
							/* Check words array size */
							if(words.length > 1) {
								
								/* Send to perform Viterbi Algorithm and find Viterbi paths */
								findViterbiPaths(ht, paths, Arrays.copyOfRange(words, 1, words.length), 
											   locale, key, key + " ");	
							}
							else {
								paths.add(key);						/* The path added paths array */
							}
							
						}
						
						
					}
					
				}
				
			}

			/* To compare the probabilities of the obtained pathways and find the best probable path */
			int counter= new Probability().viterbiProbability(ht, paths, words, locale);

			System.out.print("Sentence : ");							/* Print screen */
			pw.write("Sentence : ");									/* Write output file */
			
			/* The word of words array are turning up */
			for(int i = 0; i< words.length; i++) {

				/* Print screen and write output file word and tag */
				System.out.print(words[i] + "/" + (paths.get(counter).split(" "))[i] + " ");
				pw.write(words[i] + "/" + (paths.get(counter).split(" "))[i] + " ");
				
			}
			
			System.out.println();					/* Print new line */
			System.out.println();					/* Print new line */
			pw.println();							/* Write new line */
			pw.println();							/* Write new line */
			
			/* Paths ArrayList cleared */
			paths.clear();
			
		}
		
		sc.close();									/* Scanner close */
		pw.close();									/* PrintWriter close */
		
	}
	
	/* This recursive function finds the tag paths of the incoming words of sentence. */
	private void findViterbiPaths(Hashtable<String, Tag> ht, ArrayList<String> paths, 
									String [] words, Locale locale, String previous, String path) {

		String lcWord = words[0].toLowerCase(locale);				/* Convert lower case word */

		/* The after tags of previous tag are turning up */
		for(String key : ht.get(previous).getAfterTagTypes().keySet()) {
			
			/* Control tag contain word */
			if(ht.get(key).getWords().containsKey(lcWord)) {
				
				/* Check words array list size for empty */
				if(words.length > 1) {
					
					/* Path is added to the word and a word is deleted from 
					   array the background and sent back to the function. */
					findViterbiPaths(ht, paths, Arrays.copyOfRange(words, 1, words.length), 
									locale, key, path + key + " ");
					
				}
				else {
					paths.add(path + key);							/* The path added to paths array list */
				}
				
			}		
			
		}		
		
	}

	/* ------------------------------------- FILE CONTROLLERS ----------------------------------------- */
	
	/* This function checks whether the Scanner input file has been created. 
	   If there is it is return, if it is not, it is exiting */
	private Scanner controlInputFile(Scanner sc, File file) {
		
		try {
			
			sc = new Scanner(file);								/* Scanner created */
			
		} catch (FileNotFoundException e) {
			
			/* Error message printed if file does not open */
			System.err.println("File did not work properly!");
			
			return null;
			
	    }
		
		return sc;
		
	}
	
	/* This function checks whether the PrintWriter output file has been created. 
	   If there is it is return, if it is not, it is exiting */
	private PrintWriter controlOutputFile(PrintWriter pw, File file) {
		
		try {
			
			pw = new PrintWriter(file);							/* PrintWriter created */
			
		} catch (FileNotFoundException e) {
			
			/* Error message printed if file does not open */
			System.err.println("File did not work properly!");
			
			return null;
			
	    }

		return pw;
	}
	
	/* ------------------------------------------------------------------------------------------------ */
	
}

