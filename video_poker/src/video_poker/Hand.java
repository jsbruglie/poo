package video_poker;

import java.util.Arrays;

/**
 * A player's hand of cards
 */
public class Hand {
	
	/* The array of cards in hand */
	private Card[] cards;
	/* The amount of cards in hand */
	private int hand_size;
	
	/**
	 * Constructor
	 * @param cards Array of cards to add to the hand
	 */
	public Hand(Card[] cards){
		this.cards = cards;
		this.hand_size = cards.length;
	}
	
	/**
	 * Swaps a card at a specified index by a given cards
	 * @param index The index of the card in hand to be replaced
	 * @param c The new card
	 */
	public void swapCard(int index, Card c){
		this.cards[index] = c;
	}
	
	/* Getters and Setters */
	
	/**
	 * @return The internal card array field
	 */
	public Card[] getCards() {
		return cards;
	}
	
	/**
	 * @param cards The cards to replace all the existing ones in hand
	 */
	public void setCards(Card[] cards) {
		this.cards = cards;
	}
	
	/**
	 * @return The internal hand size field
	 */
	public int getHandSize() {
		return hand_size;
	}
	
	/**
	 * @param hand_size The new hand size
	 */
	public void setHandSize(int hand_size) {
		this.hand_size = hand_size;
	}
	
	@Override
	public String toString() {
		return "player's hand " + Arrays.toString(cards).
			replace('[', ' ').replace(']', ' ').replace(',', ' ').trim();
	}
}
