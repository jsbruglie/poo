package video_poker;

import state_machine.InteractiveSM;
import state_machine.SimulationIO;
import state_machine.StateMachineIO;
import state_machine.VideoPokerSM;

/**
 * Simulation Game Mode
 */
public class SimulationMode implements Mode {

	/** Deck of playing cards */
	private Deck deck;
	
	/** Number of deals */
	@SuppressWarnings("unused") // It is used
	private final int nb_deals;
	
	/** State machine IO handler */
	StateMachineIO state_machine_io;
	/** State machine class */
	VideoPokerSM state_machine;
	
	/**
	 * Constructor
	 * @param v The game variant
	 * @param player The player
	 * @param bet The bet to be performed each round
	 * @param nb_deals The desired number of rounds
	 */
	public SimulationMode(Variant v, Player player, int bet, int nb_deals) {
		
		if (!Game.isBetValid(bet)){
			throw new IllegalArgumentException();
		}
		
		this.nb_deals = nb_deals;
		deck = new Deck(true);
		player.setBet(bet);
		
		state_machine_io = new SimulationIO(v.strategy, v.stats, player, nb_deals);
		state_machine = new InteractiveSM(state_machine_io);
	}
	
	@Override
	public void execute(Player player, Score score, Strategy strategy, Statistics stats) {
		state_machine.run(player, deck, strategy, stats, score);
	}
}
