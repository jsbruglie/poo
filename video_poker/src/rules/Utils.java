package rules;

import static video_poker.Rank.A;
import static video_poker.Rank.J;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import video_poker.Card;
import video_poker.Rank;
import video_poker.Suit;

public class Utils {
	/**********************************************************************
	 * Utilities
	 *********************************************************************/
	
	/**
	 * Returns a list of cards given an array of cards
	 * @param c
	 * @return
	 */
	public static List<Card> allCards(Card[] c){
		List<Card> list = new ArrayList<Card>(Arrays.asList(c));
		return list;
	}
	
	/**	
	 * 
	 * @param c The array of cards to be evaluated
	 * @param indexesa
	 */
	public static boolean allSameSuit(List<Card> list){
		
		if (list != null){
			Suit suit = list.get(0).suit;
			for (int i = 1; i < list.size(); i++){
				 if (list.get(i).suit != suit)
					 return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Return the number of high cards in a given hand
	 * @param l The list of cards to be evaluated
	 * @return The number of high cards present in c
	 */
	public static int getNumHighCards(List<Card> l){
		int nb_high_cards = 0;
		for (int i = 0; i < l.size(); i++){
			if (isHighCard(l.get(i))){
				nb_high_cards++;
			}	
		}
		return nb_high_cards;
	}
	
	/**
	 * Return the number of high cards in a given hand
	 * @param c The array of cards to be evaluated
	 * @return The number of high cards present in c
	 */
	public static int getNumHighCards(Card c[]){
		int nb_high_cards = 0;
		for (Card card : c){
			if (isHighCard(card)){
				nb_high_cards++;
			}
		}
		return nb_high_cards;
	}
	
	/**
	 * Returns whether a given card is a high card (J,Q,K or A)
	 * @param c The card to be evaluated
	 * @return Whether the card is high
	 */
	public static boolean isHighCard(Card c){
		return (c.rank.equals(A) || c.rank.ordinal() >= J.ordinal());
	}
	
	/**
	 * Given a list of Rank enumerators checks for the
	 * unsuited combination
	 * @param c The card list to be evaluated
	 * @param l The desired ranks to match
	 * @return The list of matched cards
	 */
	static List<Card> cardsUnsuited(Card[] c, List<Rank> l, int[] rank_occurrences){
		
		List <Card> hold = new ArrayList<Card>();
		for ( int i = 0; i < l.size(); i++){
			// If the desired card is not present in the array
			if (rank_occurrences[l.get(i).ordinal()] == 0){
				return null;
			}
		}
		// From this point on, the array of cards is guaranteed to have the required elements
		for ( int i = 0; i < l.size(); i++){
			for (int j = 0; j < c.length; j++){
				if (c[j].rank == l.get(i)){
					hold.add(c[j]);
					break;
				}
			}
		}
		return hold;	
	}
	
	/**
	 * Given a list of Rank enumerators checks for the
	 * suited combination
	 * @param c The card list to be evaluated
	 * @param l The desired ranks to match
	 * @return The list of matched cards
	 */
	static List<Card> cardsSuited(Card[] c, List<Rank> l, int[] rank_occurrences){
		
		List<Card> hold = cardsUnsuited(c, l, rank_occurrences);
		if (allSameSuit(hold)){
			return hold;
		}
		return null;	
	}
	
	/**
	 * Returns the array of indices of the elements in list l, present in array c
	 * @param c The array of cards to be checked against
	 * @param l The array of list with desired cards
	 * @return A list of indices
	 */
	static public List<Integer> indexOf(Card[] c, List<Card> l){
		
		List<Integer> indices = new ArrayList<Integer>();
		
		if (l == null){
			return null;
		}
		
		for (int i = 0; i < c.length; i++){
			for (int j = 0; j < l.size(); j++){
				if (c[i].equals(l.get(j))){
					indices.add(i+1);
				}
			}
		}
		if (!indices.isEmpty()){
			return indices;
		}
		return null;
		
	}
}
