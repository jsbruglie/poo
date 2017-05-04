package video_poker;

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
	 */
	void printStatistics(int player_current_credit);
}
