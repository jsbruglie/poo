package video_poker;

import state_machine.InteractiveSM;
import state_machine.SimulationIO;
import state_machine.StateMachineIO;
import state_machine.VideoPokerSM;

public class SimulationMode implements Mode {

	/** Deck of playing cards */
	private Deck deck;
	
	/** Number of deals */
	private final int nb_deals;
	
	/** State machine IO handler */
	StateMachineIO state_machine_io;
	/** State machine class */
	VideoPokerSM state_machine;
	
	
	public SimulationMode(Variant v, Player player, int bet, int nb_deals) {
		
		if (bet <= 0 || bet > 5){
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
	
	// TODO - DEBUG
	public static void main(String[] args){
		
		Player player = new Player(10000, 5);
		Variant db = new DoubleBonus10_7(10000); 
		Mode m = new SimulationMode(db, player, 5, 100000);
		m.execute(player, db.score, db.strategy, db.stats);
	}
}
