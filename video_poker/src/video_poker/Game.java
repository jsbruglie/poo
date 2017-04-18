package video_poker;

public class Game {
	
	Mode mode;
	Score score;
	Player player;
	Deck deck;
	Statistics stats;
	
	public Game(String[] args){
		
		if(args[0].equals("-i")){
			mode = new Interactive(args);
		}else if(args[0] == "-d"){
			mode = new Debug(args);
		}else if(args[0] == "-s"){
			mode = new Simulation(args);
		}else{
			System.out.println("USAGE: ");
			System.out.println("Interactive Mode: ");
			System.out.println("Debug Mode: ");
			System.out.println("Simulation Mode: ");
			
			System.exit(1);
		}
		//Create game and player
		//Create game (for now has a bet)

		//Create the evaluator
		this.score = new Score();
		//Create player
		this.player = new Player(mode.getCredit());
		//Create deck and shuffle it
		this.deck = new Deck(52,true); //! 52 cards, shuffle upon creation
		
		//Create the statistics for the game
		this.stats = new Statistics(player.getCredit());
	}
	
	public void start(){
		this.mode.execute(score, player, deck, stats);
	}
	
	public void end(){
		System.exit(0);
	}
	
}
