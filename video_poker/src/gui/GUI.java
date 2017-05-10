package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import state_machine.Tag;
import video_poker.Hand;
import video_poker.Player;


public class GUI {
	
	private JFrame mainFrame;
	private JLabel[] cards = new JLabel[5];
	ImageIcon[] icon;
	private JPanel p;
	private Container pane;
	private JToggleButton[] tglbtn = new JToggleButton[5];
	private JButton betbtn, dealbtn, holdbtn, advicebtn; //statsbtn
	private JButton[] btn = new JButton[5];
	private SpringLayout layout;
	private JTextField betField;
	private JTextField adviceField;
	private Boolean[] hold = new Boolean[5];
	public String ret_input = new String();
	public String ret_output = new String();
	private JSlider creditsToBet;
	private Tag inner_tag;
	private Player inner_player;
	private JTextArea stats_area;
	
	private static GUI instance;
	
	private GUI(){
		JFrame.setDefaultLookAndFeelDecorated(false);
	}
	
	public static GUI getGUI(){
			if(instance == null)
				instance = new GUI();
			else
				return instance;
		
		return instance;
	}
	
	public void set_input(String new_input){
		ret_input = new_input;
	}
	
	public String input(){
		return ret_input;
	}
	
	public void output(String string, Tag current_tag){
		ret_output = string;
		inner_tag = current_tag;
		if(inner_tag == Tag.Out_Deal || inner_tag == Tag.Out_Hold){
			changeIcons(inner_player);
			changeCredits(inner_player);
		}
		if(inner_tag == Tag.Out_Hold){
			unclickAll();
		}
		if(inner_tag == Tag.Out_Results || inner_tag == Tag.Out_Advice){
			showResults();
		}
		if(inner_tag == Tag.Out_Stats){
			//changeStatsPane();
		}
		if(inner_tag == Tag.Out_Bet){
			changeCredits(inner_player);
			btn[4].setEnabled(true);
		}
	}
	
	void actionHappened()
	{
		if(inner_tag == Tag.In_Bet){
			int initial_credit = creditsToBet.getValue();
			String inner_ret_input = new String("b");
			
			if(initial_credit <= 0)
				return;
			dealbtn.setEnabled(true);
			betbtn.setEnabled(false);
			ret_input = inner_ret_input.concat(" " + Integer.toString(initial_credit));
		}
	}
	
	void changeHold(int i)
	{
		if(hold[i] == true)
			hold[i] = false;
		else
			hold[i] = true;
	}	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void prepareSelectorGUI(String[] args, Boolean[] forward){
		mainFrame = new JFrame("Video Poker - Mode Chooser");
		p = new JPanel();
		mainFrame.setSize(1000, 1000);
		p.setSize(1000,1000);
		mainFrame.setContentPane(p);
		mainFrame.setResizable(false);
		mainFrame.setPreferredSize(new Dimension(1000,1000));

		layout = new SpringLayout();
		
		pane = mainFrame.getContentPane();
		pane.setLayout(layout);
		
		JButton[] btn = new JButton[3];
		btn[0] = new JButton("Interactive");

		betField = new JTextField(10);
		
		layout.putConstraint(SpringLayout.NORTH, btn[0], 20, SpringLayout.NORTH, pane);
		layout.putConstraint(SpringLayout.WEST, btn[0], 20, SpringLayout.WEST, pane);

		
		
		p.add(btn[0]);
		layout.putConstraint(SpringLayout.NORTH, betField, 20, SpringLayout.NORTH, pane);
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
	
	public void prepareRefactoredInteractiveGUI(Player player){
		inner_player = player;
		mainFrame.setVisible(false);
		mainFrame = new JFrame("Video Poker");
		mainFrame.setSize(1000,1000);
		mainFrame.setResizable(false);
		mainFrame.setPreferredSize(new Dimension(1000,1000));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int i = 0; i < 5; i++)
			hold[i] = new Boolean(false);
		
		p = new JPanel();
		p.setSize(1000,1000);
		mainFrame.setContentPane(p);
		
		pane = mainFrame.getContentPane();
		
		layout = new SpringLayout();
		pane.setLayout(layout);
		
		String arg = "cards/b.gif";
		String[] btn_arg = {"Bet", "Deal", "Hold", "Advice", "Stats"};
		
		cards[0] = new LabelWithCoordinates(arg, 20, 20, pane, pane, layout);
		tglbtn[0] = new ToggleButtonWithCoordinates("Hold Card", 10, 10, cards[0], pane, layout);
		btn[0] = new ButtonWithCoordinates(btn_arg[0], 20, 20, tglbtn[0], pane, layout);
		
		for(int i = 1; i < 5; i++){
			cards[i] = new LabelWithCoordinates(arg, 30, 20, pane, cards[i-1], layout);
			tglbtn[i] = new ToggleButtonWithCoordinates("Hold Card", 10, 10, cards[0], tglbtn[i-1], layout);
			btn[i] = new ButtonWithCoordinates(btn_arg[i], 20, 20, btn[i-1], pane, layout);
			btn[i].setEnabled(false);
		}
		
		for(int i = 0; i<5; i++){
			tglbtn[i].addActionListener(new HoldListener(i));
			
			p.add(cards[i]);
			p.add(tglbtn[i]);
			p.add(btn[i]);
		}
		
		creditsToBet = new JSlider(JSlider.HORIZONTAL, 1, 5, 5);
		creditsToBet.setMajorTickSpacing(5);
		creditsToBet.setMinorTickSpacing(1);
		creditsToBet.setPaintTicks(true);
		creditsToBet.setSnapToTicks(true);
		layout.putConstraint(SpringLayout.WEST, creditsToBet, 10, SpringLayout.EAST, btn[0]);
		layout.putConstraint(SpringLayout.NORTH, creditsToBet, 23, SpringLayout.SOUTH, tglbtn[0]);
		
		betField = new JTextField(10);
		betField.setEditable(false);
		changeCredits(player);
		layout.putConstraint(SpringLayout.WEST, betField, 20, SpringLayout.EAST, creditsToBet);
		layout.putConstraint(SpringLayout.NORTH, betField, 20, SpringLayout.SOUTH, tglbtn[0]);
		
		adviceField = new JTextField(50);
		adviceField.setEditable(false);
		layout.putConstraint(SpringLayout.WEST, adviceField, 20, SpringLayout.EAST, btn[3]);
		layout.putConstraint(SpringLayout.NORTH, adviceField, 20, SpringLayout.SOUTH, btn[2]);
		
		p.add(creditsToBet);
		p.add(betField);
		p.add(adviceField);
		
		btn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int initial_credit = creditsToBet.getValue();
				String inner_ret_input = new String("b");
				
				if(initial_credit <= 0)
					return;
				btn[1].setEnabled(true);
				btn[0].setEnabled(false);
				ret_input = inner_ret_input.concat(" " + Integer.toString(initial_credit));
			}
		});
		
		btn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret_input = "d";
				btn[1].setEnabled(false);
				btn[2].setEnabled(true);
				btn[3].setEnabled(true);
				btn[0].setEnabled(false);
				changeCredits(player);
			}
		});
		
		btn[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String int_ret_input = new String("h");
				for(int i = 0; i < 5; i++){
					if(hold[i] == true)
						int_ret_input = int_ret_input.concat(" " + Integer.toString(i+1));
				}
				ret_input = int_ret_input;
				btn[0].setEnabled(true);
				btn[1].setEnabled(true);
				btn[3].setEnabled(false);
				btn[2].setEnabled(false);
				changeCredits(player);
				showResults();
				unclickAll();
			}
		});
		
		btn[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret_input = "a";
				btn[3].setEnabled(false);
			}
		});
		
		btn[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret_input = "s";
			}
		});
		
		mainFrame.setVisible(true);
	}
	
	void changeIcons(Player player){
		String initial_string = new String();
		Hand parting_hand = player.getHand();
		String parting_string = parting_hand.toString();
		String parts[] = parting_string.split("\\s+");
		for(int i = 0; i < 5; i++){
			initial_string = "cards/";
			initial_string = initial_string.concat(parts[i + 2] + ".gif");
			ImageIcon inner_icon = new ImageIcon(initial_string);
			cards[i].setIcon(inner_icon);
		}

	}
	
	void changeCredits(Player player){
		betField.setText(Integer.toString(player.getCredit()));
	}
	
	void showResults(){
		adviceField.setText(ret_output);
	}
	
	void unclickAll(){
		for(int i = 0; i < 5; i++){
			if(hold[i] == true)
				tglbtn[i].doClick();
		}
	}
	
	void changeStatsPane(){
		stats_area = new JTextArea(ret_output);
		layout.putConstraint(SpringLayout.WEST, stats_area, 50, SpringLayout.EAST, cards[4]);
		p.add(stats_area);
	}

}
