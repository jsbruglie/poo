package combinations;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Suit;

/**
 * Class to verify if an array of cards contains an N to Flush 
 */
public class NToFlush implements Rule {
	
	/** Number of cards in N to Flush draw */
	int N;
	
	/**
	 * Constructor
	 * @param N number of cards in draw
	 */
	public NToFlush(int N){
		this.N = N;
	}

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Card> hold = new ArrayList<Card>();
			
		for (Suit s : Suit.values()){
			if (occurrences.suits[s.ordinal()] == N){
				for (int j = 0; j < c.length; j++){
					if (c[j].suit == s){
						hold.add(c[j]);
					}
				}
				return hold;		
			}
		}
		return null;
	}
}
