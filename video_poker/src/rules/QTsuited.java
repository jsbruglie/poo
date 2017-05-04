package rules;

import static video_poker.Rank.Q;
import static video_poker.Rank.T;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class QTsuited implements Rule {

	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(Q); l.add(T);
		return Utils.cardsSuited(c, l, rank_occurrences);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
