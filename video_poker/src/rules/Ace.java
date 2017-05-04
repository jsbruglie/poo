package rules;

import static video_poker.Rank.A;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

public class Ace implements Rule {

	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Card> hold = new ArrayList<Card>();
		if(rank_occurrences[A.ordinal()] == 1){
			for (Card card : c){
				if (card.rank == A){
					hold.add(card);
					return hold;
				}
			}
		}
		return null;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
