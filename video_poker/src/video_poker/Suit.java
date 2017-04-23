package video_poker;

/**
 * Enum for representing a playing card suit
 */
public enum Suit {
	
	Clubs(0,'\u2663'),
	Diamonds(1,'\u2666'),
	Spades(2,'\u2660'),
	Hearts(3,'\u2764');
	
	/** An integer representation of the suit */
	private final int value;
	/** The corresponding unicode character */
	private final char symbol;
	
	private Suit(int value, char symbol){
		this.value = value;
		this.symbol = symbol;
	}
	
	public char getSymbol(){
		return symbol;
	}
	public int getValue(){
		return value;
	}
	
}
