package video_poker;

/**
 * Defines a generic game variant properties
 */
public abstract class Variant {

	/** Score class for hand evaluation */
	public Score score;
	/** Strategy for advice and automatic playing */
	public Strategy strategy;
	/** Round outcome statistics */
	public Statistics stats;
}
