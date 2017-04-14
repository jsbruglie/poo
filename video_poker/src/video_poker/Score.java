package video_poker;

public class Score {

	int[][] paytable;
	
	int[] number_occurences;
	
	public static void main(String[] args){
		//Create a hardcoded hand and a bet
		int bet = 5;
		Suit suit;
		Card[] cards = new Card[5];
		
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
		
		Hand hand = new Hand(cards, 5);
		//Print hand
		//System.out.println(hand);
		//Print the score
		Combination comb = null;
		Score s = new Score();
		System.out.println(s.getScore(hand,bet));
	}
	
	
	public Score(){
		//Initialize the pay table
		paytable = new int[][]{
			{ 250, 500, 750, 1000, 4000 },
			{ 50,  100, 150, 200, 250   },
			{ 160, 320, 480, 640, 800   },
			{ 80,  160, 240, 320, 400   },
			{ 50,  100, 150, 200, 250   },
			{ 10,  20,  30,  40,  50    },
			{ 7,   14,  21,  28,  35    },
			{ 5,   10,  15,  20,  25    },
			{ 3,   6,   9,   12,  15    },
			{ 1,   2,   3,   4,   5     },
			{ 1,   2,   3,   4,   5     } 
		};
		number_occurences = new int[13];
	}
	
	private int evaluateHand(Hand hand){
		Card[] c = hand.getCards();
		for(int i=0; i<13; i++)
			number_occurences[i] = 0;
		for(int i=0; i<5;i++){
			number_occurences[c[i].getNumber()-1]++;
		}
		int hand_index = -1;
		
		//Check if Jacks or Better
		if(checkJacksOrBetter(c)){
			hand_index = 10;
		}
		//Check if Two Pair
		if(checkTwoPair(c)){
			hand_index = 9;
		}
		//Check if Three of a Kind
		if(checkThreeOfAKind(c)){
			hand_index = 8;
		}
		//Check if Straight
		if(checkStraight(c)){
			hand_index = 7;
		}
		//Check if Flush
		if(checkFlush(c)){
			hand_index = 6;
		}
		//Check if Full House
		if(checkFullHouse(c)){
			hand_index = 5;
		}
		//Check if Four5_K
		if(checkFour5_K(c)){
			hand_index = 4;
		}
		//Check if Four2-4
		if(checkFour2_4(c)){
			hand_index = 3;
		}
		//Check if FourAces
		if(checkFourAces(c)){
			hand_index = 2;
		}
		//Check if Straight Flush
		if(checkStraightFlush(c)){
			hand_index = 1;
		}
		//Check if Royal Flush
		if(checkRoyalFlush(c)){
			hand_index = 0;
		}
		
		return hand_index;
	}
	
	public int getScore(Hand hand, int bet){
		//Evaluate the hand
		int hand_index = evaluateHand(hand);
		
		if(hand_index != -1)
			return paytable[hand_index][bet-1];
		else
			return -1;
	
	}
	
	public Combination getCombination(Hand hand){
		int hand_index = evaluateHand(hand);
		Combination comb = null;
		switch(hand_index){
			case -1:
				System.out.println("Other");
				comb = Combination.Other;
				break;
			case 0:
				System.out.println("Royal Flush");
				comb = Combination.RoyalFlush;
				break;
			case 1:
				System.out.println("Straight Flush");
				comb = Combination.StraightFlush;
				break;
			case 2:
				System.out.println("Four of a Kind - Aces");
				comb = Combination.FourOfAKind;
				break;
			case 3:
				System.out.println("Four of a Kind - 2-4");
				comb = Combination.FourOfAKind;
				break;
			case 4:
				System.out.println("Four of a Kind - 5-K");
				comb = Combination.FourOfAKind;
				break;
			case 5:
				System.out.println("Full House");
				comb = Combination.FullHouse;
				break;
			case 6:
				System.out.println("Flush");
				comb = Combination.Flush;
				break;
			case 7:
				System.out.println("Straight");
				comb = Combination.Straight;
				break;
			case 8:
				System.out.println("Three of a Kind");
				comb = Combination.ThreeOfAKind;
				break;
			case 9:
				System.out.println("Two Pair");
				comb = Combination.TwoPair;
				break;
			case 10:
				System.out.println("Jacks or Better");
				comb = Combination.JacksOrBetter;
				break;
		}
		return comb;
	}
	
	public boolean checkJacksOrBetter(Card[] c){
		//Check if there is a pair of jacks or better
		//Check in number of occurences the number of elements
		for(int i=0; i<13; i++){
			if(i==0 || i>=10){
				if(this.number_occurences[i] == 2)
					return true;
			}
		}
		return false;
	}
	
	public boolean checkTwoPair(Card[] c){
		//Check if there are 2 pairs
		int pair_count = 0;
		for(int i=0; i<13; i++){
			if(number_occurences[i] == 2){
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
			if(number_occurences[i] == 3){
				return true;
			}
		}
		return false;

	}
	public boolean checkStraight(Card[] c){
		//Check if the numbers are sequential
		for(int i=0; i<13; i++){
			if(number_occurences[i]!=0){
				if(number_occurences[i]!=1){
					return false;
				}else{
					if(i<9){ //Not allowing wraparound straights
						for(int j=i;j<i+5;j++){ //Check the next 5 cards
							if(number_occurences[j] != 1)
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
			if(number_occurences[i] == 2)
				npairs++;
			if(number_occurences[i] == 3)
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
			if(number_occurences[i] == 4){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkFour5_K(Card[] c){
		for(int i=0; i<13; i++){
			if(number_occurences[i] == 4 && i!=0 && i>=4){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkFour2_4(Card[] c){
		for(int i=0; i<13; i++){
			if(number_occurences[i] == 4 && i!=0 && i<4){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkFourAces(Card[] c){
		for(int i=0; i<13; i++){
			if(number_occurences[i] == 4 && i==0){
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
			if(number_occurences[12] == 1 && number_occurences[11] == 1 && number_occurences[10] == 1 && number_occurences[9] == 1 && number_occurences[0] == 1){
				return true;
			}
		} 
		return false;
	}
	
	
	
}
