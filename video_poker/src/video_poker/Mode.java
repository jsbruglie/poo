package video_poker;

/**
 * Interface for specifying different game modes
 */
public interface Mode {
	
	/**
	 * Method that specifies how a given game mode unfolds.
	 * @param player The player
	 * @param score  The score object
	 * @param strategy The strategy object
	 * @param stats The statistics object
	 */
	public abstract void execute(Player player, Score score, Strategy strategy, Statistics stats);
		
}
