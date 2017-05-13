package video_poker;

import combinations.Combination;

/**
 * Statistics class for keeping track of hand occurrences and displaying relevant stats
 */
public interface Statistics {

	/**
	 * Adds a round result in an occurrence table
	 * @param combination The final combination
	 */
	void addResults(Combination combination);
	
	/**
	 * Prints the current statistics
	 * @param player_current_credit The player's current credit
	 * @return The statistics table formatted in a string
	 */
	String printStatistics(int player_current_credit);
	public float getFinal_statistics();
}
