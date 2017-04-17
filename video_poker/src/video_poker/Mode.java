package video_poker;

public abstract class Mode {
	int credit = 0;
	
	
	public int getCredit() {
		return credit;
	}

	//public abstract void parseArgs(String[] args);
	public abstract void execute(Score score, Player player, Deck deck, Statistics stats);
}
