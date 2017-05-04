package rules;

import static video_poker.Rank.*;

import video_poker.Card;

public class FourAces implements CombinationChecker {

	@Override
	public boolean check(Card[] cards) {
		Occurrences occurrences = new Occurrences();
		occurrences.initialise(cards);
		return this.check(cards, occurrences);
	}

	@Override
	public boolean check(Card[] cards, Occurrences occurrences) {
		if (occurrences.rank_occurrences[A.ordinal()] == 4){
			return true;
		}	
		return false;
	}

}
