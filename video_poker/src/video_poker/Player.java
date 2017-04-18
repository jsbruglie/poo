package video_poker;

public class Player {
	int credit;

	Hand hand;
	
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
		this.credit=this.credit-bet;
	}
	public void addCredit(int gain){
		if(gain!=-1)
			this.credit=this.credit+gain;
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
