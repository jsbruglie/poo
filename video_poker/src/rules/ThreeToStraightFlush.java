package rules;

import static video_poker.Rank.A;
import static video_poker.Rank.n2;
import static video_poker.Rank.n3;
import static video_poker.Rank.n4;

import java.util.Collections;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class ThreeToStraightFlush implements Rule {
	int N;
	public ThreeToStraightFlush(int N){
		this.N = N;
	}

	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		Rule ntoflush = new NToFlush(3);
		List<Card> flush = ntoflush.run(c, rank_occurrences, suit_occurrences); 
		// If the array does not have a flush, return
		if (flush == null)
			return null;
		
		Collections.sort(flush, new Card.CardComparator());
		
		int nb_high_cards = Utils.getNumHighCards(flush);
		
		boolean has_ace = false; 
		for (int i = 0; i < flush.size(); i++){
			if (flush.get(i).rank == A){
				has_ace = true;
				break;
			}
		}
		
		int gaps = Occurrences.HAND_SIZE;
		
		// Assumes ace with lowest enum
		if (!has_ace || (has_ace && nb_high_cards == 1)){
			gaps =  (flush.get(1).rank.ordinal() - flush.get(0).rank.ordinal() - 1) + 
					(flush.get(2).rank.ordinal() - flush.get(1).rank.ordinal() - 1);
		} else if (has_ace){
			// The ace is guaranteed to be in position 0
			gaps =  (Rank.values().length - flush.get(2).rank.ordinal() - 1) +
					(flush.get(2).rank.ordinal() - flush.get(1).rank.ordinal() - 1);
		}
		
		if (N == 1){
			// Corner case - 234 is actually type 2
			if (flush.get(0).rank == n2 &&
				flush.get(1).rank == n3 &&
				flush.get(2).rank == n4){
				return null;
			}
			
			if (nb_high_cards >= gaps){
				//System.out.println("3 to Straight Flush - Type 1");
				return flush;
			}
		} else if (N == 2){
			// Corner case - 234
			if (flush.get(0).rank == n2 &&
				flush.get(1).rank == n3 &&
				flush.get(2).rank == n4){
				//System.out.println("3 to Straight Flush - Type 2");
				return flush;
			}
			
			if ((gaps == 1 && nb_high_cards == 0) ||
				(gaps == 2 && nb_high_cards == 1) ||
				(has_ace && ((gaps == 1) || (gaps == 2 && nb_high_cards == 2)))){
				//System.out.println("3 to Straight Flush - Type 2");
				return flush;
			}
		} else if (N == 3){
			if (!has_ace){
				if(nb_high_cards == 0 && gaps == 2){
					//System.out.println("3 to Straight Flush - Type 3");
					return flush;
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
