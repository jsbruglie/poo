package combinations;

import static video_poker.Rank.*;

import java.util.List;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains a Straight 
 */
public class Straight implements Rule, CombinationChecker{
	
	/**
	 * Checks if a given set of cards is a Straight
	 * @param c The set of cards to be evaluated
	 * @param occurrences An object that has both rank and suit occurrences
	 * @return Whether the set of cards has the desired combination
	 */
	public static boolean checkStraight(Card[] c, Occurrences occurrences){
		int[] rank_occurrences = occurrences.ranks;
		// Corner case: Ace high straight 
		if (rank_occurrences[T.ordinal()] == 1 && 
			rank_occurrences[J.ordinal()] == 1 &&
			rank_occurrences[Q.ordinal()] == 1 &&
			rank_occurrences[K.ordinal()] == 1 &&
			rank_occurrences[A.ordinal()] == 1){
			return true;
		}
		
		for (int i = 0; i <= Occurrences.RANKS - Occurrences.HAND_SIZE; i++){
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
	public List<Card> run(Card[] c, Occurrences occurrences) {
		if (checkStraight(c, occurrences)){
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
		return checkStraight(cards, occurrences);
	}
}
