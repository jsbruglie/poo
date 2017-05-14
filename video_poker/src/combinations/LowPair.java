package combinations;

import static video_poker.Rank.*;

import java.util.ArrayList;
import java.util.List;

import video_poker.Card;

/**
 * Class to verify if an array of cards contains a Low Pair (from 2 to T)
 */
public class LowPair implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		List<Card> hold = new ArrayList<Card>();
		for (int i = n2.ordinal(); i <= T.ordinal(); i++){
			if(occurrences.ranks[i] == 2){
				for (int j = 0; j < c.length; j++){
					if (c[j].rank.ordinal() == i){
						hold.add(c[j]);
					}
				}
			}
		}
		if (hold.size() == 2){
			return hold;
		}
		return null;
	}
}
