package state_machine;

import gui.GUI;

/**
 * State Machine I/O Handler for GUI Mode
 */
public class GUIIO implements StateMachineIO {

	@Override
	public void out(Tag tag, String string) {
		GUI existingGUI = GUI.getGUI();
		existingGUI.output(string);
	}

	@Override
	public void errOut(Tag tag, String string) {
		//can be changed later, hide errors from terminal
		//System.err.println(string);
	}

	@Override
	public String input(Tag tag) {
		GUI existingGUI = GUI.getGUI();
		return existingGUI.input();
	}

}
