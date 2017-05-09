package combinations;

import static video_poker.Rank.*;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

/**
 * Class to verify if an array of cards contains a QJ suited or unsuited 
 */
public class QJunsuitedOrSuited implements Rule {
	
	/** Whether the rule should check for suited or unsuited QJ */
	private final boolean suited;
	
	public QJunsuitedOrSuited(boolean suited){
		this.suited = suited;
	}
	
	
	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(Q); l.add(J);
		if (suited){
			return Utils.cardsSuited(c, l, occurrences.ranks);
		} else {
			return Utils.cardsUnsuited(c, l, occurrences.ranks);
		}
	}
}
