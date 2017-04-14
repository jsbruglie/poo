package video_poker;

import java.util.Random;

public class Deck {
	private Card[] cards;
	private int numberCards;
	int top;
	
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
		top = numberCards - 1; //Top of the deck
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
		top = numberCards - 1; //Reset the top card whenever you shuffle
	}
 	public Card[] getHand(){
 		//Get the top 5 cards from the deck
 		Card[] c = new Card[5];
 		int j=0;
 		

 		for(int i=top;i>top-5;i--){
 			//Hope there's no null exception here
 			c[j] = cards[i];
 			j++;
 		}
 		top = top - 5;
 		return c;
 	}
 	public Card draw(){
 		Card c = cards[top];
 		top--;
 		
 		
 		
 		return c;
 	}
	//Method to get the top n (n from 1 to 5) cards

}
