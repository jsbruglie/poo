package video_poker;

/**
 * Enum that represents a card number
 */
public enum CardNumber {
	
	/** Number 2 */
	n2('2'),
	/** Number 3 */
	n3('3'),
	/** Number 4 */
	n4('4'),
	/** Number 5 */
	n5('5'),
	/** Number 6 */
	n6('6'),
	/** Number 7 */
	n7('7'),
	/** Number 8 */
	n8('8'),
	/** Number 9 */
	n9('9'),
	/** Number 10 */
	T('T'),
	/** Jack */
	J('J'),
	/** Queen */
	Q('Q'),
	/** King */
	K('K'),
	/** Ace */
	A('A');
	
	/** Textual description of a card number */
	private final char text;
	
	/**
	 * Private constructor to allow internally set parameters 
	 * @param text The textual description of a card number
	 */
	private CardNumber(final char text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return "" + text;
    }
	
	// TODO - Add conversion mechanism from string to enum, by using text field
}