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
	/** Card images */
	ImageIcon[] icon;
	/** Main panel */
	private JPanel panel;
	/** Main frame content pane */
	private Container content_pane;
	/** Spring layout instance */
	private SpringLayout layout;
	
	/* UI */
	
	/** Slider to choose bet value */
	private JSlider slider_bet;
	/** Display current player credit */
	private JTextField txt_field_credit;
	/** Display both advice and round outcome */
	private JTextField txt_field_advice;
	/** Display game statistics */
	private JTextField[] table_txt;
	/** Toggle buttons for holding each of the cards */
	private JToggleButton[] tglbtn = new JToggleButton[5];
	/** Bet, Deal, Hold and Advice buttons */
	private JButton[] btn = new JButton[4];
	
	/** Determines which cards to be held */
	private Boolean[] hold = new Boolean[5];
	/** Input string for I/O handler requests */
	public String ret_input = new String();
	/** Last string successfully sent to I/O handler (prevent duplicates) */
	private String last_input = new String();
	/** Last I/O interaction tag */
	private Tag inner_tag;
	
	/** The player */
	private Player player;
	/** The game statistics */
	private Statistics stats;
	
	/* Button integer IDs */
	
	/** Button Bet Index */
	private final int BTN_BET = 0;
	/** Button Deal Index */
	private final int BTN_DEAL = 1;
	/** Button Hold Index */
	private final int BTN_HOLD = 2;
	/** Button Advice Index */
	private final int BTN_ADVICE = 3;
	
	/** The current GUI instance - Singleton Design Pattern */
	private static GUI instance;
	
	/**
	 * Private Constructor. Ensures a single instance of GUI
	 */
	private GUI(){
		JFrame.setDefaultLookAndFeelDecorated(false);
		last_input = null;
	}
	
	/**
	 * Ensure there is only a single instance of the GUI.
	 * If the object does not exist yet, create it
	 * @return The GUI object
	 */
	public static GUI getGUI(){
		if (instance == null){
			// Call private constructor
			instance = new GUI();
		}
		return instance;
	}
	
	/**
	 * Deliver requested input
	 * @return String encoding user actions
	 */
	public String input(){
		String tmp = ""+ret_input;
		ret_input = null;
		if (last_input == null){
			last_input = tmp;
		}
		if (last_input.equals(tmp)){
			// Prevent busy waiting
			try {
			    Thread.sleep(200);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			return null;
		}
		else{
			last_input = tmp;
			return tmp;
		}
		
	}
	
	/**
	 * Output request performed by the application
	 * @param string The output string
	 * @param tag The output request tag
	 */
	public void output(String string, Tag tag){
		
		inner_tag = tag;
				
		updateStats();
		
		if (tag == Tag.Out_Deal){
			btn[BTN_BET].setEnabled(false);
			btn[BTN_DEAL].setEnabled(false);
			btn[BTN_HOLD].setEnabled(true);
			btn[BTN_ADVICE].setEnabled(true);
			changeIcons();
			changeCredits();
		} else if (tag == Tag.Out_Hold){
			btn[BTN_BET].setEnabled(true);
			btn[BTN_DEAL].setEnabled(true);
			btn[BTN_HOLD].setEnabled(false);
			btn[BTN_ADVICE].setEnabled(false);
			changeIcons();
		} else if (tag == Tag.Out_Results || tag == Tag.Out_Advice){
			showResults(string);
		} else if (tag == Tag.Out_Bet){
			changeCredits();
			btn[BTN_DEAL].setEnabled(true);
		} else if (tag == Tag.Out_GameOver){
			System.out.println("Game Over!");
		}
	}
	
	/**
	 * Update inner bet value
	 */
	void actionHappened(){
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
	
	/**
	 * Prompt the user for an initial credit value
	 * @return The initial player credit
	 */
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
			// Prevent busy waiting
			try {
			    Thread.sleep(200);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		return initial_credit;
	}
	
	/**
	 * Prepares the initial GUI, where the user is prompted for initial credit
	 * @param args The required arguments
	 * @param forward Whether to progress with execution
	 */
	public void prepareSelectorGUI(String[] args, Boolean[] forward){
		
		main_frame = new JFrame("Video Poker - Insert $");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		main_frame.setContentPane(panel);
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
		
		panel.add(lab_credit);
		panel.add(txt_field_bet);
		panel.add(btn_play);
		
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
	
	/**
	 * Prepare main GUI window
	 * @param player The player
	 * @param stats The game statistics
	 */
	public void prepareRefactoredInteractiveGUI(Player player, Statistics stats){
		
		this.player = player;
		this.stats = stats;
		
		main_frame.setVisible(false);
		main_frame = new JFrame("Video Poker");
		main_frame.setSize(950, 400);
		main_frame.setPreferredSize(new Dimension(950, 400));
		main_frame.setResizable(false);
		main_frame.setLocationRelativeTo(null);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for (int i = 0; i < 5; i++){
			hold[i] = false;
		}
			
		panel = new JPanel();
		main_frame.setContentPane(panel);
		
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
		}
		for (int i = 1; i < 4; i++){
			btn[i] = new ButtonWithCoordinates(btn_arg[i], 20, (i == 1)? 30 : 20, btn[i-1], content_pane, layout);
			btn[i].setEnabled(false);
		}
		
		for (int i = 0; i < 5; i++){
			tglbtn[i].addActionListener(new HoldListener(i));
			panel.add(cards[i]);
			panel.add(tglbtn[i]);
		}
		for (int i = 0; i < 4; i++){
			panel.add(btn[i]);
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
		
		/* Player credit text field */
		txt_field_credit = new JTextField(10);
		txt_field_credit.setEditable(false);
		changeCredits();
		layout.putConstraint(SpringLayout.WEST, txt_field_credit, 5, SpringLayout.EAST, lab_credit);
		layout.putConstraint(SpringLayout.NORTH, txt_field_credit, 20, SpringLayout.SOUTH, tglbtn[0]);
		
		/* Advice text field */
		txt_field_advice = new JTextField(35);
		txt_field_advice.setEditable(false);
		layout.putConstraint(SpringLayout.WEST, txt_field_advice, 20, SpringLayout.EAST, btn[3]);
		layout.putConstraint(SpringLayout.NORTH, txt_field_advice, 20, SpringLayout.SOUTH, btn[2]);

		/* Add components to main panel */
		panel.add(slider_bet);
		panel.add(txt_field_credit);
		panel.add(lab_credit);
		panel.add(txt_field_advice);
		
		/* Statistics table */
		table_txt = new JTextField[12];
		for(int i = 0; i < 12; i++){
			table_txt[i] = new JTextField(30);
			layout.putConstraint(SpringLayout.WEST, table_txt[i], 570, SpringLayout.WEST, content_pane);
			layout.putConstraint(SpringLayout.NORTH, table_txt[i], (i==0)? 20 : 0, (i==0)? SpringLayout.NORTH : SpringLayout.SOUTH, (i==0)? content_pane : table_txt[i-1]);
			table_txt[i].setEditable(false);
			panel.add(table_txt[i]);
		}
		updateStats();
		
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
		
		main_frame.setVisible(true);
	}
	
	/**
	 * Change card icons in hand
	 */
	void changeIcons(){
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
	
	/**
	 * Change the player's credit text field
	 */
	void changeCredits(){
		txt_field_credit.setText(Integer.toString(player.getCredit()));
	}
	
	/**
	 * Show round results
	 * @param string The round result
	 */
	void showResults(String string){
		txt_field_advice.setText(string);
	}
	
	/**
	 * Click each held card's respective toggle button.
	 * In the end, each button should not be pressed.
	 */
	void unclickAll(){
		for(int i = 0; i < 5; i++){
			if (hold[i] == true)
				tglbtn[i].doClick();	
		}
	}
	
	/**
	 * Update main game statistics table
	 */
	void updateStats(){
		String[] split_output = stats.printStatistics(player.getCredit()).split("\\n");
		String internal_concatenation = new String("");
		for(int i = 0; i < 12; i++){
			String[] split_split = split_output[i].split("\\s+");
			internal_concatenation = "";
			for(int e = 0; e < split_split.length - 1; e++){
				internal_concatenation = internal_concatenation.concat(split_split[e] + " ");
			}
			if(i != 11){
				internal_concatenation = internal_concatenation.concat("\t\t" + split_split[split_split.length - 1]);
			}
			else{
				internal_concatenation = "";
				internal_concatenation = internal_concatenation.concat(split_split[0] + "\t\t" + split_split[1] + split_split[2]);
			}
			table_txt[i].setText(internal_concatenation);
		}
		
	}
	
	/**
	 * Creates a table panel, made of several JTextField components
	 * @return The generated table
	 */
	JPanel makeTablePanel(){
		table_txt = new JTextField[12];
		JPanel holding_area = new JPanel();
		holding_area.setLayout(layout);
		for(int i = 0; i < 12; i++){
			table_txt[i] = new JTextField(50);
			layout.putConstraint(SpringLayout.WEST, table_txt[i], 20, SpringLayout.WEST, holding_area);
			layout.putConstraint(SpringLayout.NORTH, table_txt[i], 20, (i==0)? SpringLayout.NORTH : SpringLayout.SOUTH, (i==0)? holding_area : table_txt[i-1]);
			table_txt[i].setEditable(false);
			holding_area.add(table_txt[i]);
		}
		return holding_area;
	}
}