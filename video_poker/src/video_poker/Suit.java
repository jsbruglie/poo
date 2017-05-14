package video_poker;

/**
 * Enum for representing a playing card suit
 */
public enum Suit {
	
	/** Clubs \u2663 */
	Clubs("C",'\u2663'),
	/** Diamonds \u2666 */
	Diamonds("D",'\u2666'),
	/** Spades \u2660 */
	Spades("S",'\u2660'),
	/** Hearts \u2764 */
	Hearts("H",'\u2764');
	
	/** The corresponding regular character */
	private final String value;
	/** The corresponding unicode character */
	private final char symbol;
	
	private Suit(String value, char symbol){
		this.value = value;
		this.symbol = symbol;
	}
	
	public char getSymbol(){
		return symbol;
	}
	
	public String getValue(){
		return value;
	}
	
	/**
	 * Return the corresponding suit enum given the textual description
	 * @param text The textual description
	 * @return the corresponding suit enum
	 */
	public static Suit fromString(String text) {
		for (Suit suit : Suit.values()) {
			if (suit.value.equalsIgnoreCase(text)) {
				return suit;
		    }
		}
		return null;
	}
}
