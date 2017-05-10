package gui;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.SpringLayout;

public class ButtonWithCoordinates extends JButton {

	private static final long serialVersionUID = 8741199920909063987L;

	public ButtonWithCoordinates(String text) {
		super(text);
	}

	public ButtonWithCoordinates(String name, Integer west_pad, Integer north_pad, Component north_component, Component west_component, SpringLayout layout)
	{
		super(name);
		if(north_component instanceof Container)
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.NORTH, north_component);
		else
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.SOUTH, north_component);
		
		if(west_component instanceof Container)
			layout.putConstraint(SpringLayout.WEST, this, west_pad, SpringLayout.WEST, west_component);
		else
			layout.putConstraint(SpringLayout.NORTH, this, west_pad, SpringLayout.EAST, west_component);
	}

}
