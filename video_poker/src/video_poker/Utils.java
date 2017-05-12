package video_poker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
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
		int i = 0;
		for (Card[] line : matrix){
			System.out.print(i + " - ");
			for (Card card : line){
				System.out.print(card + " ");
			}
			System.out.println("");
			i++;
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
		
        if(nb_lines == 0){
        	System.out.println("File is empty");
        	System.exit(-1);
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
		
		String[] split = line.split("\\s+");
		if (split == null){
			return null;
		}
		
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
	
	/**
	 * Counts the number of lines in a file
	 * @param filename The input file name
	 * @throws IOException When there are issues with file reading
	 * @return The number of lines in a file
	 */
	public static int countLines(String filename) throws IOException {
		
		 LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
		 @SuppressWarnings("unused")
		 String line = "";
		 while ((line = reader.readLine()) != null) {}
		 int count = reader.getLineNumber(); 
		 reader.close();
		 return count;
	}
	
	public static List<String> parseCmdFile(String filename){
		
		File file = new File(filename);
        Scanner reader = null;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		final String regex = "(\\ba\\s|\\bd\\b|(\\bb\\s([0-9]+)|b\\b)|\\b(h\\s[1-5\\s]{0,9}\\b)|\\bs\\b|\\bq\\b|\\B\\$\\B)";
		final Pattern pattern = Pattern.compile(regex);
		
		List<String> cmd_list = new ArrayList<String>();
		String line = null;
		
		// Check for empty file
		if (reader.hasNextLine())
			line = reader.nextLine();
		else{
			return null;
		}
		
		final Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
		   cmd_list.add(matcher.group(0));
		}
		
		// TODO - DEBUG
		
		System.out.println("Original file contents:");
		System.out.println(line);
		
		System.out.println("Considered commands:");
		for (int i = 0; i < cmd_list.size(); i++){
			System.out.print(cmd_list.get(i) +" ");
		}
		System.out.println("");
		
		return cmd_list;
	}
}
