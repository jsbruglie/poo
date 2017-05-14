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
		boolean MONTECARLO = false;
		if(MONTECARLO){
			double RUNS = 50.00;
			float st = 0;
			for(int i=0; i<RUNS; i++){
				Game game = new Game(args);
				game.start();
				st+=game.variant.stats.getFinal_statistics();
			}
			System.out.println(st/RUNS);
			System.exit(0);
		}else{
			Game game = new Game(args);
			game.start();
			game.end();
		}
		
	}
}
