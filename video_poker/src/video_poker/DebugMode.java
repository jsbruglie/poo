package video_poker;

import java.util.List;

import state_machine.DebugIO;
import state_machine.DebugSM;
import state_machine.StateMachineIO;
import state_machine.VideoPokerSM;
import video_poker.Utils;

public class DebugMode implements Mode {
	
	/** Input files */
	String cmd_file, card_file; 
	/** Deck of playing cards */
	private Deck deck;
		
	/** State machine IO handler */
	StateMachineIO state_machine_io;
	/** State machine class */
	VideoPokerSM state_machine;
	
	
	public DebugMode(String cmd_file, String card_file) {
		this.cmd_file = cmd_file;
		this.card_file = card_file;
		
		List<String> commands = Utils.parseCmdFile(cmd_file);
		Card[][] c = Utils.cardFileParser(this.card_file);
		deck = new Deck(c[0]);
	
		state_machine_io = new DebugIO(commands);
		state_machine = new DebugSM(state_machine_io);
	}
	
	@Override
	public void execute(Player player, Score score, Strategy strategy, Statistics stats) {
		state_machine.run(player, deck, strategy, stats, score);
	}
	
	// TODO - DEBUG
	public static void main(String[] args){
		
		Player player = new Player(10000, 5);
		Variant db = new DoubleBonus10_7(10000); 
		Mode m = new DebugMode("TESTS/cmd-file.txt", "TESTS/card-file.txt");
		m.execute(player, db.score, db.strategy, db.stats);
	}
}
