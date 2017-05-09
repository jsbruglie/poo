package state_machine;

import static state_machine.Tag.*;

import java.util.List;

import combinations.Utils;
import video_poker.Card;
import video_poker.Player;
import video_poker.Statistics;
import video_poker.Strategy;

/**
 * State Machine I/O Handler for Simulation Mode
 */
public class SimulationIO implements StateMachineIO {
	
	/** The round number counter */
	private int rounds = 0;
	/** The number of desired rounds */
	private final int max_deals;
	/** The game strategy */
	private Strategy strategy;
	/** The game statistics */
	private Statistics stats;
	/** The player */
	private Player player;
	
	/**
	 * Constructor
	 * @param strategy The game strategy
	 * @param stats The game statistics
	 * @param player The player
	 * @param rounds The number of rounds to be run
	 */
	public SimulationIO(Strategy strategy, Statistics stats, Player player, int rounds){
		this.strategy = strategy;
		this.stats = stats;
		this.player = player;
		this.max_deals = rounds;
	}
	
	@Override
	public void out(Tag tag, String string) {
		if (tag == Out_GameOver){
			System.out.println(stats.printStatistics(player.getCredit()));
		}
		// Ignore other output
	}
	
	@Override
	public void errOut(Tag tag, String string) {
		System.err.println(string);
	}

	@Override
	public String input(Tag tag) {
		if (tag == In_Bet){
			return "b";
		} else if (tag == In_Deal){
			if (rounds == max_deals){
				System.out.println(stats.printStatistics(player.getCredit()));
				return "q";
			}
			rounds++;
			return "d";
		} else if (tag == In_Hold){
			List<Card> hold =  strategy.evaluateHand(player.getHand());
			List<Integer> idx = Utils.indexOf(player.getHand().getCards(), hold);
			String output = "h";
			if (idx != null){
				for (int i = 0; i < idx.size(); i++){
					output = output + " " + Integer.toString(idx.get(i));
				}
				return output;
			}
			//System.out.println(output);
			return "h";
		}
		return null;
	}

}
