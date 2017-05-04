package video_poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static video_poker.Rank.*;		// Use Rank enums

public class PlayEvaluator {
	
	/** Array to keep track of rank occurrences */
	private int[] rank_occurrences;
	/** Array to keep track of suit occurrences */
	private int[] suit_occurrences;
	
	/** Constant number of suits */
	private final int SUITS = Suit.values().length;
	/** Constant value of Cards per suit */
	private final int RANKS = Rank.values().length;
	/** Constant value of the hand size */
	private final int HAND_SIZE = 5;
	
	/**
	 * Constructor
	 */
	public PlayEvaluator(){
		rank_occurrences = new int[RANKS];
		suit_occurrences = new int[SUITS];
		
	}
	
	/**
	 * Initialises auxiliary structures
	 * @param c 
	 */
	void initialise(Card c[]){
		
		for (Suit s : Suit.values()){
			suit_occurrences[s.ordinal()] = 0;
		}
		
		for (Rank r : Rank.values()){
			rank_occurrences[r.ordinal()] = 0;
		}
		
		for (int i = 0; i < c.length; i++){
			// Fill suit occurrences
			suit_occurrences[c[i].suit.ordinal()]++;
			// Fill number occurrences
			rank_occurrences[c[i].rank.ordinal()]++;
		}
	}
	
	/**********************************************************************
	 * Combinations
	 *********************************************************************/
	
	/**
	 * Checks if a given set of cards is a Straight Flush
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkRoyalFlush(Card[] c){
		if (checkFlush(c)){
			if (rank_occurrences[T.ordinal()] == 1 &&
				rank_occurrences[J.ordinal()] == 1 &&
				rank_occurrences[Q.ordinal()] == 1 &&
				rank_occurrences[K.ordinal()] == 1 &&
				rank_occurrences[A.ordinal()] == 1){
				
				return true;
			}
		} 
		return false;
	}
	
	/**
	 * Checks if a given set of cards is a Straight Flush
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkStraightFlush(Card[] c){
		return (checkStraight(c) && checkFlush(c));
	}
	
	/**
	 * Checks if a given set of cards is Four Aces
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkFourAces(Card[] c){
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
	public boolean checkFour5_K(Card[] c){
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
	public boolean checkFour2_4(Card[] c){
		for (int i = n2.ordinal(); i <= n4.ordinal() ; i++){
			if (rank_occurrences[i] == 4){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a given set of cards is a Full House
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkFullHouse(Card[] c){
		int npairs = 0, ntriples = 0;
		for (int i = 0; i < RANKS; i++){
			if (rank_occurrences[i] == 2)
				npairs++;
			if (rank_occurrences[i] == 3)
				ntriples++;
		}
		if (npairs == 1 && ntriples == 1){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a given set of cards is a Flush
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkFlush(Card[] c){
		
		for (int i = 0; i < HAND_SIZE - 1; i++){
			if (!c[i].suit.equals(c[i + 1].suit))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks if a given set of cards is a Straight
	 * @param c The set of cards to be evaluated
	 * @return Whether the set of cards has the desired combination
	 */
	public boolean checkStraight(Card[] c){
		
		// Corner case: Ace high straight 
		if (rank_occurrences[T.ordinal()] == 1 && 
			rank_occurrences[J.ordinal()] == 1 &&
			rank_occurrences[Q.ordinal()] == 1 &&
			rank_occurrences[K.ordinal()] == 1 &&
			rank_occurrences[A.ordinal()] == 1){
			return true;
		}
		
		for (int i = 0; i < RANKS - HAND_SIZE; i++){
			if (rank_occurrences[i] == 1){
				for (int j = i; j < i + HAND_SIZE; j++){
					if (rank_occurrences[j] != 1){
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a given set of cards is Three of a Kind
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkThreeOfAKind(Card[] c){
		// Check if there are triplets
		for (int i = 0; i < RANKS; i++){
			if (rank_occurrences[i] == 3){
				return true;
			}
		}
		return false;

	}
	
	/**
	 * Checks if a given set of cards is Two Pairs
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkTwoPair(Card[] c){
		
		int pair_count = 0;
		// For each card number, count the number of pairs
		for(int i = 0; i < RANKS; i++){
			if(rank_occurrences[i] == 2){
				pair_count++;
			}
		}
		if(pair_count == 2){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a given set of cards is Jacks or Better
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkJacksOrBetter(Card[] c){
		//Check if there is a pair of jacks or better
		//Check in number of occurrences the number of elements
		for(int i = 0; i < Rank.values().length; i++){
			if(i == A.ordinal() || i >= J.ordinal()){
				if(this.rank_occurrences[i] == 2)
					return true;
			}
		}
		return false;
	}
	
	/**********************************************************************
	 * Utilities
	 *********************************************************************/
	
	/**
	 * Returns a list of cards given an array of cards
	 * @param c
	 * @return
	 */
	public List<Card> allCards(Card[] c){
		List<Card> list = new ArrayList<Card>(Arrays.asList(c));
		return list;
	}
	
	/**	
	 * 
	 * @param c The array of cards to be evaluated
	 * @param indexes
	 * @return Whether 
	 */
	public boolean allSameSuit(List<Card> list){
		
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
	public int getNumHighCards(List<Card> l){
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
	public int getNumHighCards(Card c[]){
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
	public boolean isHighCard(Card c){
		return (c.rank.equals(A) || c.rank.ordinal() >= J.ordinal());
	}
	
	/**
	 * Given a list of Rank enumerators checks for the
	 * unsuited combination
	 * @param c The card list to be evaluated
	 * @param l The desired ranks to match
	 * @return The list of matched cards
	 */
	List<Card> cardsUnsuited(Card[] c, List<Rank> l){
		
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
	List<Card> cardsSuited(Card[] c, List<Rank> l){
		
		List<Card> hold = cardsUnsuited(c, l);
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
	public List<Integer> indexOf(Card[] c, List<Card> l){
		
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < l.size(); i++){
			for (int j = 0; j < c.length; j++){
				if (l.get(i).equals(c[j])){
					indices.add(j);
				}
			}
		}
		if (!indices.isEmpty()){
			return indices;
		}
		return null;
		
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
				gap = (((l.get(i+1).rank == A)? RANKS : l.get(i+1).rank.ordinal()) -
					   ((l.get(i).rank == A)? RANKS : l.get(i).rank.ordinal()) - 1);
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
	
	/**********************************************************************
	 * Strategy
	 *********************************************************************/
	
	/** 1. Straight flush, four of a kind, royal flush */
	
	/**
	 * Royal Straight Flush
	 * @param c
	 * @return
	 */
	List<Card> RoyalFlush(Card[] c){
		if (checkRoyalFlush(c)){
			return allCards(c);
		}
		return null;
	}
	
	/**
	 * Four of a Kind
	 * @param c
	 * @return
	 */
	List<Card> FourOfAKind(Card[] c){
		for (Rank r : Rank.values()){
			if (rank_occurrences[r.ordinal()] == 4){
				return allCards(c);
			}
		}	
		return null;
	}
	
	/**
	 * Straight Flush
	 * @param c
	 * @return
	 */
	List<Card> StraightFlush(Card[] c){
		
		// TODO - Corner Case - Royal Flush
		if (checkFlush(c) && checkStraight(c)){
			return allCards(c);
		}
		return null;
	}
	
	/** 2. 4 to royal flush */
	
	/**
	 * N to a Royal Flush
	 * @param c
	 * @param N
	 * @return
	 */
	
	List<Card> NToRoyalFlush(Card[] c, int N){
		
		int count = 0;
		
		// Get N cards from the same suit
		List<Card> flush = NToFlush(c, N);
		List<Card> hold = new ArrayList<Card>();
		
		//Check over them to see if they are N cards that could form a Royal Flush
		if(flush != null && flush.size() >= N){
			for(int i = 0; i < flush.size(); i++){
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
		if(count == N)
			return hold;
		else
			return null;
	}
	
	/** 3. 3 Aces */
	
	/**
	 * Three Aces
	 * @param c
	 * @return
	 */
	List<Card> ThreeAces(Card[] c){	
		
		List<Card> hold = new ArrayList<Card>();
		
		if(rank_occurrences[A.ordinal()] == 3){
			for (int i = 0; i < c.length; i++){
				if (c[i].rank == A){
					hold.add(c[i]);
				}
			}
		}
		if (hold.size() == 3){
			return hold;
		}
		return null;
	}
	
	/** 4. Straight, flush, full house */
	
	/**
	 * Straight
	 * @param c
	 * @return
	 */
	List<Card> Straight(Card[] c){
		if (checkStraight(c)){
			return allCards(c);
		}
		return null;
	}

	/**
	 * Flush
	 * @param c
	 * @return
	 */
	List<Card> Flush(Card[] c){
		if (checkFlush(c)){
			return allCards(c);
		}
		return null;
	}
	
	/**
	 * Full house
	 * @param c
	 * @return
	 */
	List<Card> FullHouse(Card[] c){
		if (checkFullHouse(c)){
			return allCards(c);
		}
		return null;
	}
	
	/** 5. Three of a kind (except aces) */
	
	/* 
	 * Three Of a Kind
	 * @param c
	 * @return
	 */
	List<Card> ThreeOfAKind(Card[] c){
			
		List<Card> hold = new ArrayList<Card>();
		for (Rank rank : Rank.values()){
			if(rank_occurrences[rank.ordinal()] == 3){
				for (int j = 0; j < c.length; j++){
					if (c[j].rank == rank){
						hold.add(c[j]);
					}
				}
			}
		}
		if (hold.size() == 3){
			return hold;
		}
		return null;
	}
	
	/**
	 * Four to Straight Flush
	 * @param c
	 * @return
	 */
	List<Card> _4ToStraightFlush(Card[] c) {
		
		List<Card> flush = NToFlush(c, 4);
		
		if (flush != null){	
			Collections.sort(flush, new Card.CardComparator());
			
			int gaps = 0;
			
			if (flush != null){
				for (int i = 1; i < flush.size(); i++){
					gaps += flush.get(i).rank.ordinal() - flush.get(i - 1).rank.ordinal();
				}
				if (gaps == 4){
					return flush;
				}
			}
		}	
		return null;
	}
	
	/**
	 * Two Pair
	 * @param c
	 * @return
	 */
	List<Card> TwoPair(Card[] c){
		
		List<Card> hold = new ArrayList<Card>();
		for (int i = 0; i < RANKS; i++){
			if(rank_occurrences[i] == 2){
				for (int j = 0; j < c.length; j++){
					if (c[j].rank.ordinal() == i){
						hold.add(c[j]);
					}
				}
			}
		}
		if (hold.size() == 4){
			return hold;
		}
		return null;
	}
	
	/**
	 * High Pair
	 * @param c
	 * @return
	 */
	List<Card> HighPair(Card[] c){
		
		List<Card> hold = new ArrayList<Card>();
		// Check for Ace pair
		if (rank_occurrences[A.ordinal()] == 2){
			for (int i = 0; i < c.length; i++){
				if (c[i].rank.ordinal() == A.ordinal()){
					hold.add(c[i]);
				}
			}
		}
		// Check for Jacks or higher
		for (int i = J.ordinal(); i < RANKS; i++){
			if(rank_occurrences[i] == 2){
				for (int j = 0; j < c.length; j++){
					if (c[j].rank.ordinal() == i){
						hold.add(c[j]);
					}
				}
			}
		}
		if (hold.size() == 2){
			return hold;
		}
		return null;
	}
	
	/**
	 * N to a Flush
	 * @param c
	 * @param N
	 * @return
	 */
	List<Card> NToFlush(Card[] c, int N){ 
		List<Card> hold = new ArrayList<Card>();
		
		for (Suit s : Suit.values()){
			if (suit_occurrences[s.ordinal()] == N){
				for (int j = 0; j < c.length; j++){
					if(c[j].suit == s){
						hold.add(c[j]);
					}
				}
				return hold;		
			}
		}
		return null;
 	}
	
	/**
	 * Four To an outside Straight
	 * @param c
	 * @return
	 */
	List<Card> _4ToOutsideStraight(Card c[]){
		
		for (int i = 0; i < c.length - (HAND_SIZE - 2); i++){
			
			List <Card> hold = new ArrayList<Card>();
			for (int j = i; j < i + (c.length - 1); j++){
				hold.add(c[j]);
			}
			Collections.sort(hold, new Card.CardComparator());
			
			// A234X is the only possible straight, and it is a corner-case inside straight
			if (hold.get(0).rank != A){
				// successful comparisons
				int success = 0;
				for (int k = 0; k < (HAND_SIZE - 2); k++){
					if (hold.get(k + 1).rank.ordinal() == hold.get(k).rank.ordinal() + 1){
						success++;
					}
				}
				
				// Matched 4 cards that are part of an outside straight
				if (success == 3){
					return hold;
				}
			}	
		}
		return null;
	}
	
	/**
	 * Low Pair
	 * @param c
	 * @return
	 */
	List<Card> LowPair(Card[] c){
		
		List<Card> hold = new ArrayList<Card>();
		for (int i = n2.ordinal(); i < T.ordinal(); i++){
			if(rank_occurrences[i] == 2){
				for (int j = 0; j < c.length; j++){
					if (c[j].rank.ordinal() == i){
						hold.add(c[j]);
					}
				}
			}
		}
		if (hold.size() == 2){
			return hold;
		}
		return null;
	}
			
	/**
	 * AKQJ Unsuited
	 * @param c
	 * @return
	 */
	List<Card> AKQJunsuited(Card[] c){
		List<Rank> l = new ArrayList<Rank>();
		l.add(A); l.add(K); l.add(Q); l.add(J);
		return cardsUnsuited(c, l);
	}
	
	/**
	 * 3 To Straight Flush (All 3 types)
	 * @param c
	 * @param type
	 * @return
	 */
	List<Card> _3ToStraightFlush(Card c[], int type){
		
		List<Card> flush = NToFlush(c, 3); 
		// If the array does not have a flush, return
		if (flush == null)
			return null;
		
		Collections.sort(flush, new Card.CardComparator());
		
		int nb_high_cards = getNumHighCards(flush);
		
		boolean has_ace = false; 
		for (int i = 0; i < flush.size(); i++){
			if (flush.get(i).rank == A){
				has_ace = true;
				break;
			}
		}
		
		int gaps = HAND_SIZE;
		
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
	
	// TODO
	
	/**
	 * 4 to an Inside straight
	 */
	List<Card> _4ToInsideStraight(Card c[], int desired_high_cards){
		
		boolean has_ace = (rank_occurrences[A.ordinal()] > 0);
		int nb_high_cards = 0;
		
		// Sort ascendingly, Ace at the bottom
		Arrays.sort(c, new Card.CardComparator());
		List<Card> hold = allCards(c);
		
		nb_high_cards = getNumHighCards(hold); 
		
		// Test exception "Inside" straights
		// A 2 3 4 X
		if (hold.get(0).rank == A  && hold.get(1).rank == n2 &&
			hold.get(2).rank == n3 && hold.get(3).rank == n4) {
			hold.remove(4);
			if (nb_high_cards == desired_high_cards)
				return hold;
		}
		// (A) X J Q K A
		if (hold.get(0).rank == A && hold.get(2).rank == J &&
			hold.get(3).rank == Q && hold.get(4).rank == K) {
			hold.remove(1);
			if (nb_high_cards == desired_high_cards)
				return hold;
		}
		
		// Generate subset assuming ace high
		if (has_ace && nb_high_cards > 1){
			Arrays.sort(c, new Card.CardComparatorAceHigh());
		}
		
		for (int i = 0; i < c.length - (HAND_SIZE - 2); i++){
						
			// Create a subset of 4 cards to be evaluated
			List<Card> subset = new ArrayList<Card>();	
			for (int j = i; j < i + (c.length - 1); j++){
				subset.add(c[j]);
			}
			
			nb_high_cards = getNumHighCards(subset);
			
			// Test Ace High
			if (has_ace){
				
				int gaps = _4ToInsideStraightGaps(subset, true);
				if (gaps == 1){
					if (nb_high_cards == desired_high_cards)
						return subset;
				} else if( gaps == -1){
					break;
				}
			}
			
			int gaps = _4ToInsideStraightGaps(subset, false);
			if (gaps == 1){
				if (nb_high_cards == desired_high_cards)
					return subset;
			}
		}
		return null;
	}
	
	/**
	 * QJ Unsuited or Suited
	 * @param c
	 * @param suited
	 * @return
	 * TODO - Maybe split, unifying is not that useful
	 */
	List<Card> QJunsuitedOrSuited(Card[] c, boolean suited){
		List<Rank> l = new ArrayList<Rank>();
		l.add(Q); l.add(J);
		if (suited){
			return cardsSuited(c, l);
		} else {
			return cardsUnsuited(c, l);
		}
	}
	
	/**
	 * 3 to Flush, n high cards
	 * @param c
	 * @param nb_high_cards
	 * @return
	 */
	List<Card> _3ToFlush_nHighCards(Card[] c, int nb_high_cards){ //Could return the suit
		
		List<Card> hold = new ArrayList<Card>();
		
		for(int i = 0; i < suit_occurrences.length; i++){
			if(suit_occurrences[i] == 3){ //If it's 3 to a flush	
				for (int j = 0; j < c.length; j++){
					if(c[j].suit.ordinal() == i){ //getSuit converts from int to enum
						hold.add(c[j]);
					}
				}
				if(nb_high_cards == getNumHighCards(hold)) //Check if the 3 flush cards have numHighCards
					return hold;
				else
					return null;
			}
		}
		return null;
 	}
	
	/**
	 * Suited 2 High Cards
	 * @param c
	 * @return
	 */
	List<Card> _2SuitedHighCards(Card[] c){
		List<Card> hold = new ArrayList<Card>();
		for (Card card : c){
			if (isHighCard(card)){
				hold.add(card);
			}
		}
		if (hold.size() == 2 && allSameSuit(hold)){
			return hold;
		}	
		return null;
	}
	
	/**
	 * KQJ Unsuited
	 * @param c
	 * @return
	 */
	List<Card> KQJunsuited(Card[] c){
		List<Rank> l = new ArrayList<Rank>();
		l.add(K); l.add(Q); l.add(J);
		return cardsUnsuited(c, l);
	}
	
	/**
	 * JT Suited
	 * @param c
	 * @return
	 */
	List<Card> JTsuited(Card[] c){
		List<Rank> l = new ArrayList<Rank>();
		l.add(J); l.add(T);
		return cardsSuited(c, l);
	}
		
	/**
	 * QT Suited
	 * @param c
	 * @return
	 */
	List<Card> QTsuited(Card[] c){
		List<Rank> l = new ArrayList<Rank>();
		l.add(Q); l.add(T);
		return cardsSuited(c, l);
	}
	
	/**
	 * KQ Unsuited
	 * @param c
	 * @return
	 */
	List<Card> KQunsuited(Card[] c){
		List<Rank> l = new ArrayList<Rank>();
		l.add(K); l.add(Q);
		return cardsUnsuited(c, l);
	}
	
	/**
	 * KJ Unsuited
	 * @param c
	 * @return
	 */
	List<Card> KJunsuited(Card[] c){
		List<Rank> l = new ArrayList<Rank>();
		l.add(K); l.add(J);
		return cardsUnsuited(c, l);
	}
	
	/**
	 * Ace
	 * @param c
	 * @return
	 */
	List<Card> Ace(Card[] c){
		List<Card> hold = new ArrayList<Card>();
		if(rank_occurrences[A.ordinal()] == 1){
			for (Card card : c){
				if (card.rank == A){
					hold.add(card);
					return hold;
				}
			}
		}
		return null;
	}
	
	/**
	 * KT Suited
	 * @param c
	 * @return
	 */
	List<Card> KTsuited(Card[] c){
		List<Rank> l = new ArrayList<Rank>();
		l.add(K); l.add(T);
		return cardsSuited(c, l);
	}
	
	/**
	 * Jack Queen or King
	 */
	List<Card> JackQueenOrKing(Card[] c){
		List<Card> hold = new ArrayList<Card>();
		for (Card card : c){
			if (card.rank == J || card.rank == Q || card.rank == K){
				hold.add(card);
				return hold;
			}
		}
		return null;
	}

}