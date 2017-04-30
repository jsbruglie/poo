package rules;

import static video_poker.Rank.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import video_poker.Card;

public class FourToOutsideStraight implements Rule {

	@Override
	public List<Card> run(Card[] c, int[] rank_occurrences, int[] suit_occurrences) {
		for (int i = 0; i < c.length - (Occurrences.HAND_SIZE - 2); i++){
			
			List <Card> hold = new ArrayList<Card>();
			for (int j = i; j < i + (c.length - 1); j++){
				hold.add(c[j]);
			}
			Collections.sort(hold, new Card.CardComparator());
			
			// A234X is the only possible straight, and it is a corner-case inside straight
			if (hold.get(0).rank != A){
				// successful comparisons
				int success = 0;
				for (int k = 0; k < (Occurrences.HAND_SIZE - 2); k++){
					if (hold.get(k + 1).rank.ordinal() == hold.get(k).rank.ordinal() + 1){
						success++;
					}
				}
				
				// Matched 4 cards that are part of an outside straight
				if (success == 3){
					return hold;
				}
			}	
		}
		return null;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
