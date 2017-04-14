package video_poker;

public enum Suit {
	Clubs('\u2663'),
	Diamonds('\u2666'),
	Spades('\u2660'),
	Hearts('\u2764');
	
	private Suit(char symbol){
		this.symbol = symbol;
	}
	
	private final char symbol;
	
	public char getSymbol(){
		return symbol;
	}
}
