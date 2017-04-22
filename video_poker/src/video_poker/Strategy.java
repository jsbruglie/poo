package video_poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Strategy {
	PlayEvaluator pe;
	public static void main(String[] args){
		//Create a hardcoded hand and test it
		//1. Test all combinations in the list 
		//2. Test all the difficult hands
		Card[] cards = {
						new Card(Suit.Clubs,10), 
					    new Card(Suit.Clubs,10), 
					    new Card(Suit.Clubs,10), 
					    new Card(Suit.Clubs,10), 
					    new Card(Suit.Clubs,10)
					    }
;		Hand hand = new Hand(cards, 5);
		Strategy strategy = new Strategy();
		System.out.println(strategy.evaluateHand(hand));
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
		
		
		//1.
 		cardsKeep = pe.RoyalFlush(c);
 		if(cardsKeep != null) return cardsKeep;
		
 		cardsKeep = pe.StraightFlush(c);
 		if(cardsKeep != null) return cardsKeep;
 		
 		cardsKeep = pe.FourOfAKind(c);
 		if(cardsKeep != null) return cardsKeep;
 		
		//2.
		cardsKeep = pe.NToRoyalFlush(c, 4);
		if(cardsKeep != null) return cardsKeep;

		//3.
		cardsKeep = pe.ThreeAces(c);
		if(cardsKeep != null) return cardsKeep;
		
		//4.
		cardsKeep = pe.Straight(c);
		if(cardsKeep != null) return cardsKeep;
		
		cardsKeep = pe.Flush(c);
		if(cardsKeep != null) return cardsKeep;
		
		cardsKeep = pe.FullHouse(c);
		if(cardsKeep != null) return cardsKeep;

		//5.
		cardsKeep = pe.ThreeOfAKind(c); //Except aces
		if(cardsKeep != null) return cardsKeep;
		
		//6.
		cardsKeep = pe._4ToStraightFlush(c);
		if(cardsKeep != null) return cardsKeep;
		
		//7.
		cardsKeep = pe.TwoPair(c);
		if(cardsKeep != null) return cardsKeep;
		
		//8.
		cardsKeep = pe.HighPair(c);
		if(cardsKeep != null) return cardsKeep;

		//9.
		cardsKeep = pe.NToFlush(c, 4);
		if(cardsKeep != null) return cardsKeep;
		
		//10.
		cardsKeep = pe.NToRoyalFlush(c, 3);
		if(cardsKeep != null) return cardsKeep;
		
		//11.
		cardsKeep = pe._4ToOutsideStraight(c);
		if(cardsKeep != null) return cardsKeep;
		
		//12.
		cardsKeep = pe.LowPair(c);
		if(cardsKeep != null) return cardsKeep;
		
		//13.
		cardsKeep = pe.AKQJunsuited(c);
		if(cardsKeep != null) return cardsKeep;
 		
		//14.
		cardsKeep = pe._3ToStraightFlush(c, 1);
		if(cardsKeep != null) return cardsKeep;
 		
		//15.
		cardsKeep = pe._4ToInsideStraight(c, 3);
		if(cardsKeep != null) return cardsKeep;
 		
		//16.
		cardsKeep = pe.QJ(c, true); //Suited
		if(cardsKeep != null) return cardsKeep;
		
		//17.
		cardsKeep = pe.NToFlush(c, 2);
		if(cardsKeep != null) return cardsKeep;
		
		//18.
		cardsKeep = pe._2SuitedHighCards(c);
		if(cardsKeep != null) return cardsKeep;
		
		//19.
		cardsKeep = pe._4ToInsideStraight(c, 2);
		if(cardsKeep != null) return cardsKeep;
		
		//20.
		cardsKeep = pe._3ToStraightFlush(c, 2);
		if(cardsKeep != null) return cardsKeep;
		
		//21.
		cardsKeep = pe._4ToInsideStraight(c, 1);
		if(cardsKeep != null) return cardsKeep;
		
		///22.
		cardsKeep = pe.KQJunsuited(c);
		if(cardsKeep != null) return cardsKeep;
		
		//23.
		cardsKeep = pe.JTsuited(c);
		if(cardsKeep != null) return cardsKeep;
		
		//24.
		cardsKeep = pe.QJ(c, false); //unsuited
		if(cardsKeep != null) return cardsKeep;
		
		//25.
		cardsKeep = pe._3ToFlush_nHighCards(c, 1);
		if(cardsKeep != null) return cardsKeep;
		
		//26.
		cardsKeep = pe.QTsuited(c);
		if(cardsKeep != null) return cardsKeep;
		
		//27.
		cardsKeep = pe._3ToStraightFlush(c, 3);
		if(cardsKeep != null) return cardsKeep;
		
		//28.
		cardsKeep = pe.KQunsuited(c);
		if(cardsKeep != null) return cardsKeep;
		
		cardsKeep = pe.KJunsuited(c);
		if(cardsKeep != null) return cardsKeep;
		
		//29.
		cardsKeep = pe.Ace(c);
		if(cardsKeep != null) return cardsKeep;
		
		//30.
		cardsKeep = pe.KTsuited(c);
		if(cardsKeep != null) return cardsKeep;
		
		//31.
		cardsKeep = pe.JackQueenOrKing(c);
		if(cardsKeep != null) return cardsKeep;
		
		//32.
		cardsKeep = pe._4ToInsideStraight(c, 0);
		if(cardsKeep != null) return cardsKeep;
		
		//33.
		cardsKeep = pe._3ToFlush_nHighCards(c, 0);
		if(cardsKeep != null) return cardsKeep;
		
 		return null;
	}
	
	

}
