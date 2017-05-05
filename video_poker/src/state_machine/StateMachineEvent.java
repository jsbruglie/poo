package state_machine;

import java.util.List;

public interface StateMachineEvent {

	public State execute();

	public State execute(List<String> split);
}
