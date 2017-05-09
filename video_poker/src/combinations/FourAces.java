package combinations;

import static video_poker.Rank.*;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains Four Aces
 */
public class FourAces implements CombinationChecker {

	@Override
	public boolean check(Card[] cards) {
		Occurrences occurrences = new Occurrences();
		occurrences.initialise(cards);
		return this.check(cards, occurrences);
	}

	@Override
	public boolean check(Card[] cards, Occurrences occurrences) {
		if (occurrences.ranks[A.ordinal()] == 4){
			return true;
		}	
		return false;
	}

}
