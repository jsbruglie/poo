package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI {
	
	private JFrame mainFrame;
	private JLabel[] cards;
	ImageIcon[] icon;
	private JPanel p;
	private Container pane;
	private JToggleButton[] btn;
	private JButton betbtn, dealbtn, holdbtn;
	private SpringLayout layout;
	private JTextField betField;
	private Boolean[] hold = new Boolean[5];
	public String ret_input = new String();
	public String ret_output = new String();
	
	private static GUI instance;
	
	private GUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
	}
	
	public static GUI getGUI(boolean maker){
		if(maker == true){
			if(instance == null)
				instance = new GUI();
			else
				return instance;
		}
		if(maker == false){
			if(instance == null)
				return null;
			else
				return instance;
		}
		
		return instance;
	}
	
	public String input(){
		return ret_input;
	}
	
	public void output(String string){
		ret_output = string;
		System.out.println(string);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void prepareSelectorGUI(String[] args, String mode, Boolean[] forward){
		mainFrame = new JFrame("Video Poker - Mode Chooser");
		p = new JPanel();
		mainFrame.setSize(1000, 1000);
		p.setSize(1000,1000);
		mainFrame.setContentPane(p);

		layout = new SpringLayout();
		
		pane = mainFrame.getContentPane();
		pane.setLayout(layout);
		
		JButton[] btn = new JButton[3];
		btn[0] = new JButton("Interactive");

		betField = new JTextField(10);
		
		layout.putConstraint(SpringLayout.NORTH, btn[0], 20, SpringLayout.NORTH, pane);
		layout.putConstraint(SpringLayout.WEST, btn[0], 20, SpringLayout.WEST, pane);

		layout.putConstraint(SpringLayout.NORTH, betField, 20, SpringLayout.SOUTH, btn[0]);
		
		p.add(btn[0]);
		layout.putConstraint(SpringLayout.WEST, betField, 20, SpringLayout.WEST, pane);
		p.add(betField);
		
		
		btn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				args[0] = "-i";
				args[1] = betField.getText();
				forward[1] = true;
			}
		});
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	public void prepareInteractiveGUI(){
		mainFrame.setVisible(false);
		mainFrame = new JFrame("Best Test in the West");
		mainFrame.setSize(1000, 1000);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		for(int i = 0; i < 5; i++)
			hold[i] = new Boolean(false);
		
		p = new JPanel();
		p.setSize(1000,1000);
		
		mainFrame.setContentPane(p);
		
		cards = new JLabel[5];
		icon = new ImageIcon[5];
		btn = new JToggleButton[5];
		
		String arg = "/home/mind/Documents/cards/b.gif";
		
		for(int i = 0; i<5; i++){
			btn[i] = new JToggleButton("Hold Card");
			icon[i] = new ImageIcon(arg);
			cards[i] = new JLabel();
			cards[i].setIcon(icon[i]);
		}
		
		pane = mainFrame.getContentPane();
		p.setSize(1000,1000);
		
		layout = new SpringLayout();
		pane.setLayout(layout);
		
		layout.putConstraint(SpringLayout.WEST, cards[0], 20, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, btn[0], 10, SpringLayout.SOUTH, cards[0]);
		layout.putConstraint(SpringLayout.WEST, btn[0], 10, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, cards[0], 20, SpringLayout.NORTH, pane);
		for(int i = 1; i<5; i++)
		{
			layout.putConstraint(SpringLayout.NORTH, cards[i], 20, SpringLayout.NORTH, pane);
			layout.putConstraint(SpringLayout.NORTH, btn[i], 10, SpringLayout.SOUTH, cards[i]);
			layout.putConstraint(SpringLayout.WEST, btn[i], 10, SpringLayout.EAST, btn[i-1]);
			layout.putConstraint(SpringLayout.WEST, cards[i], 30, SpringLayout.EAST, cards[i-1]);
		}
		
		betbtn = new JButton("Bet");
		layout.putConstraint(SpringLayout.WEST, betbtn, 20, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.NORTH, betbtn, 20, SpringLayout.SOUTH, btn[0]);
		p.add(betbtn);
		
		betField = new JTextField(10);
		layout.putConstraint(SpringLayout.WEST, betField, 10, SpringLayout.EAST, betbtn);
		layout.putConstraint(SpringLayout.NORTH, betField, 23, SpringLayout.SOUTH, btn[0]);
		p.add(betField);
		
		dealbtn = new JButton("Deal");
		layout.putConstraint(SpringLayout.WEST, dealbtn, 20, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.NORTH, dealbtn, 20, SpringLayout.SOUTH, betbtn);
		p.add(dealbtn);
		
		holdbtn = new JButton("Hold");
		layout.putConstraint(SpringLayout.WEST, holdbtn, 20, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.NORTH, holdbtn, 20, SpringLayout.SOUTH, dealbtn);
		p.add(holdbtn);
		
		betbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cash = betField.getText();
				int initial_credit;
				String inner_ret_input = new String("b");
				try {
					initial_credit = Integer.parseInt(cash);
				} catch (NumberFormatException z){
					//do something display worthy, i guess
					return;
				}
				if(initial_credit <= 0)
					return;
				
				ret_input = inner_ret_input.concat(" " + Integer.toString(initial_credit));
				System.out.println(ret_input);
			}
		});
		
		btn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hold[0] == false)
					hold[0] = true;
				else
					hold[0] = false;
				}
		});
		
		btn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hold[0] == false)
					hold[0] = true;
				else
					hold[0] = false;
				}
		});
		
		btn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hold[1] == false)
					hold[1] = true;
				else
					hold[1] = false;
				}
		});
		
		btn[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hold[2] == false)
					hold[2] = true;
				else
					hold[0] = false;
				}
		});
		
		btn[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hold[2] == false)
					hold[2] = true;
				else
					hold[2] = false;
				}
		});
		
		btn[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hold[3] == false)
					hold[3] = true;
				else
					hold[3] = false;
				}
		});
		
		btn[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hold[4] == false)
					hold[4] = true;
				else
					hold[4] = false;
				}
		});
		
		holdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String int_ret_input = new String("h");
				for(int i = 0; i < 4; i++)
				{
					if(hold[i] == true)
						int_ret_input = int_ret_input.concat(" " + Integer.toString(i+1));
				}
				ret_input = int_ret_input;
				changeIcons(false);
			}
		});
		
		dealbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret_input = "d";
				changeIcons(true);
			}
		});
		
		for(int i = 0; i<5; i++){
			p.add(cards[i]);
			p.add(btn[i]);
		}
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		
	}
	
	void changeIcons(boolean dealio){
		String initial_string = new String();
		if(dealio == true)
		{
			try {
			    Thread.sleep(100);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
			try {
			    Thread.sleep(1);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		String parts[] = ret_output.split("\\s+");
		for(int i = 0; i < 5; i++){
			initial_string = "/home/mind/Documents/cards/";
			initial_string = initial_string.concat(parts[i + 2] + ".gif");
			System.out.println(initial_string);
			ImageIcon inner_icon = new ImageIcon(initial_string);
			cards[i].setIcon(inner_icon);
		}
	}

}
