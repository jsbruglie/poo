package state_machine;

import static state_machine.ActionMultiplicity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import video_poker.Game;

/**
 * Defines possible actions that trigger state transitions
 */
public enum Action {
	
	/** Change bet action */
	BET(	"b", ZERO_OR_MANY, 1),
	/** Deal hand action */
	DEAL(	"d", ZERO),
	/** Hold up to 5 cards action */
	HOLD(	"h", ZERO_OR_MANY, Game.HAND_SIZE),
	/** Request advice action */
	ADVICE(	"a", ZERO),
	/** Request statistics action */
	STATS(	"s", ZERO),
	/** Request balance action */
	BALANCE("$", ZERO),
	/** Quit game action */
	QUIT(	"q", ZERO),
	/** Results automatic action */
	RESULTS(),
	/** Shuffle automatic action */
	SHUFFLE(),
	/** Invalid action */
	INVALID();
	
	/** The action trigger command */
	private String command;
	/** The action parameter multiplicity */
	private ActionMultiplicity multiplicity;
	/** The maximum number of parameters accepted */
	private int max_params;
	
	/**
	 * Constructor for automatic actions
	 */
	Action(){
		this.command = null;
		this.multiplicity = null;
		this.max_params = -1;
	}
	
	/**
	 * Constructor for actions that accept no parameters
	 * @param command The trigger command
	 * @param multiplicity The parameter multiplicity
	 */
	Action(String command, ActionMultiplicity multiplicity) {
		this.command = command;
		this.multiplicity = multiplicity;
		this.max_params = 0;
	}
	
	/**
	 * Constructor for action that accept parameters
	 * @param command The trigger command
	 * @param multiplicity The parameter multiplicity
	 * @param max_params The parameter multiplicity
	 */
	Action(String command, ActionMultiplicity multiplicity, int max_params) {
		this.command = command;
		this.multiplicity = multiplicity;
		this.max_params = max_params;
	}
	
	/**
	 * Obtain an action by its command
	 * @param command The trigger command
	 * @param params The list of parameters
	 * @return The corresponding Action enum
	 */
	public static Action fromString(String command, List<String> params){
		
		if (command != null){
			
			List<String> split = new ArrayList<String>(Arrays.asList(command.trim().split("\\s+")));
			
			for (Action e : Action.values()){
				if (e.multiplicity == ZERO) {
					if (split.size() == 1){
						if (e.command.equals(split.get(0))){
							return e;
						}
					}
				} else if (e.multiplicity == ZERO_OR_MANY) {
					if (split.size() == 1){
						if (e.command.equals(split.get(0))){
							return e;
						}
					} else if(split.size() > 1 && split.size() <= e.max_params + 1){
						if (e.command.equals(split.get(0))){
							split.remove(0);
							for (String s : split){
								params.add(s);
							}	
							return e;
						}
					}
				}
			}
			
		}
		return INVALID;
	}
}
