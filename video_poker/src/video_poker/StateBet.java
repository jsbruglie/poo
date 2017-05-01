package video_poker;

/**
 * Bet state
 * 
 * The player can bet and specify a valid amount between 1 and 5
 * 
 */
public class StateBet extends State {
	
	private boolean simulation;
	
	/**
	 * Constructor
	 * @param main_command
	 * @param commands
	 * @param accepts_input
	 * @param simulation
	 */
	public StateBet(String main_command,
					String[] commands,
					boolean accepts_input,
					boolean simulation) {
		
		super(main_command, commands, accepts_input);
		this.simulation = simulation;
	}

	@Override
	public State stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {	
		
		String[] tokens = command.split(" ");
		if (tokens.length == 1 || tokens.length == 2){
			if (tokens.length == 2){
				int new_bet = 0;
				try {
					new_bet = Integer.parseInt(tokens[1]);
				} catch (NumberFormatException e){
					if (!simulation){
						System.out.println("b: illegal parameter (non-integer)");
					}
					return this;
				}

				if (0 < new_bet && new_bet <= 5){
					player.setBet(new_bet);
				} else {
					System.out.println("b: illegal amount");
					return this;
				}
			}
			
			try{
				player.removeCredit(player.getBet());
			} catch (InsufficientCreditException e){
				// TODO Simulation mode might break this thing into an infinite loop
				System.out.println("Player has insufficient credit.");
				return this;
			}
			if(!simulation){
				System.out.println("player is betting " + player.getBet());
			}
			return this.next_state;
			
		}else{
			System.out.println("Please bet to start game");
		}
		return this;
	}
}
