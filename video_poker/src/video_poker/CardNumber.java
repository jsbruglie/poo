package video_poker;

/**
 * Enum that represents a card number
 */
public enum CardNumber {
	
	/** Number 2 */
	n2("2"),
	/** Number 3 */
	n3("3"),
	/** Number 4 */
	n4("4"),
	/** Number 5 */
	n5("5"),
	/** Number 6 */
	n6("6"),
	/** Number 7 */
	n7("7"),
	/** Number 8 */
	n8("8"),
	/** Number 9 */
	n9("9"),
	/** Number 10 */
	T("10"),
	/** Jack */
	J("J"),
	/** Queen */
	Q("Q"),
	/** King */
	K("K"),
	/** Ace */
	A("A");
	
	/** Textual description of a card number */
	private final String value;
	
	/**
	 * Private constructor to allow internally set parameters 
	 * @param value The textual description of a card number
	 */
	private CardNumber(final String value){
		this.value = value;
	}
	
	/**
	 * Return the corresponding card number enum given the textual description
	 * @param text The textual description
	 * @return the corresponding card number enum
	 */
	public static CardNumber fromString(String text) {
		for (CardNumber number : CardNumber.values()) {
			if (number.value.equalsIgnoreCase(text)) {
				return number;
		    }
		}
		return null;
	}
	
	@Override
    public String toString() {
        return value;
    }
	
	// TODO - Add conversion mechanism from string to enum, by using text field
}