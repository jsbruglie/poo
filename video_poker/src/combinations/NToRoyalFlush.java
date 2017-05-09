package combinations;

import static video_poker.Rank.A;
import static video_poker.Rank.J;
import static video_poker.Rank.K;
import static video_poker.Rank.Q;
import static video_poker.Rank.T;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

/**
 * Class to verify if an array of cards contains an N to a Royal Flush
 */
public class NToRoyalFlush implements Rule {
	
	/** Number of cards in N to Royal Flush draw */
	int N;
	
	/**
	 * Constructor
	 * @param N number of cards in draw
	 */
	public NToRoyalFlush(int N){
		this.N = N;
	}

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		int count = 0;
		
		// Get N cards from the same suit
		Rule ntoflush = new NToFlush(N);
		List<Card> flush = ntoflush.run(c, occurrences);
		List<Card> hold = new ArrayList<Card>();
		
		//Check over them to see if they are N cards that could form a Royal Flush
		if (flush != null && flush.size() >= N){
			for (int i = 0; i < flush.size(); i++){
				//Since cards are of the same suit we can check which ones they are, and we know there are no repetitions
				Rank rank = flush.get(i).rank;
				if (rank == T ||
					rank == J || 
					rank == Q ||
					rank == K ||
					rank == A){
					count++;
					hold.add(flush.get(i));
				}	
			}
		}
		if (count == N)
			return hold;
		else
			return null;
	}

}
