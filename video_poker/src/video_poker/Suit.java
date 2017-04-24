package video_poker;

/**
 * Enum for representing a playing card suit
 */
public enum Suit {
	
	Clubs('C','\u2663'),
	Diamonds('D','\u2666'),
	Spades('S','\u2660'),
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
}
