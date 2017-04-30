package video_poker;

import java.util.Arrays;

public abstract class State {
	/*
	 * Array of additional commands to check for this state 
	 */
	String[] commands;
	
	/* 
	 * Main command for this state
	 */
	String mainCommand;
	
	//This state accepts input
	boolean acceptsInput;
	
	//If the state is final
	boolean isFinal;
	
	//Indicate the final state
	State finalState;
	
	//Next state of this state
	State nextState;
	
	
	public State(String mainCommand, String[] commands, boolean acceptsInput, boolean isFinal){
		this.mainCommand = mainCommand;
		this.commands = commands;
		this.acceptsInput = acceptsInput;
		this.isFinal = isFinal;
		
	}
	
	public void setNextState(State nextState){
		this.nextState = nextState;
	}
	
	
	public State run(String command, Player player, Statistics stats, Score score, Deck deck){
			if(command == "endround"){ //This is for results and sfinal
				stateMethod(command, player, stats, score, deck);
				return this.nextState;
			}
			//Check if command given is the main command for this state
			String[] tokens = command.split(" ");
		
			//If it is it will call it's specific function and return the next state 
			if(tokens[0].equals(mainCommand)){
				stateMethod(command, player, stats, score, deck);
				return this.nextState;
				
			}else if(Arrays.asList(commands).contains(tokens[0])){ //Check if command given is allowed for this state
				//Process the command as one the following:
				if(tokens[0].equals("s")){
					stats.printStatistics(player.getCredit());
				}else if(tokens[0].equals("$")){
					System.out.println("Player credit is: " + player.getCredit());
				}else if(tokens[0].equals("a")){
					//Advice
					Strategy strategy = new Strategy();
					System.out.println(rules.Utils.indexOf(player.getHand().getCards(), strategy.valueHand(player.getHand())));
				}else if(tokens[0].equals("q")){
					return this.finalState;
				}
			}else{
				//Say it's not allowed
				System.out.println(tokens[0] + ": illegal command");
			}
			return null;
	}
	
	public abstract void stateMethod(String command, Player player, Statistics stats, Score score, Deck deck);
	
	
}
