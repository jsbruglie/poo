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
		//Test if its a flush 
		//if it's not test if its 4 to a flush
		List<Card> flush_eval = new ArrayList<Card>();
		
		Rule flush = new Flush();
		List<Card> flush_cards = flush.run(c, occurrences);

		if(flush_cards == null){
			// Get N cards from the same suit
			Rule ntoflush = new NToFlush(N);
			List<Card> ntoflush_cards = ntoflush.run(c, occurrences);
			flush_eval = ntoflush_cards;
		}else{
			flush_eval = flush_cards;
		}
		
		List<Card> hold = new ArrayList<Card>();
	
		//Check over them to see if they are N cards that could form a Royal Flush
		if (flush_eval != null && flush_eval.size() >= N){

			for (int i = 0; i < flush_eval.size(); i++){
				//Since cards are of the same suit we can check which ones they are, and we know there are no repetitions
				Rank rank = flush_eval.get(i).rank;
				
				if (rank.equals(T) || rank.equals(J) || rank.equals(Q) || rank.equals(K) || rank.equals(A)){
					count++;
					hold.add(flush_eval.get(i));
				}	
			}
		}

		if (count == N){		
			return hold;
		}else{
			
			return null;
	
		}
	}

}
