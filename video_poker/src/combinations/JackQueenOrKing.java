package combinations;

import static video_poker.Rank.*;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains a J, Q or K 
 */
public class JackQueenOrKing implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Card> hold = new ArrayList<Card>();
		for (Card card : c){
			if (card.rank == J || card.rank == Q || card.rank == K){
				hold.add(card);
				return hold;
			}
		}
		return null;
	}
}
