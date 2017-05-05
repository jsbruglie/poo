package state_machine;

import static state_machine.Event.*;
import state_machine.InteractiveIO;
import video_poker.Deck;
import video_poker.DoubleBonus10_7;
import video_poker.Player;
import video_poker.Score;
import video_poker.Statistics;
import video_poker.Strategy;
import video_poker.Variant;

public class StateMachine {
	
	private StateMachineIO io;
	
	private State current_state;
	
	private final State first_bet;
	private final State deal;
	private final State hold;
	private final State results;
	private final State shuffle;
	
	StateMachine(StateMachineIO io){
		
		this.io = io;
		
		/* States */
		
		first_bet	= new State("First Bet", true);
		deal		= new State("Deal", true);
		hold		= new State("Hold", true);
		results		= new State("Results", false, RESULTS);
		shuffle		= new State("Shuffle", false, SHUFFLE);
		
		/* Transitions */
		
		first_bet.addTransition(BET, deal, first_bet);
		first_bet.addTransition(QUIT, null, null);
		
		deal.addTransition(BET, deal, deal);
		deal.addTransition(DEAL, hold, deal);
		deal.addTransition(BALANCE, deal, deal);
		deal.addTransition(STATS, deal, deal);
		deal.addTransition(QUIT, null, null);
		
		hold.addTransition(HOLD, results, hold);
		hold.addTransition(ADVICE, hold, hold);
		hold.addTransition(BALANCE, hold, hold);
		hold.addTransition(STATS, hold, hold);
		
		results.addTransition(RESULTS, shuffle, shuffle);

		shuffle.addTransition(SHUFFLE, deal, deal);
	
		current_state = first_bet;
	}
	
	public void run(Player player, Deck deck, Strategy strategy, Statistics stats, Score score){
		
		while (current_state != null){
			current_state = Commands.execute(current_state, io, player, deck, strategy, stats, score);
		}
	}

	public static void main(String[] args){
		
		StateMachineIO io = new InteractiveIO(true);
		StateMachine sm = new StateMachine(io);
		
		Player player = new Player(100, 5);
		Deck deck = new Deck(true);
		Variant db = new DoubleBonus10_7(100); 
		
		sm.run(player, deck, db.strategy, db.stats, db.score);
	}
}
