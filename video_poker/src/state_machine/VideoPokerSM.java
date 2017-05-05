package state_machine;

import static state_machine.Event.*;
import static state_machine.State.StateName.*;
import state_machine.InteractiveIO;
import video_poker.Deck;
import video_poker.DoubleBonus10_7;
import video_poker.Player;
import video_poker.Score;
import video_poker.Statistics;
import video_poker.Strategy;
import video_poker.Variant;


public abstract class VideoPokerSM {
	
	private StateMachineIO io;
	
	private State current_state;
	
	/* Available States */
	protected final State first_bet;
	protected final State deal;
	protected final State hold;
	protected final State results;
	protected final State shuffle;
	
	VideoPokerSM(StateMachineIO io){
		
		this.io = io;
		
		/* States */
		
		first_bet	= new State(ST_FIRST_BET, true);
		deal		= new State(ST_DEAL, true);
		hold		= new State(ST_HOLD, true);
		results		= new State(ST_RESULTS, false, RESULTS);
		shuffle		= new State(ST_SHUFFLE, false, SHUFFLE);
		
		current_state = first_bet;
	}
	
	public void defineTransitions(){}
	
	public void run(Player player, Deck deck, Strategy strategy, Statistics stats, Score score){
		
		while (current_state != null){
			current_state = Commands.execute(current_state, io, player, deck, strategy, stats, score);
		}
	}
}
