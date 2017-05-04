package rules;

import static video_poker.Rank.A;
import static video_poker.Rank.J;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class HighPair implements Rule {
	
	/**
	 * Checks if a given set of cards is Jacks or Better
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkJacksOrBetter(Card[] c, int[] rank_occurrences){
		//Check if there is a pair of jacks or better
		//Check in number of occurrences the number of elements
		for(int i = 0; i < Rank.values().length; i++){
			if(i == A.ordinal() || i >= J.ordinal()){
				if(rank_occurrences[i] == 2)
					return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Card> hold = new ArrayList<Card>();
		// Check for Ace pair
		if (rank_occurrences[A.ordinal()] == 2){
			for (int i = 0; i < c.length; i++){
				if (c[i].rank.ordinal() == A.ordinal()){
					hold.add(c[i]);
				}
			}
		}
		// Check for Jacks or higher
		for (int i = J.ordinal(); i < Occurrences.RANKS; i++){
			if(rank_occurrences[i] == 2){
				for (int j = 0; j < c.length; j++){
					if (c[j].rank.ordinal() == i){
						hold.add(c[j]);
					}
				}
			}
		}
		if (hold.size() == 2){
			return hold;
		}
		return null;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
