package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

/**
 * Toggle Button with dedicated constructor to incorporate Spring layout constraints
 */
public class ToggleButtonWithCoordinates extends JToggleButton {

	private static final long serialVersionUID = 2180683168085023963L;
	
	/**
	 * COnstructor
	 * @param text The button text
	 */
	public ToggleButtonWithCoordinates(String text) {
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
	 * @param width The toggle button width
	 * @param height THe toggle button height
	 */
	public ToggleButtonWithCoordinates(String text, Integer west_pad, Integer north_pad,
			Component north_component, Component west_component, SpringLayout layout,
			int width, int height){
		
		super(text);
		
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		
		if (!(north_component instanceof JButton) && !(north_component instanceof JToggleButton) &&
			!(north_component instanceof JLabel)){
			
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.NORTH, north_component);
		} else {
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.SOUTH, north_component);
		}
		
		if (!(west_component instanceof JButton) && !(west_component instanceof JToggleButton) &&
			!(west_component instanceof JLabel)){
			
			layout.putConstraint(SpringLayout.WEST, this, west_pad, SpringLayout.WEST, west_component);
		}else {
			layout.putConstraint(SpringLayout.WEST, this, west_pad, SpringLayout.EAST, west_component);
		}
	}
}
