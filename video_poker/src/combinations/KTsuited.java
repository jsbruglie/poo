package combinations;

import static video_poker.Rank.K;
import static video_poker.Rank.T;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

/**
 * Class to verify if an array of cards contains KT suited (same suit) 
 */
public class KTsuited implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(K); l.add(T);
		return Utils.cardsSuited(c, l, occurrences.ranks);
	}
}
