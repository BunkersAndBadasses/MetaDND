<<<<<<< HEAD
//NON-BRANCH WORK
import java.util.Random;

public class DnDie {
	
	Random rng = new Random();
	//rndm comment
	// Rolls the number of a given die (ie rolls 5 20s)
	public int[] roll (int die, int dieNumber){
=======

import java.util.Random;

public class DnDie {
	
	static Random rng = new Random();
	//rndm comment
	// Rolls the number of a given die (ie rolls 5 20s)
	public static int[] roll (int die, int dieNumber){
>>>>>>> refs/remotes/origin/master
		
		int [] number = new int[dieNumber];
		
		for(int i = 0; i < dieNumber; i ++){
			
			 number[i] = rng.nextInt(die) + 1;
		}
		
		return number;
	}
	
	// loads the die, and then sends them off to get rolled
	public static void rollFavDie(){
		
		
		return;
	}
	
	// save the favorite die selection to the xml
	public static void saveFavDie(){
		
		return;
	}
	
	// load the favorite die selection from the xml
	public static void loadFavDie(){
		
		return;
	}
	
	// delete a favorite die from the xml
	public static void deleteFavDie(){
		
		return;
	}
	
}
