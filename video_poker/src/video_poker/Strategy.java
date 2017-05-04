package video_poker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rules.Occurrences;
import rules.Rule;

public class Strategy {
	
	Occurrences occurrences;
	List<Rule> ruleList;

	public Strategy() {
		occurrences = new Occurrences();
		ruleList = new ArrayList<Rule>();
		/* Add all rules to the list*/
		/* 1.Royal Flush*/
		ruleList.add((Rule) new rules.RoyalFlush());
		/* 1.Straight Flush*/
		ruleList.add((Rule) new rules.StraightFlush());
		/* 1.Four of a Kind*/
		ruleList.add((Rule) new rules.FourOfAKind());
		/* 2.Four of a royal flush*/
		ruleList.add((Rule) new rules.NToRoyalFlush(4));
		/* 3.Three Aces */
		ruleList.add((Rule) new rules.ThreeAces());
		/* 4.Straight*/
		ruleList.add((Rule) new rules.Straight());
		/* 4.Flush*/
		ruleList.add((Rule) new rules.Flush());
		/* 4.FullHouse*/
		ruleList.add((Rule) new rules.FullHouse());
		/* 5.ThreeOfAKind*/
		ruleList.add((Rule) new rules.ThreeOfAKind());
		/* 6.FourToStraightFlush*/
		ruleList.add((Rule) new rules.FourToStraightFlush());
		/* 7.Two Pair*/
		ruleList.add((Rule) new rules.TwoPair());
		/* 8.High Pair*/
		ruleList.add((Rule) new rules.HighPair());
		/* 9.4 to a flush*/
		ruleList.add((Rule) new rules.NToFlush(4));
		/* 10.3 to a royal flush*/
		ruleList.add((Rule) new rules.NToRoyalFlush(3));
		/*11. 4 to an outside straight*/
		ruleList.add((Rule) new rules.FourToOutsideStraight());
		/*12 Low Pair*/
		ruleList.add((Rule) new rules.LowPair());
		/*13. AKQJ unsuited*/
		ruleList.add((Rule) new rules.AKQJunsuited());
		/*14. 3 to a straight flush (type 1)*/
		ruleList.add((Rule) new rules.ThreeToStraightFlush(1));
		/*15. 4 to an inside straight with 3 high cards*/
		ruleList.add((Rule) new rules.FourToInsideStraight(3));
		/*16. QJ suited*/
		ruleList.add((Rule) new rules.QJunsuitedOrSuited(1));
		/*17. 3 to a flush (type 2)*/
		ruleList.add((Rule) new rules.ThreeToFlush_nHighCards(2));
		/*18. 2 suited high cards*/
		ruleList.add((Rule) new rules.TwoSuitedHighCards());
		/*19. 4 to an inside straight with 2 high cards*/
		ruleList.add((Rule) new rules.FourToInsideStraight(2));
		/*20. 3 to a straight flush (type 2)*/
		ruleList.add((Rule) new rules.ThreeToStraightFlush(2));
		/*21. 4 to an inside straight with 1 cards*/
		ruleList.add((Rule) new rules.FourToInsideStraight(1));
		/*22. KQJ unsuited*/
		ruleList.add((Rule) new rules.KQJunsuited());
		/*23. JT suited*/
		ruleList.add((Rule) new rules.JTsuited());
		/*24. QJ unsuited*/
		ruleList.add((Rule) new rules.QJunsuitedOrSuited(0));
		/*25. 3 to a flush with 1 high card*/
		ruleList.add((Rule) new rules.ThreeToFlush_nHighCards(1));
		/*26. QT suited*/
		ruleList.add((Rule) new rules.QTsuited());
		/*27. 3 to a straight flush (type 3)*/
		ruleList.add((Rule) new rules.ThreeToStraightFlush(3));
		/*28. KQ unsuited*/
		ruleList.add((Rule) new rules.KQunsuited());
		/*28. KJ unsuited */
		ruleList.add((Rule) new rules.KJunsuited());
		/*29. Ace*/
		ruleList.add((Rule) new rules.Ace());
		/*30. KT suited*/
		ruleList.add((Rule) new rules.KTsuited());
		/*31. JackQueenOrKing*/
		ruleList.add((Rule) new rules.JackQueenOrKing());
		/*32. 4 to an inside straight with 0 high cards*/
		ruleList.add((Rule) new rules.FourToInsideStraight(0));
		/*33. 3 to a flush with 0 high cards*/
		ruleList.add((Rule) new rules.ThreeToFlush_nHighCards(0));
		/*34. Nothing*/
	}

	public String evaluateHand(Hand hand){
		//Get value of hand and display advice of cards to keep
		List<Card> cardsKeep = valueHand(hand);
		if (cardsKeep != null){
			if(!cardsKeep.isEmpty()) {
				return "player should hold cards " + 
						rules.Utils.indexOf(hand.getCards(), cardsKeep).toString().
						replace('[', ' ').replace(']', ' ').replace(',', ' ').trim();
			}	
		}
		return "player should discard everything";
	}
	
	public List<Card> debugValueHand(Hand hand){
		//Get value of hand and display advice of cards to keep
		List<Card> cardsKeep = valueHand(hand);
		System.out.println(debugcount);
		return cardsKeep;
	}
	
	public List<Card> valueHand(Hand hand){ //Returns the cards to keep, later process can be done
		
		Card[] c = hand.getCards();
		occurrences.initialise(c);
		
		/*Go through the list until a rule doesn't return null*/
		List<Card> cardsKeep = null;
		for(int i = 0; i < ruleList.size(); i++){
			cardsKeep = ruleList.get(i).run(c, occurrences.rank_occurrences, occurrences.suit_occurrences);
			if(cardsKeep != null){
				// TODO - DEBUG
				/*
				System.out.print(ruleList.get(i).getClass().getSimpleName()+ ": ");
				for (Card card : cardsKeep){
					System.out.print(card + " ");
				}
				*/
				return cardsKeep;
			}
		}

 		return null;
	}
	

	/**
	 * Main to test stRategy evaluation
	 * @param args
	 */
	public static void main(String[] args){
		Strategy strategy = new Strategy();
		
		final String regex = "(T|[0-9]|[JQKA])([HCDS])";
		final Pattern pattern = Pattern.compile(regex);
		
		Rank rank = null;
		Suit suit = null;
		
		int counter = 1;
		
		try (Scanner scanner = new Scanner(new File("TESTS/difficult_hands.txt"))) {
			while (scanner.hasNext()){
				
				String line = scanner.nextLine();
				String[] split = line.split(" ");
				Card[] cards = new Card[split.length];
				
				for (int i = 0; i < split.length; i++){
					Matcher matcher = pattern.matcher(split[i]);
					while (matcher.find()) {
						rank = Rank.fromString(matcher.group(1));
						suit = Suit.fromString(matcher.group(2));
					}
					cards[i] = new Card(rank, suit);
				}
				
				Hand hand = new Hand(cards);
				System.out.println(counter + " - " + hand);
				String keep = strategy.evaluateHand(hand);
				System.out.println("\t" + strategy.getDebugcount() + " - " + keep);
				counter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	int debugcount = 1;
	
	public int getDebugcount() {
		return debugcount;
	}

	public void setDebugcount(int debugcount) {
		this.debugcount = debugcount;
	}
	
}
