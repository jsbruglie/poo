package rules;

import static video_poker.Rank.*;

import video_poker.Card;

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
