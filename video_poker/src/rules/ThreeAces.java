package rules;

import static video_poker.Rank.A;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

public class ThreeAces implements Rule {

	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Card> hold = new ArrayList<Card>();
		
		if(rank_occurrences[A.ordinal()] == 3){
			for (int i = 0; i < c.length; i++){
				if (c[i].rank == A){
					hold.add(c[i]);
				}
			}
		}
		if (hold.size() == 3){
			return hold;
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
