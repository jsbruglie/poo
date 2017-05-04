package rules;

import static video_poker.Rank.J;
import static video_poker.Rank.K;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class KJunsuited implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(K); l.add(J);
		return Utils.cardsUnsuited(c, l, occurrences.rank_occurrences);
	}
}
