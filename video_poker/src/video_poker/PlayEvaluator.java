package video_poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayEvaluator {
	
	/*
	
	private Tuple[] number_occurences;
	private int[] suit_occurences;
	public PlayEvaluator(){
		number_occurences = new Tuple[13];
		suit_occurences = new int[4];
		
	}
	// The following are all package methods
	
	//----------------------------------------INITIALIZE--------------------------------
	//Every time a new card is evaluated call this method
	void initialize(Card c[]){
		for(int i=0; i<13; i++){
			number_occurences[i] = new Tuple(0, new ArrayList<Integer>());
		}
		for(int i=0; i<4; i++){
			suit_occurences[i] = 0;
		}
		for(int i=0; i<5;i++){
			suit_occurences[c[i].getSuit().getValue()]++;//Fill suit occurences
			number_occurences[c[i].getNumber()-1].x++; //Fill number occurences
			(number_occurences[c[i].getNumber()-1].y).add(i);//Insert cards in buckets
		}
	}
	
	//----------------------------------------AUXILIARY----------------------------------
	//Create a list with all the card indexes of the hand and return
	public List<Integer> allCards(Card[] c){
		List<Integer> cards = new ArrayList<Integer>();
		for(int i=0; i<5; i++)
			cards.add(i);
		return cards;
	}
	//Given an integer value return the corresponding suit enum
	public Suit getSuit(int value){
		if(value == 0){
			return Suit.Clubs;
		}
		if(value == 1){
			return Suit.Diamonds;
		}
		if(value == 2){
			return Suit.Spades;
		}
		if(value == 3){
			return Suit.Hearts;
		}
		return null;
	}
	//Check if a list of card indexes all have the same suit
	public boolean allSameSuit(Card c[], List<Integer> indexes){
		Suit s;
		s = c[indexes.get(0)].getSuit();
		for(int i=0; i<indexes.size(); i++){
			 if(c[indexes.get(i)].getSuit() != s)
				 return false;
		}
		return true;
	}
	//Get the number of High Cards in a list of card indexes
	public int getNumHighCards(List<Integer> cardlist){
		int count=0;
		for(int i=0; i<cardlist.size(); i++){
			 if(isHighCard(cardlist.get(i))){
				 count++;
			 }
		}
		return count;
	}
	//Check if a given card index is a High Card
	public boolean isHighCard(int card_index){
		if(card_index == 1 || card_index == 11 || card_index == 12 || card_index == 13)
			return true;
		return false;
	}
	
	//Checks for a full house
	public boolean checkFullHouse(Card[] c){
		//If checkTwoPair and checkThreeOfAKind are both true and the cards they evaluate are different
		int npairs = 0, ntriples = 0;
		for(int i=0; i<13;i++){
			if(number_occurences[i].x == 2)
				npairs++;
			if(number_occurences[i].x == 3)
				ntriples++;
		}
		if(npairs == 1 && ntriples == 1)
			return true;
		else
			return false;
	}
	
	private List<Integer> NToStraight(Card[] c, int N) {
		//5 is handsize shouldn't be hardcoded
		int[] arr = new int[5];
		for(int i=0; i<5; i++){
			arr[i] = c[i].getNumber();
		}
		
	    Arrays.sort(arr);
	    for (int i = 0; i < arr.length - N + 1; ++i) {
	        if (arr[i] == arr[i + (N-1)] - (N-1)) {
	        	List<Integer> sequential_values = new ArrayList<Integer>();
	        	for(int j=i;j<i+N;j++){
	        		sequential_values.add(j);
	        	}
	        	return sequential_values;
	        }
	    }
	    return null;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++PLAYS BEGIN HERE++++++++++++++++++++++++++++++++
	
	//----------------------------------------ROYAL FLUSH----------------------------------
	List<Integer> RoyalFlush(Card[] c){
		List<Integer> cards = Flush(c);
		if(cards != null){ //Check if the hand is a flush
			//Check if each of the cards for the royal flush is present
			if(number_occurences[12].x == 1 && 
					number_occurences[11].x == 1 && 
					number_occurences[10].x == 1 && 
					number_occurences[9].x == 1 && 
					number_occurences[0].x == 1){
				return cards;
			}
		} 
		return null;
	}
	
	//----------------------------------------FOUR OF A KIND----------------------------------
	List<Integer> FourOfAKind(Card[] c){
		for(int i=0; i<13; i++){
			if(number_occurences[i].x == 4){ //If there are four cards of the same number, return all of them
				return number_occurences[i].y;
			}
		}
		return null;
	}
	
	//----------------------------------------STRAIGHT FLUSH----------------------------------
	List<Integer> StraightFlush(Card[] c){
		List<Integer> cards = Flush(c);
		//Note that TJQKA is not a sequential straight flush, but its a Royal Flush so no need to check
		if(cards != null){ //if hand is a flush
			List<Integer> temp = NToStraight(c, 5);
			Collections.sort(cards);
			if(cards.equals(temp)){ //if card numbers are sequential, with N=5 (whole hand)
				return cards;	
			}
		}
		return null;
	}
	
	//----------------------------------------N TO A ROYAL FLUSH----------------------------------
	List<Integer> NToRoyalFlush(Card[] c, int N){
		int count = 0;
		List<Integer> temp = NToFlush(c, N); //Get N cards with the same suit
		List<Integer> rethand = new ArrayList<Integer>();
		//Check over them to see if they are N cards that could form a Royal Flush
		if(temp!= null && temp.size()>=N){
			for(int i=0; i<5; i++){
				//Since cards are of the same suit we can check which ones they are, and we know there are no repetitions
				if(c[i].getNumber() == 1 || c[i].getNumber() == 10 || c[i].getNumber() == 11 || c[i].getNumber() == 12 || c[i].getNumber() == 13){
					count++;
					rethand.add(i);
				}
					
			}
		}
		
		//Add each of the cards that checks this
		if(count == N)
			return rethand;
		else
			return null;
	}
	
	//----------------------------------------THREE ACES----------------------------------
	List<Integer> ThreeAces(Card[] c){	
		if(number_occurences[0].x == 3){
			//if there are three Aces, return them
			return number_occurences[0].y;
		}
		return null;
	}
	
	//----------------------------------------STRAIGHT----------------------------------
	List<Integer> Straight(Card[] c){
		//If TJQKA cards are not sequential, but its a straight (suits don't matter)
		if(number_occurences[9].x == 1 && number_occurences[10].x ==1 && number_occurences[11].x == 1 && number_occurences[12].x == 1 && number_occurences[0].x == 1){
			List<Integer> cs = allCards(c);
			return cs;
		}

		List<Integer> cards = NToStraight(c, 5); //returns null if it fails
		return cards;	
	}

	//----------------------------------------FULL HOUSE----------------------------------
	List<Integer> FullHouse(Card[] c){
		if(checkFullHouse(c)){
			List<Integer> cards = allCards(c);
			return cards;
		}
		return null;
	}
	
	//----------------------------------------FLUSH----------------------------------
	List<Integer> Flush(Card[] c){
		if(c[0].getSuit().equals(c[1].getSuit()) && c[1].getSuit().equals(c[2].getSuit()) && c[2].getSuit().equals(c[3].getSuit()) && c[3].getSuit().equals(c[4].getSuit())){
			List<Integer> cards = allCards(c);
			return cards;
		}
		return null;
	}
	
	//----------------------------------------THREE OF A KIND (no Aces)----------------------------------
	List<Integer> ThreeOfAKind(Card[] c){
		for(int i=1; i<13; i++){ //Start with 2 (no Aces). If there are three cards of the same number, return all of them
			if(number_occurences[i].x == 3){
				return number_occurences[i].y;
			}
		}
		return null;
	}
	
	//----------------------------------------4 TO A STRAIGHT FLUSH----------------------------------
	List<Integer> _4ToStraightFlush(Card[] c) {
		List<Integer> temp = NToStraight(c, 4); //Get 4 to a straight
		if(temp != null){ //Check if they have the same suit
			if(allSameSuit(c, temp))
				return temp;
		}
		return null;
	}
	
	//----------------------------------------TWO PAIR----------------------------------
	List<Integer> TwoPair(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		for(int i=0; i<13; i++){
			if(number_occurences[i].x == 2){ //If a pair is found, add its card indexes to the list
				temp.add(number_occurences[i].y.get(0)); temp.add(number_occurences[i].y.get(1));
			}
		}
		if(temp.size() == 4) //If we found 2 pairs
			return temp;
		else
			return null;
	}
	
	//----------------------------------------HIGH PAIR----------------------------------
	List<Integer> HighPair(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[0].x == 2){ //Pair of Aces
			temp.add(number_occurences[0].y.get(0)); temp.add(number_occurences[0].y.get(1));
			return temp;
		}
		for(int i=10;i<13;i++){ //Pair of J,Q,K
			if(number_occurences[i].x == 2){
				temp.add(number_occurences[i].y.get(0)); temp.add(number_occurences[i].y.get(1));
				return temp;
			}
		}
		return null;
	}
	
	//----------------------------------------N TO A FLUSH----------------------------------
	List<Integer> NToFlush(Card[] c, int N){ 
		List<Integer> temp = new ArrayList<Integer>();
		
		for(int i=0; i<4; i++){
			if(suit_occurences[i] >= N){ //If we found N cards of the same suit, go through the hand to find+add them to a list
				for(int j=0; j<5; j++){
					if(c[j].getSuit() == getSuit(i)){ //Check if it has the right suit. getSuit converts from int to enum
						temp.add(j);
					}
				}
				return temp;		
			}
		}
		return null;
 	}
	
	//----------------------------------------3 TO A ROYAL FLUSH is in N TO A ROYAL FLUSH above----------------------------------
	
	//----------------------------------------4 TO AN OUTSIDE STRAIGHT----------------------------------
	List<Integer> _4ToOutsideStraight(Card c[]){
		List<Integer> temp = NToStraight(c,4); //Get 4 sequential cards
		//JQKA is not sequential so will never come from NToStraight (and is an inside straight)

		//If the first card is Ace then we have A234 which is an inside straight
		if(temp != null && temp.get(0) != 0)  //All other 4 sequential cards are a 4 to OutsideStraight
			return temp;
		return null;
	}
	
	//----------------------------------------LOW PAIR----------------------------------
	List<Integer> LowPair(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		for(int i=1; i<10; i++){ //Low Pairs
			if(number_occurences[i].x == 2){
				temp.add(number_occurences[i].y.get(0)); temp.add(number_occurences[i].y.get(1));
				return temp;
			}
		}
		return null;
	}
	
	//----------------------------------------AKQJ UNSUITED----------------------------------
	List<Integer> AKQJunsuited(Card[] c){
		//AKQJ suited is 4 to a royal flush and would have already been checked
		//If number of occurences of A,K,Q or J >= 1 (e.g AAKQJ) would be a High Pair which would have already been checked
		//If 3 in 4 of AKQJ are suited it's a 3 to a Royal Flush and would have already been checked
		if(number_occurences[0].x == 1 && number_occurences[10].x == 1 && number_occurences[11].x == 1 && number_occurences[12].x == 1){
			List<Integer> temp = new ArrayList<Integer>();
			temp.add(number_occurences[0].y.get(0)); temp.add(number_occurences[10].y.get(0));
			temp.add(number_occurences[11].y.get(0)); temp.add(number_occurences[12].y.get(0));
			return temp;
		}
		return null;
	}
	
	//----------------------------------------3 TO A STRAIGHT FLUSH. ALL TYPES----------------------------------
	//Could be a class with 3 inherited implementations
	List<Integer> _3ToStraightFlush(Card c[], int type){
		
		List<Integer> temp = NToFlush(c, 3); 
		if(temp == null) return null; //If not a 3 to a flush terminate here
		List<Integer> aceHighVal = null;
		//Check if any of those cards is an Ace(0) and create another "alternative hand" with 13 there
		int hasAce = 0;
		for(int i=0; i<temp.size(); i++){
			if(temp.get(i) == 0){
				hasAce = 1;
				aceHighVal = new ArrayList<Integer>(temp);
				aceHighVal.set(i, 13);
			}
		}
		//Do this regardless of Ace or not (does A=0 aswell)
		int nHighCards = getNumHighCards(temp);
		//Sort temp
		Collections.sort(temp);
		//Compute total number of gaps
		int gaps = ((temp.get(1)-temp.get(0))-1)+((temp.get(2)-temp.get(1))-1);
		switch(type){ 
		
			case 1:	//e.g 8JQ
				if(nHighCards >= gaps)
					return temp;
						
				if(hasAce == 1){
					//Consider A = 13 and do the same thing
					//Sort temp
					Collections.sort(aceHighVal);
					//Compute total number of gaps
					int ace_gaps = ((aceHighVal.get(1)-aceHighVal.get(0))-1)+((aceHighVal.get(2)-aceHighVal.get(1))-1);
					if(nHighCards >= ace_gaps)
						return aceHighVal;
				}
				break;
			case 2:
				//Check if 234 suited
				if(temp.get(0)==2 && temp.get(1)==3 && temp.get(2)==4)
					return temp;
				if(gaps == 1)
					return temp;
				if(gaps == 2 && nHighCards == 2)
					return temp;
				if(hasAce == 1){
					//Consider A = 13 and do the same thing
					//Sort aceHighVal
					Collections.sort(aceHighVal);
					//Compute total number of gaps
					int ace_gaps = ((aceHighVal.get(1)-aceHighVal.get(0))-1)+((aceHighVal.get(2)-aceHighVal.get(1))-1);
					if(ace_gaps == 1)
						return aceHighVal;
					if(ace_gaps == 2 && nHighCards == 2)
						return aceHighVal;
				}
				break;
			case 3:
				if(hasAce == 0){ //Type 3 cannot have an Ace
					if(nHighCards == 0 && gaps == 2)
						return temp;
				}
				break;
		}
		return null;
	}
	
	//----------------------------------------4 TO AN INSIDE STRAIGHT. ALL HIGH CARDS----------------------------------
	List<Integer> _4ToInsideStraight(Card[] c, int nHighcards){
		List<Integer> temp_c = new ArrayList<Integer>();
		List<Integer> temp = new ArrayList<Integer>();
		// The following 2 NEED TO BE FIXED to return hand indexes instead of number of occurences
		//Check if A234  is in the hand
		if(number_occurences[0].x >= 1 && number_occurences[1].x >= 1 && number_occurences[2].x >= 1 && number_occurences[3].x >= 1){
			if(nHighcards == 1 || nHighcards == 2){ //These are the only situations where this makes sense
				//Add A234 to temp and return
				temp.add(number_occurences[0].y.get(0));
				temp.add(number_occurences[1].y.get(0));
				temp.add(number_occurences[2].y.get(0));
				temp.add(number_occurences[3].y.get(0));
			}
				
		}
		//Check if JQKA is in the hand
		if(number_occurences[0].x >= 1 && number_occurences[12].x >= 1 && number_occurences[11].x >= 1 && number_occurences[10].x >= 1){
			if(nHighcards == 3){ //These are the only situations where this makes sense
				//Add JQKA to temp and return
				temp.add(number_occurences[0].y.get(0));
				temp.add(number_occurences[10].y.get(0));
				temp.add(number_occurences[11].y.get(0));
				temp.add(number_occurences[12].y.get(0));
			}
				
		}
		//Add all cards to temp
		for(int i=0; i<5; i++)
			temp.add(c[i].getNumber());
		//Order and find 4 cards with only gaps == 1 (its either cards 0 1 2 3 or 1 2 3 4)
		//Order temp, create two sublists for 0 1 2 3 and 1 2 3 4, check if gaps == 1, if they are evaluate nHighCards of each sublist

		Collections.sort(temp);
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				if(c[j].getNumber() == temp.get(i)){
					temp_c.add(j);
				}
			}
		}
		//System.out.println(temp_c);
		List<Integer> sub1_c = new ArrayList<Integer>(); List<Integer> sub1 = new ArrayList<Integer>();
		List<Integer> sub2_c = new ArrayList<Integer>(); List<Integer> sub2 = new ArrayList<Integer>();
		sub1_c.add(temp_c.get(0));sub1_c.add(temp_c.get(1));sub1_c.add(temp_c.get(2));sub1_c.add(temp_c.get(3));
		sub2_c.add(temp_c.get(1));sub2_c.add(temp_c.get(2));sub2_c.add(temp_c.get(3));sub2_c.add(temp_c.get(4));
		sub1.add(temp.get(0));sub1.add(temp.get(1));sub1.add(temp.get(2));sub1.add(temp.get(3));
		sub2.add(temp.get(1));sub2.add(temp.get(2));sub2.add(temp.get(3));sub2.add(temp.get(4));
		Collections.sort(sub1);
		Collections.sort(sub2);
		int gaps1 = ((sub1.get(1)-sub1.get(0))-1)+((sub1.get(2)-sub1.get(1))-1)+((sub1.get(3)-sub1.get(2))-1);
		//System.out.println(sub1);
		//System.out.println(gaps1);
		int gaps2 = ((sub2.get(1)-sub2.get(0))-1)+((sub2.get(2)-sub2.get(1))-1)+((sub2.get(3)-sub2.get(2))-1);
		//System.out.println("SUBS2" + sub2);
		//System.out.println(gaps2);
		if(gaps2 == 1){
			//Compare result with given nHighCards
			int straightNumHighCards = getNumHighCards(sub2);
			//System.out.println(straightNumHighCards);
			if(straightNumHighCards == nHighcards)
				return sub2_c;
			
		}
		if(gaps1 == 1){
			//Compare result with given nHighCards
			int straightNumHighCards = getNumHighCards(sub1);
			
			if(straightNumHighCards == nHighcards)
				return sub1_c;
			
		}
		return null;
		
	}
	
	//----------------------------------------QJ, SUITED OR UNSUITED----------------------------------
	List<Integer> QJ(Card[] c, boolean suited){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[10].x == 1 && number_occurences[11].x == 1){ //Check if QJ, Q >= 1 or J >= 1 would have been a High Pair
			temp.add(number_occurences[10].y.get(0)); temp.add(number_occurences[11].y.get(0));
			if(suited == true){
				if(c[temp.get(0)].getSuit() == c[temp.get(1)].getSuit()) //check if suited
					return temp;
				else
					return null;		
			}else{
				return temp;
			}
			
		}
		return null;
	}
	
	//----------------------------------------3 TO A FLUSH, ALL HIGH CARDS----------------------------------
	List<Integer> _3ToFlush_nHighCards(Card[] c, int numHighCards){ //Could return the suit
		List<Integer> temp = new ArrayList<Integer>();
		for(int i=0; i<4; i++){
			if(suit_occurences[i] == 3){ //If it's 3 to a flush
				
				for(int j=0; j<5; j++){
					if(c[j].getSuit() == getSuit(i)){ //getSuit converts from int to enum
						temp.add(c[j].getNumber());
					}
				}
				if(numHighCards == getNumHighCards(temp)) //Check if the 3 flush cards have numHighCards
					return temp;
				else
					return null;
				
			}
		}
		return null;
 	}
	
	//----------------------------------------2 SUITED HIGH CARDS----------------------------------
	List<Integer> _2SuitedHighCards(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[0].x == 1){ //Check Aces
			temp.add(number_occurences[0].y.get(0));
		}
		for(int i=10; i<13; i++){
			temp.add(number_occurences[i].y.get(0));
		}
		if(temp.size() == 2 && allSameSuit(c,temp))
			return temp;
		return null;
	}
	
	//----------------------------------------KQJ UNSUITED----------------------------------
	List<Integer> KQJunsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[10].x == 1 && number_occurences[11].x == 1 && number_occurences[12].x == 1){
			temp.add(number_occurences[10].y.get(0));
			temp.add(number_occurences[11].y.get(0));
			temp.add(number_occurences[12].y.get(0));
			//If any two of them had the same suited, it would be picked up by 2 suited high cards, so they are unsuited
			return temp;

		}
		return null;
	}
	
	//----------------------------------------JT SUITED----------------------------------
	List<Integer> JTsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[9].x == 1 && number_occurences[10].x == 1){
			temp.add(number_occurences[9].y.get(0)); temp.add(number_occurences[10].y.get(0));
			if(c[temp.get(0)].getSuit() == c[temp.get(1)].getSuit())
				return temp;
			else
				return null;
		}
		return null;
	}
	
	//----------------------------------------QJ UNSUITED was already considered see above--------------------------------
	
	//----------------------------------------QT SUITED----------------------------------
	List<Integer> QTsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[9].x == 1 && number_occurences[11].x == 1){
			temp.add(number_occurences[9].y.get(0));temp.add(number_occurences[11].y.get(0));
			if(c[temp.get(0)].getSuit() == c[temp.get(1)].getSuit())
				return temp;
			else
				return null;
		}
		return null;
	}
	
	//----------------------------------------KQ unsuited----------------------------------
	List<Integer> KQunsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[11].x == 1 && number_occurences[12].x == 1){
			temp.add(number_occurences[11].y.get(0)); temp.add(number_occurences[12].y.get(0));
			return temp;
		}
		return null;
	}
	
	//----------------------------------------KJ unsuited----------------------------------
	List<Integer> KJunsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[10].x == 1 && number_occurences[12].x == 1){
			temp.add(number_occurences[10].y.get(0)); temp.add(number_occurences[12].y.get(0));
			return temp;
		}
		return null;
	}
	
	//----------------------------------------ACE----------------------------------
	List<Integer> Ace(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[0].x == 1){
			temp.add(number_occurences[0].y.get(0));
			return temp;
		}
		return null;
	}
	
	//----------------------------------------KT suited----------------------------------
	List<Integer> KTsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(number_occurences[9].x == 1 && number_occurences[12].x == 1){
			temp.add(number_occurences[9].y.get(0));temp.add(number_occurences[12].y.get(0));
			if(c[temp.get(0)].getSuit() == c[temp.get(1)].getSuit()){
				return temp;
			}else{
				return null;
			}
		}
		return null;
	}
	
	//----------------------------------------JACK, QUEEN, KING----------------------------------
	List<Integer> JackQueenOrKing(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		for(int i=10;i<13;i++){
			if(number_occurences[i].x == 1){
				temp.add(number_occurences[0].y.get(0));
				return temp;
			}
		}
		return null;
	}
	*/
}
