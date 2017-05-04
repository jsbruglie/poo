package video_poker;

import rules.Occurrences;

public interface CombinationChecker {
	
	public boolean check(Card[] cards);
	// TODO - Migrate occurrences
	public boolean check(Card[] cards, Occurrences occurrences);
}
