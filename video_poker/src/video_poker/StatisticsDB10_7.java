package video_poker;

public class StatisticsDB10_7 implements Statistics {
	
	/** Total number of deals */
	private int number_deals;
	/** Initial player credit */
	private int initial_player_credit;
	/** THe valued combinations */
	private Combination[] combinations;
	/** Array that stores the occurrence of the different combinations */
	private int number_hand_occurrences[];
	
	/* Auxiliary format strings */
	private final String format1 = "%-20s%s%n";
	private final String format2 = "%-20s%d%n";
	private final String format3 = "%-17s%d (%.2f) %n";
	
	/**
	 * Constructor
	 * @param credit Initial player credit
	 * @param combinations 
	 */
	public StatisticsDB10_7(int credit, Combination[] combinations){
		this.number_deals = 0;
		this.initial_player_credit = credit;
		this.combinations = combinations;
		this.number_hand_occurrences = new int[combinations.length];
	}
	
	/**
	 * 
	 * @param combination
	 * @return
	 */
	private boolean validCombination(Combination combination){
		if (combination != null){
			for (Combination cb : combinations){
				if (combination.equals(cb)){
					return true;
				}
			}
		}	
		return false;
	}
	
	/**
	 * Adds a given combination to the occurrence table
	 * @param combination The combination to be added
	 */
	public void addResults(Combination combination){
		if (validCombination(combination)){
			number_hand_occurrences[combination.order]++;
		}	
		number_deals++;	
	}
	
	/**
	 * Prints statistics regarding hand occurrences and wins/losses
	 * @param current_credit The current player credit
	 */
	public void printStatistics(int current_credit){
		
		System.out.printf(format1, "Hand", "Nb");
		
		for (int i = combinations.length - 1; i >= 0; i--){
			Combination cb = combinations[i];
			/* All Four Of A Kind Combinations are shown as one entry */
			if (cb.name.equals("Four 2 to 4")){
				// Group all possible Four of a Kind combinations
				System.out.printf(format2, "Four of a Kind",
						number_hand_occurrences[combinations[i].order] +
						number_hand_occurrences[combinations[i-1].order] +
						number_hand_occurrences[combinations[i-2].order]);
				i -= 2;
			} else {
			      System.out.printf(format2, cb.name, number_hand_occurrences[i]);
			}
		}
		
		System.out.printf(format2, "Total", number_deals);
		System.out.printf(format3, "Credit", current_credit,
				((float)current_credit / (float)initial_player_credit) * 100.0);
	}
}
