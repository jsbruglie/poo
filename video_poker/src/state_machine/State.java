package state_machine;

public class State {
	
	public final String name;
	public final boolean has_input;
	private State[][] transitions;
	
	private final int SUCCESS = 0;
	private final int FAILURE = 1;
	
	State(String name, boolean has_input){
		this.name = name;
		this.has_input = has_input;
		this.transitions = new State[Event.values().length][2];
	}
	
	void addTransition(Event event, State on_success, State on_failure){
		transitions[event.ordinal()][SUCCESS] = on_success;
		transitions[event.ordinal()][FAILURE] = on_failure;
	}
	
	public boolean valid(Event event){
		return (transitions[event.ordinal()][0] != null);
	}	
}