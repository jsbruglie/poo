package rules;

import static video_poker.Rank.A;
import static video_poker.Rank.J;
import static video_poker.Rank.K;
import static video_poker.Rank.Q;
import static video_poker.Rank.T;

import java.util.List;

import video_poker.Card;
import video_poker.CombinationChecker;

public class Straight implements Rule, CombinationChecker{
	
	/**
	 * Checks if a given set of cards is a Straight
	 * @param c The set of cards to be evaluated
	 * @return Whether the set of cards has the desired combination
	 */
	public static boolean checkStraight(Card[] c, int[] rank_occurrences){
		
		// Corner case: Ace high straight 
		if (rank_occurrences[T.ordinal()] == 1 && 
			rank_occurrences[J.ordinal()] == 1 &&
			rank_occurrences[Q.ordinal()] == 1 &&
			rank_occurrences[K.ordinal()] == 1 &&
			rank_occurrences[A.ordinal()] == 1){
			return true;
		}
		
		for (int i = 0; i < Occurrences.RANKS - Occurrences.HAND_SIZE; i++){
			if (rank_occurrences[i] == 1){
				for (int j = i; j < i + Occurrences.HAND_SIZE; j++){
					if (rank_occurrences[j] != 1){
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		if (checkStraight(c, rank_occurrences)){
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
		// Corner case: Ace high straight 
		if (rank_occurrences[T.ordinal()] == 1 && 
			rank_occurrences[J.ordinal()] == 1 &&
			rank_occurrences[Q.ordinal()] == 1 &&
			rank_occurrences[K.ordinal()] == 1 &&
			rank_occurrences[A.ordinal()] == 1){
			return true;
		}
		
		for (int i = 0; i < Occurrences.RANKS - Occurrences.HAND_SIZE; i++){
			if (rank_occurrences[i] == 1){
				for (int j = i; j < i + Occurrences.HAND_SIZE; j++){
					if (rank_occurrences[j] != 1){
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}


}
