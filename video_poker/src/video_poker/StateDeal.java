package video_poker;

public class StateDeal extends State {
	
	public StateDeal(	String main_command,
						String[] commands,
						boolean accepts_input,
						boolean simulation) {
		
		super(main_command, commands, accepts_input);
		this.simulation = simulation;
	}

	@Override
	public State stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {
		
		
		
		/* In case the player's bet has not been set */
		if (player.getBet() == -1){
			System.out.println("Please define an initial bet");
			return this;
		}
		
		/* Collect bet */
		try{
			player.removeCredit(player.getBet());
		} catch (InsufficientCreditException e){
			// TODO Simulation mode might break this thing into an infinite loop
			System.out.println("Player has insufficient credit to place desired bet.");
			if (simulation){
				return null;
			}
			return this;
		}
		
		// Draw 5 cards and add them to the player's hand
		player.setHand(deck.getHand(player.hand_size));
		if(!simulation)
			player.printHand();
		
		return this.next_state;
	}
}
