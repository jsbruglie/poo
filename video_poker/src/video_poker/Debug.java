package video_poker;

import static video_poker.Rank.A;
import static video_poker.Rank.J;
import static video_poker.Rank.K;
import static video_poker.Rank.Q;
import static video_poker.Rank.T;
import static video_poker.Rank.n2;
import static video_poker.Rank.n3;
import static video_poker.Rank.n4;
import static video_poker.Rank.n5;
import static video_poker.Rank.n6;
import static video_poker.Rank.n7;
import static video_poker.Rank.n8;
import static video_poker.Rank.n9;
import static video_poker.Suit.Clubs;
import static video_poker.Suit.Diamonds;
import static video_poker.Suit.Hearts;
import static video_poker.Suit.Spades;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Debug implements Mode {
	/** Deck of playing cards */
	private Deck deck;
	/** Scan for command-line input */
	private Scanner reader;
	
	String cmd_file, card_file; 

	public Debug(String cmd_file, String card_file) {
		this.cmd_file = "/home/pedro/poo/video_poker/TESTS/cmd-file.txt";
		this.card_file = "/home/pedro/poo/video_poker/TESTS/card-file.txt";
		Card[][] c= Utils.cardFileParser(this.card_file);
		//DEBUG
		/*for(int i=0; i<c[0].length; i++)
			System.out.println(c[0][i]);*/
		
		deck = new Deck(c[0]);
	}
	
	public void execute(Player player, Score score, Statistics stats) {
		File file = new File(this.cmd_file);
        reader = null;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Processing command file");
		List<String> commands = null;
		if(reader.hasNextLine()) //Check that file is not empty
			 commands = parseCmdFile(reader.nextLine());

		System.out.println("Printing command list");
		//DEBUG
		/*for(int i=0; i<commands.size(); i++)
			System.out.println(commands.get(i));*/
		
		State current_state, temp_state;
		State sfinal = new StateFinal(null,  new String[]{}, false, true);
		
		State results = new StateResults(null, new String[]{}, false,false, sfinal, false);
		State bet = new StateBet( "b", new String[]{"s", "$", "q"} , true, false, sfinal, false);
		State deal = new StateDeal("d", new String[]{"s", "$"}, true, false, false);
		State hold = new StateHold("h", new String[]{"s", "$", "a"}, true, false, false);

		bet.setNextState(deal); 
		deal.setNextState(hold);
		hold.setNextState(results);
		results.setNextState(bet); //By default the next
		current_state = bet;
		String command = null;
		for(int i=0; i<commands.size(); i++){
			if (current_state.acceptsInput){
				command = commands.get(i);
			}else{
				command = "endround";
				i--; //Freeze i for the next command after showing results
			}
			System.out.println("[" + command + "]");
			temp_state = current_state.run(command, player, stats, score, deck);
			if(temp_state != null)
				current_state = temp_state;
			//if the current state after last command is a results or sfinal 
			//and this is the last round, show the results (or show nothing if sfinal) and leave
			if(i == commands.size()-1 && current_state.mainCommand==null) 
				temp_state = current_state.run("endround", player, stats, score, deck);
		}
		
		
		
	}
	public static boolean isNumber(String value) {
	    boolean ret = false;
	    ret = value.matches("^[0-9]*$"); //Accepts exactly one digit between 1 and 5 
	    return ret;
	}
	
	public static List<String> parseCmdFile(String line){
		System.out.println(line);
		List<String> cmd_list = new ArrayList<String>();
		String[] parts = line.split(" ");
		int i=0;
		for(i=0; i<parts.length; i++){
			String current = parts[i];
			if(current.equals("d") || current.equals("$") || current.equals("a") || current.equals("s")){
				cmd_list.add(current);
			}else if(current.equals("b")){
				//Check the next token
				
				//if next token is an integer, check if 0<b<5 and append it to current and increment i to skip it
				if(isNumber(parts[i+1])){
					int n = Integer.parseInt(parts[i+1]);
					if(1 <= n && n <= 5){
						current = current + " " + parts[i+1];
						i++;
					}else{
						//There is an invalid bet, don't consider this command
						System.out.println("File has an invalid bet!");
						i++;
					}

				}else{ //if not add current
					cmd_list.add(current);
				}
			}else if(current.equals("h")){
				//For the next 5 tokens check if they are digits. If they are append it to current command
				for(int j=1; j<5; j++){
					if(i+j < parts.length){
						if(isNumber(parts[i+j])){
							current = current + " " + parts[i+j];
						}else{
							i = i+j-1; //-1 to compensate for the outer loop increment
							cmd_list.add(current);
							break;
						}
					}else{
						cmd_list.add(current);
						break;
					}
				}
			}
		}
		
		return cmd_list;
	}
	

	
	
	

	

	
	 
}
