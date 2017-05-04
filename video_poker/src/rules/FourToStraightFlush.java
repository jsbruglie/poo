package rules;

import java.util.Collections;
import java.util.List;

import video_poker.Card;

public class FourToStraightFlush implements Rule {

	@Override
	public List<Card> run(Card[] c, Occurrences occurrences) {
		Rule ntoflush = (Rule) new NToFlush(4);
		List<Card> flush = ntoflush.run(c, occurrences);
				
		if (flush != null){	
			Collections.sort(flush, new Card.CardComparator());
			
			int gaps = 0;
			
			if (flush != null){
				for (int i = 1; i < flush.size(); i++){
					gaps += flush.get(i).rank.ordinal() - flush.get(i - 1).rank.ordinal();
				}
				if (gaps == 4){
					return flush;
				}
			}
		}	
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
