package rules;

import java.util.List;

import video_poker.Card;


public interface Rule {

	public abstract List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences);
	
}
