package video_poker;

import state_machine.InteractiveIO;
import state_machine.InteractiveSM;
import state_machine.StateMachineIO;
import state_machine.VideoPokerSM;

public class InteractiveMode implements Mode {
	
	/** Deck of playing cards */
	private Deck deck;
	
	/** State machine IO handler */
	StateMachineIO state_machine_io;
	/** State machine class */
	VideoPokerSM state_machine;
	
	public InteractiveMode(boolean terminal){
		deck = new Deck(true);
		state_machine_io = new InteractiveIO();
		state_machine = new InteractiveSM(state_machine_io);
	}
	
	@Override
	public void execute(Player player, Score score, Strategy strategy, Statistics stats) {
		state_machine.run(player, deck, strategy, stats, score);
	}
	
	// TODO - DEBUG
	public static void main(String[] args){
		
		Player player = new Player(10000, 5);
		Variant db = new DoubleBonus10_7(10000); 
		Mode m = new InteractiveMode(true);
		m.execute(player, db.score, db.strategy, db.stats);
	}
}
