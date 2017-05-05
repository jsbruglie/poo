package state_machine;

import static state_machine.EventsMul.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Event {

	BET(	"b", ZERO_OR_MANY, 1),
	DEAL(	"d", ZERO),
	HOLD(	"h", ZERO_OR_MANY, 5),
	ADVICE(	"a", ZERO),
	STATS(	"s", ZERO),
	BALANCE("$", ZERO),
	QUIT(	"q", ZERO),
	AUTO();
	
	private String command;
	private EventsMul multiplicity;
	private int max_params;
	
	Event(){
		this.command = null;
		this.multiplicity = null;
		this.max_params = -1;
	}
	
	Event(String command, EventsMul multiplicity) {
		this.command = command;
		this.multiplicity = multiplicity;
		this.max_params = 0;
	}
	
	Event(String command, EventsMul multiplicity, int max_params) {
		this.command = command;
		this.multiplicity = multiplicity;
		this.max_params = max_params;
	}
	
	public static Event fromString(String command, List<String> params){
		
		if (command != null){
			
			List<String> split = new ArrayList<String>(Arrays.asList(command.split(" ")));
			
			for (Event e : Event.values()){
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
		return null;
	}
	
	public static void main(String[] args){
		
		List<String> out_params = new ArrayList<String>();
		// Bet command
		Event e0 = Event.fromString("b", out_params);
		System.out.println(e0);
		// Bet command with argument
		Event e1 = Event.fromString("b 5", out_params);
		System.out.println(e1 + " " + Arrays.toString(out_params.toArray()));
		// Invalid deal command
		Event e2 = Event.fromString("d 5", out_params);
		System.out.println(e2);
		// Hold command
		out_params = new ArrayList<String>();
		Event e3 = Event.fromString("h 1 2 3", out_params);
		System.out.println(e3 + " " + Arrays.toString(out_params.toArray()));
		// Invalid hold command
		out_params = new ArrayList<String>();
		Event e4 = Event.fromString("h 1 2 3 4 5 6", out_params);
		System.out.println(e4);
	}
}
