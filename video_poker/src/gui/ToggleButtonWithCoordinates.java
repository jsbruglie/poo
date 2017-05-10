package gui;


import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

public class ToggleButtonWithCoordinates extends JToggleButton {

	private static final long serialVersionUID = 2180683168085023963L;

	public ToggleButtonWithCoordinates(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ToggleButtonWithCoordinates(String name, Integer west_pad, Integer north_pad, Component north_component, Component west_component, SpringLayout layout)
	{
		super(name);
		this.setMaximumSize(new Dimension(93, 27));
		this.setMinimumSize(new Dimension(93, 27));
		this.setPreferredSize(new Dimension(93, 27));
		
		if(!(north_component instanceof JButton) && !(north_component instanceof JToggleButton) && !(north_component instanceof JLabel)){
			
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.NORTH, north_component);
			}
		else
			layout.putConstraint(SpringLayout.NORTH, this, north_pad, SpringLayout.SOUTH, north_component);
		
		if(!(west_component instanceof JButton) && !(west_component instanceof JToggleButton) && !(west_component instanceof JLabel)){
			
			layout.putConstraint(SpringLayout.WEST, this, west_pad, SpringLayout.WEST, west_component);}
		else
			layout.putConstraint(SpringLayout.WEST, this, west_pad, SpringLayout.EAST, west_component);
	}
}
