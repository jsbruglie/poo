package video_poker;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {
	
	/**
	 * Reads a card file to memory
	 * @param args Not used
	 */
	public static void main(String[] args){
		Card[][] matrix = cardFileParser("TESTS/difficult_hands.txt");
		for (Card[] line : matrix){
			for (Card card : line){
				System.out.print(card + " ");
			}
			System.out.println("");
		}
		System.exit(0);
	}
	
	/**
	 * Processes a card file and returns a matrix of cards
	 * @param filename The card file to be processed
	 * @return A matrix of cards read from the input file
	 */
	public static Card[][] cardFileParser(String filename){
		File file = new File(filename);
        Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        int nb_lines = 0;
		try {
			nb_lines = countLines(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		Card[][] cards = new Card[nb_lines][];
        
		int i = 0;

        while(sc.hasNextLine() && i < nb_lines){
        	cards[i++] = parseCardLine(sc.nextLine());
        }
		return cards;
		
	}
	
	/**
	 * Parses a single line of a card file
	 * @param line The line string
	 * @return The array of cards present in the input line string
	 */
	public static Card[] parseCardLine(String line){
		
		final String regex = "([2-9]|[TJQKA])([HCDS])";
		final Pattern pattern = Pattern.compile(regex);
		
		Rank number = null;
		Suit suit = null;
		
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
		
		return cards;
	}
	
	// TODO Create our own function
	
	/**
	 * Counts the number of lines in a file
	 * @param filename The input file name
	 * @see http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
	 */
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
	        return (count == 0 && !empty) ? 1 : count + 1;
	    } finally {
	        is.close();
	    }
	}
}
