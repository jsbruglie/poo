package video_poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static video_poker.Suit.*;
import static video_poker.Rank.*;

public class Test {
	
	public static void main(String[] args) {
		
		List<Card> hold = new ArrayList<Card>();
		
		hold.add(new Card(n8, Hearts));
		hold.add(new Card(n2, Hearts));
		hold.add(new Card(n4, Clubs));
		hold.add(new Card(K, Diamonds));
		hold.add(new Card(J, Diamonds));
		
		for(Card c : hold) {
            System.out.print(c+" ");
        }
		System.out.print("\n");
		
		Collections.sort(hold, new Card.CardComparator());
		
		for(Card c : hold) {
            System.out.print(c+" ");
        }
		System.out.print("\n");
		
		Card[] cards = new Card[5];
		cards[0] = new Card(n8, Hearts);
		cards[1] = new Card(n2, Hearts);
		cards[2] = new Card(n4, Clubs);
		cards[3] = new Card(K, Diamonds);
		cards[4] = new Card(J, Diamonds);
	
		Arrays.sort(cards, new Card.CardComparator());
		
		for(Card c : cards) {
            System.out.print(c+" ");
        }
		System.out.print("\n");
	
	}

}
