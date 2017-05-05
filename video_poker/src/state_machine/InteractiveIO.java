package state_machine;

import java.util.Scanner;

public class InteractiveIO implements StateMachineIO {

	private final boolean terminal;
	
	/** Scan for command-line input */
	private Scanner reader;
	
	InteractiveIO(boolean terminal){
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
	public void errOut(String string) {
		if (terminal){
			System.out.println(string);
		}

	}

	@Override
	public String input() {
		if (terminal){
			return reader.nextLine();
		}
		return null;
	}

}
