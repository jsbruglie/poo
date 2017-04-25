package video_poker;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

public class Strategy {

	/*
	
	PlayEvaluator pe;

	int debugcount = 1;
	public static void main(String[] args){
		//Create a hardcoded hand and test it
		//1. Test all combinations in the list 
		Card[][] combinations = {
				//1. 4 of a kind: TTTT7
				{new Card(Suit.Clubs,10), new Card(Suit.Diamonds,10), new Card(Suit.Hearts,10), new Card(Suit.Spades,10), new Card(Suit.Diamonds,7)},	
				//1. Royal Flush: JTAQK
				{new Card(Suit.Clubs,11), new Card(Suit.Clubs,10), new Card(Suit.Clubs,1), new Card(Suit.Clubs,12), new Card(Suit.Clubs,13)},
				//1. Straight Flush: 32456
				{new Card(Suit.Clubs,3), new Card(Suit.Clubs,2), new Card(Suit.Clubs,4), new Card(Suit.Clubs,5), new Card(Suit.Clubs,6)},
				//2. 4ToRoyalFlush: JTA3K, J4AQK 
				{new Card(Suit.Clubs,11), new Card(Suit.Diamonds,4), new Card(Suit.Clubs,1), new Card(Suit.Clubs,12), new Card(Suit.Clubs,13)},
				//3. Three Aces: AAA32, A4AJA
				{new Card(Suit.Diamonds,1), new Card(Suit.Diamonds,4), new Card(Suit.Clubs,1), new Card(Suit.Clubs,11), new Card(Suit.Spades,1)},
				//4. Straight: 24356
				{new Card(Suit.Diamonds,2), new Card(Suit.Diamonds,4), new Card(Suit.Clubs,3), new Card(Suit.Clubs,5), new Card(Suit.Spades,6)},
				//4. Flush: 98365
				{new Card(Suit.Diamonds,9), new Card(Suit.Diamonds,8), new Card(Suit.Diamonds,3), new Card(Suit.Diamonds,6), new Card(Suit.Diamonds,5)},
				//4. FullHouse: 83383
				{new Card(Suit.Diamonds,8), new Card(Suit.Diamonds,3), new Card(Suit.Spades,3), new Card(Suit.Hearts,8), new Card(Suit.Clubs,3)},
				//5. Three of a Kind (except Aces): KQ3KK
				{new Card(Suit.Diamonds,13), new Card(Suit.Diamonds,12), new Card(Suit.Spades,3), new Card(Suit.Hearts,12), new Card(Suit.Clubs,12)},
				//6. 4 to a straight flush: 3456T
				{new Card(Suit.Diamonds,3), new Card(Suit.Diamonds,4), new Card(Suit.Diamonds,5), new Card(Suit.Diamonds,6), new Card(Suit.Clubs,10)},
				//7. Two Pairs: KKJQJ
				{new Card(Suit.Diamonds,13), new Card(Suit.Hearts,13), new Card(Suit.Spades,11), new Card(Suit.Hearts,12), new Card(Suit.Clubs,11)},
				//8. High Pair: K59KJ
				{new Card(Suit.Diamonds,12), new Card(Suit.Diamonds,5), new Card(Suit.Spades,9), new Card(Suit.Hearts,12), new Card(Suit.Clubs,11)},
				//9. 4 to a flush: 692T4 T is not suited
				{new Card(Suit.Diamonds,6), new Card(Suit.Diamonds,9), new Card(Suit.Diamonds,2), new Card(Suit.Hearts,10), new Card(Suit.Diamonds,4)},
				//10. 3 to a royal flush: QJ47A
				{new Card(Suit.Diamonds,12), new Card(Suit.Diamonds,11), new Card(Suit.Spades,4), new Card(Suit.Hearts,7), new Card(Suit.Diamonds,1)},
					
				//11. 4 to an outside straight: 3798T
				{new Card(Suit.Diamonds,3), new Card(Suit.Clubs,7), new Card(Suit.Spades,9), new Card(Suit.Hearts,8), new Card(Suit.Clubs,10)},
				
				//12. Low Pair: AT3T6
				{new Card(Suit.Diamonds,1), new Card(Suit.Diamonds,10), new Card(Suit.Spades,3), new Card(Suit.Hearts,10), new Card(Suit.Clubs,6)},
				//13. AKQJ unsuited: QAJ5K (spades,diamonds,hearts,hearts,clubs)
				{new Card(Suit.Spades,12), new Card(Suit.Diamonds,1), new Card(Suit.Hearts,11), new Card(Suit.Hearts,5), new Card(Suit.Clubs,13)},
				
				//14. 3 to a straight flush(type 1): 46J5T (4,6,5 are suited)		
				{new Card(Suit.Spades,4), new Card(Suit.Spades,6), new Card(Suit.Hearts,11), new Card(Suit.Spades,5), new Card(Suit.Clubs,10)},
				
				//15. 4 to an inside straight with 3 high cards: there is only one comb.: JK4Q9 , JQKA
				{new Card(Suit.Spades,11), new Card(Suit.Spades,13), new Card(Suit.Hearts,4), new Card(Suit.Clubs,12), new Card(Suit.Hearts,9)}
				
				//16. QJ Suited 
		};
		//2. Test all the difficult hands
		Card[][] difficultHands;
		for(int i=0; i<combinations.length; i++){
			Hand hand = new Hand(combinations[i], 5);
			Strategy strategy = new Strategy();
			System.out.println(strategy.evaluateHand(hand));
			System.out.println("Above was: " + strategy.getDebugcount());
		}
	}
	
	public int getDebugcount() {
		return debugcount;
	}

	public void setDebugcount(int debugcount) {
		this.debugcount = debugcount;
	}

	public Strategy() {
		pe = new PlayEvaluator();
	}

	public String evaluateHand(Hand hand){
		//Get value of hand and display advice of cards to keep
		List<Integer> cardsKeep = valueHand(hand);
		if(cardsKeep != null)
			return cardsKeep.toString();
		else
			return "Discard Everything";
	}
	
	public List<Integer> valueHand(Hand hand){ //Returns the cards to keep, later process can be done
		
		Card[] c = hand.getCards();
		pe.initialize(c);
		
		List<Integer> cardsKeep = new ArrayList<Integer>();
		debugcount=1;
		
		//1. Straight Flush, Four of A kind, Royal Flush
 		cardsKeep = pe.RoyalFlush(c);
 		if(cardsKeep != null) return cardsKeep;
		
 		cardsKeep = pe.StraightFlush(c);
 		if(cardsKeep != null) return cardsKeep;
 		
 		cardsKeep = pe.FourOfAKind(c);
 		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//2. 4 To A Royal Flush
		cardsKeep = pe.NToRoyalFlush(c, 4);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//3. Three Aces
		cardsKeep = pe.ThreeAces(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//4. Straight, Flush, Full House
		cardsKeep = pe.Straight(c);
		if(cardsKeep != null) return cardsKeep;
		
		cardsKeep = pe.Flush(c);
		if(cardsKeep != null) return cardsKeep;
		
		cardsKeep = pe.FullHouse(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//5. Three of A Kind
		cardsKeep = pe.ThreeOfAKind(c); //Except aces
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//6. 
		cardsKeep = pe._4ToStraightFlush(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//7.
		cardsKeep = pe.TwoPair(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//8.
		cardsKeep = pe.HighPair(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//9.
		cardsKeep = pe.NToFlush(c, 4);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//10.
		cardsKeep = pe.NToRoyalFlush(c, 3);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//11.
		cardsKeep = pe._4ToOutsideStraight(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//12.
		cardsKeep = pe.LowPair(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//13.
		cardsKeep = pe.AKQJunsuited(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//14.
		cardsKeep = pe._3ToStraightFlush(c, 1);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//15.
		cardsKeep = pe._4ToInsideStraight(c, 3);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//16.
		cardsKeep = pe.QJ(c, true); //Suited
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//17.
		cardsKeep = pe.NToFlush(c, 2);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//18.
		cardsKeep = pe._2SuitedHighCards(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//19.
		cardsKeep = pe._4ToInsideStraight(c, 2);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//20.
		cardsKeep = pe._3ToStraightFlush(c, 2);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//21.
		cardsKeep = pe._4ToInsideStraight(c, 1);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		///22.
		cardsKeep = pe.KQJunsuited(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//23.
		cardsKeep = pe.JTsuited(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//24.
		cardsKeep = pe.QJ(c, false); //unsuited
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//25.
		cardsKeep = pe._3ToFlush_nHighCards(c, 1);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//26.
		cardsKeep = pe.QTsuited(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//27.
		cardsKeep = pe._3ToStraightFlush(c, 3);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//28.
		cardsKeep = pe.KQunsuited(c);
		if(cardsKeep != null) return cardsKeep;
		
		cardsKeep = pe.KJunsuited(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//29.
		cardsKeep = pe.Ace(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//30.
		cardsKeep = pe.KTsuited(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//31.
		cardsKeep = pe.JackQueenOrKing(c);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//32.
		cardsKeep = pe._4ToInsideStraight(c, 0);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
		//33.
		cardsKeep = pe._3ToFlush_nHighCards(c, 0);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		
 		return null;
	}
	
	*/

}
