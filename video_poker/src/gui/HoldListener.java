package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Monitors changes in hold card toggle buttons
 */
public class HoldListener implements ActionListener {
	
	/** The toggle button index */
	private int i;
	/** The GUI instance */
	private GUI existingGUI = GUI.getGUI();
	
	/**
	 * Constructor
	 * @param i The toggle button index
	 */
	public HoldListener(int i) {
		this.i = i;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		existingGUI.changeHold(i);
	}

}
