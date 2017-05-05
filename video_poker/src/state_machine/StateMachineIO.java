package state_machine;

public interface StateMachineIO {

	void out(String string);

	void errOut(String string);

	String input();

	
}
