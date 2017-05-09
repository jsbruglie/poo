package video_poker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import combinations.Occurrences;
import combinations.Rule;

/**
 * Double Bonus 10/7 Optimal Strategy
 */
public class StrategyDB10_7 implements Strategy {
	
	/** Card rank and suit occurrences */
	private Occurrences occurrences;
	/** The list of rules to be checked */
	private List<Rule> rule_list;

	/**
	 * Constructor
	 */
	public StrategyDB10_7() {
		
		occurrences = new Occurrences();
		rule_list = new ArrayList<Rule>();
		
		/* Add all rules to the list*/

		/* 1.Royal Flush*/
		rule_list.add((Rule) new combinations.RoyalFlush());
		/* 1.Straight Flush*/
		rule_list.add((Rule) new combinations.StraightFlush());
		/* 1.Four of a Kind*/
		rule_list.add((Rule) new combinations.FourOfAKind());
		/* 2.Four of a royal flush*/
		rule_list.add((Rule) new combinations.NToRoyalFlush(4));
		/* 3.Three Aces */
		rule_list.add((Rule) new combinations.ThreeAces());
		/* 4.Straight*/
		rule_list.add((Rule) new combinations.Straight());
		/* 4.Flush*/
		rule_list.add((Rule) new combinations.Flush());
		/* 4.FullHouse*/
		rule_list.add((Rule) new combinations.FullHouse());
		/* 5.ThreeOfAKind*/
		rule_list.add((Rule) new combinations.ThreeOfAKind());
		/* 6.FourToStraightFlush*/
		rule_list.add((Rule) new combinations.FourToStraightFlush());
		/* 7.Two Pair*/
		rule_list.add((Rule) new combinations.TwoPair());
		/* 8.High Pair*/
		rule_list.add((Rule) new combinations.HighPair());
		/* 9.4 to a flush*/
		rule_list.add((Rule) new combinations.NToFlush(4));
		/* 10.3 to a royal flush*/
		rule_list.add((Rule) new combinations.NToRoyalFlush(3));
		/*11. 4 to an outside straight*/
		rule_list.add((Rule) new combinations.FourToOutsideStraight());
		/*12 Low Pair*/
		rule_list.add((Rule) new combinations.LowPair());
		/*13. AKQJ unsuited*/
		rule_list.add((Rule) new combinations.AKQJunsuited());
		/*14. 3 to a straight flush (type 1)*/
		rule_list.add((Rule) new combinations.ThreeToStraightFlush(1));
		/*15. 4 to an inside straight with 3 high cards*/
		rule_list.add((Rule) new combinations.FourToInsideStraight(3));
		/*16. QJ suited*/
		rule_list.add((Rule) new combinations.QJunsuitedOrSuited(true));
		/*17. 3 to a flush (type 2)*/
		rule_list.add((Rule) new combinations.ThreeToFlushNHighCards(2));
		/*18. 2 suited high cards*/
		rule_list.add((Rule) new combinations.TwoSuitedHighCards());
		/*19. 4 to an inside straight with 2 high cards*/
		rule_list.add((Rule) new combinations.FourToInsideStraight(2));
		/*20. 3 to a straight flush (type 2)*/
		rule_list.add((Rule) new combinations.ThreeToStraightFlush(2));
		/*21. 4 to an inside straight with 1 cards*/
		rule_list.add((Rule) new combinations.FourToInsideStraight(1));
		/*22. KQJ unsuited*/
		rule_list.add((Rule) new combinations.KQJunsuited());
		/*23. JT suited*/
		rule_list.add((Rule) new combinations.JTsuited());
		/*24. QJ unsuited*/
		rule_list.add((Rule) new combinations.QJunsuitedOrSuited(false));
		/*25. 3 to a flush with 1 high card*/
		rule_list.add((Rule) new combinations.ThreeToFlushNHighCards(1));
		/*26. QT suited*/
		rule_list.add((Rule) new combinations.QTsuited());
		/*27. 3 to a straight flush (type 3)*/
		rule_list.add((Rule) new combinations.ThreeToStraightFlush(3));
		/*28. KQ unsuited*/
		rule_list.add((Rule) new combinations.KQunsuited());
		/*28. KJ unsuited */
		rule_list.add((Rule) new combinations.KJunsuited());
		/*29. Ace*/
		rule_list.add((Rule) new combinations.Ace());
		/*30. KT suited*/
		rule_list.add((Rule) new combinations.KTsuited());
		/*31. JackQueenOrKing*/
		rule_list.add((Rule) new combinations.JackQueenOrKing());
		/*32. 4 to an inside straight with 0 high cards*/
		rule_list.add((Rule) new combinations.FourToInsideStraight(0));
		/*33. 3 to a flush with 0 high cards*/
		rule_list.add((Rule) new combinations.ThreeToFlushNHighCards(0));
		/*34. Nothing - Player should discard everything */
	}
	
	/**
	 * Provides advice on which cards to hold, given a hand
	 * @param hand The given hand
	 * @return A string with the advice
	 */
	public String holdAdvice(Hand hand){
		//Get value of hand and display advice of cards to keep
		List<Card> hold = evaluateHand(hand);
		if (hold != null){
			if(!hold.isEmpty()) {
				return "player should hold cards " + 
						combinations.Utils.indexOf(hand.getCards(), hold).toString().
						replace('[', ' ').replace(']', ' ').replace(',', ' ').trim();
			}	
		}
		return "player should discard everything";
	}
	
	/**
	 * Evaluates a given hand and detects draws
	 * @param hand The given hand
	 * @return The cards required for the identified (most likely/profitable) draw
	 */
	public List<Card> evaluateHand(Hand hand){
		
		Card[] c = hand.getCards();
		occurrences.initialise(c);
		
		/* Go through the list until a rule doesn't return null */
		List<Card> hold = null;
		for (int i = 0; i < rule_list.size(); i++){
			hold = rule_list.get(i).run(c, occurrences);
			if (hold != null){
				
				// TODO - DEBUG
				/*
				System.out.print("\t"+rule_list.get(i).getClass().getSimpleName()+ ": ");
				for (Card card : hold){
					System.out.print(card + " ");
				}
				System.out.println("");
				*/
				return hold;
			}
		}

 		return null;
	}
	
	// TODO - DEBUG

	/**
	 * Test difficult hands to verify strategy correctness
	 * @param args Not used
	 */
	public static void main(String[] args){
		StrategyDB10_7 strategy = new StrategyDB10_7();
		
		final String regex = "(T|[2-9]|[JQKA])([HCDS])";
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
				System.out.println(counter + ". " + hand);
				String keep = strategy.holdAdvice(hand);
				System.out.println("\t" + keep);
				counter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
