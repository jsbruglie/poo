package video_poker;

import java.util.Comparator;

/**
 * Public Card Class
 */
public class Card {

	/** The card's number */
	public Rank rank;
	/** The card's suit */
	public Suit suit;
	
	/**
	 * Card Constructor
	 * @param rank The card's rank
	 * @param suit The card's suit
	 */
	public Card(Rank rank, Suit suit){
		this.rank = rank;
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		String name = this.rank.toString();
		return name + (char) this.suit.getSymbol();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
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
		if (rank != other.rank) {
			return false;
		}
		if (suit != other.suit) {
			return false;
		}
		return true;
	}
	
	/**
	 *
	 */
	public static class CardComparator implements Comparator<Card> {
		@Override
		public int compare(Card c1, Card c2) {
			return c1.rank.ordinal() - c2.rank.ordinal();
		}
	}
}