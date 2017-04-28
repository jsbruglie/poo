package video_poker;

public class StateDeal extends State {
	private boolean simulation;
	public StateDeal(String mainCommand, String[] commands, boolean acceptsInput, boolean isFinal, boolean simulation) {
		super(mainCommand, commands, acceptsInput, isFinal);
		this.simulation = simulation;
	}

	@Override
	public void stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {
		// Draw 5 cards and add them to the player's hand
		player.setHand(deck.getHand(player.hand_size));
		if(!simulation)
			player.printHand();

	}

}
