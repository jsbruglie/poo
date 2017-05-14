package combinations;

import video_poker.Card;

/**
 * Interface for checking if an array of cards corresponds to a combination
 */
public interface CombinationChecker {
	
	/**
	 * Checks if an array of cards is a given  combination
	 * Creates an Occurrences object
	 * @param cards The array of cards to be checked
	 * @return Whether the input array is a given combination
	 */
	public boolean check(Card[] cards);
	
	/**
	 * Checks if an array of cards is a given  combination
	 * Creates an Occurrences object
	 * @param cards The array of cards to be checked
	 * @param occurrences An object that has both rank and suit occurrences
	 * @return Whether the input array is a given combination
	 */
	public boolean check(Card[] cards, Occurrences occurrences);
}
