package video_poker;

/**
 * Enum that represents a card number
 */
public enum CardNumber {
	
	A("A"),
	n2("2"),
	n3("3"),
	n4("4"),
	n5("5"),
	n6("6"),
	n7("7"),
	n8("8"),
	n9("9"),
	J("J"),
	Q("Q"),
	K("K");
	
	/** Textual description of a card number */
	private final String text;
	
	/**
	 * Private constructor to allow internally set parameters 
	 * @param text The textual description of a card number
	 */
	private CardNumber(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return text;
    }
}