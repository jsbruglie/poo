package video_poker;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Mode mode = null;
		int credit = 0, bet_value, number_deals = 0;
		String cmd_file, card_file;
		
		if(args[0].equals("-i")){
			mode = Mode.Interactive;
			if(args.length == 2){
				//! Read credit
				credit = Integer.parseInt(args[1]);
			}
		}else if(args[0] == "-d"){
			mode  = Mode.Debug;
			if(args.length == 4){
				//! Read credit
				credit = Integer.parseInt(args[1]);
				//! Read command-file
				cmd_file = args[2];
				//! Read card-file
				card_file = args[3];
			}
		}else if(args[0] == "-s"){
			mode = Mode.Simulation;
			if(args.length == 4){
				//! Read credit
				credit = Integer.parseInt(args[1]);
				//! Read bet value
				bet_value = Integer.parseInt(args[2]);
				//Read number of deals
				number_deals = Integer.parseInt(args[3]);
			}
		}else{
			System.out.println("USAGE: ");
			System.exit(1);
		}
		//Create game and player
		//Create game (for now has a bet)
		int bet = 0;
		//Create the evaluator
		Score score = new Score();
		//Create player
		Player player = new Player(credit);
		//Create deck and shuffle it
		Deck deck = new Deck(52,true); //! 52 cards, shuffle upon creation
		
		//Create the statistics for the game
		Statistics stats = new Statistics(player.getCredit());
		
		//Game loop
		Phase phase = Phase.Bet;
		while(true){
			switch(mode){
				
				case Interactive:
					Scanner reader = new Scanner(System.in);
					/* ---------------------BET-----------------------------*/
					if(phase == Phase.Bet){
						String read = reader.nextLine();
						//Bet
						String[] tokens = read.split(" ");
						if(tokens[0].equals("b")){
							if(tokens.length == 1){
								//If no previous bet, bet is 5
								if(bet == 0)
									bet = 5;
								//If previous bet, use it (don't need to do anything)
								//Remove bet from players credit
								player.removeCredit(bet);
								phase = phase.next(); //Go to next phase
							}else if(tokens.length == 2){
								bet = Integer.parseInt(tokens[1]); //Must check if bet is 0<bet<5
								//Remove bet from players credit
								player.removeCredit(bet);
								phase = phase.next(); //Go to next phase
							}else{
								System.out.println("Please bet to start game");
							}
							
						
						}else if(tokens[0].equals("s")){
							//Player wants to see statistics
							stats.printStatistics(player.getCredit());
						}else if(tokens[0].equals("$")){
							//Player wants 
						}else{
							//Illegal command
							System.out.println(tokens[0] + ": illegal command");
						}
					}
					/* ----------------------DEAL----------------------------*/
					if(phase == Phase.Deal){
						//Deal
						String read = reader.nextLine();
						if(read.equals("d")){
							//Give player a hand + Get a hand from the deck
						
							Card[] c = deck.getHand();
							
							Hand h = new Hand(c, 5);
							
							//System.out.println(h);
							player.setHand(h);
							
							//Show the hand to the player
							player.printHand();
							//Move next phase
							phase = phase.next();
						}else if(read.equals("$")){
						
						}else if(read.equals("s")){
							
						}else{
							//Illegal command
							System.out.println(read + ": illegal command");
						}
					}
					/* ----------------------HOLD----------------------------*/
					if(phase == Phase.Hold){
						//Hold
						String read = reader.nextLine();
						String[] tokens = read.split(" ");
						if(tokens[0].equals("h")){
							if(tokens.length > 1){ //Player actually wants to swap anything
								//Check if all tokens are valid numbers between 1-5 inclusive
								for(int i=1;i<tokens.length;i++){
									if(Integer.parseInt(tokens[i])<1 || Integer.parseInt(tokens[i])>5){
										//Invalid token size
									}
								}
		
										
								//Player wants to swap tokens.length - 1 cards
								for(int j=1;j<tokens.length;j++){
									//Draw a card
									Card drawn = deck.draw();
									System.out.println(drawn);
									//Card at hand index tokens[i] needs to be swapped
									player.hand.swapCard(Integer.parseInt(tokens[j])-1,drawn);
								}		
								if(tokens.length > 6){
									//Too many arguments
								}
							}
							phase = phase.next();
								
								
						}else if(read.equals("$")){
							
						}else if(read.equals("a")){
						
						}else if(read.equals("s")){
							stats.printStatistics(player.getCredit());
						}else{
							//Illegal command
							System.out.println(read + ": illegal command");
						}
					}
					/* ----------------------RESULT----------------------------*/
					if(phase == Phase.Results){
						//Show the hand after swap
						player.printHand();
						//Show results (Get player's hand after swap and evaluate it)
						Combination comb = score.getCombination(player.hand);
						//Get the score corresponding to the player's hand (there's an unecessary double check here)
						player.addCredit(score.getScore(player.hand, bet));
						//Show result
						if(comb == Combination.Other){
							System.out.println("Player loses and his credit is " + player.getCredit());
						}else{
							System.out.println("Player wins with a " + comb.getName() + " and his credit is " + player.getCredit());
						}
						
						//Add the result to statistics
						stats.addResults(comb);		
						//Reinsert all cards in the deck + Shuffle deck at the end
						deck.shuffle();
						//Ready for a new round
						phase = Phase.Bet; //Go back to first phase
					}
					
					break; //End of Interactive Mode
					
				case Debug:
					break; //End of Debug Mode
				case Simulation:
					break; //End of Simulation Mode
			}
		}


	}

}
