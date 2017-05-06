package state_machine;

import java.util.Scanner;
import gui.GUI;

public class InteractiveIO implements StateMachineIO {

	/** Whether to show terminal output */
	private final boolean terminal;
	
	/** Scan for command-line input */
	private Scanner reader;
	
	public InteractiveIO(boolean terminal){
		this.terminal = terminal;
		if (terminal){
			if(GUI.getGUI(false) == null)
				reader = new Scanner(System.in);
			else
				reader = null;
		} else{
			reader = null;
		}
	}
	
	@Override
	public void out(String string) {
		GUI existingGUI = GUI.getGUI(false); 
		if (terminal){
			if(existingGUI == null)
				System.out.println(string);
			else
				existingGUI.output(string);				
		}
	}
	
	@Override
	public void outForced(String string) {
		out(string);
	}

	@Override
	public void errOut(String string) {
		GUI existingGUI = GUI.getGUI(false);
		
		if (terminal){
			if(existingGUI == null)
				System.err.println(string);
		}

	}

	@Override
	public String input(State state) {
		GUI existingGUI = GUI.getGUI(false);
		if (terminal){
			if(existingGUI == null)
				return reader.nextLine();
			else
				return existingGUI.input();
		}
		return null;
	}
}
