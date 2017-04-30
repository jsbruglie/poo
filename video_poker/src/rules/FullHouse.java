package rules;

import java.util.List;

import video_poker.Card;

public class FullHouse implements Rule {
	
	/**
	 * Checks if a given set of cards is a Full House
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkFullHouse(Card[] c, int[] rank_occurrences){
		int npairs = 0, ntriples = 0;
		for (int i = 0; i < Occurrences.RANKS; i++){
			if (rank_occurrences[i] == 2)
				npairs++;
			if (rank_occurrences[i] == 3)
				ntriples++;
		}
		if (npairs == 1 && ntriples == 1){
			return true;
		}
		return false;
	}
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		if (checkFullHouse(c, rank_occurrences)){
			return Utils.allCards(c);
		}
		return null;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
