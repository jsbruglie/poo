package video_poker;

public class Player {
	int credit;
	int initial_credit;
	Hand hand;
	
	public Player(int credit) {
		this.credit = credit;
		this.initial_credit = credit;
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
	public int getInitial_credit() {
		return initial_credit;
	}
	public void setInitial_credit(int initial_credit) {
		this.initial_credit = initial_credit;
	}
	public Hand getHand() {
		return hand;
	}
	
	
}
