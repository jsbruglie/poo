package state_machine;

import static state_machine.State.StateName.*;

import java.util.List;

import video_poker.Card;
import video_poker.Player;
import video_poker.Statistics;
import video_poker.Strategy;

import rules.Utils;

public class SimulationIO implements StateMachineIO {

	private int rounds = 0;
	
	private final int max_deals;
	
	private Strategy strategy;
	
	private Statistics stats;
	
	private Player player;
	
	public SimulationIO(Strategy strategy, Statistics stats, Player player, int rounds){
		this.strategy = strategy;
		this.stats = stats;
		this.player = player;
		this.max_deals = rounds;
	}
	
	@Override
	public void out(String string) {
		//System.out.println(string);
	}
	
	@Override
	public void outForced(String string) {
		System.out.println(string);
	}
	
	@Override
	public void errOut(String string) {
		System.err.println(string);
	}

	@Override
	public String input(State state) {
		if (state.name == ST_FIRST_BET){
			return "b";
		} else if (state.name == ST_DEAL){
			if (rounds == max_deals){
				System.out.println(stats.printStatistics(player.getCredit()));
				return "q";
			}
			rounds++;
			return "d";
		} else if (state.name == ST_HOLD){
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
