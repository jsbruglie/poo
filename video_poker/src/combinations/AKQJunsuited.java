package combinations;

import static video_poker.Rank.A;
import static video_poker.Rank.J;
import static video_poker.Rank.K;
import static video_poker.Rank.Q;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

/**
 * Class to verify if an array of cards contains AKQJ Suited (same suit)
 */
public class AKQJunsuited implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(A); l.add(K); l.add(Q); l.add(J);
		return Utils.cardsUnsuited(c, l, occurrences.ranks);
	}
}
