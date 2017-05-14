package video_poker;

import combinations.Combination;

/**
 * Interface for a hand evaluator class
 */
public interface Score {
	
	/**
	 * Return the corresponding combination in a given hand
	 * @param hand The player's hand to be evaluated
	 * @return The corresponding combination
	 */
	Combination evaluateHand(Hand hand);
	
	/**
	 * Returns the pay-out for a given hand
	 * @param hand The player's hand to be evaluated
	 * @param bet The bet amount
	 * @return The corresponding pay-out
	 */
	public int payoutHand(Hand hand, int bet);
}
