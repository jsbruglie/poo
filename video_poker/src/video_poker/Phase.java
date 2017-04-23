package video_poker;

/**
 * Enum for representing a phase of the game execution
 */
public enum Phase {
	Bet,
	Deal,
	Hold,
	Results;
	
	/**
	 * 
	 * @return The next phase
	 */
	public Phase next() {
        return values()[(ordinal() + 1) % values().length];
    }
}
