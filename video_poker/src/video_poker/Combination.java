package video_poker;

public class Combination {
	
	/** Textual description of the combination */
	public final String name;
	/** The order in which the combination is checked */
	public final int order;
	/** The payout array */
	private final int[] payout;
	/** The checker class */
	public final CombinationChecker checker;
	
	/**
	 * Constructor
	 * @param name The combination name
	 * @param payout The combination payout entry
	 * @param checker The combination checker class
	 */
	Combination(String name, int order, int[] payout, CombinationChecker checker){
		
		this.name = name;
		if (order < 0){
			 throw new IllegalArgumentException("Combination priority should be > 0");
		}
		this.order = order;
		this.payout = payout;
		this.checker = checker;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + order;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Combination)) {
			return false;
		}
		Combination other = (Combination) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (order != other.order) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}

	/*
	 * Returns the payout for a given bet
	 */
	public int getPayout(int bet){
		
		try{
			return payout[bet-1];
		} catch (IndexOutOfBoundsException e){
			System.err.println("Invalid payout table entry requested.");
			return 0;
		}
	}
}
