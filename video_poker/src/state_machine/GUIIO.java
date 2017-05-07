package state_machine;

import gui.GUI;

public class GUIIO implements StateMachineIO {

	@Override
	public void out(String string) {
		GUI existingGUI = GUI.getGUI();
		existingGUI.output(string);
	}

	@Override
	public void outForced(String string) {
		out(string);

	}

	@Override
	public void errOut(String string) {
		//can be changed later, hide errors from terminal
		//System.err.println(string);
	}

	@Override
	public String input(State state) {
		GUI existingGUI = GUI.getGUI();
		return existingGUI.input();
	}

}
