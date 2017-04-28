package video_poker;

import java.util.ArrayList;
import java.util.List;

public class StateHold extends State {
	private boolean simulation;
	public StateHold(String mainCommand, String[] commands, boolean acceptsInput, boolean isFinal, boolean simulation) {
		super(mainCommand, commands, acceptsInput, isFinal);
		this.simulation = simulation;
	}
	
	public List<Integer> indexOf(Card[] c, List<Card> l){
			
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < l.size(); i++){
			for (int j = 0; j < c.length; j++){
				if (l.get(i).equals(c[j])){
					indices.add(j);
				}
			}
		}
		if (!indices.isEmpty()){
			return indices;
		}
		return null;
		
	}
	
	@Override
	public void stateMethod(String command, Player player, Statistics stats, Score score, Deck deck) {
		boolean hold[] = new boolean[5];
		String[] tokens = null;
		if(!simulation){
			tokens = command.split(" ");
		}else{
			//Get strategy
			Strategy strategy = new Strategy();
			List<Card> cardsKeep = strategy.valueHand(player.getHand());
			List<Integer> indexKeep = indexOf(player.getHand().getCards(),cardsKeep);
			tokens = new String[indexKeep.size()+1];
			tokens[0] = "h";
			for(int i = 1; i < indexKeep.size(); i++){
				tokens[i] = Integer.toString(indexKeep.get(i));
			}
		}
		int index = -1;
		// If the player desires to hold certain cards (discarding others)
		if (tokens.length >= 1 && tokens.length <= 5){
			//Check if all tokens are valid numbers between 1-5 inclusive
			for (int i = 1; i < tokens.length; i++){
				try{
					index = Integer.parseInt(tokens[i]);
				} catch (NumberFormatException e){
					System.err.println("Invalid card held: " + index);
					return;
				}
				if (index < 1 || index > 5){
					System.out.println("Invalid card held: " + index);
					return;
				}
				hold[index - 1] = true;
			}
			
			for (int i = 0; i < 5; i++){
				if (! hold[i]){
					// Draw a card from the deck and replace the discarded one
					Card drawn;
					try{
						drawn = deck.draw();
					} catch (DeckEmptyException e){
						System.err.println("The deck has insufficient cards for a draw.");
						return;
					}
					player.getHand().swapCard(i, drawn);
				}
			}		
		}


		// Print the resulting hand
		player.printHand();

	}

}
