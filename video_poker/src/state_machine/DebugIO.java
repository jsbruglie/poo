package state_machine;

import java.util.List;

/**
 * State Machine I/O Handler for Debug Mode
 */
public class DebugIO implements StateMachineIO {
	
	/** The list of in-game commands to be executed */
	private List<String> commands;
	/** The index of the current command */
	private int idx = 0;
	
	/**
	 * Constructor
	 * @param commands The list of in-game commands
	 */
	public DebugIO(List<String> commands){
		if (commands == null){
			System.err.println("Illegal command list provided.");
			throw new IllegalArgumentException();
		}
		this.commands = commands;
	}
	
	@Override
	public void out(Tag tag, String string) {
		System.out.println(string);
	}
	
	@Override
	public void errOut(Tag tag, String string) {
		System.out.println(string);
	}

	@Override
	public String input(Tag tag) {
		if (idx == commands.size()){
			return "q";
		}
		return commands.get(idx++);
	}
}
