package rules;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Suit;

public class NToFlush implements Rule {
	int N;
	public NToFlush(int N){
		this.N = N;
	}
	

	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		List<Card> hold = new ArrayList<Card>();
			
		for (Suit s : Suit.values()){
			if (suit_occurrences[s.ordinal()] == N){
				for (int j = 0; j < c.length; j++){
					if(c[j].suit == s){
						hold.add(c[j]);
					}
				}
				return hold;		
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
