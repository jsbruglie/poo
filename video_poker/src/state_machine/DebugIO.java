package state_machine;

import java.util.List;

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
		this.commands = commands;
	}
	
	@Override
	public void out(Tag tag, String string) {
		System.out.println(string);
	}
	
	@Override
	public void errOut(Tag tag, String string) {}

	@Override
	public String input(Tag tag) {
		try{
			if (idx == commands.size()){
				return "q";
			}
		}catch(NullPointerException e){
			// TODO - MOve this to constructor Illegal argument instead; and check at the source
			//e.printStackTrace();
			System.out.println("Could not get commands, if using one, command file is probably empty");
			System.exit(-1);
		}
		return commands.get(idx++);
	}
}
