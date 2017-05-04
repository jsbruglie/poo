package video_poker;

public class StateResults extends State {
	
	/**
	 * 
	 * @param main_command
	 * @param commands
	 * @param accepts_input
	 * @param simulation
	 */
	public StateResults(String main_command,
						String[] commands,
						boolean accepts_input,
						boolean simulation) {
		
		super(main_command, commands, accepts_input);
		this.simulation = simulation;
	}

	@Override
	public State stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {
		
		// Get the combination corresponding to the current player hand
		Combination comb = score.evaluateHand(player.getHand());
		
		if (comb != null){
			player.addCredit(comb.getPayout(player.getBet()));
		}
			
		// Show result
		if(!simulation){
			if(comb == null){
				System.out.println("player loses and his credit is " + player.getCredit());
			}else{
				System.out.println("player wins with a " + comb.toString().toUpperCase() + " and his credit is " + player.getCredit());
			}	
		}
		
		// Add the result to statistics
		stats.addResults(comb);		
		
		if (player.getCredit() == 0){
			return null;
		} else {
			return this.next_state;
		}
	}

}
