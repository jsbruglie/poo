package video_poker;

/**
 * Simplified command-line argument framework 
 */
public class Options {
	
	/** The chosen game mode */
	public String mode;
	/** The initial credit */
	public int initial_credit;
	/** The command file needed for debug mode */
	public String cmd_file;
	/** The card file needed for debug mode */
	public String card_file;
	/** Value of bet to be played each deal in simulation mode */
	public int bet = 0;
	/** Number of deals for simulation mode */
	public int nb_deals;
	
	/** Current program version */
	private final static String VERSION = "dev 0.8_rev42";
	// Use relative paths
	private final String WORKING_DIRECTORY = "";
	private final String DEFAULT_CMD_FILE = WORKING_DIRECTORY + "TESTS/"+ "cmd-file.txt";
	private final String DEFAULT_CARD_FILE = WORKING_DIRECTORY + "TESTS/"+ "card-file.txt";
	
	/**
	 * Constructor
	 * @param args Command-line arguments
	 */
	public Options(String args[]){
		
		if(args == null || args.length == 0){
			printUsage();
			System.exit(1);
		}
		
		mode = null;
		
		if (args[0].equals("-g")){
			// GUI mode chosen
			mode = "GUI";
			return;
		}
		
		if (args.length > 1){
			
			try {
				initial_credit = Integer.parseInt(args[1]);
			} catch (NumberFormatException e){
				System.err.println("Invalid credit provided!");
				return;
			}
			
			if (args[0].equals("-i")){
				// Interactive mode chosen
				mode = "Interactive";
			} else if (args[0].equals("-d")){
				// Debug mode chosen
				if (args.length == 2){
					cmd_file = DEFAULT_CMD_FILE;
					card_file = DEFAULT_CARD_FILE;
				}else if (args.length == 4){
					cmd_file = args[2];
					card_file = args[3];
				}else{
					printUsage();
					return;
				}
				mode = "Debug";
				
			} else if (args[0].equals("-s")){
				// Simulation mode chosen
				if (args.length == 4){
					try {
						bet = Integer.parseInt(args[2]);
						nb_deals = Integer.parseInt(args[3]);
					} catch (NumberFormatException e){
						System.err.println("Invalid bet or number of deals provided!");
					} 
					// Check if bet is an integer between 1 and 5 and valid nb_deals
					if(Game.isBetValid(bet) && nb_deals > 0){ 
						mode = "Simulation";
					}else{
						printUsage();
					}
				} else {
					printUsage();
				}
			}else{
				printUsage();
			}
		}else{
			printUsage();
		}
		
		// Check if correct values are present
		if (initial_credit < 0){
			System.err.println("Invalid initial credit provided");
			System.exit(-1);
		}
	}

	private static void printUsage() {
		System.out.print(
			"Video Poker OOP Project 2017 - Group 29 " + VERSION + '\n' +
			"usage: java -jar videopoker.jar [mode] [credit] [args]\n" +
			"Modes:\n"+
			"\t-i [credit] \t\t\t\tInteractive mode\n" +
			"\t-d [credit] [cmd_file] [card_file]\tDebug mode - requires command and card files\n" +
			"\t-s [credit] [bet] [nbdeals]\t\tSimulation mode\n" +
			"\t-g \t\t\t\t\tGraphical user interface mode\n"
		);
	}
}
