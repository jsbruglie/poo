package video_poker;

/**
 * Represents a player
 */
public class Player {
	
	/** The player's current credit */
	int credit;
	/** The player's hand */
	Hand hand;
	
	/**
	 * Constructor
	 * @param credit The player's initial credit
	 */
	public Player(int credit) {
		this.credit = credit;
	}
	
	public void setHand(Hand hand){
		this.hand = new Hand(hand);
	}
	public void printHand(){
		System.out.println(this.hand);
	}
	
	public void removeCredit(int bet){
		this.credit -= bet;
	}
	public void addCredit(int gain){
		if(gain != -1){
			this.credit += gain;
	
		}
	}	
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public Hand getHand() {
		return hand;
	}
	
	
}
