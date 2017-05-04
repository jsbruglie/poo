package video_poker;

public abstract class PayTable {
	
	private int[][] table;
	private final int MAX_BET = 5;
	
	public PayTable(){
		table = new int[Combination.values().length][5];
	};
	
	public int getPayout(Combination c, int bet){
		if (bet > 0 && bet <= MAX_BET){
			return table[c.ordinal()][bet - 1];
		}
	return 0;
	}
	
	public void setTable(int table[][]){
		if (table != null){
			if (table.length == Combination.values().length){
				if (table[0].length == MAX_BET){
					this.table = table;
				}
			}
		}
	}
}