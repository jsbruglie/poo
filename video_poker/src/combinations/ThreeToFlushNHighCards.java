package combinations;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains a Three to Flush with N high cards 
 */
public class ThreeToFlushNHighCards implements Rule {
	
	/** Number of desired high cards in draw */
	private final int N;
	
	/**
	 * Constructor
	 * @param N Number of desired high cards in draw
	 */
	public ThreeToFlushNHighCards(int N){
		this.N = N;
	}

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Card> hold = new ArrayList<Card>();
		int[] suit_occurrences = occurrences.suits;
		for(int i = 0; i < suit_occurrences.length; i++){
			if(suit_occurrences[i] == 3){
				for (int j = 0; j < c.length; j++){
					if(c[j].suit.ordinal() == i){
						hold.add(c[j]);
					}
				}
				// Check for the desired number of high cards
				if(N == Utils.getNumHighCards(hold))
					return hold;
				else
					return null;
			}
		}
		return null;
	}
}