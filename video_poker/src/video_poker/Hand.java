package video_poker;

import java.util.Arrays;

public class Hand {
	private Card[] cards;
	private int handsize;
	
	public Hand(Card[] cards, int handsize){
		this.handsize = handsize;
		this.cards = new Card[handsize];
		
		for(int i=0;i<handsize;i++){
			this.cards[i] = new Card(cards[i].getSuit(), cards[i].getNumber());
		}
	}
	public Hand(Hand hand){
		this.handsize = hand.handsize;
		this.cards = hand.cards;
	}
	
	public void swapCard(int index, Card c){
		this.cards[index] = c;
	}
	
	public Card[] getCards() {
		return cards;
	}
	public void setCards(Card[] cards) {
		this.cards = cards;
	}
	public int getHandsize() {
		return handsize;
	}
	public void setHandsize(int handsize) {
		this.handsize = handsize;
	}
	@Override
	public String toString() {
		return "Hand [cards=" + Arrays.toString(cards) + ", handsize=" + handsize + "]";
	}
}
