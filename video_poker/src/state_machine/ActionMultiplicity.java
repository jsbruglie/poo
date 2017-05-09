package state_machine;

/**
 * Defines action parameter multiplicity
 */
public enum ActionMultiplicity {
	/** Action accepts no parameters */
	ZERO,
	/** Action accepts zero or more parameters */
	ZERO_OR_MANY,
	/** Action accepts one or many parameters */
	MANY;
}
