package rules;

import static video_poker.Rank.T;
import static video_poker.Rank.n2;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

public class LowPair implements Rule {

	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Card> hold = new ArrayList<Card>();
		for (int i = n2.ordinal(); i < T.ordinal(); i++){
			if(rank_occurrences[i] == 2){
				for (int j = 0; j < c.length; j++){
					if (c[j].rank.ordinal() == i){
						hold.add(c[j]);
					}
				}
			}
		}
		if (hold.size() == 2){
			return hold;
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
