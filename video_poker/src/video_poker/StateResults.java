package video_poker;

public class StateResults extends State {
	private boolean simulation;
	public StateResults(String mainCommand, String[] commands, boolean acceptsInput, boolean isFinal, State finalState, boolean simulation) {
		super(mainCommand, commands, acceptsInput, isFinal);
		// TODO Auto-generated constructor stub
		this.finalState = finalState;
		this.simulation = simulation;
	}

	@Override
	public void stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {
		// Get the combination corresponding to the current player hand
		Combination comb = score.evaluateHand(player.getHand());
		// Get the score corresponding to the player's hand
		player.addCredit(score.getScore(comb, player.getBet()));
		
		// Show result
		if(!simulation){
			if(comb == Combination.Other){
				System.out.println("player loses and his credit is " + player.getCredit());
			}else{
				System.out.println("player wins with a " + comb.toString().toUpperCase() + " and his credit is " + player.getCredit());
			}	
		}
		
		// Add the result to statistics
		stats.addResults(comb);		
		
		if (player.getCredit() == 0){
			this.nextState = finalState;
		}
	}

}
