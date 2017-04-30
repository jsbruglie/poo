package rules;

import static video_poker.Rank.K;
import static video_poker.Rank.T;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class KTsuited implements Rule {

	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Rank> l = new ArrayList<Rank>();
		l.add(K); l.add(T);
		return Utils.cardsSuited(c, l, rank_occurrences);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
