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
        	System.out.println(temp);
        	cards[i] = new Card[temp.size()];
        	for(int j=0; j<temp.size(); j++)
        		cards[i][j] = new Card(temp.get(j).getSuit(), temp.get(j).getNumber());
              
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
			System.out.println(cardstring);
			int number = 0;
			switch(cardstring.charAt(0)){
			case 'A':
				number = 1;
				break;
			case '2':
				number = 2;
				break;
			case '3':
				number = 3;
				break;
			case '4':
				number = 4;
				break;
			case '5':
				number = 5;
				break;
			case '6':
				number = 6;
				break;
			case '7':
				number = 7;
				break;
			case '8':
				number = 8;
				break;
			case '9':
				number = 9;
				break;
			case 'T':
				number = 10;
				break;
			case 'J':
				number = 11;
				break;
			case 'Q':
				number = 12;
				break;
			case 'K':
				number = 13;
				break;
			}
			System.out.println(number);
			switch(cardstring.charAt(1)){
				case 'H':
					cardlist.add(new Card(Hearts,number));
					break;
				case 'D':
					cardlist.add(new Card(Diamonds,number));
					break;
				case 'S':
					cardlist.add(new Card(Spades,number));
					break;
				case 'C':
					cardlist.add(new Card(Clubs,number));
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
