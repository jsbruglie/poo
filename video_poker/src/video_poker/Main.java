package video_poker;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Mode mode = null;
		int credit, bet_value, number_deals;
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
		//Create game
		//Create player
		
		
		//Game loop
		int phase = 0;
		while(true){
			switch(mode){
				
				case Interactive:
					Scanner reader = new Scanner(System.in);
					if(phase == 0){
						String read = reader.nextLine();
						//Bet
						String[] tokens = read.split(" ");
						if(tokens[0].equals("b")){
							if(tokens.length == 1){
								//If no previous bet, bet is 5
								//If previous bet, use it
								phase++; //Go to next phase
							}else if(tokens.length == 2){
								int bet = Integer.parseInt(tokens[1]);
								phase++; //Go to next phase
							}else{
								System.out.println("Please bet to start game");
							}
						}else if(tokens[0].equals("a")){
							//Player wants advice
						}else if(tokens[0].equals("s")){
							//Player wants to see statistics
						}
					}

					if(phase == 1){
						//Deal
						String read = reader.nextLine();
						if(read.equals("d")){
							//Give player a hand

							//Show the hand to the player
							phase++;
						}else{
							//Illegal command
						}
					}
					
					if(phase == 2){
						//Hold
						String read = reader.nextLine();
						String[] tokens = read.split(" ");
						if(tokens[0].equals("h")){
							
							//Check if all tokens are valid numbers between 1-5 inclusive
							for(int i=0;i<tokens.length;i++)
								if(Integer.parseInt(tokens[i])<1 || Integer.parseInt(tokens[i])>5)
									//Invalid token size
									
							if(tokens.length == 2){
								//Player wants to swap 1 card
							}else if(tokens.length == 3){
								//Player wants to swap 2 cards
							}else if(tokens.length == 4){
								//Player wants to swap 3 cards
							}else if(tokens.length == 5){
								//Player wants to swap 4 cards
							}else if(tokens.length == 6){
								//Player wants to swap 5 cards
							}else{
								//Too many arguments
							}
								
						}else{
							//Illegal command
						}
					}
					
					if(phase == 3){
						//Show results (Get player's hand after swap and evaluate it)
						//Add results to statistics
						//Shuffle deck
						//Ready for a new round
						phase = 0;
					}
					
					break;
				case Debug:
					break;
				case Simulation:
					break;
			}
		}


	}

}
