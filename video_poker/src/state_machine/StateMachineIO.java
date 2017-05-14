package state_machine;

/**
 * Interface for a State Machine I/O Handler.
 * Allows an implementation to specify different
 * reactions to input and output requests.
 * 
 * It makes use of tags to identify the requests.
 */
public interface StateMachineIO {
	
	/**
	 * Outputs a generic message
	 * @param tag Communication tag
	 * @param string The output message
	 */
	void out(Tag tag, String string);

	/**
	 * Outputs an error message
	 * @param tag Communication tag
	 * @param string The output error message
	 */
	void errOut(Tag tag, String string);

	/**
	 * Request input
	 * @param tag Communication tag
	 * @return The requested user input
	 */
	String input(Tag tag);
}
