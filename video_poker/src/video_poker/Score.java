package video_poker;

// Static imports to use name space
import static video_poker.CombinationDB10_7.*;	// Use Combination enums
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
	public CombinationDB10_7 evaluateHand(Hand hand){
		
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
	public int getScore(CombinationDB10_7 comb, int bet){
		if (bet >= 1 && bet <= 5){
			return pay_table.getPayout(comb, bet);
		}
		return 0;
	}
	
	
	public static void main(String[] args){
		
		Card[][] matrix = Utils.cardFileParser("TESTS/score_test.txt");
		Score score = new Score(new PT_DoubleBonus10_7());
		
		for (Card[] array : matrix){
			Hand hand = new Hand(array);
			System.out.println(hand);
			System.out.println(score.evaluateHand(hand));
		}
	}
}
