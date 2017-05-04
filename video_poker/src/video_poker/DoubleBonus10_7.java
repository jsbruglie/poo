package video_poker;

public final class DoubleBonus10_7 extends PayTable {

	public DoubleBonus10_7(){
		super();
		super.setTable(new int[][]{
			{ 1,   2,   3,   4,	   5    },	// Jacks or Better
			{ 1,   2,   3,   4,    5    },	// Two Pair
			{ 3,   6,   9,   12,   15   },	// Three of a Kind
			{ 5,   10,  15,  20,   25   },	// Straight
			{ 7,   14,  21,  28,   35   },	// Flush
			{ 10,  20,  30,  40,   50   },	// Full House
			{ 50,  100, 150, 200,  250  },	// Four 5-K
			{ 80,  160, 240, 320,  400  },	// Four 2-4
			{ 160, 320, 480, 640,  800  },	// Four Aces
			{ 50,  100, 150, 200,  250  },	// Straight Flush
			{ 250, 500, 750, 1000, 4000 },	// Royal Flush
			{ 0,   0,   0,   0,    0    }	// Other - Invalid Combination
		});
	}
	
}
