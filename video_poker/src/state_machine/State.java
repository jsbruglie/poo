package state_machine;

public class State {
	
	public enum StateName{
		ST_FIRST_BET,
		ST_DEAL,
		ST_HOLD,
		ST_RESULTS,
		ST_SHUFFLE;
	}
	
	public final StateName name;
	public final boolean has_input;
	public final boolean has_default_behaviour;
	private State[][] transitions;
	private boolean[] triggered_by;
	private final Event default_behaviour;
	
	private final int SUCCESS = 0;
	private final int FAILURE = 1;
	
	State(StateName name, boolean has_input){
		this(name, has_input, null);
	}
	
	State(StateName name, boolean has_input, Event default_behaviour){
		this.name = name;
		this.has_input = has_input;
		this.transitions = new State[Event.values().length][2];
		triggered_by = new boolean[Event.values().length];
		if (default_behaviour != null){
			has_default_behaviour = true;
		} else {
			has_default_behaviour = false;
		}
		this.default_behaviour = default_behaviour;
	}
	
	void addTransition(Event event, State on_success, State on_failure){
		transitions[event.ordinal()][SUCCESS] = on_success;
		transitions[event.ordinal()][FAILURE] = on_failure;
		triggered_by[event.ordinal()] = true;
	}
	
	public boolean valid(Event event){
		if (event != null){
			return triggered_by[event.ordinal()];
		}
		return false;
	}
	
	public Event getDefaultBehaviour(){
		return default_behaviour;
	}
	
	public State getEventOutcome(Event event, boolean success){
		if (event == null){
			return this;
		}
		int index = (success)? 0 : 1;
		State next = transitions[event.ordinal()][index];
		return next;
	}

	@Override
	public String toString() {
		return "State " + name;
	}
}