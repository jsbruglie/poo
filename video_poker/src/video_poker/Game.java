package video_poker;

import gui.GUI;

/**
 * Main game class
 */
public class Game {
	
	/** The game mode */
	Mode mode;
	/** The game variant */
	Variant variant;
	/** The player */
	Player player;
	
	/* Constants */
	
	/** The maximum number of cards that count towards a combination */
	public final static int HAND_SIZE = 5;
	/** The maximum bet a player can perform */
	private final static int MAX_BET = 5;
	
	public Game(String[] args){
		
		Options opt = new Options(args);
		// No options provided.
		if (opt.mode == null){
			System.exit(1);
		}
		
		// Assign game variant
		this.variant = new DoubleBonus10_7(opt.initial_credit);
		// Create player
		this.player = new Player(opt.initial_credit, 5);
	
		// Assign game mode
		if ((opt.mode).equals("Interactive")){
			this.mode = new InteractiveMode();
		} else if ((opt.mode).equals("Debug")){
			this.mode = new DebugMode(opt.cmd_file, opt.card_file);
		} else if ((opt.mode).equals("Simulation")){
			this.mode = new SimulationMode(variant, player, opt.bet, opt.nb_deals);
		} else if ((opt.mode).equals("GUI")){
			int initial_credit = GUI.getInitialCredit();
			this.variant = new DoubleBonus10_7(initial_credit);
			this.player = new Player(initial_credit, MAX_BET);
			this.mode = new GUIMode();
		} else {
			System.exit(-1);
		}
	}
	
	/**
	 * Evaluates if a bet is valid
	 * @param bet The bet number
	 * @return Whether the bet is valid
	 */
	public static boolean isBetValid(int bet){
		if (bet >= 0 && bet <= MAX_BET){
			return true;
		}
		return false;
	}
	
	/**
	 * Starts game execution
	 */
	public void start(){
		this.mode.execute(player, variant.score, variant.strategy, variant.stats);
	}
	
	/**
	 * Ends game execution
	 */
	public void end(){
		System.exit(0);
	}
}
