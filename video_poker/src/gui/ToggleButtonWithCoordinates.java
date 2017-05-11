package gui;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

public class ToggleButtonWithCoordinates extends JToggleButton {

	private static final long serialVersionUID = 2180683168085023963L;

	public ToggleButtonWithCoordinates(String arg0) {
		super(arg0);
	}

	public ToggleButtonWithCoordinates(String name, Integer west_pad, Integer north_pad,
			Component north_component, Component west_component, SpringLayout layout,
			int dim_x, int dim_y){
		
		super(name);
		
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setMaximumSize(new Dimension(dim_x, dim_y));
		this.setMinimumSize(new Dimension(dim_x, dim_y));
		this.setPreferredSize(new Dimension(dim_x, dim_y));
		
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
