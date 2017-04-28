package video_poker;

public class Simulation implements Mode {
	int bet, nb_deals = 0;
	/** Deck of playing cards */
	private Deck deck;
	
	public Simulation(int bet, int nb_deals) {
		this.bet = bet;
		this.nb_deals = nb_deals;
		deck = new Deck(true);
	}
	
	public void execute(Player player, Score score, Statistics stats) {
		State current_state, temp_state;
		State sfinal = new StateFinal(null,  new String[]{}, false, true);
		
		State results = new StateResults(null, new String[]{}, false,false, sfinal, true); //Last parameter is if we are running a simulation
		State bet = new StateBet( "b", new String[]{"s", "$", "q"} , true, false, sfinal);
		State deal = new StateDeal("d", new String[]{"s", "$"}, true, false);
		State hold = new StateHold("h", new String[]{"s", "$", "a"}, true, false, true); //Last parameter is if we are running a simulation

		bet.setNextState(deal); 
		deal.setNextState(hold);
		hold.setNextState(results);
		results.setNextState(bet); //By default the next
		current_state = bet;
		
		String command = null;
		
		String[] commands = new String[]{"b", "d", "h", "endround"};
		
		for(int i=0; i<nb_deals; i++){
			for(int j=0; j < commands.length; j++){
				temp_state = current_state.run(command, player, stats, score, deck);
				if(temp_state != null)
					current_state = temp_state;
			}
		}
		
	}
	
	


	
}
