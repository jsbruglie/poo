package video_poker;

// Static imports to use name space
import static video_poker.Combination.*;	// Use Combination enums
import static video_poker.CardNumber.*;		// Use CardNumber enums

/**
 * 
 */
public class Score {

	/** A table assigning a payout for each combination per bet value */
	private PayTable pay_table;
	/** An array with the card number occurrences in a given hand */
	private int[] card_number_occurrences;
	
	/** Maximum number of cards in hand/combination */
	private final int MAX_CARDS = 5;
	
	// It is assumed that the hand has always the same amount of cards
	// as the maximum number of cards in a combination (5, by default).
	
	/** Cards per suit */
	private final int CARDS_PER_SUIT = CardNumber.values().length;
	
	public Score(PayTable pay_table){
		
		this.pay_table = pay_table;
		card_number_occurrences = new int[CARDS_PER_SUIT];
	}
	
	/**
	 * Evaluates a hand and calculates the combination it corresponds to.
	 * @param hand The hand to be evaluated
	 * @return The corresponding combination Enum
	 */
	public Combination evaluateHand(Hand hand){
		
		Card[] c = hand.getCards();
		if (c.length != MAX_CARDS){
			return null;
		}
		
		for (int i = 0; i < CARDS_PER_SUIT ; i++){
			card_number_occurrences[i] = 0;
			//System.out.println(i + " " + card_number_occurrences[i]);
		}
		for (int i = 0; i < MAX_CARDS; i++){
			card_number_occurrences[c[i].number.ordinal()]++;
		}
		
		if (checkRoyalFlush(c)){ 
			return RoyalFlush;
		} else if (checkStraightFlush(c)){
			return StraightFlush;
		} else if (checkFourAces(c)){
			return FourAces;
		} else if (checkFour2_4(c)){
			return Four2_4;
		} else if (checkFour5_K(c)){
			return Four5_K;
		} else if (checkFullHouse(c)){
			return FullHouse;
		} else if (checkStraight(c)){
			return Straight;
		} else if(checkThreeOfAKind(c)){
			return ThreeOfAKind;
		} else if(checkTwoPair(c)){
			return TwoPair;
		} else if(checkJacksOrBetter(c)){
			return JacksOrBetter;
		}
		return Other;
	}
	
	/**
	 * Returns the payout for a given combination and bet
	 * @param comb The final combination
	 * @param bet The player proposed bet
	 * @return The corresponding payout
	 */
	public int getScore(Combination comb, int bet){
		if (bet >= 1 && bet <= 5){
			return pay_table.getPayout(comb, bet);
		}
		return 0;
	}
	
	/**
	 * Checks if a given set of cards is Jacks or Better
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkJacksOrBetter(Card[] c){
		//Check if there is a pair of jacks or better
		//Check in number of occurrences the number of elements
		for(int i = 0; i < CardNumber.values().length; i++){
			if(i == A.ordinal() || i >= J.ordinal()){
				if(this.card_number_occurrences[i] == 2)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a given set of cards is Jacks or Better
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 * TODO - replace by V2, once tested
	 */
	public boolean checkJacksOrBetterV2(Card[] c){
		for (int i = J.ordinal(); i <= A.ordinal(); i++){
			if (card_number_occurrences[i] == 2){
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
		for(int i = 0; i < CARDS_PER_SUIT; i++){
			if(card_number_occurrences[i] == 2){
				pair_count++;
			}
		}
		if(pair_count == 2){
			return true;
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
		for (int i = 0; i < CARDS_PER_SUIT; i++){
			if (card_number_occurrences[i] == 3){
				return true;
			}
		}
		return false;

	}
	
	/**
	 * Checks if a given set of cards is a Straight
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 * TODO - replace by V2, once tested
	 */
	public boolean checkStraight(Card[] c){
		
		//Check if straight is TJQKA (the only straight that is not number sequential)
		if (card_number_occurrences[T.ordinal()] == 1 && 
			card_number_occurrences[J.ordinal()] == 1 &&
			card_number_occurrences[Q.ordinal()] == 1 &&
			card_number_occurrences[K.ordinal()] == 1 &&
			card_number_occurrences[A.ordinal()] == 1){
			
			return true;
		}	
		//Check if the numbers are sequential
		for(int i=0; i<CARDS_PER_SUIT; i++){
			if(card_number_occurrences[i]!=0){
				if(card_number_occurrences[i]!=1){
					return false;
				}else{
					if(i<9){ //Not allowing wraparound straights
						for(int j=i;j<i+5;j++){ //Check the next 5 cards
							if(card_number_occurrences[j] != 1)
								return false;
						}
						return true;
					}	
				
				}
			}
		}
		return false;
	}
	
	/**
	 * Simpler version of checkStraight; Needs testing
	 * @param c The set of cards to be evaluated
	 * @return Whether the set of cards has the desired combination
	 */
	public boolean checkStraightV2(Card[] c){
		for (int i = 0; i < CARDS_PER_SUIT - MAX_CARDS; i++){
			if (card_number_occurrences[i] == 1){
				for (int j = i; j < i + MAX_CARDS; j++){
					if (card_number_occurrences[j] != 1){
						return false;
					}
				}
				return true;
			}
		}
		if (card_number_occurrences[A.ordinal()] == 1 && 
			card_number_occurrences[n2.ordinal()] == 1 &&
			card_number_occurrences[n3.ordinal()] == 1 &&
			card_number_occurrences[n4.ordinal()] == 1 &&
			card_number_occurrences[n5.ordinal()] == 1){
			
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
		//Check if they all have the same suit
		if (c[0].suit.equals(c[1].suit) &&
			c[1].suit.equals(c[2].suit) &&
			c[2].suit.equals(c[3].suit) &&
			c[3].suit.equals(c[4].suit)){
			
			return true;
		}
		return false;
	}
	
	/**
	 * Iterative version of checkFlush
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkFlushV2(Card[] c){
		
		for (int i = 0; i < MAX_CARDS - 1; i++){
			if (!c[i].suit.equals(c[i + 1].suit))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks if a given set of cards is a Full House
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkFullHouse(Card[] c){
		int npairs = 0, ntriples = 0;
		for (int i = 0; i < CARDS_PER_SUIT; i++){
			if (card_number_occurrences[i] == 2)
				npairs++;
			if (card_number_occurrences[i] == 3)
				ntriples++;
		}
		if (npairs == 1 && ntriples == 1){
			return true;
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
			if (card_number_occurrences[i] == 4){
				return true;
			}
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
			if(card_number_occurrences[i] == 4){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a given set of cards is Four Aces
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkFourAces(Card[] c){
		if(card_number_occurrences[A.ordinal()] == 4){
			return true;
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
	 * Checks if a given set of cards is a Straight Flush
	 * @param c The set of cards to be evaluated  
	 * @return Whether the set of cards is the desired combination
	 */
	public boolean checkRoyalFlush(Card[] c){
		if (checkFlush(c)){
			if (card_number_occurrences[T.ordinal()] == 1 &&
				card_number_occurrences[J.ordinal()] == 1 &&
				card_number_occurrences[Q.ordinal()] == 1 &&
				card_number_occurrences[K.ordinal()] == 1 &&
				card_number_occurrences[A.ordinal()] == 1){
				
				return true;
			}
		} 
		return false;
	}
	
	public static void main(String[] args){
		
		/*
		5C 7D 8H 2S 5D	: Pair of 5 - No reward
		KC 6D 8H 2S KD	: Pair of Kings
		JC 7D 7H 2S JD	: Pair of 7's and Jacks
		4C 4D 7H 4S JD	: Triple 4's
		4C 5D 6H 7S 8D	: Straight
		KC AC 2D 3S 4H	: Wrap-around Straight - No reward
		4C 5C 6C 2C TC	: Flush
		4C 4D 4H QS QD	: Full House Pair 4's Triple Queens
		3C 3D 3H 3S QD	: Four 2-4
		8C 8D 8H 8S QD	: Four 5-K
		AC AD AH AS QD	: Four Aces
		4D 5D 6D 7D 8D	: Straight Flush 
		TH JH QH KH AH	: Royal Flush 
		*/
	}
}
