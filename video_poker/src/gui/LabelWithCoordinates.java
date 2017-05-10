package gui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

public class LabelWithCoordinates extends JLabel {

	private static final long serialVersionUID = 2199507286232114471L;

	public LabelWithCoordinates(String arg, Integer west_pad, Integer north_pad, Component north_component, Component west_component, SpringLayout layout)
	{
		super();
		ImageIcon transient_icon = new ImageIcon(arg);
		this.setIcon(transient_icon);
		
		if(!(north_component instanceof JButton) && !(north_component instanceof JToggleButton) && !(north_component instanceof JLabel)){
			
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.NORTH, north_component);
			}
		else
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.SOUTH, north_component);
		
		if(!(west_component instanceof JButton) && !(west_component instanceof JToggleButton) && !(west_component instanceof JLabel)){
			
			layout.putConstraint(SpringLayout.WEST, this, west_pad, SpringLayout.WEST, west_component);
			}
		else
			layout.putConstraint(SpringLayout.WEST, this, west_pad, SpringLayout.EAST, west_component);
	}

}
