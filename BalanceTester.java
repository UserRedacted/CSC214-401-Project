
public class BalanceTester {
	
	
	static FighterList fighters = new FighterList(); // Loading the game's fighters
	static PlayerList players = new PlayerList();
	
	static AI p1;
	static AI p2;
	
	static int p1_won = 0; // numerical win rate for Fighter 1
	static int p2_won = 0; // numerical win rate for Fighter 2
	
	public static void main(String[] args) {


		p1 = (AI) players.getPlayers().get(2);
		p2 = (AI) players.getPlayers().get(3);
		
		p1.setOpponent(p2);
		p2.setOpponent(p1);
		
		testAll("Shifty", 100000);



	}

	// Tests one fighter against every other fighter
	private static void testAll(String fighter_name, int numBattles) {
		System.out.println("_______________________________________");
		for(int i = 0; i < fighters.getFighters().size(); i++) {
			if(fighter_name != fighters.getFighters().get(i).getName())
				test(fighter_name, fighters.getFighters().get(i).getName(), numBattles);
		}
		System.out.println("_______________________________________");

	}
	
	
	// Function for testing two given fighters
	private static void test(String fighter1_name, String fighter2_name, int numBattles) {
		p1_won = 0;
		p2_won = 0;
		System.out.println("Match-up: " + fighter1_name + " vs. " + fighter2_name);
		for(int i = 0; i < numBattles; i ++) {
			setFighters(fighter1_name, fighter2_name);
			
			while(p1.getFighter().getHp() > 0 && p2.getFighter().getHp() > 0) {
				takeTurn();
			}
			
			if(p1.getFighter().getHp() > 0) {
				p1_won ++;
			} else if(p2.getFighter().getHp() > 0) {
				p2_won ++;
			}
		}	
		printResults(numBattles);
	}

	
	
	// HELPER METHODS FOR TEST
	
	// Prints whether the fighter being tests won or lost a majority of their battles
	// and displays the win-rate.
	private static void printResults(int numBattles) {
		double percentWon;
		percentWon = 100.0*p1_won/(p1_won+p2_won);
		System.out.println("\t" + p1.getFighter().getName() + " wins " + p1_won + " / " +p2_won + " (" + percentWon + "%) of matches");
	}

	// One turn of combat, shortened and refined
	private static void takeTurn() {
		
		p1.getFighter().setChosenAction(p1.makeDecision());
		p2.getFighter().setChosenAction(p2.makeDecision());

		Fighter.compareAction(p1.getFighter(), p2.getFighter());
	}

	// Looks for fighters with a name the same as the strings sent as parameters
	private static void setFighters(String fighter1_name, String fighter2_name) {
		for(int i = 0; i < fighters.getFighters().size(); i++) {
			if(fighters.getFighters().get(i).getName().equals(fighter1_name)) {
				p1.setFighter(new Fighter(fighters.getFighters().get(i)));
			}
			if(fighters.getFighters().get(i).getName().equals(fighter2_name)) {
				p2.setFighter(new Fighter(fighters.getFighters().get(i)));
			}
		}
	}
	
	
	
	
	
	
	
}
