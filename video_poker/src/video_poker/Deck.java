package video_poker;

import java.util.Random;

public class Deck {
	private Card[] cards;
	private int numberCards;
	
	//! Constructor to create the deck
	public Deck(int numCards, boolean shuffle){
		this.numberCards = numCards;
		this.cards = new Card[this.numberCards];
		int c=0;
		for(int s=0; s < 4; s++){
			for(int n = 1; n <= 13; n++){
				this.cards[c] = new Card(Suit.values()[s], n);
				c++;
			}
		}
		if(shuffle){
			this.shuffle();
		}
		
	}
	
	//! Method to shuffle the deck
	public void shuffle() {
		Random r = new Random();
		Card temp;
		
		int j;
		for(int i=0; i < this.numberCards; i++){
			j = r.nextInt(this.numberCards);
			
			temp = this.cards[i];
			this.cards[i] = this.cards[j];
			this.cards[j] = temp;
		}
		
	}
	
	//Method to get the top n (n from 1 to 5) cards

}
