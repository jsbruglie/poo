package video_poker;

public class Card {

	private Suit suit; /**< One of the four valid suits	*/
	private int number; /**< The number, where Ace: 1, Jack-King: 11-13*/
	
	public Card(Suit suit, int number){
		this.suit = suit;
		this.number = number;
	}
	
	public Suit getSuit() {
		return suit;
	}
	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		String name = null;
		switch(this.number){
			case 2:
				name = "Two";
				break;
			case 3:
				name = "Three";
				break;
			case 4:
				name = "Four";
				break;
			case 5:
				name = "Five";
				break;
			case 6:
				name = "Six";
				break;
			case 7:
				name = "Seven";
				break;
			case 8:
				name = "Eight";
				break;
			case 9:
				name = "Nine";
				break;
			case 10:
				name = "Ten";
				break;
			case 11:
				name = "Jack";
				break;
			case 12:
				name = "Queen";
				break;
			case 13:
				name = "King";
				break;
			case 1:
				name = "Ace";
				break;
		}
		return name + " of " + this.suit.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (number != other.number)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
	
	
}