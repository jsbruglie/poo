package rules;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class ThreeOfAKind implements Rule, CombinationChecker {
	
	/**
	 * Checks if a given set of cards is Three of a Kind
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkThreeOfAKind(Card[] c, Occurrences occurrences){
		// Check if there are triplets
		for (int i = 0; i < Occurrences.RANKS; i++){
			if (occurrences.rank_occurrences[i] == 3){
				return true;
			}
		}
		return false;

	}
	
	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Card> hold = new ArrayList<Card>();
		for (Rank rank : Rank.values()){
			if(occurrences.rank_occurrences[rank.ordinal()] == 3){
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
