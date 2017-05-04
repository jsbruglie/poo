package video_poker;

public class Combination {
	
	/** Textual description of the combination */
	public String name;
	/** The payout array */
	private int[] payout;
	/** The checker class */
	public CombinationChecker checker;
	
	/**
	 * Constructor
	 * @param name The combination name
	 * @param payout The combination payout entry
	 * @param checker The combination checker class
	 */
	Combination(String name, int[] payout, CombinationChecker checker){
		
		this.name = name;
		this.payout = new int[payout.length];
		this.checker = checker;
	}
	
	/*
	 * Returns the payout for a given bet
	 */
	public int getPayout(int bet){
		try{
			return payout[bet];
		} catch (IndexOutOfBoundsException e){
			System.err.println("Invalid payout table entry requested.");
			return 0;
		}
	}
}
