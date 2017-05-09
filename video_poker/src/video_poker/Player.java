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
	private int bet = -1;
	/** The size of a player hand */
	public final int hand_size = Game.HAND_SIZE;
	/** The maximum (Default) bet */
	public final int max_bet;
	
	/**
	 * Constructor
	 * @param credit The player's initial credit
	 * @param max_bet The maximum valid bet
	 */
	public Player(int credit, int max_bet) {
		this.credit = credit;
		this.max_bet = max_bet;
	}
	
	/**
	 * Removes a bet from the player's credit
	 * @param bet The amount of credit to be removed
	 * @throws InsufficientCreditException When player has insufficient credit
	 */
	public void removeCredit(int bet) throws InsufficientCreditException {
		if (bet > this.credit){
			throw new InsufficientCreditException();
		}
		this.credit -= bet;
	}
	
	/**
	 * Add credit to the player
	 * @param gain The amount of credit to be added
	 */
	public void addCredit(int gain) {
		if (gain > 0){
			this.credit += gain;
		}
	}
	
	/**
	 * Prints the player's hand
	 */
	public void printHand() {
		System.out.println(this.hand);
	}
	
	/**
	 * @return The player's last bet
	 */
	public int getBet(){
		return this.bet;
	}
	
	/**
	 * @param bet The new bet value
	 */
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	/**
	 * @return The player's credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * @param credit The new credit value
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}

	/**
	 * @return The hand
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * @param hand The new hand value
	 */
	public void setHand(Hand hand) {
		this.hand = hand;
	}	
}
