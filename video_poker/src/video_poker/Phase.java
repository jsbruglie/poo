package video_poker;

public enum Phase {
	Bet,
	Deal,
	Hold,
	Results {
        @Override
        public Phase next() {
            return null; // see below for options for this line
        };
    };
	public Phase next() {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal() + 1];
    }
}
