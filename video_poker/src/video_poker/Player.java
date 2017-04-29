package video_poker;

/**
 * Represents a player
 */
public class Player {
	
	/** The player's current credit */
	private int credit;
	/** The player's hand */
	private Hand hand;
	/** Last bet */
	private int bet = 5;
	/** The size of a player hand */
	final int hand_size = 5;
	/**
	 * Constructor
	 * @param credit The player's initial credit
	 */
	public Player(int credit) {
		this.credit = credit;
	}
	
	public void removeCredit(int bet) throws InsufficientCreditException {
		if (bet > this.credit){
			throw new InsufficientCreditException();
		}
		this.credit -= bet;
	}
	public void addCredit(int gain) {
		if(gain != -1){
			this.credit += gain;
		}
	}
	
	public int getBet(){
		return this.bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	public void printHand() {
		System.out.println(this.hand);
	}

	/**
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}

	/**
	 * @return the hand
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * @param hand the hand to set
	 */
	public void setHand(Hand hand) {
		this.hand = hand;
	}	
}
