package combinations;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

/**
 * Class to verify if an array of cards contains a Three of a Kind 
 */
public class ThreeOfAKind implements Rule, CombinationChecker {
	
	/**
	 * Checks if a given set of cards is Three of a Kind
	 * @param c The set of cards to be evaluated 
	 * @param occurrences An object that has both rank and suit occurrences
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkThreeOfAKind(Card[] c, Occurrences occurrences){
		// Check if there are triplets
		for (int i = 0; i < Occurrences.RANKS; i++){
			if (occurrences.ranks[i] == 3){
				return true;
			}
		}
		return false;

	}
	
	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Card> hold = new ArrayList<Card>();
		for (Rank rank : Rank.values()){
			if(occurrences.ranks[rank.ordinal()] == 3){
				for (int j = 0; j < c.length; j++){
					if (c[j].rank == rank){
						hold.add(c[j]);
					}
				}
			}
		}
		if (hold.size() == 3){
			return hold;
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
		return checkThreeOfAKind(cards, occurrences);
	}
}
