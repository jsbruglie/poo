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
		
		// Assign game variant
		this.variant = new DoubleBonus10_7(opt.initial_credit);
		// Create player
		this.player = new Player(opt.initial_credit, variant.getMaxBet());
	
		// Assign game mode
		if ((opt.mode).equals("Interactive")){
			this.mode = new InteractiveMode(true);
		} else if ((opt.mode).equals("Debug")){
			this.mode = new DebugMode(opt.cmd_file, opt.card_file);
		} else if ((opt.mode).equals("Simulation")){
			this.mode = new SimulationMode(variant, player, opt.bet, opt.nb_deals);
		} else if ((opt.mode).equals("GUI")){
			this.mode = new GUIMode();
		} else {
			System.exit(-1);
		}
	}
	
	public void start(){
		this.mode.execute(player, variant.score, variant.strategy, variant.stats);
	}
	
	public void end(){
		System.exit(0);
	}
	
}
