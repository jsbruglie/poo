package video_poker;

import gui.*;
import state_machine.GUIIO;
import state_machine.GUISM;
import state_machine.StateMachineIO;
import state_machine.VideoPokerSM;

public class GUIMode implements Mode{
	
	/** The deck of cards */
	private Deck deck;
	/** The state machine I/O handler */
	StateMachineIO state_machine_io;
	/** The video poker state machine */
	VideoPokerSM state_machine;
	
	/**
	 * Constructor
	 */
	public GUIMode(){
		deck = new Deck(true);
		state_machine_io = new GUIIO();
		state_machine = new GUISM(state_machine_io);
	}
	
	@Override
	public void execute(Player player, Score score, Strategy strategy, Statistics stats) {

		GUI firstGUI = GUI.getGUI();
		firstGUI.prepareInteractiveGUI(player);
		state_machine.run(player, deck, strategy, stats, score);
		
	}
}
