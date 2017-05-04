package rules;

import static video_poker.Rank.J;
import static video_poker.Rank.T;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class JTsuited implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(J); l.add(T);
		return Utils.cardsSuited(c, l, occurrences.rank_occurrences);
	}
}
