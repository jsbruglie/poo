package video_poker;

/**
 * Public Card Class
 */
public class Card {

	/** The card's number */
	public CardNumber number;
	/** The card's suit */
	public Suit suit;
	
	/**
	 * Card Constructor
	 * @param number The card's number
	 * @param suit The card's suit
	 */
	public Card(CardNumber number, Suit suit){
		this.number = number;
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		String name = this.number.toString();
		return name + (char) this.suit.getSymbol();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Card)) {
			return false;
		}
		Card other = (Card) obj;
		if (number != other.number) {
			return false;
		}
		if (suit != other.suit) {
			return false;
		}
		return true;
	}	
}