package rules;

import static video_poker.Rank.J;
import static video_poker.Rank.Q;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class QJunsuitedOrSuited implements Rule {
	int N;
	public QJunsuitedOrSuited(int N){
		this.N = N;
	}
	
	
	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(Q); l.add(J);
		if (N == 1){
			return Utils.cardsSuited(c, l, occurrences.rank_occurrences);
		} else {
			return Utils.cardsUnsuited(c, l, occurrences.rank_occurrences);
		}
	}
}
