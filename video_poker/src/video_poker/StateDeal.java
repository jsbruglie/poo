package video_poker;

public class StateDeal extends State {

	public StateDeal(String mainCommand, String[] commands, boolean acceptsInput, boolean isFinal) {
		super(mainCommand, commands, acceptsInput, isFinal);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {
		// Draw 5 cards and add them to the player's hand
		player.setHand(deck.getHand(player.hand_size));	
		player.printHand();

	}

}
