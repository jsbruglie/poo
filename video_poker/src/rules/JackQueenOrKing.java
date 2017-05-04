package rules;

import static video_poker.Rank.J;
import static video_poker.Rank.K;
import static video_poker.Rank.Q;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

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
