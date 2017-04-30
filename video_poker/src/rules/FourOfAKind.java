package rules;

import static video_poker.Rank.A;
import static video_poker.Rank.K;
import static video_poker.Rank.n2;
import static video_poker.Rank.n4;
import static video_poker.Rank.n5;

import java.util.List;

import video_poker.Card;
import video_poker.Rank;

public class FourOfAKind implements Rule {
	/**
	 * Checks if a given set of cards is Four Aces
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkFourAces(Card[] c, int[] rank_occurrences){
		if(rank_occurrences[A.ordinal()] == 4){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a given set of cards is a Four of a Kind, from 5 to K
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkFour5_K(Card[] c, int[] rank_occurrences){
		for (int i = n5.ordinal(); i <= K.ordinal(); i++){
			if(rank_occurrences[i] == 4){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a given set of cards is a Four of a Kind, from 2 to 4
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public static boolean checkFour2_4(Card[] c, int[] rank_occurrences){
		for (int i = n2.ordinal(); i <= n4.ordinal() ; i++){
			if (rank_occurrences[i] == 4){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		for (Rank r : Rank.values()){
			if (rank_occurrences[r.ordinal()] == 4){
				return Utils.allCards(c);
			}
		}	
		return null;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
