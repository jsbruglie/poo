package video_poker;

// Static imports to use name space
import static video_poker.Combination.*;	// Use Combination enums
import static video_poker.Rank.*;		// Use CardNumber enums

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rules.RoyalFlush;
import rules.Rule;
/**
 * 
 */
public class Score {

	/** A table assigning a payout for each combination per bet value */
	private PayTable pay_table;
	/** An array with the card number occurrences in a given hand */
	private int[] card_rank_occurrences;
	
	/** Maximum number of cards in hand/combination */
	private final int MAX_CARDS = 5;
	
	// It is assumed that the hand has always the same amount of cards
	// as the maximum number of cards in a combination (5, by default).
	
	/** Cards per suit */
	private final int CARDS_PER_SUIT = Rank.values().length;
	
	public Score(PayTable pay_table){
		
		this.pay_table = pay_table;
		card_rank_occurrences = new int[CARDS_PER_SUIT];
	}
	
	/**
	 * Evaluates a hand and calculates the combination it corresponds to.
	 * @param hand The hand to be evaluated
	 * @return The corresponding combination Enum
	 */
	public Combination evaluateHand(Hand hand){
		
		Card[] c = hand.getCards();
		if (c.length != MAX_CARDS){
			return null;
		}
		
		for (int i = 0; i < CARDS_PER_SUIT ; i++){
			card_rank_occurrences[i] = 0;
			//System.out.println(i + " " + card_number_occurrences[i]);
		}
		for (int i = 0; i < MAX_CARDS; i++){
			card_rank_occurrences[c[i].rank.ordinal()]++;
		}
		
		if (rules.RoyalFlush.checkRoyalFlush(c, card_rank_occurrences)){ 
			return RoyalFlush;
		} else if (rules.StraightFlush.checkStraightFlush(c, card_rank_occurrences)){
			return StraightFlush;
		} else if (rules.FourOfAKind.checkFourAces(c, card_rank_occurrences)){
			return FourAces;
		} else if (rules.FourOfAKind.checkFour2_4(c, card_rank_occurrences)){
			return Four2_4;
		} else if (rules.FourOfAKind.checkFour5_K(c, card_rank_occurrences)){
			return Four5_K;
		} else if (rules.FullHouse.checkFullHouse(c, card_rank_occurrences)){
			return FullHouse;
		} else if (rules.Straight.checkStraight(c, card_rank_occurrences)){
			return Straight;
		} else if(rules.Flush.checkFlush(c)){
			return Flush;
		} else if(rules.ThreeOfAKind.checkThreeOfAKind(c, card_rank_occurrences)){
			return ThreeOfAKind;
		} else if(rules.TwoPair.checkTwoPair(c, card_rank_occurrences)){
			return TwoPair;
		} else if(rules.HighPair.checkJacksOrBetter(c, card_rank_occurrences)){
			return JacksOrBetter;
		}
		return Other;
	}
	
	/**
	 * Returns the payout for a given combination and bet
	 * @param comb The final combination
	 * @param bet The player proposed bet
	 * @return The corresponding payout
	 */
	public int getScore(Combination comb, int bet){
		if (bet >= 1 && bet <= 5){
			return pay_table.getPayout(comb, bet);
		}
		return 0;
	}
	
	
	public static void main(String[] args){
		
		// 5C 7D 8H 2S 5D	: Pair of 5 - No reward		
		// KC 6D 8H 2S KD	: Pair of Kings
		// JC 7D 7H 2S JD	: Pair of 7's and Jacks
		// 4C 4D 7H 4S JD	: Triple 4's
		// 4C 5D 6H 7S 8D	: Straight
		// KC AC 2D 3S 4H	: Wrap-around Straight - No reward
		// 4C 5C 6C 2C TC	: Flush
		// 4C 4D 4H QS QD	: Full House Pair 4's Triple Queens
		// 3C 3D 3H 3S QD	: Four 2-4
		// 8C 8D 8H 8S QD	: Four 5-K
		// AC AD AH AS QD	: Four Aces
		// 4D 5D 6D 7D 8D	: Straight Flush
		
		final String regex = "(10|[0-9]|[JQKA])([HCDS])";
		final Pattern pattern = Pattern.compile(regex);
		
		Rank number = null;
		Suit suit = null;
		
		Score score = new Score(new DoubleBonus10_7());
		
		try (Scanner scanner = new Scanner(new File("src/video_poker/score_test.txt"))) {
			while (scanner.hasNext()){
				
				String line = scanner.nextLine();
				String[] split = line.split(" ");
				Card[] cards = new Card[split.length];
				
				for (int i = 0; i < split.length; i++){
					Matcher matcher = pattern.matcher(split[i]);
					while (matcher.find()) {
						number = Rank.fromString(matcher.group(1));
						suit = Suit.fromString(matcher.group(2));
					}
					cards[i] = new Card(number, suit);
				}
				
				Hand hand = new Hand(cards);
				System.out.println(hand);
				System.out.println(score.evaluateHand(hand));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
