package rules;

import java.util.List;

import video_poker.Card;

public class Flush implements Rule {
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


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
