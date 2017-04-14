package video_poker;

public enum Combination {
	JacksOrBetter(0, "JACKS OR BETTER"),
	TwoPair(1, "TWO PAIR"),
	ThreeOfAKind(2, "THREE OF A KIND"),
	Straight(3, "STRAIGHT"),
	Flush(4, "FLUSH"),
	FullHouse(5, "FULL HOUSE"),
	FourOfAKind(6, "FOUR OF A KIND"),
	StraightFlush(7, "STRAIGHT FLUSH"),
	RoyalFlush(8, "ROYAL FLUSH"),
	Other(9, null);
	
	private final int value;
	private final String name;
	
	private Combination(int value, String name){
		this.value = value;
		this.name = name;
	}
	public int getValue(){
		return value;
	}
	public String getName(){
		return name;
	}
}
