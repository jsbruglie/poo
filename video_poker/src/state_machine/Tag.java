package state_machine;

/**
 * Tags used in communications between commands executed by the state machine
 * and the respective I/O handlers
 */
public enum Tag {
	/** Require input to change bet */
	In_Bet,
	/** Notify that bet was changed */
	Out_Bet,
	/** Require input to deal hand */
	In_Deal,
	/** Notify that hand was dealt */
	Out_Deal,
	/** Require cards to be held */
	In_Hold,
	/** Notify that a new hand was dealt, after successful hold */
	Out_Hold,
	/** Notify Game Over */
	Out_GameOver,
	/** Show round outcome */
	Out_Results,
	/** Show requested advice */
	Out_Advice,
	/** Show requested statistics */
	Out_Stats,
	/** Show requested balance */
	Out_Balance,
	/** Generic error */
	Error;
}
