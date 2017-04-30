package rules;

import video_poker.Card;
import video_poker.Rank;
import video_poker.Suit;

public class Occurrences {
	/** Array to keep track of rank occurrences */
	public int[] rank_occurrences;
	/** Array to keep track of suit occurrences */
	public int[] suit_occurrences;
	
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
		rank_occurrences = new int[RANKS];
		suit_occurrences = new int[SUITS];
		
	}
	
	/**
	 * Initialises auxiliary structures
	 * @param c 
	 */
	public void initialise(Card c[]){
		
		for (Suit s : Suit.values()){
			suit_occurrences[s.ordinal()] = 0;
		}
		
		for (Rank r : Rank.values()){
			rank_occurrences[r.ordinal()] = 0;
		}
		
		for (int i = 0; i < c.length; i++){
			// Fill suit occurrences
			suit_occurrences[c[i].suit.ordinal()]++;
			// Fill number occurrences
			rank_occurrences[c[i].rank.ordinal()]++;
		}
	}
}
