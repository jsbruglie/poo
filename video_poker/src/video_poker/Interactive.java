package video_poker;

import java.util.Scanner;

/**
 * Interactive mode for standard playing
 */
public class Interactive implements Mode {
	
	/** Last bet */
	private int bet;
	/** Current game mode phase */
	private Phase phase;
	/** Scan for command-line input */
	private Scanner reader;
	/** Whether the game is over */
	private boolean game_over;
	
	public Interactive(){
		// If no previous bet is set, the maximum bet is chosen
		bet = 5;
		game_over = false;
	}
	
	public void execute(Score score, Player player, Deck deck, Statistics stats){
		
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
				dealState(command, deck, player, stats);
			}else if (phase == Phase.Hold){
				holdState(command, deck, player, stats);
			}else if (phase == Phase.Results){
				resultsState(player, deck, stats, score);
				if (player.getCredit() == 0){
					game_over = true;
				}
			}
		}
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
						System.out.println("Please introduce a bet between 1 and 5.");
						return;
					}
				}
				
				try{
					player.removeCredit(bet);
				}catch (Exception InsufficientCreditException){
					System.out.println("Player has insufficient credit.");
					return;
				}
				
				phase = phase.next();
				
			}else{
				System.out.println("Please bet to start game");
			}
		}else if (tokens[0].equals("s")){
			stats.printStatistics(player.getCredit());
		}else if (tokens[0].equals("$")){
			System.out.println("Player credit is: " + player.getCredit());
		}else{
			System.out.println(tokens[0] + ": illegal command");
		}
	}
	
	/**
	 * 
	 * @param command
	 * @param deck
	 * @param player
	 * @param stats
	 */
	private void dealState(String command, Deck deck, Player player, Statistics stats){
		
		if(command.equals("d")){
			// Draw 5 cards and add them to the player's hand
			Card[] c = deck.getHand();
			Hand h = new Hand(c, 5);
			player.setHand(h);
			
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
	 * 
	 * @param command
	 * @param deck
	 * @param player
	 * @param stats
	 */
	private void holdState(String command, Deck deck, Player player, Statistics stats){
		
		String[] tokens = command.split(" ");
		if(tokens[0].equals("h")){
			
			boolean hold[] = new boolean[5];
			
			// If the player desires to hold certain cards (discarding others)
			if(tokens.length > 1 && tokens.length <= 5){
				//Check if all tokens are valid numbers between 1-5 inclusive
				for(int i = 1; i < tokens.length; i++){
					int index = Integer.parseInt(tokens[i]);
					if(index < 1 || index > 5){
						System.out.println("Invalid card held: " + index);
						return;
					}
					hold[index] = true;
				}
				
				for(int i = 1; i < 5; i++){
					if (hold[i]){
						// Draw a card from the deck and replace the discarded one
						Card drawn;
						try{
							drawn = deck.draw();
						}catch (DeckEmptyException e){
							System.err.println("The deck has insufficient cards for a draw.");
							return;
						}
						player.getHand().swapCard(i, drawn);
					}
				}		
			}
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
	 * 
	 * @param player
	 * @param deck
	 * @param stats
	 * @param score
	 */
	private void resultsState(Player player, Deck deck, Statistics stats, Score score){
		
		// Get the combination corresponding to the current player hand
		Combination comb = score.getCombination(player.getHand());
		// Get the score corresponding to the player's hand
		player.addCredit(score.getScore(comb, bet));
		
		//Show result
		if(comb == Combination.Other){
			System.out.println("Player loses and his credit is " + player.getCredit());
		}else{
			System.out.println("Player wins with a " + comb.getName() + " and his credit is " + player.getCredit());
		}
		
		//Add the result to statistics
		stats.addResults(comb);		
		//Reinsert all cards in the deck + Shuffle deck at the end
		deck.shuffle();
		//Ready for a new round
		phase = Phase.Bet; //Go back to first phase
	}
}
