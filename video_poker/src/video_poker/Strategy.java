package video_poker;

import java.util.ArrayList;
import java.util.List;

public class Strategy {

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
				{new Card(Suit.Spades,11), new Card(Suit.Spades,13), new Card(Suit.Hearts,4), new Card(Suit.Clubs,12), new Card(Suit.Hearts,9)},
				
				//16. QJ Suited: Q47J2 (QJ spades) 
				{new Card(Suit.Spades,12), new Card(Suit.Hearts,4), new Card(Suit.Hearts,7), new Card(Suit.Spades,11), new Card(Suit.Clubs,2)},
				
				//17. 3 to a flush with 2 high cards: QK724 (QK7 are suited, spades)
				{new Card(Suit.Spades,12), new Card(Suit.Spades,13), new Card(Suit.Spades,5), new Card(Suit.Hearts,2), new Card(Suit.Clubs,4)},
				
				//18. 2 Suited High Cards: J29KT (J and K hearts)
				{new Card(Suit.Hearts,11), new Card(Suit.Spades,2), new Card(Suit.Clubs,9), new Card(Suit.Hearts,13), new Card(Suit.Diamonds,10)},
		
				//19. 4 to an inside straight with 2 high cards(missing card inside): 89J3Q
				{new Card(Suit.Hearts,8), new Card(Suit.Spades,9), new Card(Suit.Clubs,11), new Card(Suit.Diamonds,3), new Card(Suit.Hearts,12)},
		
				//20. 3 to a straight flush (type 2): one gap: 7852T two gaps, one high card: TQ846 234 suited: 243KT
				{new Card(Suit.Spades,7), new Card(Suit.Spades,8), new Card(Suit.Spades,5), new Card(Suit.Hearts,2), new Card(Suit.Diamonds,10)},
		
				//21. 4 to an inside straight with 1 high card(missing card inside): J497T
				{new Card(Suit.Spades,11), new Card(Suit.Spades,4), new Card(Suit.Spades,9), new Card(Suit.Hearts,7), new Card(Suit.Diamonds,10)},
		
				//22. KQJ unsuited: K3Q7J (K Q J spades hearts and clubs)
				{new Card(Suit.Spades,13), new Card(Suit.Spades,3), new Card(Suit.Hearts,12), new Card(Suit.Diamonds,7), new Card(Suit.Clubs,11)},
				
				//23. JT suited: 37J2T (J T diamonds)
				{new Card(Suit.Spades,3), new Card(Suit.Hearts,7), new Card(Suit.Diamonds,11), new Card(Suit.Hearts,2), new Card(Suit.Diamonds,10)},
		
				//24. QJ unsuited, Q45J2 (Q hearts J clubs)
				{new Card(Suit.Hearts,12), new Card(Suit.Hearts,4), new Card(Suit.Diamonds,5), new Card(Suit.Clubs,11), new Card(Suit.Diamonds,2)},
				
				//25. 3 to a flush with 1 high card: 9J356 (T J 3 hearts)
				{new Card(Suit.Hearts,9), new Card(Suit.Hearts,11), new Card(Suit.Hearts,3), new Card(Suit.Diamonds,5), new Card(Suit.Spades,6)},
				
				//26. QT suited: Q629T (Q and T spades)
				{new Card(Suit.Spades,12), new Card(Suit.Hearts,6), new Card(Suit.Diamonds,2), new Card(Suit.Hearts,9), new Card(Suit.Spades,10)},
				
				//27. 3 to a straight flush (type 3): (two gaps no hgih cards) 246T8 (2 4 6 hearts)
				{new Card(Suit.Hearts,2), new Card(Suit.Hearts,4), new Card(Suit.Hearts,6), new Card(Suit.Diamonds,10), new Card(Suit.Diamonds,8)},
				
				//28. KQ unsuited: Q62K5 (Q and K spades and diamonds)
				{new Card(Suit.Spades,12), new Card(Suit.Hearts,6), new Card(Suit.Diamonds,2), new Card(Suit.Diamonds,13), new Card(Suit.Hearts,5)},
				//28. KJ unsuited: K629J (K and J spades and diamonds)
				{new Card(Suit.Spades,13), new Card(Suit.Hearts,6), new Card(Suit.Diamonds,2), new Card(Suit.Hearts,9), new Card(Suit.Diamonds,11)},
				
				//29. Ace: A483T
				{new Card(Suit.Spades,1), new Card(Suit.Hearts,4), new Card(Suit.Diamonds,8), new Card(Suit.Clubs,3), new Card(Suit.Diamonds,10)},
		
				//30. KT suited: K4T39
				{new Card(Suit.Diamonds,13), new Card(Suit.Hearts,4), new Card(Suit.Diamonds,10), new Card(Suit.Clubs,3), new Card(Suit.Clubs,9)},
		
				//31. Jack, Queen or King 2587K
				{new Card(Suit.Diamonds,2), new Card(Suit.Hearts,5), new Card(Suit.Diamonds,8), new Card(Suit.Clubs,7), new Card(Suit.Clubs,13)},
				
				//32. 4 to an inside straight with no high cards: 4537T
				{new Card(Suit.Diamonds,4), new Card(Suit.Hearts,5), new Card(Suit.Clubs,3), new Card(Suit.Spades,7), new Card(Suit.Clubs,10)},
				
				//33. 3 to a flush with no high cards: 28T74 (2 8 7 spades)
				{new Card(Suit.Spades,2), new Card(Suit.Spades,8), new Card(Suit.Diamonds,10), new Card(Suit.Spades,7), new Card(Suit.Clubs,4)},
		
		};
		//2. Test all the difficult hands
		Card[][] difficultHands = {
				//1. SHOULD EVALUATE:1 Keep straight flush, KQJT9
				{new Card(Suit.Clubs,13), new Card(Suit.Clubs,12), new Card(Suit.Clubs,11), new Card(Suit.Clubs,10), new Card(Suit.Clubs,9)},	
		
				//2. SHOULD EVALUATE:2 Keep 4 to a royal flush, AKQJT
				{new Card(Suit.Diamonds,1), new Card(Suit.Diamonds,13), new Card(Suit.Spades,12), new Card(Suit.Diamonds,11), new Card(Suit.Diamonds,10)},
		
				//3. SHOULD EVALUATE:2 Keep 4 to a royal flush, AKJT9
				{new Card(Suit.Spades,1), new Card(Suit.Spades,13), new Card(Suit.Spades,11), new Card(Suit.Spades,10), new Card(Suit.Spades,9)},
				
				//4. SHOULD EVALUATE:3 Keep three aces, AAA22
				{new Card(Suit.Hearts,1), new Card(Suit.Diamonds,1), new Card(Suit.Spades,1), new Card(Suit.Clubs,2), new Card(Suit.Spades,2)},
				
				//5. SHOULD EVALUATE:4 Keep full house, 44455
				{new Card(Suit.Clubs,4), new Card(Suit.Spades,4), new Card(Suit.Hearts,4), new Card(Suit.Diamonds,5), new Card(Suit.Clubs,5)},
				
				//6. SHOULD EVALUATE:4 Keep flush, 5678J
				{new Card(Suit.Spades,5), new Card(Suit.Spades,6), new Card(Suit.Spades,7), new Card(Suit.Spades,8), new Card(Suit.Spades,11)},
		
				//7. SHOULD EVALUATE:4 Keep straight 34567
				{new Card(Suit.Diamonds,3), new Card(Suit.Hearts,4), new Card(Suit.Hearts,5), new Card(Suit.Hearts,6), new Card(Suit.Hearts,7)},
				
				//8. SHOULD EVALUATE:4 Keep straight AKQJT
				{new Card(Suit.Clubs,1), new Card(Suit.Diamonds,13), new Card(Suit.Diamonds,12), new Card(Suit.Diamonds,11), new Card(Suit.Spades,10)},
				
				//9. SHOULD EVALUATE:6 Keep 4 to a straight flush
				{new Card(Suit.Clubs,13), new Card(Suit.Clubs,12), new Card(Suit.Clubs,11), new Card(Suit.Clubs,9), new Card(Suit.Diamonds,4)},
				
				//10. SHOULD EVALUATE:7 Keep Two Pair AAKKQ
				{new Card(Suit.Hearts,1), new Card(Suit.Spades,1), new Card(Suit.Diamonds,13), new Card(Suit.Spades,13), new Card(Suit.Spades,12)},
		
				//11. SHOULD EVALUATE:8 Keep High Pair JJ479
				{new Card(Suit.Clubs,11), new Card(Suit.Diamonds,11), new Card(Suit.Diamonds,4), new Card(Suit.Diamonds,7), new Card(Suit.Diamonds,9)},
		
				//12. SHOULD EVALUATE:8 Keep High Pair QQJA2
				{new Card(Suit.Spades,12), new Card(Suit.Hearts,12), new Card(Suit.Hearts,11), new Card(Suit.Hearts,1), new Card(Suit.Clubs,2)},
				
				//13. SHOULD EVALUATE:9 Keep High Pair 8JQK9
				{new Card(Suit.Clubs,8), new Card(Suit.Clubs,11), new Card(Suit.Clubs,12), new Card(Suit.Clubs,13), new Card(Suit.Hearts,9)}
				
				//14. SHOULD EVALUATE:9
		
		};
		
		
		for(int i=0; i<combinations.length; i++){
			Hand hand = new Hand(combinations[i], 5);
			Strategy strategy = new Strategy();
			System.out.println(strategy.evaluateHand(hand));
			System.out.println("Above was: " + strategy.getDebugcount());
		}
		System.out.println("Testing difficult hands....");
		for(int i=0; i<difficultHands.length; i++){
			Hand hand = new Hand(difficultHands[i], 5);
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
		cardsKeep = pe._3ToFlush_nHighCards(c, 2);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;
		/*cardsKeep = pe.NToFlush(c, 2);
		if(cardsKeep != null) return cardsKeep;
 		debugcount++;*/
		
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
	
	

}
