package rules;

import video_poker.Card;

public interface CombinationChecker {
	
	public boolean check(Card[] cards);
	public boolean check(Card[] cards, Occurrences occurrences);
}
