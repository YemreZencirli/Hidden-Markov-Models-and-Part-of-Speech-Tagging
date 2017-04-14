import java.util.Hashtable;

/*
* In this NLP assignment, I used Brown corpus which has got all the words tagged with a PoStag. 
* I used this corpus in order to train a Hidden Markov Model for PoS tagging. 
* Therefore, for any given sentence, I found the PoS tag of each word. 
* 
* @author  Yunus Emre Zencirli - 21328667(ID)
* @since   2017-04-14
* 
*/

public class Main {

	/* ---------------------------------------- MAIN FUNCTION ----------------------------------------- */
	
	/* It is the main function where the main data structure I use is located 
	   and this data structure is sent to the task class constructor. */
	public static void main(String[] args) {
		
		/* Tag class object */
		Tag tg = new Tag();
		
		/* The Hidden Markov Model on all the tasks is the main data structure. */
		Hashtable<String, Tag> ht = new Hashtable<String, Tag>();
		
		/* Send the main data structure to perform tasks in Task class */
		new Task(tg, ht);
		
	}
	
	/* ------------------------------------------------------------------------------------------------ */

}

