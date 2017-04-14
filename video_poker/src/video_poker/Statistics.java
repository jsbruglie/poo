package video_poker;

public class Statistics {

	private int number_hand_occurences[];
	private int number_deals;
	private int initial_player_credit;
	
	public Statistics(int credit){
		this.number_hand_occurences = new int[10];
		for(int i=0; i<number_hand_occurences.length; i++){
			number_hand_occurences[i] = 0;
		}
		this.number_deals = 0;
		this.initial_player_credit = credit;
		
	}
	
	public void addResults(Combination comb){
		number_hand_occurences[comb.getValue()]++;
		number_deals++;
	}
	
	public void printStatistics(int current_player_credit){
		String format1 = "%-20s%d%n";
		String format2 = "%-20s%s%n";
		String format3 = "%-17s%d (%.2f) %n";
		System.out.printf(format2, "Hand", "Nb");
		System.out.printf(format1, "Jacks or Better", number_hand_occurences[0]);
		System.out.printf(format1, "Two Pair", number_hand_occurences[1]);
		System.out.printf(format1, "Three of a Kind", number_hand_occurences[2]);
		System.out.printf(format1, "Straight", number_hand_occurences[3]);
		System.out.printf(format1, "Flush", number_hand_occurences[4]);
		System.out.printf(format1, "Full House", number_hand_occurences[5]);
		System.out.printf(format1, "Four of a Kind", number_hand_occurences[6]);
		System.out.printf(format1, "Straight Flush", number_hand_occurences[7]);
		System.out.printf(format1, "Royal Flush", number_hand_occurences[8]);
		System.out.printf(format1, "Other", number_hand_occurences[9]);
		System.out.printf(format1, "Total", number_deals);
		System.out.printf(format3, "Credit", current_player_credit, ((float)current_player_credit/(float)initial_player_credit)*100.0);
	}
}
