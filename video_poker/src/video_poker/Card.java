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
	
	/**
	 * Card toString. Generate textual description
	 */
	@Override
	public String toString() {
		String name = this.rank.toString();
		return name + (char) this.suit.getSymbol();
	}

	/**
	 * Generate hashcode based on rank and suit enums
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}
	
	/**
	 * Equals
	 */
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
	 * CardComparator Object
	 * Allows to sort card by ascending rank enum (Ace low)
	 */
	public static class CardComparator implements Comparator<Card> {
		@Override
		public int compare(Card c1, Card c2) {
			return c1.rank.ordinal() - c2.rank.ordinal();
		}
	}
	
	/**
	 * CardComparator Object
	 * Allows to sort card by ascending rank enum (Ace high)
	 */
	public static class CardComparatorAceHigh implements Comparator<Card> {
		@Override
		public int compare(Card c1, Card c2) {
			int ace_high = Rank.values().length;
			int r1 = (c1.rank == Rank.A)? ace_high : Rank.A.ordinal();
			int r2 = (c2.rank == Rank.A)? ace_high : Rank.A.ordinal();
			return r1 - r2;
		}
	}
}