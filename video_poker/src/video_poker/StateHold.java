package video_poker;

import java.util.ArrayList;
import java.util.List;

public class StateHold extends State {
	
	private boolean simulation;
	
	public StateHold(	String mainCommand,
						String[] commands,
						boolean acceptsInput,
						boolean simulation) {
		
		super(mainCommand, commands, acceptsInput);
		this.simulation = simulation;
	}
	
	// TODO Migrate to PlayEvaluator
	
	/**
	 * 
	 * @param c
	 * @param l
	 * @return
	 */
	public List<Integer> indexOf(Card[] c, List<Card> l){
			
		List<Integer> indices = new ArrayList<Integer>();
		
		if (l == null){
			return null;
		}
		
		for (int i = 0; i < c.length; i++){
			for (int j = 0; j < l.size(); j++){
				if (c[i].equals(l.get(j))){
					indices.add(i+1);
				}
			}
		}
		if (!indices.isEmpty()){
			return indices;
		}
		return null;
		
	}
	
	// TODO Strategy should be accepted as a parameter
	
	/**
	 * 
	 */
	@Override
	public State stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {
		
		boolean hold[] = new boolean[5];
		String[] tokens = null;
		
		if(!simulation){
			tokens = command.split(" ");
		} else {
			//Get strategy
			//System.out.println("Getting strategy...");
			Strategy strategy = new Strategy();
			List<Card> cardsKeep = strategy.valueHand(player.getHand());
			List<Integer> indexKeep = indexOf(player.getHand().getCards(), cardsKeep);
			if (indexKeep != null){
				tokens = new String[indexKeep.size() + 1];
				for (int i = 0; i < indexKeep.size(); i++){
					tokens[i+1] = Integer.toString(indexKeep.get(i));
				}
			} else {
				tokens = new String[1];
			}
			tokens[0] = "h";
			
			// DEBUG Print chosen optimal command
			/*
			for (String token : tokens){
				System.out.print(token + " ");
			}
			System.out.println("");
			*/
		}
		
		int index = -1;
		// If the player desires to hold certain cards (discarding others)
		if (tokens.length >= 1 && tokens.length <= 5){
			//Check if all tokens are valid numbers between 1-5 inclusive
			for (int i = 1; i < tokens.length; i++){
				try{
					index = Integer.parseInt(tokens[i]);
				} catch (NumberFormatException e){
					System.err.println("h: invalid card held: " + index);
					return this;
				}
				if (index < 1 || index > 5){
					System.out.println("h: nvalid card held: " + index);
					return this;
				}
				hold[index - 1] = true;
			}
			
			for (int i = 0; i < 5; i++){
				if (hold[i] == false){
					// Draw a card from the deck and replace the discarded one
					Card drawn;
					try{
						drawn = deck.draw();
					} catch (DeckEmptyException e){
						System.err.println("The deck has insufficient cards for a draw.");
						return null;
					}
					player.getHand().swapCard(i, drawn);
				}
			}
			
			// Print the resulting hand
			if(!simulation){
				player.printHand();
			}	
		}
		return this.next_state;
	}
}
