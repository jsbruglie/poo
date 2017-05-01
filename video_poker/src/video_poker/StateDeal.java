package video_poker;

public class StateDeal extends State {
	private boolean simulation;
	public StateDeal(	String main_command,
						String[] commands,
						boolean accepts_input,
						boolean simulation) {
		
		super(main_command, commands, accepts_input);
		this.simulation = simulation;
	}

	@Override
	public State stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {
		
		// Draw 5 cards and add them to the player's hand
		player.setHand(deck.getHand(player.hand_size));
		if(!simulation)
			player.printHand();
		
		return this.next_state;
	}
}
