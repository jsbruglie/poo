package video_poker;

import state_machine.InteractiveIO;
import state_machine.InteractiveSM;
import state_machine.StateMachineIO;
import state_machine.VideoPokerSM;

/**
 * Interactive Game Mode
 */
public class InteractiveMode implements Mode {
	
	/** Deck of playing cards */
	private Deck deck;
	
	/** State machine IO handler */
	StateMachineIO state_machine_io;
	/** State machine class */
	VideoPokerSM state_machine;
	
	/**
	 * Constructor
	 */
	public InteractiveMode(){
		deck = new Deck(true);
		state_machine_io = new InteractiveIO();
		state_machine = new InteractiveSM(state_machine_io);
	}
	
	@Override
	public void execute(Player player, Score score, Strategy strategy, Statistics stats) {
		state_machine.run(player, deck, strategy, stats, score);
	}
}
