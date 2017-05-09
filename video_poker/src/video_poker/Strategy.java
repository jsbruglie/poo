package video_poker;

import java.util.List;

/**
 * Strategy interface for providing in-game advice and optimal playing
 */
public interface Strategy {
	
	/**
	 * Provides a textual description of the advice
	 * @param hand The player's hand
	 * @return A string with the advice
	 */
	String holdAdvice(Hand hand);

	/**
	 * Returns the list of cards to be held according to strategy
	 * @param hand The player's hand
	 * @return The list of cards to be held
	 */
	List<Card> evaluateHand(Hand hand);
}
