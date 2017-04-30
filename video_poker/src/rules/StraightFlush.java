package rules;

import java.util.List;

import video_poker.Card;

public class StraightFlush implements Rule {
	/**
	 * Checks if a given set of cards is a Straight Flush
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkStraightFlush(Card[] c, int[] rank_occurrences){
		Flush flush = new Flush();
		Straight straight = new Straight();
		return (straight.checkStraight(c, rank_occurrences) && flush.checkFlush(c));
	}
	
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		// TODO - Corner Case - Royal Flush
		if (checkStraightFlush(c, rank_occurrences)){
			return Utils.allCards(c);
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
