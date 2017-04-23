package video_poker;

public enum Combination {
	
	JacksOrBetter("Jacks or Better"),
	TwoPair("Two Pair"),
	ThreeOfAKind("Three of a Kind"),
	Straight("Straight"),
	Flush("Flush"),
	FullHouse("Full House"),
	FourOfAKind("Four of a Kind"),
	StraightFlush("Straight Flush"),
	RoyalFlush("Royal Flush"),
	Other("Other");
	
	/** Textual description of a combination */
	private final String name;
	
	/**
	 * Private constructor to allow internally set parameters
	 * @param name A textual description of the combination
	 */
	private Combination(String name){
		this.name = name;
	}
	
	@Override
    public String toString() {
        return name;
    }
}
