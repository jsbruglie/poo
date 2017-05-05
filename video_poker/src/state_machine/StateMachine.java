package state_machine;

import static state_machine.Event.*;

public class StateMachine {
	
	private StateMachineIO io;
	
	StateMachine(StateMachineIO io){
		
		this.io = io;
		
		/* States */
		
		State first_bet = new State("First Bet", true);
		State deal		= new State("Deal", true);
		State hold		= new State("Hold", true);
		State results	= new State("Results", false);
		
		/* Transitions */
		
		first_bet.addTransition(BET, deal, first_bet);
		first_bet.addTransition(QUIT, null, null);
		
		deal.addTransition(BET, deal, deal);
		deal.addTransition(DEAL, hold, deal);
		deal.addTransition(BALANCE, deal, deal);
		deal.addTransition(STATS, deal, deal);
		deal.addTransition(QUIT, null, null);
		
		hold.addTransition(HOLD, results, hold);
		hold.addTransition(BALANCE, hold, hold);
		hold.addTransition(STATS, hold, hold);
		
		results.addTransition(AUTO, deal, deal);
	}
}
