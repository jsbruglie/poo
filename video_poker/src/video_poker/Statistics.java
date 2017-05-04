package video_poker;

/**
 * Statistics class for keeping track of hand occurrences and displaying relevant stats
 */
public class Statistics {

	/** Array that stores the occurrence of the different combinations */
	private int number_hand_occurrences[];
	/** Total number of deals */
	private int number_deals;
	/** Initial player credit */
	private int initial_player_credit;
	
	/* Auxiliary format strings */
	private final String format1 = "%-20s%s%n";
	private final String format2 = "%-20s%d%n";
	private final String format3 = "%-17s%d (%.2f) %n";
	
	/**
	 * Constructor
	 * @param credit Initial player credit
	 * @param combinations 
	 */
	public Statistics(int credit, Combination[] combinations){
		this.number_hand_occurrences = new int[CombinationDB10_7.values().length];
		this.number_deals = 0;
		this.initial_player_credit = credit;
		
	}
	
	/**
	 * Adds a given combination to the occurrence table
	 * @param combination The combination to be added
	 */
	public void addResults(CombinationDB10_7 combination){
		number_hand_occurrences[combination.ordinal()]++;
		number_deals++;
	}
	
	/**
	 * Prints statistics regarding hand occurrences and wins/losses
	 * @param curr_credit The current player credit
	 */
	public void printStatistics(int curr_credit){
		
		System.out.printf(format1, "Hand", "Nb");
		CombinationDB10_7 comb[] = CombinationDB10_7.values();
		for (int i = 0; i < comb.length; i++){
			if (i == CombinationDB10_7.Four2_4.ordinal()){
				// Group all possible Four of a Kind combinations
				System.out.printf(format2, "Four of a Kind",
						number_hand_occurrences[CombinationDB10_7.Four2_4.ordinal()] +
						number_hand_occurrences[CombinationDB10_7.Four5_K.ordinal()] +
						number_hand_occurrences[CombinationDB10_7.FourAces.ordinal()]);
				i += 2;
			}else{
			      System.out.printf(format2, comb[i], number_hand_occurrences[i]);
			}
		}
		
		System.out.printf(format2, "Total", number_deals);
		System.out.printf(format3, "Credit", curr_credit,
				((float)curr_credit / (float)initial_player_credit) * 100.0);
	}
}
