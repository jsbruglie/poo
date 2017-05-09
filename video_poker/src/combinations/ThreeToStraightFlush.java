package combinations;

import static video_poker.Rank.A;
import static video_poker.Rank.n2;
import static video_poker.Rank.n3;
import static video_poker.Rank.n4;

import java.util.Collections;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;

/**
 * Class to verify if an array of cards contains a Three to a Straight Flush (all 3 types) 
 */
public class ThreeToStraightFlush implements Rule {
	
	/** Type of the desired straight flush (Type 1,2 or 3) */
	int type;
	
	public ThreeToStraightFlush(int type){
		this.type = type;
	}

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		Rule ntoflush = new NToFlush(3);
		List<Card> flush = ntoflush.run(c, occurrences); 
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
		
		if (type == 1){
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
		} else if (type == 2){
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
		} else if (type == 3){
			if (!has_ace){
				if(nb_high_cards == 0 && gaps == 2){
					//System.out.println("3 to Straight Flush - Type 3");
					return flush;
				}
			}
		}
		return null;
	}
}
