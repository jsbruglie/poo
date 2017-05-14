package state_machine;

import java.util.Scanner;

/**
 * State Machine I/O Handler for Interactive Mode
 */
public class InteractiveIO implements StateMachineIO {

	/** Scan for command-line input */
	private Scanner reader;
	
	/**
	 * Constructor
	 */
	public InteractiveIO(){
		reader = new Scanner(System.in);
	}
	
	@Override
	public void out(Tag tag, String string) {
		System.out.println(string);		
	}
	
	@Override
	public void errOut(Tag tag, String string) {
		System.err.println(string);
	}

	@Override
	public String input(Tag tag) {
		return reader.nextLine();
	}
}
