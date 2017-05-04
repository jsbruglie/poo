package video_poker;

import java.util.Arrays;

public abstract class State {
	
	/* Main command - mainly used for state transition triggers */
	String main_command;
	
	/* Array of additional commands or parameters*/
	String[] commands;
	
	/** Whether the state accepts user input */
	boolean accepts_input;
	
	/** The state that naturally follows the current state */
	State next_state;
	
	/** */
	boolean simulation;
	
	/**
	 * Constructor
	 * @param main_command
	 * @param commands
	 * @param accepts_input
	 */
	public State(String main_command, String[] commands, boolean accepts_input){
		this.main_command = main_command;
		this.commands = commands;
		this.accepts_input = accepts_input;
	}
	
	public void setNextState(State next_state){
		this.next_state = next_state;
	}
	
	private State changeBet(	String[] tokens,
								Player player,
								Statistics stats,
								Score score,
								Deck deck){
		
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
			} else {
				// TODO - Where does 5 come from
				player.setBet(5);
			}
			
			if(!simulation){
				System.out.println("player is betting " + player.getBet());
			}
			return this;
			
		}else{
			System.out.println("Please bet to start game");
		}
		return this;	
	}
	
	public State run(	String command,
						Player player,
						Statistics stats,
						Score score,
						Deck deck){
			
			if (main_command == null){
				return stateMethod(command, player, stats, score, deck);
			}
		
			// Check if command given is the main command for this state
			String[] tokens = command.split(" ");
		
			// If it is it will call it's specific function and return the next state 
			if (tokens[0].equals(main_command)){
				return stateMethod(command, player, stats, score, deck);
				
			} else if (Arrays.asList(commands).contains(tokens[0])){ //Check if command given is allowed for this state
				//Process the command as one the following:
				if (tokens[0].equals("b")){
					changeBet(tokens, player, stats, score, deck);
				} else if (tokens[0].equals("s")){
					stats.printStatistics(player.getCredit());
				} else if (tokens[0].equals("$")){
					System.out.println("Player credit is: " + player.getCredit());
				} else if (tokens[0].equals("a")){
					//Advice
					Strategy strategy = new Strategy();
					System.out.println(strategy.holdAdvice(player.getHand()));
				}else if (tokens[0].equals("q")){
					return null;
				}
			}else{
				System.out.println(tokens[0] + ": illegal command");
			}
			/* Remain in current state */
			return this;
	}
	
	/**
	 * To be implemented by each state
	 * @param command
	 * @param player
	 * @param stats
	 * @param score
	 * @param deck
	 * @return 
	 */
	public abstract State stateMethod(String command, Player player, Statistics stats, Score score, Deck deck);
}
