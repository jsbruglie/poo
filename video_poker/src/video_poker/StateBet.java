package video_poker;

public class StateBet extends State {

	public StateBet(String mainCommand, String[] commands, boolean acceptsInput, boolean isFinal, State finalState) {
		super(mainCommand, commands, acceptsInput, isFinal);
		this.finalState = finalState;
	}

	@Override
	public void stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {	
		String[] tokens = command.split(" ");
		if (tokens.length == 1 || tokens.length == 2){
			if (tokens.length == 2){
				int new_bet = Integer.parseInt(tokens[1]);
				if (0 < new_bet && new_bet <= 5){
					player.setBet(new_bet);
				}else{
					System.out.println("b: illegal amount");
					return;
				}
			}
			
			try{
				player.removeCredit(player.getBet());
			}catch (Exception InsufficientCreditException){
				System.out.println("Player has insufficient credit.");
				return;
			}
			
			System.out.println("player is betting " + player.getBet());
			
		}else{
			System.out.println("Please bet to start game");
		}

	}
}
