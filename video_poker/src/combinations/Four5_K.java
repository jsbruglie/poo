package combinations;

import static video_poker.Rank.*;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains a Four of a Kind of 
 * cards from 5 to K
 */
public class Four5_K implements CombinationChecker {

	@Override
	public boolean check(Card[] cards) {
		Occurrences occurrences = new Occurrences();
		occurrences.initialise(cards);
		return this.check(cards, occurrences);
	}

	@Override
	public boolean check(Card[] cards, Occurrences occurrences) {
		for (int i = n5.ordinal(); i <= K.ordinal() ; i++){
			if (occurrences.ranks[i] == 4){
				return true;
			}
		}
		return false;
	}

}
