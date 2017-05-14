package state_machine;

/**
 * State class for defining available states of program execution
 */
public class State {
	
	/** State Name enum */
	public enum StateName{
		/** State First Bet */
		STATE_FIRST_BET,
		/** State Deal */
		STATE_DEAL,
		/** State Hold */
		STATE_HOLD,
		/** State Results */
		STATE_RESULTS,
		/** State Shuffle */
		STATE_SHUFFLE;
	}
	
	/** The state name enumerator */
	public final StateName name;
	/** Whether the state accepts input */
	public final boolean accepts_input;
	/** Whether the state has a default behaviour (automatic events) */
	public final boolean has_default_behaviour;
	/** A transition map describing an outcome for a an action, for both success and failure cases */
	private State[][] transitions;
	/** Whether the state is triggered by a given event */
	private boolean[] triggered_by;
	/** The default (automatic) action to be executed */
	private final Action default_behaviour;
	
	/** Index of successful action outcome in transition map */
	private final int SUCCESS = 0;
	/** Index of unsuccessful action outcome in transition map */
	private final int FAILURE = 1;
	
	/**
	 * Constructor for state with no default action
	 * @param name The state name
	 * @param accepts_input Whether the state accepts input
	 */
	State(StateName name, boolean accepts_input){
		this(name, accepts_input, null);
	}
	
	/**
	 * Constructor
	 * @param name The state name
	 * @param accepts_input Whether the state accepts input
	 * @param default_behaviour The default action to be executed
	 */
	State(StateName name, boolean accepts_input, Action default_behaviour){
		this.name = name;
		this.accepts_input = accepts_input;
		this.transitions = new State[Action.values().length][2];
		triggered_by = new boolean[Action.values().length];
		if (default_behaviour != null){
			has_default_behaviour = true;
		} else {
			has_default_behaviour = false;
		}
		this.default_behaviour = default_behaviour;
	}
	
	/**
	 * Add a transition for a given action
	 * @param action The trigger action
	 * @param on_success The result state if action is successful
	 * @param on_failure The result state if action is unsuccessful
	 */
	void addTransition(Action action, State on_success, State on_failure){
		transitions[action.ordinal()][SUCCESS] = on_success;
		transitions[action.ordinal()][FAILURE] = on_failure;
		triggered_by[action.ordinal()] = true;
	}
	
	/**
	 * Whether an action is valid in this state
	 * @param action The action to be evaluated
	 * @return Whether action is valid
	 */
	public boolean isValid(Action action){
		if (action != null){
			return triggered_by[action.ordinal()];
		}
		return false;
	}
	
	/**
	 * @return Default (automatic) action to be executed
	 */
	public Action getDefaultBehaviour(){
		return default_behaviour;
	}
	
	/**
	 * Obtain the resulting state after an action is performed
	 * @param action The performed action
	 * @param success Whether the action was successful
	 * @return The resulting state
	 */
	public State getEventOutcome(Action action, boolean success){
		if (action == null){
			return this;
		}
		int index = (success)? 0 : 1;
		State next = transitions[action.ordinal()][index];
		return next;
	}

	@Override
	public String toString() {
		return "State " + name;
	}
}