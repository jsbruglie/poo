package rules;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class ThreeOfAKind implements Rule {
	
	/**
	 * Checks if a given set of cards is Three of a Kind
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkThreeOfAKind(Card[] c, int[] rank_occurrences){
		// Check if there are triplets
		for (int i = 0; i < Occurrences.RANKS; i++){
			if (rank_occurrences[i] == 3){
				return true;
			}
		}
		return false;

	}
	
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Card> hold = new ArrayList<Card>();
		for (Rank rank : Rank.values()){
			if(rank_occurrences[rank.ordinal()] == 3){
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
