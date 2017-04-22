package video_poker;

public enum Suit {
	Clubs(0,'\u2663'),
	Diamonds(1,'\u2666'),
	Spades(2,'\u2660'),
	Hearts(3,'\u2764');
	
	private Suit(int value, char symbol){
		this.symbol = symbol;
		this.value = value;
	}
	
	private final char symbol;
	private final int value;
	
	public char getSymbol(){
		return symbol;
	}
	public int getValue(){
		return value;
	}
	
}
