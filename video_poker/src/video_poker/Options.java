package video_poker;

/**
 * Simplified command-line argument framework 
 */
public class Options {
	
	/** The chosen game mode */
	public Mode mode;
	/** The (required) initial credit */
	public int initial_credit;
	/** The command file needed for debug mode */
	public String cmd_file;
	/** The card file needed for debug mode */
	public String card_file;
	/** Value of bet to be played each deal in simulation mode */
	public int bet;
	/** Number of deals for simulation mode */
	public int nb_deals;
	
	/** Current program version */
	private final String VERSION = "v0.5";
	private final String DEFAULT_CMD_FILE = "cmd-file.txt";
	private final String DEFAULT_CARD_FILE = "card-file.txt";
	
	public Options(String args[]){
		
		if (args.length > 1){
			
			try {
				initial_credit = Integer.parseInt(args[1]);
			} catch (NumberFormatException e){
				System.err.println("Invalid credit provided!");
				return;
			}
			
			if (args[0].equals("-g")){
				// GUI Option chosen. Further options are chosen from GUI
				// TODO
				return;
			}else if (args[0].equals("-i")){
				// Interactive mode chosen
				mode = new Interactive();
			}else if (args[0].equals("-d")){
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
				mode = new Debug();
				
			}else if (args[0].equals("-s")){
				// Simulation mode chosen
				if (args.length == 4){
					try {
						bet = Integer.parseInt(args[2]);
						nb_deals = Integer.parseInt(args[3]);
					} catch (NumberFormatException e){
						System.err.println("Invalid bet or number of deals provided!");
					} 
					// TODO Safety check value of bet
					mode = new Simulation();
				}
			}else{
				printUsage();
			}
		}else{
			printUsage();
		}
	}

	private void printUsage() {
		System.out.print(
			"Video Poker OOP Project 2017 - Group 29 " + VERSION + '\n' +
			"usage: java -jar videopoker.jar [mode] [credit] [args]\n" +
			"Modes:\n"+
			"\t-i [credit] \t\t\t\t Interactive mode\n" +
			"\t-d [credit] [cmd_file] [card_file] \t Debug mode - requires command and card files\n" +
			"\t-s [credit] [bet] [nbdeals] \t\t Simulation mode\n" +
			"\t-g \t\t\t\t\t Graphical user interface mode\n"
		);
	}
	
	public static void main(String args[]){
		Options opt = new Options(args);
		System.out.println(opt.mode);
		System.out.println(opt.initial_credit);
		System.out.println(opt.cmd_file);
		System.out.println(opt.card_file);
	}
}
