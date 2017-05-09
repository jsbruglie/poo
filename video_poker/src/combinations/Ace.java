package combinations;

import static video_poker.Rank.A;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains an Ace
 */
public class Ace implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Card> hold = new ArrayList<Card>();
		if(occurrences.ranks[A.ordinal()] == 1){
			for (Card card : c){
				if (card.rank == A){
					hold.add(card);
					return hold;
				}
			}
		}
		return null;
	}
}
