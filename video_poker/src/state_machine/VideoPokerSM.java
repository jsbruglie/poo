package state_machine;

import static state_machine.Action.*;
import static state_machine.State.StateName.*;
import video_poker.Deck;
import video_poker.Player;
import video_poker.Score;
import video_poker.Statistics;
import video_poker.Strategy;

/*
 * Generic video poker state machine
 */
public abstract class VideoPokerSM {
	
	/** I/O handler */
	private StateMachineIO io;
	/** Current state of program execution */
	private State current_state;
	
	/* Available States */
	
	/** First bet state */
	protected final State first_bet;
	/** Deal state */
	protected final State deal;
	/** Hold state */
	protected final State hold;
	/** Results state */
	protected final State results;
	/** Shuffle deck state */
	protected final State shuffle;
	
	/**
	 * Constructor
	 * @param io The State Machine I/O Handler
	 */
	protected VideoPokerSM(StateMachineIO io){
		
		this.io = io;
		
		/* States */
		first_bet	= new State(STATE_FIRST_BET, true);
		deal		= new State(STATE_DEAL, true);
		hold		= new State(STATE_HOLD, true);
		results		= new State(STATE_RESULTS, false, RESULTS);
		shuffle		= new State(STATE_SHUFFLE, false, SHUFFLE);
		
		current_state = first_bet;
	}
	
	/**
	 * Defines the state transitions. Must be implemented by subclasses
	 */
	public void defineTransitions(){}
	
	/**
	 * Effectively runs the main state machine loop
	 * @param player The player
	 * @param deck The deck of cards
	 * @param strategy The game strategy
	 * @param stats The game statistics
	 * @param score The game score object to evaluate hands
	 */
	public void run(Player player, Deck deck, Strategy strategy, Statistics stats, Score score){
		
		// A null state corresponds to the end of the program execution
		while (current_state != null){
			current_state = Commands.execute(current_state, io, player, deck, strategy, stats, score);
		}
	}
}