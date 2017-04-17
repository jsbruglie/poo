package video_poker;

public class Debug extends Mode {
	
	String cmd_file, card_file;
	
	public Debug(String[] args) {
		super();
		if(args.length == 4){
			//! Read credit
			credit = Integer.parseInt(args[1]);
			//! Read command-file
			cmd_file = args[2];
			//! Read card-file
			card_file = args[3];
		}
	}
	public void execute(Score score, Player player, Deck deck, Statistics stats){
		
	}

}
