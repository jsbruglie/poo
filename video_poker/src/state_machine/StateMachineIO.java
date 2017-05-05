package state_machine;

public interface StateMachineIO {

	void out(String string);

	void outForced(String string);
	
	void errOut(String string);

	String input(State state);
}
