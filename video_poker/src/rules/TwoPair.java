package rules;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

public class TwoPair implements Rule {
	/**
	 * Checks if a given set of cards is Two Pairs
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkTwoPair(Card[] c, int[] rank_occurrences){
		
		int pair_count = 0;
		// For each card number, count the number of pairs
		for(int i = 0; i < Occurrences.RANKS; i++){
			if(rank_occurrences[i] == 2){
				pair_count++;
			}
		}
		if(pair_count == 2){
			return true;
		}
		return false;
	}
	
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Card> hold = new ArrayList<Card>();
		for (int i = 0; i < Occurrences.RANKS; i++){
			if(rank_occurrences[i] == 2){
				for (int j = 0; j < c.length; j++){
					if (c[j].rank.ordinal() == i){
						hold.add(c[j]);
					}
				}
			}
		}
		if (hold.size() == 4){
			return hold;
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}