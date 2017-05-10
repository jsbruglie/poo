package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendInputListener implements ActionListener {

	//private String arg;
	private GUI existingGUI = GUI.getGUI();
	
	public SendInputListener() {
		//arg = _arg;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//existingGUI.set_input(arg);
		existingGUI.actionHappened();
	}

}
