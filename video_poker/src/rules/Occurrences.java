package rules;

import video_poker.Card;
import video_poker.Rank;
import video_poker.Suit;

public class Occurrences {
	/** Array to keep track of rank occurrences */
	public int[] ranks;
	/** Array to keep track of suit occurrences */
	public int[] suits;
	
	/** Constant number of suits */
	public static final int SUITS = Suit.values().length;
	/** Constant value of Cards per suit */
	public static final int RANKS = Rank.values().length;
	/** Constant value of the hand size */
	public static final int HAND_SIZE = 5;
	
	/**
	 * Constructor
	 */
	public Occurrences(){
		ranks = new int[RANKS];
		suits = new int[SUITS];
		
	}
	
	/**
	 * Initialises auxiliary structures
	 * @param c 
	 */
	public void initialise(Card c[]){
		
		for (Suit s : Suit.values()){
			suits[s.ordinal()] = 0;
		}
		
		for (Rank r : Rank.values()){
			ranks[r.ordinal()] = 0;
		}
		
		for (int i = 0; i < c.length; i++){
			// Fill suit occurrences
			suits[c[i].suit.ordinal()]++;
			// Fill number occurrences
			ranks[c[i].rank.ordinal()]++;
		}
	}
}
