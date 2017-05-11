package state_machine;

import java.util.ArrayList;
import java.util.List;

import combinations.Combination;
import video_poker.Card;
import video_poker.Deck;
import video_poker.DeckEmptyException;
import video_poker.Game;
import video_poker.InsufficientCreditException;
import video_poker.Player;
import video_poker.Score;
import video_poker.Statistics;
import video_poker.Strategy;

import static state_machine.State.StateName.*;
import static state_machine.Action.*;
import static state_machine.Tag.*;

/**
 * Defines static methods for each available command
 */
public class Commands {
	
	/**
	 * Executes a given command and returns the resulting state
	 * @param current The current state
	 * @param io The state machine I/O handler
	 * @param player The player
	 * @param deck The deck of cards
	 * @param strategy The employed strategy
	 * @param stats The game statistics
	 * @param score The score class for evaluating hands and respective pay-outs
	 * @return The resulting state after performing the given command
	 */
	public static State execute(State current, StateMachineIO io,
		Player player, Deck deck, Strategy strategy, Statistics stats, Score score){
		
		Action action = null;
		List<String> params = new ArrayList<String>();
		
		if (current.accepts_input){
			
			Tag tag = null;
			if (current.name == STATE_FIRST_BET){
				tag = In_Bet;
			} else if (current.name == STATE_DEAL){
				tag = In_Deal;
			} else if(current.name == STATE_HOLD){
				tag = In_Hold;
			} else {
				tag = Error;
			}
			String command = io.input(tag);
			action = Action.fromString(command, params);
			
		} else if (current.has_default_behaviour) {
			action = current.getDefaultBehaviour();
		}
		
		if (current.isValid(action)){
			boolean success = runCommand(action, params, io,
					player, deck, strategy, stats, score);

			return current.getEventOutcome(action, success);
		}
		io.errOut(Error, "invalid command");
		return current;
	}
	
	/**
	 * Runs a command corresponding to the desired action
	 * @param action The action descriptor
	 * @param params The provided command parameters
	 * @param io The state machine I/O handler
	 * @param player The player
	 * @param deck The deck of cards
	 * @param strategy The employed strategy
	 * @param stats The game statistics
	 * @param score The score class for evaluating hands and respective pay-outs
	 * @return Whether the command was successfully executed
	 */
	public static boolean runCommand(Action action, List<String> params, StateMachineIO io,
		Player player, Deck deck, Strategy strategy, Statistics stats, Score score){

		if (action != null){
			if (action == BET){
				return bet(params, player, io);
			} else if (action == DEAL){
				return deal(player, deck, io);
			} else if (action == HOLD){
				return hold(params, player, deck, io);
			} else if (action == ADVICE){
				return advice(player, strategy, io);
			} else if (action == STATS){
				return statistics(player, stats, io);
			} else if (action == BALANCE){
				return balance(player, io);
			} else if (action == RESULTS) {
				return results(player, stats, score, io);
			} else if (action == SHUFFLE){
				return shuffle(deck);
			} else if (action == QUIT){
				return quit();
			}
		}
		io.errOut(Error, "invalid command");
		return false;
	}
	
	/**
	 * Changes the player bet
	 * @param params The bet value
	 * @param player The player
	 * @param io The state machine I/O handler
	 * @return Whether the bet was successfully changed
	 */
	private static boolean bet(List<String> params, Player player, StateMachineIO io){
		
		int new_bet = 0;
		if (params == null || params.isEmpty()){
			new_bet = player.max_bet;
		} else {
			try {
				new_bet = Integer.parseInt(params.get(0));
			} catch (NumberFormatException e){
				io.errOut(Error, "b: illegal parameter (non-integer)");
				return false;
			}
		}
		
		if (Game.isBetValid(new_bet) && new_bet <= player.getCredit()){
			player.setBet(new_bet);
			io.out(Out_Bet, "player is betting " + player.getBet());
			return true;
		} else {
			io.errOut(Error, "b: illegal amount");
		}
		
		return false;
	}
	
	/**
	 * Deals a new hand and removes the player bet from his credit
	 * @param player The player
	 * @param deck The deck of cards
	 * @param io The state machine I/O handler
	 * @return Whether a new hand was successfully dealt
	 */
	private static boolean deal(Player player, Deck deck, StateMachineIO io){
		
		try{
			player.removeCredit(player.getBet());
		} catch (InsufficientCreditException e){
			io.errOut(Error, "Player has insufficient credit to place desired bet.");
			return false;
		}
		
		// Draw 5 cards and add them to the player's hand
		player.setHand(deck.getHand(player.hand_size));
		try {
			io.out(Out_Deal, player.getHand().toString());
		} catch (NullPointerException e) {
			System.out.println("Failed to get a hand - card file is probably empty or not enough cards. Exiting...");
			System.exit(-1);
		}
		
		return true;
	}
	
	/**
	 * Determines which cards to be held and discards the rest
	 * @param params The cards to be held
	 * @param player The player
	 * @param deck The deck of cards
	 * @param io The state machine I/O handler
	 * @return Whether the command was successful
	 */
	private static boolean hold(List<String> params, Player player, Deck deck, StateMachineIO io){
		
		boolean[] hold = new boolean[player.hand_size];
		
		if (params != null) {
			int hold_idx;
			for (int i = 0; i < params.size(); i++){
				try{
					hold_idx = Integer.parseInt(params.get(i));
				} catch (NumberFormatException e){
					io.errOut(Error, "h: invalid card held.");
					return false;
				}
				if (hold_idx < 1 || hold_idx > Game.HAND_SIZE){
					io.errOut(Error, "h: nvalid card held: " + hold_idx);
					return false;
				}
				hold[hold_idx - 1] = true;
			}
		}
		
		for (int i = 0; i < player.hand_size; i++){
			if (hold[i] == false){
				// Draw a card from the deck and replace the discarded one
				Card drawn = null;
				try{
					drawn = deck.draw();
				} catch (DeckEmptyException e){
					io.errOut(Error, "The deck has insufficient cards for a draw.");
					System.exit(-1);
				}
				player.getHand().swapCard(i, drawn);
			}
		}
		io.out(Out_Hold, player.getHand().toString());
		return true;
	}
	
	/**
	 * Returns advice on which cards to be dealt
	 * @param player The player
	 * @param strategy The employed strategy
	 * @param io The state machine I/O handler
	 * @return The advice specifying the index of the cards to be held
	 */
	private static boolean advice(Player player, Strategy strategy, StateMachineIO io){
		io.out(Out_Advice, strategy.holdAdvice(player.getHand()));
		return true;
	}
	
	/**
	 * Returns a string with the game statistics
	 * @param player The player
	 * @param stats The game statistics
	 * @param io The state machine I/O handler
	 * @return The game statistics
	 */
	private static boolean statistics(Player player, Statistics stats, StateMachineIO io){
		io.out(Out_Stats, stats.printStatistics(player.getCredit()));
		return true;
	}
	
	/**
	 * Returns a string with the player's balance
	 * @param player The player
	 * @param io The state machine I/O handler
	 * @return The player's balance
	 */
	private static boolean balance(Player player, StateMachineIO io){
		io.out(Out_Balance, "player credit is: " + player.getCredit());
		return true;
	}
	
	/**
	 * Calculates the outcome of a round.
	 * Can trigger a game over if the player's credit reaches 0
	 * @param player The player
	 * @param stats The game statistics
	 * @param score The 
	 * @param io The state machine I/O handler
	 * @return The result of a round
	 */
	private static boolean results(Player player, Statistics stats, Score score, StateMachineIO io){
		Combination comb = score.evaluateHand(player.getHand());
		if (comb != null){
			player.addCredit(comb.getPayout(player.getBet()));
			io.out(Out_Results, "player wins with a " + comb.toString().toUpperCase() + " and his credit is " + player.getCredit());
		} else {
			io.out(Out_Results, "player loses and his credit is " + player.getCredit());
		}
		
		// Add the result to statistics
		stats.addResults(comb);		
		
		if (player.getCredit() == 0){
			io.out(Out_GameOver, "player ran out of credit");
			System.exit(0);
		}
		return true;
	}
	
	/**
	 * Shuffles the deck of cards
	 * @param deck The deck of cards
	 * @return True
	 */
	private static boolean shuffle(Deck deck) {
		deck.shuffle();
		return true;
	}
	
	/**
	 * Used to signal the program that the player has quit
	 * @return True
	 */
	private static boolean quit(){
		return true;
	}
}
