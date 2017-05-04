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
	
	/**
	 * Constructor
	 */
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
	public void execute(Player player, Score score, Strategy strategy, Statistics stats){
		
		// TODO - DEBUG (FORCE CONSOLE)
		System.out.println("Starting new game...");
		
		reader = new Scanner(System.in);
		String command = null;
		State current_state, next_state;
		
		/* State declaration */
		State deal		= new StateDeal("d", new String[]{"b", "s", "$", "q"}, true, false);
		State hold		= new StateHold("h", new String[]{"s", "$", "a"}, true, false);
		State results	= new StateResults(null, new String[]{}, false,false);
		
		/* Declare default state transitions */
		deal.setNextState(hold);
		hold.setNextState(results);
		results.setNextState(deal);
		
		/* Initial state */
		current_state = deal;
		
		while (current_state != null){
			if (current_state.accepts_input){
				command = reader.nextLine();
			} else {
				command = null;
				deck.shuffle();
			}
			next_state = current_state.run(command, player, stats, score, deck);
			current_state = next_state;
		}
		System.out.println("Player has lost (or quited). Exiting...");
	}
	
}