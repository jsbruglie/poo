package video_poker;

import java.util.Arrays;

/**
 * 
 */
public class Hand {
	
	/* The array of cards in hand */
	private Card[] cards;
	/* The amount of cards in hand */
	private int handsize;
	
	/**
	 * Constructor
	 * @param cards Array of cards to add to the hand
	 * @param handsize The number of cards in hand
	 */
	public Hand(Card[] cards, int handsize){
		this.handsize = handsize;
		this.cards = new Card[handsize];
		
		/* TODO - FIX OUT OF BOUNDS */
		
		for(int i = 0; i < handsize; i++){
			this.cards[i] = new Card(cards[i].suit, cards[i].number);
		}
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
