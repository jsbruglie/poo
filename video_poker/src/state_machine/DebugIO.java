package state_machine;

import java.util.List;

public class DebugIO implements StateMachineIO {
	
	private List<String> commands;
	private int idx = 0;
	
	
	public DebugIO(List<String> commands){
		this.commands = commands;
	}
	
	@Override
	public void out(String string) {
		System.out.println(string);
	}

	@Override
	public void outForced(String string) {
		System.out.println(string);
	}
	
	@Override
	public void errOut(String string) {}

	@Override
	public String input(State state) {
		if (idx == commands.size()){
			return "q";
		}
		return commands.get(idx++);
	}
}
