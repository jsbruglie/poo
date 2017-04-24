package video_poker;

public class Game {
	
	Mode mode;
	Score score;
	Player player;
	Deck deck;
	Statistics stats;
	
	public Game(String[] args){
		
		int initial_credit = 30;
		
		//Double bonus game
		
		if(args[0].equals("-i")){
			mode = new Interactive();
		}else if(args[0] == "-d"){
			mode = new Debug();
		}else if(args[0] == "-s"){
			mode = new Simulation();
		}else{
			System.out.println("USAGE: ");
			System.out.println("Interactive Mode: ");
			System.out.println("Debug Mode: ");
			System.out.println("Simulation Mode: ");
			
			System.exit(1);
		}
		// Create game and player
		// Create game (for now has a bet)

		// Create player
		this.player = new Player(initial_credit);
		// Create deck and shuffle it
		this.deck = new Deck(true);
		// Create the statistics for the game
		this.stats = new Statistics(initial_credit);
	}
	
	public void start(){
		this.mode.execute(player, deck, stats);
	}
	
	public void end(){
		System.exit(0);
	}
	
}
