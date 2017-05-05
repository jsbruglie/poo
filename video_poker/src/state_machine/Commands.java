package state_machine;

import java.util.ArrayList;
import java.util.List;

import video_poker.Deck;
import video_poker.InsufficientCreditException;
import video_poker.Player;
import video_poker.Statistics;

import static state_machine.Event.*;

public class Commands {
	
	Commands(){
		
	}
	
	public static boolean runCommand(String command, State current, StateMachineIO io,
			Player player, Deck deck){
		
		Event event;
		List<String> params = new ArrayList<String>();
		
		event = Event.fromString(command, params);
		
		if (event != null){
			if (current.valid(event)){
				if (event == BET){
					return bet(params, player, io);
				} else if (event == DEAL){
					return deal(player, deck, io);
				} else if (event == HOLD){
					return hold(params, player, io);
				} else if (event == ADVICE){
					
				} else if (event == STATS){
					
				} else if (event == BALANCE){
					
				} else if (event == QUIT){
					
				}
			}
		}
		
		return false;
	}
	
	private static boolean bet(List<String> params, Player player, StateMachineIO io){
		
		int new_bet = 0;
		if (params == null){
			new_bet = player.max_bet;
		} else {
			try {
				new_bet = Integer.parseInt(params.get(0));
			} catch (NumberFormatException e){
					io.out("b: illegal parameter (non-integer)");
				return false;
			}
		}
		
		if (0 < new_bet && new_bet <= player.max_bet){
			player.setBet(new_bet);
			return true;
		} else {
			io.out("b: illegal amount");
		}
		
		return false;
	}
	
	private static boolean deal(Player player, Deck deck, StateMachineIO io){
		
		try{
			player.removeCredit(player.getBet());
		} catch (InsufficientCreditException e){
			// TODO Simulation mode might break this thing into an infinite loop
			io.out("Player has insufficient credit to place desired bet.");
			return false;
		}
		
		// Draw 5 cards and add them to the player's hand
		player.setHand(deck.getHand(player.hand_size));
		io.out(player.getHand().toString());
		return true;
	}
	
	private static boolean hold(List<String> params, Player player, StateMachineIO io){
		
		return false;
	}
	
}
