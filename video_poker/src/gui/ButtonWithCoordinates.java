package gui;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

/**
 * Button with dedicated constructor to incorporate Spring layout constraints
 */
public class ButtonWithCoordinates extends JButton {

	private static final long serialVersionUID = 8741199920909063987L;

	/**
	 * Constructor
	 * @param text The button text
	 */
	public ButtonWithCoordinates(String text) {
		super(text);
	}
	
	/**
	 * Constructor
	 * @param text The button text
	 * @param west_pad West padding
	 * @param north_pad North padding
	 * @param north_component North padding reference object
	 * @param west_component West padding reference object
	 * @param layout Spring layout instance
	 */
	public ButtonWithCoordinates(String text, Integer west_pad, Integer north_pad,
			Component north_component, Component west_component, SpringLayout layout){
		
		super(text);
		if (!(north_component instanceof JButton) && !(north_component instanceof JToggleButton) &&
			!(north_component instanceof JLabel)){
			
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.NORTH, north_component);
		} else {
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.SOUTH, north_component);
		}
		
		if (!(west_component instanceof JButton) && !(west_component instanceof JToggleButton) &&
			!(west_component instanceof JLabel)){
			
			layout.putConstraint(SpringLayout.WEST, this, west_pad, SpringLayout.WEST, west_component);
		} else {
			layout.putConstraint(SpringLayout.WEST, this, west_pad, SpringLayout.EAST, west_component);
		}
	}
}
