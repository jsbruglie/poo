package video_poker;

public enum CombinationDB10_7 {
	
	/** Jacks or Better */
	JacksOrBetter("Jacks or Better"),
	/** Two Pair */
	TwoPair("Two Pair"),
	/** Three of a Kind */
	ThreeOfAKind("Three of a Kind"),
	/** Straight */
	Straight("Straight"),
	/** Flush */
	Flush("Flush"),
	/** Full House */
	FullHouse("Full House"),
	/** Four of a Kind, 2 to 4 */
	Four2_4("Four of a Kind - 2 to 4"),
	/** Four of a Kind, 5 to King */
	Four5_K("Four of a Kind - 5 to K"),
	/** Four Aces */
	FourAces("Four of a Kind - A"),
	/** Straight Flush */
	StraightFlush("Straight Flush"),
	/** Royal Flush */
	RoyalFlush("Royal Flush"),
	/** Other (invalid) combination */
	Other("Other");
	
	/** Textual description of a combination */
	private final String name;
	
	/**
	 * Private constructor to allow internally set parameters
	 * @param name A textual description of the combination
	 */
	private CombinationDB10_7(String name){
		this.name = name;
	}
	
	@Override
    public String toString() {
        return name;
    }
}
