package video_poker;

public class Game {
	
	/** The game mode */
	Mode mode;
	/** The game variant */
	Variant variant;
	/** The player */
	Player player;
	
	public Game(String[] args){
		
		Options opt = new Options(args);
		
		if (opt.mode == null){
			System.exit(1);
		}
		
		// Assign game mode
		this.mode = opt.mode;
		// Assign game variant
		this.variant = new DoubleBonus10_7(opt.initial_credit);
		// Create player
		this.player = new Player(opt.initial_credit);
	}
	
	public void start(){
		this.mode.execute(player, variant.score, variant.strategy, variant.stats);
	}
	
	public void end(){
		System.exit(0);
	}
	
}
