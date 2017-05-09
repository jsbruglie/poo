package combinations;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains Two Suited High Cards 
 */
public class TwoSuitedHighCards implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Card> hold = new ArrayList<Card>();
		for (Card card : c){
			if (Utils.isHighCard(card)){
				hold.add(card);
			}
		}
		if (hold.size() == 2 && Utils.allSameSuit(hold)){
			return hold;
		}	
		return null;
	}
}
