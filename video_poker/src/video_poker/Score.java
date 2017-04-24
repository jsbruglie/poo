package video_poker;

import static video_poker.Combination.*;

/**
 * 
 */
public class Score {

	/** A table assigning a payout for each combination per bet value */
	private int[][] paytable;
	/** An array with the card number occurrences in a given hand */
	private int[] card_number_occurrences;
	
	public Score(){
		
		paytable = new int[][]{			
			{ 0,   0,   0,   0,    0    },	// Other - Invalid Combination
			{ 1,   2,   3,   4,	   5    },	// Jacks or Better
			{ 1,   2,   3,   4,    5    },	// Two Pair
			{ 3,   6,   9,   12,   15   },	// Three of a Kind
			{ 5,   10,  15,  20,   25   },	// Straight
			{ 7,   14,  21,  28,   35   },	// Flush
			{ 10,  20,  30,  40,   50   },	// Full House
			{ 50,  100, 150, 200,  250  },	// Four 5-K
			{ 80,  160, 240, 320,  400  },	// Four 2-4
			{ 160, 320, 480, 640,  800  },	// Four Aces
			{ 50,  100, 150, 200,  250  },	// Straight Flush
			{ 250, 500, 750, 1000, 4000 }	// Royal Flush
		};
		
		card_number_occurrences = new int[Combination.values().length];
	}
	
	/**
	 * Evaluates a hand and calculates the combination it corresponds to.
	 * @param hand The hand to be evaluated
	 * @return The corresponding combination Enum
	 */
	private Combination evaluateHand(Hand hand){
		
		Card[] c = hand.getCards();
		for (int i = 0; i < CardNumber.values().length; i++)
			card_number_occurrences[i] = 0;
		for (int i = 0; i < 5;i++){
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
	 * @return
	 */
	public int getScore(Combination comb, int bet){
		if (bet >= 1 && bet <= 5){
			return paytable[comb.ordinal()][bet];
		}
		return 0;
	}
	
	public boolean checkJacksOrBetter(Card[] c){
		//Check if there is a pair of jacks or better
		//Check in number of occurences the number of elements
		for(int i=0; i<13; i++){
			if(i==0 || i>=10){
				if(this.card_number_occurrences[i] == 2)
					return true;
			}
		}
		return false;
	}
	
	public boolean checkTwoPair(Card[] c){
		//Check if there are 2 pairs
		int pair_count = 0;
		for(int i=0; i<13; i++){
			if(card_number_occurrences[i] == 2){
				pair_count++;
			}
		}
		if(pair_count == 2){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkThreeOfAKind(Card[] c){
		//Check if there are triplets
		for(int i=0; i<13; i++){
			if(card_number_occurrences[i] == 3){
				return true;
			}
		}
		return false;

	}
	public boolean checkStraight(Card[] c){
		//Check if straight is TJQKA (the only straight that is not number sequential)
		if(card_number_occurrences[9] == 1 && card_number_occurrences[10]==1 && card_number_occurrences[11] == 1 && card_number_occurrences[12] == 1 && card_number_occurrences[13] == 1)
			return true;
		//Check if the numbers are sequential
		for(int i=0; i<13; i++){
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
	public boolean checkFlush(Card[] c){
		//Check if they all have the same suit
		if(c[0].getSuit().equals(c[1].getSuit())
				&& c[1].getSuit().equals(c[2].getSuit()) 
				&& c[2].getSuit().equals(c[3].getSuit()) 
				&& c[3].getSuit().equals(c[4].getSuit())){
			return true;
		}
		return false;
	}
	
	public boolean checkFullHouse(Card[] c){
		//If checkTwoPair and checkThreeOfAKind are both true and the cards they evaluate are different
		int npairs = 0, ntriples = 0;
		for(int i=0; i<13;i++){
			if(card_number_occurrences[i] == 2)
				npairs++;
			if(card_number_occurrences[i] == 3)
				ntriples++;
		}
		if(npairs == 1 && ntriples == 1)
			return true;
		else
			return false;
	}
	
	public boolean checkFourOfAKind(Card[] c){
		//If there are four cards of the same number
		for(int i=0; i<13; i++){
			if(card_number_occurrences[i] == 4){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkFour5_K(Card[] c){
		for(int i=0; i<13; i++){
			if(card_number_occurrences[i] == 4 && i!=0 && i>=4){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkFour2_4(Card[] c){
		for(int i=0; i<13; i++){
			if(card_number_occurrences[i] == 4 && i!=0 && i<4){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkFourAces(Card[] c){
		for(int i=0; i<13; i++){
			if(card_number_occurrences[i] == 4 && i==0){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkStraightFlush(Card[] c){
		//If checkStraight and checkFlush are both true
		if(checkStraight(c) && checkFlush(c)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkRoyalFlush(Card[] c){
		//If checkFlush is true and the sequence is a Ten, a Jack, a Queen, a King and an Ace
		if(checkFlush(c)){
			if(card_number_occurrences[12] == 1 && card_number_occurrences[11] == 1 && card_number_occurrences[10] == 1 && card_number_occurrences[9] == 1 && card_number_occurrences[0] == 1){
				return true;
			}
		} 
		return false;
	}
	
	/*-----------------------THESE ARE USEFUL FOR STRATEGY-------------------------*/
	public boolean checkNToRoyalFlush(Card[] c, int N){
		int hits = 0;
		int[] indexes = new int[N];
		for(int i=0;i<13;i++){
			if(card_number_occurrences[8] == 1){
				indexes[hits] = 8;
				hits++;	
			}
			
			if(card_number_occurrences[9] == 1){
				indexes[hits] = 9;
				hits++;
			}

			if(card_number_occurrences[10] == 1){
				indexes[hits] = 10;
				hits++;
			}

			if(card_number_occurrences[11] == 1){
				indexes[hits] = 11;
				hits++;
			}
			
			if(card_number_occurrences[12] == 1){
				indexes[hits] = 12;
				hits++;
			}
		}
		Suit s = null;
		if(hits == N){
			//Check the cards received
			for(int i=0; i<5; i++){
				int n = c[i].getNumber();
				for(int j=0; j<N; j++){
					if(n == indexes[j]){
						//Get suit
						if(s == null)
							s = c[i].getSuit();
						else if(c[i].getSuit() != s)
							return false;
					}
				}
			}
		}else{
			return false;
		}
		return true;	
	}
	
	public static void main(String[] args){
		//Create a hardcoded hand and a bet
		/*
		int bet = 5;
		Suit suit;
		Card[] cards = new Card[5];
		*/
		/* Pair of 5's -- should give no reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,5);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,7);
		suit = Suit.Hearts;
		cards[2] = new Card(suit,8);
		suit = Suit.Spades;
		cards[3] = new Card(suit,2);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,5);*/
		
		/* Pair of Kings -- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,13);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,7);
		suit = Suit.Hearts;
		cards[2] = new Card(suit,8);
		suit = Suit.Spades;
		cards[3] = new Card(suit,2);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,13);*/
		
		/* Pair of 7's and Jacks -- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,11);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,7);
		suit = Suit.Hearts;
		cards[2] = new Card(suit,7);
		suit = Suit.Spades;
		cards[3] = new Card(suit,2);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,11);*/
		
		/* Triple 4's -- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,4);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,4);
		suit = Suit.Hearts;
		cards[2] = new Card(suit,7);
		suit = Suit.Spades;
		cards[3] = new Card(suit,4);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,11);*/
		
		/* Straight -- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,4);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,5);
		suit = Suit.Hearts;
		cards[2] = new Card(suit,6);
		suit = Suit.Spades;
		cards[3] = new Card(suit,7);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,8);*/
		
		/* Flush -- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,4);
		suit = Suit.Clubs;
		cards[1] = new Card(suit,5);
		suit = Suit.Clubs;
		cards[2] = new Card(suit,6);
		suit = Suit.Clubs;
		cards[3] = new Card(suit,2);
		suit = Suit.Clubs;
		cards[4] = new Card(suit,10);*/
		
		/* Full House - two 4's three Queens-- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,4);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,4);
		suit = Suit.Hearts;
		cards[2] = new Card(suit,12);
		suit = Suit.Spades;
		cards[3] = new Card(suit,12);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,12);*/
		
		/* Four 5-K 4 8's-- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,8);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,8);
		suit = Suit.Hearts;
		cards[2] = new Card(suit,8);
		suit = Suit.Spades;
		cards[3] = new Card(suit,8);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,12);*/
		
		/* Four 2-4 -- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,3);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,3);
		suit = Suit.Hearts;
		cards[2] = new Card(suit,3);
		suit = Suit.Spades;
		cards[3] = new Card(suit,3);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,12);*/
		
		/* Four Aces -- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,1);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,1);
		suit = Suit.Hearts;
		cards[2] = new Card(suit,1);
		suit = Suit.Spades;
		cards[3] = new Card(suit,1);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,12);*/
		
		/* Straight Flush -- should give reward*/
		/*
		suit = Suit.Diamonds;
		cards[0] = new Card(suit,4);
		suit = Suit.Diamonds;
		cards[1] = new Card(suit,5);
		suit = Suit.Diamonds;
		cards[2] = new Card(suit,6);
		suit = Suit.Diamonds;
		cards[3] = new Card(suit,7);
		suit = Suit.Diamonds;
		cards[4] = new Card(suit,8);
		*/
		
		/* Royal Flush -- should give reward*/
		/*suit = Suit.Clubs;
		cards[0] = new Card(suit,10);
		suit = Suit.Clubs;
		cards[1] = new Card(suit,11);
		suit = Suit.Clubs;
		cards[2] = new Card(suit,12);
		suit = Suit.Clubs;
		cards[3] = new Card(suit,13);
		suit = Suit.Clubs;
		cards[4] = new Card(suit,1);*/
		
		/*
		Hand hand = new Hand(cards, 5);
		Score s = new Score();
		System.out.println(s.getScore(hand,bet));
		*/
	}
}
