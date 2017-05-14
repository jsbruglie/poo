package combinations;

import java.util.List;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains a Straight Flush 
 */
public class StraightFlush implements Rule, CombinationChecker {
	/**
	 * Checks if a given set of cards is a Straight Flush
	 * @param c The set of cards to be evaluated 
	 * @param occurrences An object that has both rank and suit occurrences
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkStraightFlush(Card[] c, Occurrences occurrences){
		return (Straight.checkStraight(c, occurrences) && Flush.checkFlush(c));
	}
	
	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		if (checkStraightFlush(c, occurrences)){
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
		return checkStraightFlush(cards, occurrences);
	}
}
