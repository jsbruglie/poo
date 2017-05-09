package combinations;

import java.util.List;

import video_poker.Card;

/**
 * Interface to fetch a list of cards from an array of cards needed for a given draw
 */
public interface Rule {

	/**
	 * Obtain which cards should be held in order to fulfil a given draw
	 * @param c The array of cards to be checked
	 * @param occurrences An initialised Occurrences object
	 * @return The list of cards needed for a given draw
	 */
	public abstract List<Card> run(Card[] c, Occurrences occurrences);
	
}
