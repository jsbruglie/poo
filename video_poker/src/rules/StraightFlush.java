package rules;

import java.util.List;

import video_poker.Card;
import video_poker.CombinationChecker;

public class StraightFlush implements Rule, CombinationChecker {
	/**
	 * Checks if a given set of cards is a Straight Flush
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkStraightFlush(Card[] c, int[] rank_occurrences){
		return (Straight.checkStraight(c, rank_occurrences) && Flush.checkFlush(c));
	}
	
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		if (checkStraightFlush(c, rank_occurrences)){
			return Utils.allCards(c);
		}
		return null;
	}

	@Override
	public boolean check(Card[] cards) {
		Occurrences occurrences = new Occurrences();
		occurrences.initialise(cards);
		return this.check(cards, occurrences);
	}
	
	@Override
	public boolean check(Card[] cards, Occurrences occurrences) {
		if (Straight.checkStraight(cards, occurrences.rank_occurrences) &&
			Flush.checkFlush(cards)){
				return true;
		} 
		return false;
	}

}
