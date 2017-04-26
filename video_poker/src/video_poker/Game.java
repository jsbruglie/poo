package video_poker;

public class Game {
	
	/** The game mode */
	Mode mode;
	/** The player */
	Player player;
	/** The score class that evaluates hands and determines payouts */
	Score score;
	/** Basic outcome statistics */
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
		// Create a score instance
		this.score = new Score(new DoubleBonus10_7());
		// Create the statistics for the game
		this.stats = new Statistics(opt.initial_credit);
	}
	
	public void start(){
		this.mode.execute(player, score, stats);
	}
	
	public void end(){
		System.exit(0);
	}
	
}
