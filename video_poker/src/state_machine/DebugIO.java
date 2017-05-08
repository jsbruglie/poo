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
		try{
			if (idx == commands.size()){
				return "q";
			}
		}catch(NullPointerException e){
			// TODO - refine this
			//e.printStackTrace();
			System.out.println("Could not get commands, if using one, command file is probably empty");
			System.exit(-1);
		}
		return commands.get(idx++);
	}
}
