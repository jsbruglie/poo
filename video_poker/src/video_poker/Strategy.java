package video_poker;

import java.util.List;

public interface Strategy {

	String holdAdvice(Hand hand);

	List<Card> evaluateHand(Hand hand);

}
