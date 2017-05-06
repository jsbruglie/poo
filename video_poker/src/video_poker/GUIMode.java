package video_poker;

import gui.*;

public class GUIMode implements Mode{

	boolean terminal_false;
	
	public GUIMode(){
		
	}
	
	@Override
	public void execute(Player player, Score score, Strategy strategy, Statistics stats) {
		// TODO Auto-generated method stub
		
		GUI firstGUI = GUI.getGUI(true);
		
		String[] args = new String[2];
		String mode = new String();
		Boolean[] forward = new Boolean[2];
		int initial_credit;
		forward[0] = new Boolean(false);
		forward[1] = new Boolean(false);
		firstGUI.prepareSelectorGUI(args,mode,forward);
		
		while(!forward[0]){
			System.out.print("");
			if(forward[1]){
				try {
					initial_credit = Integer.parseInt(args[1]);
				} catch (NumberFormatException e){
					//do something display worthy, i guess
					continue;
				}
				if(initial_credit > 0)
					forward[0] = true;
			}
		}
			
		
		Game newgame = new Game(args);
		firstGUI.prepareInteractiveGUI();
		newgame.start();
		newgame.end();
	}

}
