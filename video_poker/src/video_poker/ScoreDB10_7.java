package video_poker;

import rules.Occurrences;

public class ScoreDB10_7 implements Score{
	
	/** Possible valued combinations */
	private Combination[] combinations;
	/** Auxiliary class for hand evaluation */
	private Occurrences occurrences;
	
	/**
	 * Constructor
	 * @param combinations
	 */
	public ScoreDB10_7(Combination[] combinations) {
		this.combinations = combinations;
		this.occurrences = new Occurrences();
	}
	
	/**
	 * 
	 * @param hand
	 * @return
	 */
	public Combination evaluateHand(Hand hand){
		
		Card[] cards = hand.getCards();
		occurrences.initialise(cards);
		
		// Check each combination
		for (Combination cb : combinations){
			if (cb.checker.check(cards)){
				return cb;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param hand
	 * @param bet
	 * @return
	 */
	public int payoutHand(Hand hand, int bet){
		Combination cb = evaluateHand(hand);
		if (cb != null){
			return cb.getPayout(bet);
		}
		return 0;
	}
}
