package combinations;

import static video_poker.Rank.*;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

/**
 * Class to verify if an array of cards contains JT suited (same suit) 
 */
public class JTsuited implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(J); l.add(T);
		return Utils.cardsSuited(c, l, occurrences.ranks);
	}
}
