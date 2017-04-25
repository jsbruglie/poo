package video_poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayEvaluator {
	private Bin[] card_number_occurrences;
	private int[] suit_occurrences;
	public PlayEvaluator(){
		card_number_occurrences = new Bin[13];
		suit_occurrences = new int[4];
		
	}
	/* The following are all package methods */
	
	//----------------------------------------INITIALIZE--------------------------------
	//Every time a new card is evaluated call this method
	void initialize(Card c[]){
		for(int i=0; i<13; i++){
			card_number_occurrences[i] = new Bin(0, new ArrayList<Integer>());
		}
		for(int i=0; i<4; i++){
			suit_occurrences[i] = 0;
		}
		for(int i=0; i<5;i++){
			suit_occurrences[c[i].getSuit().getValue()]++;//Fill suit occurences
			card_number_occurrences[c[i].getNumber()-1].count++; //Fill number occurences
			(card_number_occurrences[c[i].getNumber()-1].cardlist).add(i);//Insert cards in buckets
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
		switch(value){
		case 0:
			return Suit.Clubs;
		case 1:
			return Suit.Diamonds;
		case 2:
			return Suit.Spades;
		case 3:
			return Suit.Hearts;
		}
		return null;
	}
	
	//Check if a list of card indexes all have the same suit
	public boolean allSameSuit(Card c[], List<Integer> indexes){
		Suit s = c[indexes.get(0)].getSuit();
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
			 if(isHighCard(cardlist.get(i)))
				 count++;
		}
		return count;
	}
	
	//Check if a given card index is a High Card
	public boolean isHighCard(int card_index){
		if(card_index == 1 || card_index == 11 || card_index == 12 || card_index == 13 || card_index == 14) //14 is for special ace
			return true;
		return false;
	}
	
	private List<Integer> NToStraight(Card[] c, int N) {
		//5 is handsize shouldn't be hardcoded
		int[] arr = new int[5];
		int[] original_arr = new int[5];
		for(int i=0; i<5; i++){
			arr[i] = c[i].getNumber();
			original_arr[i] = c[i].getNumber();
		}
		
	    Arrays.sort(arr);
	    /*for(int i=0; i<5; i++)
	    	System.out.println(arr[i]);*/
	    
	    for (int i = 0; i < arr.length - N + 1; ++i) {
	        if (arr[i] == arr[i + (N-1)] - (N-1)) { //If the values are purely sequential
	        	List<Integer> sequential_values = new ArrayList<Integer>();
	        	for(int j=i;j<i+N;j++){
	        		//Get the original card index and add it to sequential values
	        		for(int k=0; k<5; k++)
	        			if(arr[j] == original_arr[k])
	        				sequential_values.add(k);
	        	}
	        	return sequential_values;
	        }else{
	        	List<Integer> sequential_values = new ArrayList<Integer>();
	        	int totalgaps = 0;
	        	for(int j=i;j<i+N;j++){
	        		if(j!=i+N-1)
	        			totalgaps+=((arr[j+1]-arr[j])-1);
	        		//Get the original card index and add it to sequential values
	        		for(int k=0; k<5; k++)
	        			if(arr[j] == original_arr[k])
	        				sequential_values.add(k);
	        	}
	        	//Check for gaps
	        	//System.out.println("Gaps " + totalgaps);
	        	//If the gaps could potentially be replaced this is a straight candidate
	        	if(totalgaps == 5-N)
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
			if(card_number_occurrences[12].count == 1 && 
					card_number_occurrences[11].count == 1 && 
					card_number_occurrences[10].count == 1 && 
					card_number_occurrences[9].count == 1 && 
					card_number_occurrences[0].count == 1){
				return cards;
			}
		} 
		return null;
	}
	
	//----------------------------------------FOUR OF A KIND----------------------------------
	List<Integer> FourOfAKind(Card[] c){
		for(int i=0; i<13; i++){
			if(card_number_occurrences[i].count == 4){ //If there are four cards of the same number, return all of them
				return card_number_occurrences[i].cardlist;
			}
		}
		return null;
	}
	
	//----------------------------------------STRAIGHT FLUSH----------------------------------
	List<Integer> StraightFlush(Card[] c){
		List<Integer> cards = Flush(c);
		//System.out.println(cards);
		//Note that TJQKA is not a sequential straight flush, but its a Royal Flush so no need to check
		if(cards != null){ //if hand is a flush
			List<Integer> temp = NToStraight(c, 5);
			Collections.sort(cards);
			if(temp!=null)
				Collections.sort(temp);
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
		//System.out.println(temp);
		List<Integer> rethand = new ArrayList<Integer>();
		//Check over them to see if they are N cards that could form a Royal Flush
		if(temp!= null && temp.size()>=N){
			for(int i=0; i<temp.size(); i++){
				//Since cards are of the same suit we can check which ones they are, and we know there are no repetitions
				if(c[temp.get(i)].getNumber() == 1 || c[temp.get(i)].getNumber() == 10 || 
						c[temp.get(i)].getNumber() == 11 || c[temp.get(i)].getNumber() == 12 || c[temp.get(i)].getNumber() == 13){
					count++;
					rethand.add(temp.get(i));
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
		if(card_number_occurrences[0].count == 3){
			//if there are three Aces, return them
			return card_number_occurrences[0].cardlist;
		}
		return null;
	}
	
	//----------------------------------------STRAIGHT----------------------------------
	List<Integer> Straight(Card[] c){
		List<Integer> temp_c = allCards(c);
		//If TJQKA cards are not sequential, but its a straight (suits don't matter)
		if(card_number_occurrences[9].count == 1 && card_number_occurrences[10].count ==1 && card_number_occurrences[11].count == 1 && card_number_occurrences[12].count == 1 && card_number_occurrences[0].count == 1){
			return temp_c;
		}
		List<Integer> temp = new ArrayList<Integer>();
		for(int i=0; i<5; i++)
			temp.add(c[i].getNumber());
		
		Collections.sort(temp); //Sort the cards
		int j=0; //Get the lowest card and check if there is only one of it and the 4 bins after it are filled
		int idx = temp.get(0)-1;
		if(idx > 8) //Straight cannot start at 8
			return null;
		for(int i=idx; i<idx+5; i++){
			if(card_number_occurrences[i].count == 1){
				j++;
			}
		}
		if(j == 5)
			return temp_c;
		else 
			return null;
	}

	//----------------------------------------FULL HOUSE----------------------------------
	List<Integer> FullHouse(Card[] c){
		int npairs = 0, ntriples = 0;
		for(int i=0; i<card_number_occurrences.length;i++){
			if(card_number_occurrences[i].count == 2)
				npairs++;
			if(card_number_occurrences[i].count == 3)
				ntriples++;
		}
		if(npairs == 1 && ntriples == 1){
			List<Integer> cards = allCards(c);
			return cards;
		}else{
			return null;
		}
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
		for(int i=1; i<card_number_occurrences.length; i++){ //Start with 2 (no Aces). If there are three cards of the same number, return all of them
			if(card_number_occurrences[i].count == 3){
				return card_number_occurrences[i].cardlist;
			}
		}
		return null;
	}
	
	//----------------------------------------4 TO A STRAIGHT FLUSH----------------------------------
	List<Integer> _4ToStraightFlush(Card[] c) {
		List<Integer> temp = NToStraight(c, 4); //Get 4 to a straight
		//System.out.println(temp);
		if(temp != null){ //Check if they have the same suit
			if(allSameSuit(c, temp))
				return temp;
		}
		return null;
	}
	
	//----------------------------------------TWO PAIR----------------------------------
	List<Integer> TwoPair(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		for(int i=0; i<card_number_occurrences.length; i++){
			if(card_number_occurrences[i].count == 2){ //If a pair is found, add its card indexes to the list
				temp.add(card_number_occurrences[i].cardlist.get(0)); temp.add(card_number_occurrences[i].cardlist.get(1));
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
		if(card_number_occurrences[0].count == 2){ //Pair of Aces
			for(int j=0; j<2; j++)
				temp.add(card_number_occurrences[j].cardlist.get(j)); 
			return temp;
		}
		for(int i=10;i<13;i++){ //Pair of J,Q,K
			if(card_number_occurrences[i].count == 2){
				for(int j=0; j<2; j++)
					temp.add(card_number_occurrences[i].cardlist.get(j)); 
				return temp;
			}
		}
		return null;
	}
	
	//----------------------------------------N TO A FLUSH----------------------------------
	List<Integer> NToFlush(Card[] c, int N){ 
		List<Integer> temp = new ArrayList<Integer>();
		
		for(int i=0; i<4; i++){
			if(suit_occurrences[i] >= N){ //If we found N cards of the same suit, go through the hand to find+add them to a list
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
		//5 is handsize shouldn't be hardcoded
	    for (int i = 0; i < 5 - 4 + 1; ++i) {
	        	List<Integer> temp = new ArrayList<Integer>();
	        	List<Integer> temp_c = new ArrayList<Integer>();
	        	for(int j=i;j<i+4;j++){
	        		temp_c.add(j);    		
	        		temp.add(c[j].getNumber());
	        	}
        		Collections.sort(temp);

        		int m=0;
        		for(int k=0; k<4; k++){
        			if(k<3)
        				if(temp.get(k+1) == temp.get(k)+1) //JQKA is not an outside straight so no problem
        					m++;
        		}
        		if(m == 3 && (temp.get(0)-1) != 0) //If the first card is Ace then we have A234 which is an inside straight
	        	  //All other 4 sequential cards are a 4 to OutsideStraight
	    			return temp_c;
	    }
		return null;
	}
	
	//----------------------------------------LOW PAIR----------------------------------
	List<Integer> LowPair(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		for(int i=1; i<10; i++){ //Low Pairs
			if(card_number_occurrences[i].count == 2){
				for(int j=0; j<2; j++)
					temp.add(card_number_occurrences[i].cardlist.get(j)); 
				return temp;
			}
		}
		return null;
	}
	
	//----------------------------------------AKQJ UNSUITED----------------------------------
	List<Integer> AKQJunsuited(Card[] c){
		//AKQJ suited is 4 to a royal flush and would have already been checked
		//If number of occurrences of A,K,Q or J >= 1 (e.g AAKQJ) would be a High Pair which would have already been checked
		//If 3 in 4 of AKQJ are suited it's a 3 to a Royal Flush and would have already been checked
		if(card_number_occurrences[0].count == 1 && card_number_occurrences[10].count == 1 && card_number_occurrences[11].count == 1 && card_number_occurrences[12].count == 1){
			List<Integer> temp = new ArrayList<Integer>();
			temp.add(card_number_occurrences[0].cardlist.get(0)); 
			for(int k=10; k<13; k++)
				temp.add(card_number_occurrences[k].cardlist.get(0));
			
			return temp;
		}
		return null;
	}
	
	//----------------------------------------3 TO A STRAIGHT FLUSH. ALL TYPES----------------------------------
	//Could be a class with 3 inherited implementations
	List<Integer> _3ToStraightFlush(Card c[], int type){
		
		List<Integer> temp_c = NToFlush(c, 3); 
		if(temp_c == null) return null; //If not a 3 to a flush terminate here
		
		List<Integer> temp = new ArrayList<Integer>();
		for(int i=0; i<temp_c.size(); i++)
			temp.add(c[temp_c.get(i)].getNumber());
		
		//Sort temp
		Collections.sort(temp);
		
		List<Integer> aceHighVal = null;
		//Check if any of those cards is an Ace(0) and create another "alternative hand" with 14 there
		int hasAce = 0;
		if(temp.get(0) == 1){
			hasAce = 1;
			aceHighVal = new ArrayList<Integer>(temp);
			aceHighVal.set(0, 14);
		}
		
		//Do this regardless of Ace or not (does A=0 aswell)
		int nHighCards = getNumHighCards(temp);

		//Compute total number of gaps
		int gaps = ((temp.get(1)-temp.get(0))-1)+((temp.get(2)-temp.get(1))-1);
		switch(type){ 
		
			case 1:	//e.g 8JQ
				//234 fulfills the condition of type 1 but is actually a type 2
				if(temp.get(0)==2 && temp.get(1)==3 && temp.get(2)==4){
					System.out.println("234 is actually type 2 not 1");
					return null;
				}
				
				if(nHighCards >= gaps){
					System.out.println("Type 1");
					return temp_c;
				}
						
				if(hasAce == 1){ //Consider A = 14 and do the same thing
					//Sort aceHighVal
					Collections.sort(aceHighVal);
					//Compute total number of gaps
					int ace_gaps = ((aceHighVal.get(1)-aceHighVal.get(0))-1)+((aceHighVal.get(2)-aceHighVal.get(1))-1);
					if(nHighCards >= ace_gaps){
						System.out.println("Type 1");
						return temp_c;
					}
						
				}
				break;
			case 2:
				//System.out.println(temp);
				//Check if 234 suited
				if(temp.get(0)==2 && temp.get(1)==3 && temp.get(2)==4){
					System.out.println("Type 2");
					return temp_c;
				}
				/** @see https://wizardofodds.com/games/video-poker/strategy/jacks-or-better/9-6/intermediate/*/
				if(gaps == 1 && nHighCards == 0){ //One gap and no high cards
					System.out.println("Type 2");
					return temp_c;
				}
				if(gaps == 2 && nHighCards == 1){ //2 gaps and 1 high card
					System.out.println("Type 2");
					return temp_c;
				}
				if(hasAce == 1){ //Consider A = 14 and do the same thing
					//Sort aceHighVal
					Collections.sort(aceHighVal);
					//Compute total number of gaps
					int ace_gaps = ((aceHighVal.get(1)-aceHighVal.get(0))-1)+((aceHighVal.get(2)-aceHighVal.get(1))-1);
					if(ace_gaps == 1)
						return temp_c;
					if(ace_gaps == 2 && nHighCards == 2){
						System.out.println("Type 2");
						return temp_c;
					}
						
				}
				break;
			case 3:
				if(hasAce == 0){ //Type 3 cannot have an Ace
					if(nHighCards == 0 && gaps == 2){
						System.out.println("Type 3");
						return temp_c;
					}
				}
				break;
		}
		return null;
	}
	
	//----------------------------------------4 TO AN INSIDE STRAIGHT. ALL HIGH CARDS----------------------------------
	List<Integer> aceCheck(Card c[], int nHighCards){
		Card[] c_ace = new Card[5];
		
		for(int i=0; i<5; i++){
			if(c[i].getNumber() != 1)
				c_ace[i] = new Card(c[i].getSuit(), c[i].getNumber());
			else
				c_ace[i] = new Card(c[i].getSuit(), 14);
				
		}
		List<Integer> tempace_c = new ArrayList<Integer>();
		List<Integer> tempace = new ArrayList<Integer>();
		for(int i=0; i<5; i++)
			tempace.add(c_ace[i].getNumber());

		Collections.sort(tempace);
		
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				if(c_ace[j].getNumber() == tempace.get(i)){
					tempace_c.add(j);
				}
			}
		}
		for(int i=0; i<2; i++){
			List<Integer> sub_c = new ArrayList<Integer>(); List<Integer> sub = new ArrayList<Integer>();
			sub_c.add(tempace_c.get(i));sub_c.add(tempace_c.get(i+1));sub_c.add(tempace_c.get(i+2));sub_c.add(tempace_c.get(i+3));
			sub.add(tempace.get(i));sub.add(tempace.get(i+1));sub.add(tempace.get(i+2));sub.add(tempace.get(i+3));
			Collections.sort(sub);
			//System.out.println(sub);
			int gaps = 0;
			
			for(int j=0; j<3; j++){
				int g = ((sub.get(j+1)-sub.get(j))-1);
				if(g == -1) g=0; //This means j+1 and j are equal
				gaps+=g;
			}
			//System.out.println(gaps);
			if(gaps == 1){
				//Compare result with given nHighCards
				if(getNumHighCards(sub) == nHighCards)
					return sub_c;
				
			}
		}
		return null;
	}
	
	List<Integer> _4ToInsideStraight(Card[] c, int nHighcards){
		List<Integer> temp_c = new ArrayList<Integer>();
		List<Integer> temp = new ArrayList<Integer>();
		/* The following 2 NEED TO BE FIXED to return hand indexes instead of number of occurences*/
		//Check if A234  is in the hand
		if(card_number_occurrences[0].count >= 1 && card_number_occurrences[1].count >= 1 && card_number_occurrences[2].count >= 1 && card_number_occurrences[3].count >= 1){
			if(nHighcards == 1 || nHighcards == 2){ //These are the only situations where this makes sense
				//Add A234 to temp and return
				for(int i=0; i<4; i++)
					temp.add(card_number_occurrences[i].cardlist.get(0));
			}
		}
		//Check if JQKA is in the hand
		if(card_number_occurrences[0].count >= 1 && card_number_occurrences[12].count >= 1 && card_number_occurrences[11].count >= 1 && card_number_occurrences[10].count >= 1){
			if(nHighcards == 3){ //These are the only situations where this makes sense
				//Add JQKA to temp and return
				temp.add(card_number_occurrences[0].cardlist.get(0)); 
				for(int i=10; i<13; i++)
					temp.add(card_number_occurrences[i].cardlist.get(0));
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
		
		//If there is an Ace in the hand try it first with Ace = 14
		if(temp.get(0) == 1){
			List<Integer> aceList = aceCheck(c, nHighcards);
			if(aceList != null)
				return aceList;
		}
		
		//Since this did not work go ahead and try with the normal ace
		for(int i=0; i<2; i++){
			List<Integer> sub_c = new ArrayList<Integer>(); List<Integer> sub = new ArrayList<Integer>();
			sub_c.add(temp_c.get(i));sub_c.add(temp_c.get(i+1));sub_c.add(temp_c.get(i+2));sub_c.add(temp_c.get(i+3));
			sub.add(temp.get(i));sub.add(temp.get(i+1));sub.add(temp.get(i+2));sub.add(temp.get(i+3));
			Collections.sort(sub);
			//System.out.println(sub);
			int gaps = 0;
			//int gaps1 = ((sub1.get(1)-sub1.get(0))-1)+((sub1.get(2)-sub1.get(1))-1)+((sub1.get(3)-sub1.get(2))-1);
			for(int j=0; j<3; j++){
				int g = ((sub.get(j+1)-sub.get(j))-1);
				if(g == -1) g=0;
				gaps+=g;
			}
			//System.out.println(gaps);
			if(gaps == 1){
				//Compare result with given nHighCards
				if(getNumHighCards(sub) == nHighcards)
					return sub_c;
			}
		}
		return null;	
	}
	
	//----------------------------------------QJ, SUITED OR UNSUITED----------------------------------
	List<Integer> QJ(Card[] c, boolean suited){
		List<Integer> temp = new ArrayList<Integer>();
		if(card_number_occurrences[10].count == 1 && card_number_occurrences[11].count == 1){ //Check if QJ, Q >= 1 or J >= 1 would have been a High Pair
			temp.add(card_number_occurrences[10].cardlist.get(0)); 
			temp.add(card_number_occurrences[11].cardlist.get(0));
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
		List<Integer> temp_c = new ArrayList<Integer>();
		for(int i=0; i<suit_occurrences.length; i++){
			if(suit_occurrences[i] == 3){ //If it's 3 to a flush	
				for(int j=0; j<5; j++){
					if(c[j].getSuit() == getSuit(i)){ //getSuit converts from int to enum
						temp.add(c[j].getNumber());
						temp_c.add(j);
					}
				}
				if(numHighCards == getNumHighCards(temp)) //Check if the 3 flush cards have numHighCards
					return temp_c;
				else
					return null;
				
			}
		}
		return null;
 	}
	
	//----------------------------------------2 SUITED HIGH CARDS----------------------------------
	List<Integer> _2SuitedHighCards(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(card_number_occurrences[0] != null){
			if(card_number_occurrences[0].count == 1){ //Check Aces
				temp.add(card_number_occurrences[0].cardlist.get(0));
			}
		}
		for(int i=10; i<13; i++){
			if(card_number_occurrences[i].count == 1)
				temp.add(card_number_occurrences[i].cardlist.get(0));
		}
		if(temp.size() == 2 && allSameSuit(c,temp))
			return temp;
		return null;
	}
	
	//----------------------------------------KQJ UNSUITED----------------------------------
	List<Integer> KQJunsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(card_number_occurrences[10].count == 1 && card_number_occurrences[11].count == 1 && card_number_occurrences[12].count == 1){
			for(int i=10; i<13; i++){
				temp.add(card_number_occurrences[i].cardlist.get(0));
			}
			//If any two of them had the same suited, it would be picked up by 2 suited high cards, so they are unsuited
			return temp;

		}
		return null;
	}
	
	//----------------------------------------JT SUITED----------------------------------
	List<Integer> JTsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(card_number_occurrences[9].count == 1 && card_number_occurrences[10].count == 1){
			temp.add(card_number_occurrences[9].cardlist.get(0)); 
			temp.add(card_number_occurrences[10].cardlist.get(0));
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
		if(card_number_occurrences[9].count == 1 && card_number_occurrences[11].count == 1){
			temp.add(card_number_occurrences[9].cardlist.get(0));
			temp.add(card_number_occurrences[11].cardlist.get(0));
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
		if(card_number_occurrences[11].count == 1 && card_number_occurrences[12].count == 1){
			temp.add(card_number_occurrences[11].cardlist.get(0)); 
			temp.add(card_number_occurrences[12].cardlist.get(0));
			return temp;
		}
		return null;
	}
	
	//----------------------------------------KJ unsuited----------------------------------
	List<Integer> KJunsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(card_number_occurrences[10].count == 1 && card_number_occurrences[12].count == 1){
			temp.add(card_number_occurrences[10].cardlist.get(0)); 
			temp.add(card_number_occurrences[12].cardlist.get(0));
			return temp;
		}
		return null;
	}
	
	//----------------------------------------ACE----------------------------------
	List<Integer> Ace(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(card_number_occurrences[0].count == 1){
			temp.add(card_number_occurrences[0].cardlist.get(0));
			return temp;
		}
		return null;
	}
	
	//----------------------------------------KT suited----------------------------------
	List<Integer> KTsuited(Card[] c){
		List<Integer> temp = new ArrayList<Integer>();
		if(card_number_occurrences[9].count == 1 && card_number_occurrences[12].count == 1){
			temp.add(card_number_occurrences[9].cardlist.get(0));
			temp.add(card_number_occurrences[12].cardlist.get(0));
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
			if(card_number_occurrences[i].count == 1){
				temp.add(card_number_occurrences[i].cardlist.get(0));
				return temp;
			}
		}
		return null;
	}

}
