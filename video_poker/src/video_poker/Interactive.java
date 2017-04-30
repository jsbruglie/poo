package video_poker;

import java.util.Scanner;

/**
 * Interactive mode for standard playing
 */
public class Interactive implements Mode {
	
	/** Deck of playing cards */
	private Deck deck;
	/** Scan for command-line input */
	private Scanner reader;
	
	public Interactive(){
		// Create a full deck and shuffle it
		deck = new Deck(true);
	}
	
	/**
	 * Mode execution specification
	 * @param deck The deck of playing cards
	 * @param score The score object for evaluating hands and calculating payouts
	 * @param stats The current game statistics
	 */
	public void execute(Player player, Score score, Statistics stats){
		
		reader = new Scanner(System.in);
		String command = null;
		State current_state, temp_state;
		State sfinal = new StateFinal(null,  new String[]{}, false, true);
		
		State bet = new StateBet( "b", new String[]{"s", "$", "q"} , true, false, sfinal, false);
		State deal = new StateDeal("d", new String[]{"s", "$"}, true, false, false);
		State hold = new StateHold("h", new String[]{"s", "$", "a"}, true, false, false);
		State results = new StateResults(null, new String[]{}, false,false, sfinal, false);

		bet.setNextState(deal); 
		deal.setNextState(hold);
		hold.setNextState(results);
		results.setNextState(bet); //By default the next
		current_state = bet;
		
		while(current_state.isFinal != true){
			
			if (current_state.acceptsInput){
				command = reader.nextLine();
			}else{
				command = "endround";
				// Reinsert all cards in the deck and shuffle it
				deck.shuffle();
			}
			temp_state = current_state.run(command, player, stats, score, deck);
			if(temp_state != null)
				current_state = temp_state;
			
		}
		
		System.out.println("Player has lost (or quited). Exiting...");
	}
	
}