package state_machine;

import static state_machine.Action.*;

/**
 * State Machine for Interactive Mode
 */
public class InteractiveSM extends VideoPokerSM{

	/**
	 * Constructor
	 * @param io State Machine I/O handler
	 */
	public InteractiveSM(StateMachineIO io) {
		super(io);
		defineTransitions();
	}
	
	@Override
	public void defineTransitions(){
		
		first_bet.addTransition(BET, deal, first_bet);
		first_bet.addTransition(BALANCE, first_bet, first_bet);
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
	}
}
