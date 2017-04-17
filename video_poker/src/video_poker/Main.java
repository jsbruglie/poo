package video_poker;

public class Main {
	
	public static void main(String[] args) {
		Mode mode = null;
		
		if(args[0].equals("-i")){
			mode = new Interactive(args);
		}else if(args[0] == "-d"){
			mode = new Debug(args);
		}else if(args[0] == "-s"){
			mode = new Simulation(args);
		}else{
			System.out.println("USAGE: ");
			System.exit(1);
		}
		//Create game and player
		//Create game (for now has a bet)

		//Create the evaluator
		Score score = new Score();
		//Create player
		Player player = new Player(mode.getCredit());
		//Create deck and shuffle it
		Deck deck = new Deck(52,true); //! 52 cards, shuffle upon creation
		
		//Create the statistics for the game
		Statistics stats = new Statistics(player.getCredit());
		
		mode.execute(score, player, deck, stats);

		System.exit(0);
	}
}
