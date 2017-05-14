package combinations;

import java.util.List;

import video_poker.Card;
import video_poker.Rank;

/**
 * Class to verify if an array of cards contains a generic Four Of a Kind
 */
public class FourOfAKind implements Rule {
	
	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		for (Rank r : Rank.values()){
			if (occurrences.ranks[r.ordinal()] == 4){
				return Utils.allCards(c);
			}
		}	
		return null;
	}
}
