package state_machine;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;
import video_poker.Combination;
import video_poker.Deck;
import video_poker.DeckEmptyException;
import video_poker.InsufficientCreditException;
import video_poker.Player;
import video_poker.Score;
import video_poker.Statistics;
import video_poker.Strategy;

import static state_machine.Event.*;

public class Commands {
	
	public static State execute(State current, StateMachineIO io,
		Player player, Deck deck, Strategy strategy, Statistics stats, Score score){
		
		Event event = null;
		List<String> params = new ArrayList<String>();
		
		if (current.has_input){
			String command = io.input();
			event = Event.fromString(command, params);
		} else if (current.has_default_behaviour) {
			event = current.getDefaultBehaviour();
		}
		
		if (current.valid(event)){
			boolean success = runCommand(event, params, io,
					player, deck, strategy, stats, score);
				
			return current.getEventOutcome(event, success);
		}
		return current;
	}
	
	
	public static boolean runCommand(Event event, List<String> params, StateMachineIO io,
		Player player, Deck deck, Strategy strategy, Statistics stats, Score score){

		if (event != null){
			if (event == BET){
				return bet(params, player, io);
			} else if (event == DEAL){
				return deal(player, deck, io);
			} else if (event == HOLD){
				return hold(params, player, deck, io);
			} else if (event == ADVICE){
				return advice(player, strategy, io);
			} else if (event == STATS){
				return statistics(player, stats, io);
			} else if (event == BALANCE){
				return balance(player, io);
			} else if (event == RESULTS) {
				return results(player, stats, score, io);
			} else if (event == SHUFFLE){
				return shuffle(deck);
			} else if (event == QUIT){
				return quit();
			}
		}
		io.errOut("invalid command");
		return false;
	}
	
	private static boolean bet(List<String> params, Player player, StateMachineIO io){
		
		int new_bet = 0;
		if (params == null || params.isEmpty()){
			new_bet = player.max_bet;
		} else {
			try {
				new_bet = Integer.parseInt(params.get(0));
			} catch (NumberFormatException e){
					io.errOut("b: illegal parameter (non-integer)");
				return false;
			}
		}
		
		if (0 < new_bet && new_bet <= player.max_bet){
			player.setBet(new_bet);
			io.out("player is betting " + player.getBet());
			return true;
		} else {
			io.errOut("b: illegal amount");
		}
		
		return false;
	}
	
	private static boolean deal(Player player, Deck deck, StateMachineIO io){
		
		try{
			player.removeCredit(player.getBet());
		} catch (InsufficientCreditException e){
			// TODO Simulation mode might break this thing into an infinite loop
			io.errOut("Player has insufficient credit to place desired bet.");
			return false;
		}
		
		// Draw 5 cards and add them to the player's hand
		player.setHand(deck.getHand(player.hand_size));
		io.out(player.getHand().toString());
		return true;
	}
	
	private static boolean hold(List<String> params, Player player, Deck deck, StateMachineIO io){
		
		boolean[] hold = new boolean[player.hand_size];
		
		if (params != null) {
			int hold_idx;
			for (int i = 0; i < params.size(); i++){
				try{
					hold_idx = Integer.parseInt(params.get(i));
				} catch (NumberFormatException e){
					io.errOut("h: invalid card held: ");
					return false;
				}
				if (hold_idx < 1 || hold_idx > 5){
					io.errOut("h: nvalid card held: " + hold_idx);
					return false;
				}
				hold[hold_idx - 1] = true;
			}
		}
		
		for (int i = 0; i < player.hand_size; i++){
			if (hold[i] == false){
				// Draw a card from the deck and replace the discarded one
				Card drawn;
				try{
					drawn = deck.draw();
				} catch (DeckEmptyException e){
					io.errOut("The deck has insufficient cards for a draw.");
					return false;
				}
				player.getHand().swapCard(i, drawn);
			}
		}
		io.out(player.getHand().toString());
		return true;
	}
	
	private static boolean advice(Player player, Strategy strategy, StateMachineIO io){
		io.out(strategy.holdAdvice(player.getHand()));
		return true;
	}
	
	private static boolean statistics(Player player, Statistics stats, StateMachineIO io){
		io.out(stats.printStatistics(player.getCredit()));
		return true;
	}
	
	private static boolean balance(Player player, StateMachineIO io){
		io.out("player credit is: " + player.getCredit());
		return true;
	}
	
	private static boolean results(Player player, Statistics stats, Score score, StateMachineIO io){
		Combination comb = score.evaluateHand(player.getHand());
		if (comb != null){
			player.addCredit(comb.getPayout(player.getBet()));
			io.out("player wins with a " + comb.toString().toUpperCase() + " and his credit is " + player.getCredit());
		} else {
			io.out("player loses and his credit is " + player.getCredit());
		}
		if (player.getCredit() == 0){
			return false;
		}
		return true;
	}
	
	private static boolean shuffle(Deck deck) {
		deck.shuffle();
		return true;
	}
	
	private static boolean quit(){
		return true;
	}
}
