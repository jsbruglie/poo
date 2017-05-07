package video_poker;

import gui.*;
import state_machine.GUIIO;
import state_machine.GUISM;
import state_machine.StateMachineIO;
import state_machine.VideoPokerSM;

public class GUIMode implements Mode{

	private Deck deck;
	
	StateMachineIO state_machine_io;
	
	VideoPokerSM state_machine;
	
	public GUIMode(){
		deck = new Deck(true);
		//state_machine_io = new GUIIO();
		state_machine_io = new GUIIO();
		state_machine = new GUISM(state_machine_io);
	}
	
	@Override
	public void execute(Player player, Score score, Strategy strategy, Statistics stats) {
		// TODO Auto-generated method stub
		
		GUI firstGUI = GUI.getGUI();
		firstGUI.prepareInteractiveGUI(player);
		state_machine.run(player, deck, strategy, stats, score);
		
	}

}
