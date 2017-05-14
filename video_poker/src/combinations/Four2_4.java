package combinations;

import static video_poker.Rank.*;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains a Four of a Kind of low 
 * cards (from 2 to 4)
 */
public class Four2_4 implements CombinationChecker {

	@Override
	public boolean check(Card[] cards) {
		Occurrences occurrences = new Occurrences();
		occurrences.initialise(cards);
		return this.check(cards, occurrences);
	}

	@Override
	public boolean check(Card[] cards, Occurrences occurrences) {
		for (int i = n2.ordinal(); i <= n4.ordinal() ; i++){
			if (occurrences.ranks[i] == 4){
				return true;
			}
		}
		return false;
	}

}
