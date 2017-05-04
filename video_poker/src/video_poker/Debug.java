package video_poker;

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
		this.cmd_file = cmd_file;
		this.card_file = card_file;
		Card[][] c = Utils.cardFileParser(this.card_file);
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
		
		State current_state, next_state;
		
		/* State declaration */
		State deal		= new StateDeal("d", new String[]{"b", "s", "$", "q"}, true, false);
		State hold		= new StateHold("h", new String[]{"s", "$", "a"}, true, false);
		State results	= new StateResults(null, new String[]{}, false, false);	
		
		/* Declare default state transitions */
		deal.setNextState(hold);
		hold.setNextState(results);
		results.setNextState(deal);
		
		/* Initial state */
		current_state = deal;
		
		String command = null;
		int i = 0;
		
		while (current_state != null){
			
			if (current_state.accepts_input){
				if ( i == commands.size()){
					break;
				} else {
					command = commands.get(i++);
					// TODO - DEBUG
					System.out.println(command);
				}
			} else {
				command = null;
				deck.shuffle();
			}
			next_state = current_state.run(command, player, stats, score, deck);
			current_state = next_state;
		}
		
		System.out.println("Player has lost (or quited). Exiting...");
	}
	
	// TODO Migrate to Utils
	
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
