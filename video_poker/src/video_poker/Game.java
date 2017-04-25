package video_poker;

public class Game {
	
	Mode mode;
	Score score;
	Player player;
	Statistics stats;
	
	public Game(String[] args){
		
		Options opt = new Options(args);
		
		if (opt.mode == null){
			System.exit(1);
		}
		
		// Assign game mode
		this.mode = opt.mode;
		// Create player
		this.player = new Player(opt.initial_credit);
		// Create the statistics for the game
		this.stats = new Statistics(opt.initial_credit);
	}
	
	public void start(){
		this.mode.execute(player, stats);
	}
	
	public void end(){
		System.exit(0);
	}
	
}
