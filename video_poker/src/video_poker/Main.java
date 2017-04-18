package video_poker;

public class Main {

	public static void main(String[] args) {
		Game game = new Game(args);
		game.start();
		game.end();
	}

}
