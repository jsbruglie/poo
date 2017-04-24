package video_poker;

public enum Combination {
	
	JacksOrBetter("Jacks or Better"),
	TwoPair("Two Pair"),
	ThreeOfAKind("Three of a Kind"),
	Straight("Straight"),
	Flush("Flush"),
	FullHouse("Full House"),
	Four2_4("Four of a Kind - 2 to 4"),
	Four5_K("Four of a Kind - 5 to K"),
	FourAces("Four of a Kind - A"),
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
