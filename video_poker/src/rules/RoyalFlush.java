package rules;

import static video_poker.Rank.A;
import static video_poker.Rank.J;
import static video_poker.Rank.K;
import static video_poker.Rank.Q;
import static video_poker.Rank.T;

import java.util.List;

import video_poker.Card;
import video_poker.CombinationChecker;

public class RoyalFlush implements Rule, CombinationChecker {

	/**
	 * Checks if a given set of cards is a Straight Flush
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkRoyalFlush(Card[] c, int[] rank_occurrences){
		if (Flush.checkFlush(c)){
			if (rank_occurrences[T.ordinal()] == 1 &&
				rank_occurrences[J.ordinal()] == 1 &&
				rank_occurrences[Q.ordinal()] == 1 &&
				rank_occurrences[K.ordinal()] == 1 &&
				rank_occurrences[A.ordinal()] == 1){
				
				return true;
			}
		} 
		return false;
	}
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		if (checkRoyalFlush(c, rank_occurrences)){
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
		int[] rank_occurrences = occurrences.rank_occurrences;
		if (Flush.checkFlush(cards)){
			if (rank_occurrences[T.ordinal()] == 1 &&
				rank_occurrences[J.ordinal()] == 1 &&
				rank_occurrences[Q.ordinal()] == 1 &&
				rank_occurrences[K.ordinal()] == 1 &&
				rank_occurrences[A.ordinal()] == 1){
					return true;
			}
		} 
		return false;
	}
	
}
