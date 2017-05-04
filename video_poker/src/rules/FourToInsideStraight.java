package rules;

import static video_poker.Rank.A;
import static video_poker.Rank.J;
import static video_poker.Rank.K;
import static video_poker.Rank.Q;
import static video_poker.Rank.n2;
import static video_poker.Rank.n3;
import static video_poker.Rank.n4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import video_poker.Card;

public class FourToInsideStraight implements Rule {
	
	int N;
	
	public FourToInsideStraight(int N){
		this.N = N;
	}
	
	/**
	 * Aux method for 4 to inside straight gaps
	 * @param l
	 * @param has_ace_high Whether one desires to test for Ace High straight
	 * @see 
	 * @return
	 */
	int _4ToInsideStraightGaps(List<Card> l, boolean has_ace_high){
		
		int gaps = 0; int gap = 0;
		for (int i = 0; i < l.size() - 1; i++){
			// Ace high sorted
			if (has_ace_high){
				gap = (((l.get(i+1).rank == A)? Occurrences.RANKS : l.get(i+1).rank.ordinal()) -
					   ((l.get(i).rank == A)? Occurrences.RANKS : l.get(i).rank.ordinal()) - 1);
			} else {
				gap = ((l.get(i+1).rank.ordinal()) -
					   (l.get(i).rank.ordinal()) - 1);
			}
			// If a pair is found
			if (gap == -1){
				return -1;
			}
			gaps += gap;
		}
		return gaps;
	}
	
	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		
		boolean has_ace = (occurrences.ranks[A.ordinal()] > 0);
		int nb_high_cards = 0;
		
		Card[] sorted = new Card[Occurrences.HAND_SIZE];
		for (int i = 0; i < sorted.length; i++){
			sorted[i] = c[i];
		}
		
		// Sort ascendingly, Ace at the bottom
		Arrays.sort(sorted, new Card.CardComparator());
		List<Card> hold = Utils.allCards(sorted);
		
		nb_high_cards = Utils.getNumHighCards(hold); 
		
		// Test exception "Inside" straights
		// A 2 3 4 X
		if (hold.get(0).rank == A  && hold.get(1).rank == n2 &&
			hold.get(2).rank == n3 && hold.get(3).rank == n4) {
			hold.remove(4);
			if (nb_high_cards == N)
				return hold;
		}
		// (A) X J Q K A
		if (hold.get(0).rank == A && hold.get(2).rank == J &&
			hold.get(3).rank == Q && hold.get(4).rank == K) {
			hold.remove(1);
			if (nb_high_cards == N)
				return hold;
		}
		
		// Generate subset assuming ace high
		if (has_ace && nb_high_cards > 1){
			Arrays.sort(sorted, new Card.CardComparatorAceHigh());
		}
		
		for (int i = 0; i < sorted.length - (Occurrences.HAND_SIZE - 2); i++){
						
			// Create a subset of 4 cards to be evaluated
			List<Card> subset = new ArrayList<Card>();	
			for (int j = i; j < i + (sorted.length - 1); j++){
				subset.add(sorted[j]);
			}
			
			nb_high_cards = Utils.getNumHighCards(subset);
			
			// Test Ace High
			if (has_ace){
				
				int gaps = _4ToInsideStraightGaps(subset, true);
				if (gaps == 1){
					if (nb_high_cards == N)
						return subset;
				} else if( gaps == -1){
					break;
				}
			}
			
			int gaps = _4ToInsideStraightGaps(subset, false);
			if (gaps == 1){
				if (nb_high_cards == N)
					return subset;
			}
		}
		return null;
	}
}
