package rules;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

public class ThreeToFlush_nHighCards implements Rule {
	int N;
	public ThreeToFlush_nHighCards(int N){
		this.N = N;
	}

	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Card> hold = new ArrayList<Card>();
		
		for(int i = 0; i < suit_occurrences.length; i++){
			if(suit_occurrences[i] == 3){ //If it's 3 to a flush	
				for (int j = 0; j < c.length; j++){
					if(c[j].suit.ordinal() == i){ //getSuit converts from int to enum
						hold.add(c[j]);
					}
				}
				if(N == Utils.getNumHighCards(hold)) //Check if the 3 flush cards have numHighCards
					return hold;
				else
					return null;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
