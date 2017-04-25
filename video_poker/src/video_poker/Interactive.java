package video_poker;

import java.util.Scanner;

/**
 * Interactive mode for standard playing
 */
public class Interactive implements Mode {
	
	/** Deck of playing cards */
	private Deck deck;
	/** Last bet */
	private int bet;
	/** Current game mode phase */
	private Phase phase;
	/** Scan for command-line input */
	private Scanner reader;
	/** Whether the game is over */
	private boolean game_over;
	/** The size of a player hand */
	private final int hand_size = 5;
	
	public Interactive(){
		// Create a full deck and shuffle it
		deck = new Deck(true);
		// If no previous bet is set, the maximum bet is chosen
		bet = 5;
		game_over = false;
	}
	
	/**
	 * Mode execution specification
	 * @param deck The deck of playing cards
	 * @param score The score object for evaluating hands and calculating payouts
	 * @param stats The current game statistics
	 */
	public void execute(Player player, Score score, Statistics stats){
		
		phase = Phase.Bet;
		reader = new Scanner(System.in);
		String command = null;
		
		while(!game_over){	
			if (phase != Phase.Results){
				command = reader.nextLine();
			}
			
			if (phase == Phase.Bet){
				betState(command, player, stats);
			}else if (phase == Phase.Deal){
				dealState(command, player, stats);
			}else if (phase == Phase.Hold){
				holdState(command, player, stats);
			}else if (phase == Phase.Results){
				resultsState(player, stats, score);
				if (player.getCredit() == 0){
					game_over = true;
				}
			}
		}
		
		System.out.println("Player has lost. Exiting...");
	}
	
	/**
	 * Behaviour in bet state
	 * @param command The input command
	 * @param player The current player
	 * @param stats The current game statistics
	 */
	private void betState(String command, Player player, Statistics stats){
		
		String[] tokens = command.split(" ");
		if (tokens[0].equals("b")){
			if (tokens.length == 1 || tokens.length == 2){
				
				if (tokens.length == 2){
					int new_bet = Integer.parseInt(tokens[1]);
					if (0 < new_bet && new_bet <= 5){
						bet = new_bet;
					}else{
						System.out.println("b: illegal amount");
						return;
					}
				}
				
				try{
					player.removeCredit(bet);
				}catch (Exception InsufficientCreditException){
					System.out.println("Player has insufficient credit.");
					return;
				}
				
				System.out.println("player is betting " + bet);
				phase = phase.next();
				
			}else{
				System.out.println("Please bet to start game");
			}
		}else if (tokens[0].equals("s")){
			stats.printStatistics(player.getCredit());
		}else if (tokens[0].equals("$")){
			System.out.println("player credit is: " + player.getCredit());
		}else{
			System.out.println(tokens[0] + ": illegal command");
		}
	}
	
	/**
	 * Behaviour in deal state
	 * 
	 * During this phase, the player is given a hand of cards from the
	 * top of the deck.
	 * 
	 * @param command The input command
	 * @param player The current player
	 * @param stats The current game statistics
	 */
	private void dealState(String command, Player player, Statistics stats){
		
		if(command.equals("d")){
			// Draw 5 cards and add them to the player's hand
			player.setHand(deck.getHand(hand_size));
			
			player.printHand();
			phase = phase.next();
		
		}else if(command.equals("$")){
			System.out.println("Player credit is: " + player.getCredit());
		}else if(command.equals("s")){
			stats.printStatistics(player.getCredit());
		}else{
			System.out.println(command + ": illegal command");
		}
	}
	
	/**
	 * Behaviour in hold state
	 * 
	 * During this phase the player can decide to hold certain 
	 * cards and discard the remaining in hand.
	 * 
	 * @param command The input command
	 * @param player The current player
	 * @param stats The current game statistics
	 */
	private void holdState(String command, Player player, Statistics stats){
		
		String[] tokens = command.split(" ");
		int index = -1;
		
		if (tokens[0].equals("h")){
			
			boolean hold[] = new boolean[5];
			
			// If the player desires to hold certain cards (discarding others)
			if (tokens.length >= 1 && tokens.length <= 5){
				//Check if all tokens are valid numbers between 1-5 inclusive
				for (int i = 1; i < tokens.length; i++){
					try{
						index = Integer.parseInt(tokens[i]);
					} catch (NumberFormatException e){
						System.err.println("Invalid card held: " + index);
						return;
					}
					if (index < 1 || index > 5){
						System.out.println("Invalid card held: " + index);
						return;
					}
					hold[index - 1] = true;
				}
				
				for (int i = 0; i < 5; i++){
					if (! hold[i]){
						// Draw a card from the deck and replace the discarded one
						Card drawn;
						try{
							drawn = deck.draw();
						} catch (DeckEmptyException e){
							System.err.println("The deck has insufficient cards for a draw.");
							return;
						}
						player.getHand().swapCard(i, drawn);
					}
				}		
			}
			// Print the resulting hand
			player.printHand();
			phase = phase.next();
				
		}else if(command.equals("$")){
			System.out.println("Player credit is: " + player.getCredit());
		}else if(command.equals("a")){
			// Advice
		}else if(command.equals("s")){
			stats.printStatistics(player.getCredit());
		}else{
			System.out.println(command + ": illegal command");
		}
	}
	
	/**
	 * Behaviour in results state
	 * 
	 * During this phase the final combination of cards is determined
	 * and the payout calculated and given to the player.
	 * The cards are returned to the deck and it is shuffled.
	 * 
	 * @param player The current player
	 * @param stats The current game statistics
	 * @param score The score evaluator class	
	 */
	private void resultsState(Player player, Statistics stats, Score score){
		
		// Get the combination corresponding to the current player hand
		Combination comb = score.evaluateHand(player.getHand());
		// Get the score corresponding to the player's hand
		player.addCredit(score.getScore(comb, bet));
		
		// Show result
		if(comb == Combination.Other){
			System.out.println("player loses and his credit is " + player.getCredit());
		}else{
			System.out.println("player wins with a " + comb.toString().toUpperCase() + " and his credit is " + player.getCredit());
		}
		
		// Add the result to statistics
		stats.addResults(comb);		
		// Reinsert all cards in the deck and shuffle it
		deck.shuffle();
		
		phase = phase.next();
	}
}
