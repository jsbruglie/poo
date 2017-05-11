package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import state_machine.Tag;
import video_poker.Hand;
import video_poker.Player;
import video_poker.Statistics;

/**
 * GUI class for handling the GUI wrapper interface
 */
public class GUI {
	
	/** Main frame */
	private JFrame main_frame;
	/** Array of cards*/
	private JLabel[] cards = new JLabel[5];
	ImageIcon[] icon;
	private JPanel p;
	private Container content_pane;
	private SpringLayout layout;
	
	private JSlider slider_bet;
	private JTextField txt_field_credit;
	private JTextField txt_field_advice;
	private JTextArea txt_stats;
	
	private JToggleButton[] tglbtn = new JToggleButton[5];
	private JButton[] btn = new JButton[5];
	
	private Boolean[] hold = new Boolean[5];
	public String ret_input = new String();
	public String ret_output = new String();
	
	private Tag inner_tag;
	private Player player;
	private Statistics stats;
	
	private final int BTN_BET = 0;
	private final int BTN_DEAL = 1;
	private final int BTN_HOLD = 2;
	private final int BTN_ADVICE = 3;
	
	/** The current GUI instance - Singleton Design Pattern */
	private static GUI instance;
	
	private GUI(){
		JFrame.setDefaultLookAndFeelDecorated(false);
	}
	
	public static GUI getGUI(){
		if (instance == null){
			// Call private constructor
			instance = new GUI();
		}
		return instance;
	}
	
	public String input(){
		String tmp = ""+ret_input;
		ret_input = null;
		return tmp;
	}
	
	/**
	 * Output request performed by the application
	 * @param string The output string
	 * @param tag The output request tag
	 */
	public void output(String string, Tag tag){
		
		ret_output = string;
		inner_tag = tag;
				
		changeStatsPane();
		
		if (tag == Tag.Out_Deal){
			btn[BTN_BET].setEnabled(false);
			btn[BTN_DEAL].setEnabled(false);
			btn[BTN_HOLD].setEnabled(true);
			btn[BTN_ADVICE].setEnabled(true);
			changeIcons(player);
			changeCredits(player);
		} else if (tag == Tag.Out_Hold){
			btn[BTN_BET].setEnabled(true);
			btn[BTN_DEAL].setEnabled(true);
			btn[BTN_HOLD].setEnabled(false);
			btn[BTN_ADVICE].setEnabled(false);
			changeIcons(player);
		} else if (tag == Tag.Out_Results || tag == Tag.Out_Advice){
			showResults();
		} else if (tag == Tag.Out_Bet){
			changeCredits(player);
			btn[BTN_DEAL].setEnabled(true);
		} else if (tag == Tag.Out_GameOver){
			System.out.println("Game Over!");
		}
	}
	
	void actionHappened()
	{
		if(inner_tag == Tag.In_Bet){
			int initial_credit = slider_bet.getValue();
			String inner_ret_input = new String("b");
			
			if(initial_credit <= 0)
				return;

			ret_input = inner_ret_input.concat(" " + Integer.toString(initial_credit));
		}
	}
	
	/**
	 * Change whether the card at index i is held
	 * @param i Index of the card to be held (0 indexed)
	 */
	void changeHold(int i){
		if (hold[i] == true){
			hold[i] = false;
		} else {
			hold[i] = true;
		}
	}	
	
	public static int getInitialCredit(){
		GUI firstGUI = GUI.getGUI();
		String[] args = new String[2];
		Boolean[] forward = new Boolean[2];
		int initial_credit = 0;
		forward[0] = new Boolean(false);
		forward[1] = new Boolean(false);
		firstGUI.prepareSelectorGUI(args,forward);
		
		while(!forward[0]){
			System.out.print("");
			if(forward[1]){
				try {
					initial_credit = Integer.parseInt(args[1]);
				} catch (NumberFormatException e){
					continue;
				}
				if(initial_credit > 0)
					forward[0] = true;
			}
		}
		return initial_credit;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void prepareSelectorGUI(String[] args, Boolean[] forward){
		
		main_frame = new JFrame("Video Poker - Insert $");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p = new JPanel();
		main_frame.setContentPane(p);
		main_frame.setPreferredSize(new Dimension(300, 100));
		main_frame.setResizable(false);
		
		layout = new SpringLayout();
		content_pane = main_frame.getContentPane();
		content_pane.setLayout(layout);
		
		JLabel lab_credit = new JLabel("Insert credit:");
		JTextField txt_field_bet = new JTextField(10);
		JButton btn_play = new JButton("Play");
		
		layout.putConstraint(SpringLayout.WEST, lab_credit, 33, SpringLayout.WEST, content_pane);
		layout.putConstraint(SpringLayout.NORTH, lab_credit, 20, SpringLayout.NORTH, content_pane);
		layout.putConstraint(SpringLayout.WEST, txt_field_bet, 5, SpringLayout.EAST, lab_credit);
		layout.putConstraint(SpringLayout.NORTH, txt_field_bet, 20, SpringLayout.NORTH, content_pane);
		layout.putConstraint(SpringLayout.NORTH, btn_play, 50, SpringLayout.NORTH, content_pane);
		layout.putConstraint(SpringLayout.EAST, btn_play, 0, SpringLayout.EAST, txt_field_bet);
		
		p.add(lab_credit);
		p.add(txt_field_bet);
		p.add(btn_play);
		
		btn_play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				args[1] = txt_field_bet.getText();
				forward[1] = true;
			}
		});
		
		main_frame.pack();
		// Centre the window
		main_frame.setLocationRelativeTo(null);
		main_frame.setVisible(true);
	}
	
	public void prepareRefactoredInteractiveGUI(Player player, Statistics stats){
		
		this.player = player;
		this.stats = stats;
		
		main_frame.setVisible(false);
		main_frame = new JFrame("Video Poker");
		main_frame.setSize(1000, 500);
		main_frame.setPreferredSize(new Dimension(1000, 500));
		main_frame.setResizable(false);
		main_frame.setLocationRelativeTo(null);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for (int i = 0; i < 5; i++){
			hold[i] = false;
		}
			
		p = new JPanel();
		//p.setSize(1200,500);
		main_frame.setContentPane(p);
		
		layout = new SpringLayout();
		content_pane = main_frame.getContentPane();
		content_pane.setLayout(layout);
		
		String arg = "cards/b.gif";
		String[] btn_arg = {"Bet", "Deal", "Hold", "Advice", "Stats"};
		
		cards[0] = new LabelWithCoordinates(arg, 40, 20, content_pane, content_pane, layout);
		tglbtn[0] = new ToggleButtonWithCoordinates("Hold Card", 36, 20, cards[0], content_pane, layout, 80, 20);
		btn[0] = new ButtonWithCoordinates(btn_arg[0], 20, 20, tglbtn[0], content_pane, layout);
		
		for (int i = 1; i < 5; i++){
			cards[i] = new LabelWithCoordinates(arg, 25, 20, content_pane, cards[i-1], layout);
			tglbtn[i] = new ToggleButtonWithCoordinates("Hold Card", 22, 20, cards[i-1], cards[i-1], layout, 80, 20);
			btn[i] = new ButtonWithCoordinates(btn_arg[i], 20, (i == 1)? 30 : 20, btn[i-1], content_pane, layout);
			btn[i].setEnabled(false);
		}
		
		for (int i = 0; i<5; i++){
			tglbtn[i].addActionListener(new HoldListener(i));
			p.add(cards[i]);
			p.add(tglbtn[i]);
			p.add(btn[i]);
		}
		
		/* Bet slider */
		slider_bet = new JSlider(JSlider.HORIZONTAL, 1, 5, 5);
		slider_bet.setMajorTickSpacing(5);
		slider_bet.setMinorTickSpacing(1);
		slider_bet.setPaintLabels(true);
		slider_bet.setPaintTicks(true);
		slider_bet.setLabelTable(slider_bet.createStandardLabels(1));
		slider_bet.setSnapToTicks(true);

		layout.putConstraint(SpringLayout.WEST, slider_bet, 20, SpringLayout.EAST, btn[0]);
		layout.putConstraint(SpringLayout.NORTH, slider_bet, 20, SpringLayout.SOUTH, tglbtn[0]);
		
		/* Player credit label */
		JLabel lab_credit = new JLabel("Player Credit:");
		layout.putConstraint(SpringLayout.WEST, lab_credit, 20, SpringLayout.EAST, slider_bet);
		layout.putConstraint(SpringLayout.NORTH, lab_credit, 20, SpringLayout.SOUTH, tglbtn[0]);
		
		/* Credit text field */
		txt_field_credit = new JTextField(10);
		txt_field_credit.setEditable(false);
		changeCredits(player);
		layout.putConstraint(SpringLayout.WEST, txt_field_credit, 5, SpringLayout.EAST, lab_credit);
		layout.putConstraint(SpringLayout.NORTH, txt_field_credit, 20, SpringLayout.SOUTH, tglbtn[0]);
		
		txt_field_advice = new JTextField(35);
		txt_field_advice.setEditable(false);
		layout.putConstraint(SpringLayout.WEST, txt_field_advice, 20, SpringLayout.EAST, btn[3]);
		layout.putConstraint(SpringLayout.NORTH, txt_field_advice, 20, SpringLayout.SOUTH, btn[2]);
		
		txt_stats = new JTextArea();
		txt_stats.setEditable(false);
		changeStatsPane();
		layout.putConstraint(SpringLayout.WEST, txt_stats, 570, SpringLayout.WEST, content_pane);
		layout.putConstraint(SpringLayout.NORTH, txt_stats, 20, SpringLayout.NORTH, content_pane);
		
		p.add(slider_bet);
		p.add(txt_field_credit);
		p.add(lab_credit);
		p.add(txt_field_advice);
		p.add(txt_stats);
		
		btn[BTN_BET].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int initial_credit = slider_bet.getValue();
				String inner_ret_input = new String("b");
				if (initial_credit <= 0){
					return;
				}
				ret_input = inner_ret_input.concat(" " + Integer.toString(initial_credit));
			}
		});
		
		btn[BTN_DEAL].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret_input = "d";
			}
		});
		
		btn[BTN_HOLD].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String int_ret_input = new String("h");
				for (int i = 0; i < 5; i++){
					if (hold[i] == true){
						int_ret_input = int_ret_input.concat(" " + Integer.toString(i+1));
					}
				}
				ret_input = int_ret_input;
				unclickAll();
			}
		});
		
		btn[BTN_ADVICE].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret_input = "a";
			}
		});
		
		btn[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret_input = "s";
			}
		});
		
		main_frame.setVisible(true);
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
		txt_field_credit.setText(Integer.toString(player.getCredit()));
	}
	
	void showResults(){
		txt_field_advice.setText(ret_output);
	}
	
	void unclickAll(){
		for(int i = 0; i < 5; i++){
			if (hold[i] == true)
				tglbtn[i].doClick();	
		}
	}
	
	void changeStatsPane(){
		String str_stats = stats.printStatistics(player.getCredit());
		txt_stats.setText(str_stats);
	}
}
