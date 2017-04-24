package video_poker;

/**
 * Enum for representing a playing card suit
 */
public enum Suit {
	
	/** Clubs \u2663 */
	Clubs('C','\u2663'),
	/** Diamonds \u2666 */
	Diamonds('D','\u2666'),
	/** Spades \u2660 */
	Spades('S','\u2660'),
	/** Hearts \u2764 */
	Hearts('H','\u2764');
	
	/** The corresponding regular character */
	private final char value;
	/** The corresponding unicode character */
	private final char symbol;
	
	private Suit(char value, char symbol){
		this.value = value;
		this.symbol = symbol;
	}
	
	public char getSymbol(){
		return symbol;
	}
	
	// TODO - Add conversion mechanism from string to enum, by using value field
}
