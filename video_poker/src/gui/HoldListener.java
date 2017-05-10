package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HoldListener implements ActionListener {
	
	private int i;
	private GUI existingGUI = GUI.getGUI();
	
	public HoldListener(int _i) {
		i = _i;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*if(hold == false){
			hold = true;
			System.out.println("I am now true, as should be");
		}
			
		if(hold == true)
			hold = false;*/
		existingGUI.changeHold(i);
	}

}
