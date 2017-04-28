package video_poker;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static video_poker.Suit.*;
import static video_poker.Rank.*;

public final class Utils {
	public static void main(String[] args){
		Card[][] c = cardFileParser("/home/pedro/poo/video_poker/TESTS/difficult_hands.txt");
		System.out.println(c);
		System.exit(0);
	}
	public static Card[][] cardFileParser(String filename){
		File file = new File(filename);
        Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int numlines = 0;
		try {
			numlines = countLines(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Card[][] cards = new Card[numlines][];
        int i=0;


        while(sc.hasNextLine()){
        	List<Card> temp = parseCardLine(sc.nextLine());
        	//System.out.println(temp);
        	cards[i] = new Card[temp.size()];
        	for(int j=0; j<temp.size(); j++)
        		cards[i][j] = new Card(temp.get(j).rank, temp.get(j).suit);
              
        	i++;
        }
		return cards;
		
	}
	public static List<Card> parseCardLine(String line){
		List<Card> cardlist = new ArrayList<Card>();
		//While not end line
		String[] parts = line.split(" ");
		//Read pairs of chars space separated
		
		for(int i=0; i<parts.length; i++){
			String cardstring = parts[i];
			//System.out.println(cardstring);
			Rank rank = null;
			switch(cardstring.charAt(0)){
			case 'A':
				rank = A;
				break;
			case '2':
				rank = n2;
				break;
			case '3':
				rank = n3;
				break;
			case '4':
				rank = n4;
				break;
			case '5':
				rank = n5;
				break;
			case '6':
				rank = n6;
				break;
			case '7':
				rank = n7;
				break;
			case '8':
				rank = n8;
				break;
			case '9':
				rank = n9;
				break;
			case 'T':
				rank = T;
				break;
			case 'J':
				rank = J;
				break;
			case 'Q':
				rank = Q;
				break;
			case 'K':
				rank = K;
				break;
			}
			
			switch(cardstring.charAt(1)){
				case 'H':
					cardlist.add(new Card(rank, Hearts));
					break;
				case 'D':
					cardlist.add(new Card(rank, Diamonds));
					break;
				case 'S':
					cardlist.add(new Card(rank, Spades));
					break;
				case 'C':
					cardlist.add(new Card(rank, Clubs));
					break;
			}
		}
		//parse first element of the pair as number and the second as suit
		
		return cardlist;
	}
	
	/** @see http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java */
	public static int countLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
}
