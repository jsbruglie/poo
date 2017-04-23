package video_poker;

public class Card {

	private Suit suit; /**< One of the four valid suits	*/
	private int number; /**< The number, where Ace: 1, Jack-King: 11-13*/
	
	public Card(Suit suit, int number){
		this.suit = suit;
		this.number = number;
	}
	//Alternative constructor that receives the name of the card (instead of number)
	public Card(Suit suit, String number){
		this.suit = suit;
		switch(number){
			case "A":
				this.number = 1;
				break;
			case "2":
				this.number = 2;
				break;
			case "3":
				this.number = 3;
				break;
			case "4":
				this.number = 4;
				break;
			case "5":
				this.number = 5;
				break;
			case "6":
				this.number = 6;
				break;
			case "7":
				this.number = 7;
				break;
			case "8":
				this.number = 8;
				break;
			case "9":
				this.number = 9;
				break;
			case "T":
				this.number = 10;
				break;
			case "J":
				this.number = 11;
				break;
			case "Q":
				this.number = 12;
				break;
			case "K":
				this.number = 13;
				break;
		}
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
				name = "2";
				break;
			case 3:
				name = "3";
				break;
			case 4:
				name = "4";
				break;
			case 5:
				name = "5";
				break;
			case 6:
				name = "6";
				break;
			case 7:
				name = "7";
				break;
			case 8:
				name = "8";
				break;
			case 9:
				name = "9";
				break;
			case 10:
				name = "T";
				break;
			case 11:
				name = "J";
				break;
			case 12:
				name = "Q";
				break;
			case 13:
				name = "K";
				break;
			case 1:
				name = "A";
				break;
			/*case 14:
				name = "A";
				break;*/
		}
		return name + (char)this.suit.getSymbol();
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