package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dedicated action listener for monitoring input 
 */
public class SendInputListener implements ActionListener {

	/** GUI instance */
	private GUI existingGUI = GUI.getGUI();
	
	/**
	 *	Constructor
	 */
	public SendInputListener() {}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Notify GUI
		existingGUI.actionHappened();
	}

}
