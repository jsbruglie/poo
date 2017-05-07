package state_machine;

import java.util.Scanner;

public class InteractiveIO implements StateMachineIO {

	/** Whether to show terminal output */
	private final boolean terminal;
	
	/** Scan for command-line input */
	private Scanner reader;
	
	public InteractiveIO(boolean terminal){
		this.terminal = terminal;
		if (terminal){
				reader = new Scanner(System.in);
		} else{
			reader = null;
		}
	}
	
	@Override
	public void out(String string) {
		if (terminal){
				System.out.println(string);		
		}
	}
	
	@Override
	public void outForced(String string) {
		out(string);
	}

	@Override
	public void errOut(String string) {
		if (terminal){
				System.err.println(string);
		}

	}

	@Override
	public String input(State state) {
		if (terminal){
				return reader.nextLine();
		}
		return null;
	}
}
