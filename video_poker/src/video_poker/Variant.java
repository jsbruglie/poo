package video_poker;

public abstract class Variant {

	/** Score class for hand evaluation */
	public Score score;
	/** Strategy for advice and automatic playing */
	public Strategy strategy;
	/** Round outcome statistics */
	public Statistics stats;
	
	/**
	 * 
	 * @param bet
	 * @return
	 */
	public abstract boolean isBetValid(int bet);
	
	public abstract int getMaxBet();
}
