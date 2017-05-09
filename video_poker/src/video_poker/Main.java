package video_poker;

/**
 * Main class. Used for running the main program.
 */
public class Main {
	/**
	 * Main program method
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		Game game = new Game(args);
		game.start();
		game.end();
	}
}
