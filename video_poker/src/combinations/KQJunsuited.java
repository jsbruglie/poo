package combinations;

import static video_poker.Rank.*;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

/**
 * Class to verify if an array of cards contains KQJ Unsuited 
 */
public class KQJunsuited implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(K); l.add(Q); l.add(J);
		return Utils.cardsUnsuited(c, l, occurrences.ranks);
	}
}
