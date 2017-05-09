package video_poker;

import java.util.Random;

/**
 * Represents the deck of cards
 */
public class Deck {
	
	/** An array of cards */
	private Card[] cards;
	/** The deck's card total */
	private int number_cards;
	/** The index of the card at the top of the deck */
	private int top;
	
	/** Constant number of suits */
	private final int SUITS = Suit.values().length;
	/** Constant value of Cards per suit */
	private final int CARDS_PER_SUIT = Rank.values().length;
	/** Constant maximum total of cards */
	private final int MAX_CARDS = SUITS * CARDS_PER_SUIT;
	
	/**
	 * Constructor for a regular 52 card deck
	 * @param shuffle Whether the deck should be shuffled
	 */
	public Deck(boolean shuffle){
		
		this.number_cards = MAX_CARDS;
		this.cards = new Card[this.number_cards];
		for(int c = 0, s = 0; s < SUITS; s++){
			for(int n = 0; n < CARDS_PER_SUIT; n++, c++){
				this.cards[c] = new Card(Rank.values()[n], Suit.values()[s]);
			}
		}
		if(shuffle){
			this.shuffle();
		}
		top = number_cards - 1;
	}
	/**
	 * Constructor from card array.
	 * Used when reading a deck from a card file
	 * @param cards Array of cards to be inserted in the deck
	 */
	public Deck(Card[] cards){
		
		this.number_cards = cards.length;
		this.cards = new Card[number_cards];
		// The deck must be inverted to match specification
		for (int i = 0; i < cards.length; i++){
			this.cards[i] = cards[cards.length - 1 - i];
		}
		/** @attention Do not shuffle this deck */
		top = this.number_cards - 1;
	}
	/**
	 * Shuffles the deck randomly
	 * 
	 * Implements the Fisher-Yates shuffle algorithm, which effectively
	 * creates an unbiased permutation of a finite sequence.
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle"> Wikipedia - Fisher–Yates shuffle</a>
	 */
	public void shuffle() {
		
		Random r = new Random();
		for (int i = number_cards - 1; i > 0; i--){
			int index = r.nextInt(i+1);
			Card tmp = cards[index];
			cards[index] = cards[i];
			cards[i] = tmp; 
		}
		top = number_cards - 1;
	}
	
	/**
	 * Draws a hand from the deck
	 * 
	 * @param size The size of a player's hand
	 * @return The array of drawn cards
	 */
 	public Hand getHand(int size){
 		
 		Card[] c = new Card[size];
 		for(int i = 0; i < size; i++){
 			try{
 				c[i] = this.draw();
 			}catch(DeckEmptyException e){
 				System.err.println("The deck has insufficient cards for a draw.");
 				return null;
 			}
 		}
 		
 		return new Hand(c);
 	}
 	
 	/**
 	 * Draws a single card from the deck
 	 * @throws DeckEmptyException When the deck is empty
 	 * @return The drawn card upon success
 	 */
 	public Card draw() throws DeckEmptyException{
 		
 		Card c = null;
 		
 		if (top == -1){
 			throw new DeckEmptyException();
 		}
 		else{
 			c = cards[top--];
 		}
 		return c;
 	}
}
