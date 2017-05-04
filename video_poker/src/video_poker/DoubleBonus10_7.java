package video_poker;

import rules.*;

/**
 * 
 * 
 */
public class DoubleBonus10_7 extends Variant{

	/* Constants /
	
	/** TODO - The maximum number of cards that count towards a combination */
	private final int COMBINATION_SIZE = 5;
	/** Total number of valued combinations */
	private final int N_COMBINATIONS = 11;
	/** Pay Table */
	private final int[][] PAY_TABLE = {
		{ 250, 500, 750, 1000, 4000 },	// Royal Flush
		{ 50,  100, 150, 200,  250  },	// Straight Flush
		{ 160, 320, 480, 640,  800  },	// Four Aces
		{ 50,  100, 150, 200,  250  },	// Four 5-K
		{ 80,  160, 240, 320,  400  },	// Four 2-4
		{ 10,  20,  30,  40,   50   },	// Full House
		{ 7,   14,  21,  28,   35   },	// Flush
		{ 5,   10,  15,  20,   25   },	// Straight
		{ 3,   6,   9,   12,   15   },	// Three of a Kind
		{ 1,   2,   3,   4,    5    },	// Two Pair
		{ 1,   2,   3,   4,	   5    },	// Jacks or Better
	};
	
	/* Internal fields */
	
	/** Available combinations */
	private Combination[] combinations;
	
	/**
	 * Constructor
	 */
	DoubleBonus10_7(int initial_credit){
		
		Combination[] c = new Combination[N_COMBINATIONS];
		c[0] = new Combination("Royal Flush",		0, PAY_TABLE[0], new RoyalFlush());
		c[1] = new Combination("Straight Flush",	1, PAY_TABLE[1], new StraightFlush());
		c[2] = new Combination("Four Aces",			2, PAY_TABLE[2], new FourAces());
		c[3] = new Combination("Four5_K",			3, PAY_TABLE[3], new Four5_K());
		c[4] = new Combination("Four2_4",			4, PAY_TABLE[4], new Four2_4());
		c[5] = new Combination("Full House",		5, PAY_TABLE[5], new FullHouse());
		c[6] = new Combination("Flush",				6, PAY_TABLE[6], new Flush());
		c[7] = new Combination("Straight",			7, PAY_TABLE[7], new Straight());
		c[8] = new Combination("Three of a Kind",	8, PAY_TABLE[8], new ThreeOfAKind());
		c[9] = new Combination("Two Pair",			9, PAY_TABLE[9], new TwoPair());
		c[10] = new Combination("JacksOrBetter",	10, PAY_TABLE[10], new HighPair());
		
		combinations = c;
		
		score = new ScoreDB10_7(combinations);
		strategy = new Strategy();
		stats = new StatisticsDB10_7(initial_credit, combinations);
	}
	
	/**
	 * Evaluates whether a bet is valid or not
	 * @param bet
	 * @return
	 */
	public boolean isBetValid(int bet){
		if (1 <= bet && bet <= 5)
			return true;
		return false;
	}
	
	// TODO - Debug
	
	public static void main(String[] args){
		
		Variant db = new DoubleBonus10_7(10);
		int[] payout = { 250, 500, 750, 1000, 4000 };
		db.stats.addResults(new Combination("Royal Flush", 0, payout, new RoyalFlush()));
		db.stats.addResults(null);
		db.stats.printStatistics(10);
		
	}
}
