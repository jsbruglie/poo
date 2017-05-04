package rules;

import java.util.List;

import video_poker.Card;
import video_poker.CombinationChecker;

public class Flush implements Rule, CombinationChecker {
	/**
	 * Checks if a given set of cards is a Flush
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkFlush(Card[] c){
		
		for (int i = 0; i < Occurrences.HAND_SIZE - 1; i++){
			if (!c[i].suit.equals(c[i + 1].suit))
				return false;
		}
		return true;
	}
	
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		if (checkFlush(c)){
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

	// TODO All Same suit, or check suit occurrences, since we bother calculating them
	@Override
	public boolean check(Card[] cards, Occurrences occurrences) {
		for (int i = 0; i < Occurrences.HAND_SIZE - 1; i++){
			if (!cards[i].suit.equals(cards[i + 1].suit))
				return false;
		}
		return true;
	}

}
