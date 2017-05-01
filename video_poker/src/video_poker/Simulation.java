package video_poker;

public class Simulation implements Mode {
	
	/** */
	int bet = 0;
	/** */
	int nb_deals = 0;
	/** Deck of playing cards */
	private Deck deck;
	
	/**
	 * Constructor
	 * @param bet
	 * @param nb_deals
	 */
	public Simulation(int bet, int nb_deals) {
		this.bet = bet;
		this.nb_deals = nb_deals;
		deck = new Deck(true);
	}
	
	public void execute(Player player, Score score, Statistics stats) {
		
		State current_state, next_state;
		
		/* State declaration */
		State bet		= new StateBet( "b", new String[]{"s", "$", "q"} , true, true);
		State deal		= new StateDeal("d", new String[]{"s", "$"}, true, true);
		State hold		= new StateHold("h", new String[]{"s", "$", "a"}, true, true);
		State results	= new StateResults(null, new String[]{}, false, true);
		
		/* Declare default state transitions */
		bet.setNextState(deal); 
		deal.setNextState(hold);
		hold.setNextState(results);
		results.setNextState(bet);
		
		/* Initial state */
		current_state = bet;
		
		String[] commands = new String[]{"b", "d", "h", null};
		
		System.out.println(nb_deals);
		System.out.println(this.bet);
		
		for (int i = 0; i < nb_deals; i++){
			for (int j = 0; j < commands.length; j++){
				if (current_state == null){
					System.out.println("Player ran out of money!");
					// TODO Make this hacky thing prettier
					i = nb_deals; break;
				}
				else {
					if (commands[j] == null){
						// TODO Maybe put this inside results
						// Shuffle in results step
						deck.shuffle();
					}
					next_state = current_state.run(commands[j], player, stats, score, deck);
					current_state = next_state;
				}
			}
		}
		
		/** Show statistics at the end! */
		stats.printStatistics(player.getCredit());
	}
}