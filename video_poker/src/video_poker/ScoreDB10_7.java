package video_poker;

import rules.Occurrences;

public class ScoreDB10_7 {
	
	
	private Combination[] combinations;
	private Occurrences occurrences;
	
	public ScoreDB10_7(Combination[] combinations) {
		this.combinations = combinations;
		this.occurrences = new Occurrences();
	}
	
	public Combination evaluateHand(Hand hand){
		
		Card[] cards = hand.getCards();
		occurrences.initialise(cards);
		
		for (Combination cb : combinations){
			if (cb.checker.check(cards)){
				return cb;
			}
		}
		return null;
	}
	
	public int payoutHand(Hand hand, int bet){
		Combination cb = evaluateHand(hand);
		if (cb != null){
			return cb.getPayout(bet);
		}
		return 0;
	}
}
