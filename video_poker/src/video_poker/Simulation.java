package video_poker;

public class Simulation extends Mode {
	
	int bet_value, number_deals = 0;
	
	public Simulation(String[] args) {
		super();
		if(args.length == 4){
			//! Read credit
			credit = Integer.parseInt(args[1]);
			//! Read bet value
			bet_value = Integer.parseInt(args[2]);
			//Read number of deals
			number_deals = Integer.parseInt(args[3]);
		}
	}
	public void execute(Score score, Player player, Deck deck, Statistics stats){
		
	}
	
}
