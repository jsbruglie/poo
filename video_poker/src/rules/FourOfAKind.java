package rules;

import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class FourOfAKind implements Rule {
	
	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		for (Rank r : Rank.values()){
			if (occurrences.rank_occurrences[r.ordinal()] == 4){
				return Utils.allCards(c);
			}
		}	
		return null;
	}
}
