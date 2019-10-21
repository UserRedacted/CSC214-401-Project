
public class BalanceTester {
	
	
	static FighterList fighters = new FighterList(); // Loading the game's fighters
	static Fighter f1;
	static Fighter f2;
	static int f1_won = 0; // numerical win rate for Fighter 1
	static int f2_won = 0; // numerical win rate for Fighter 2
	
	public static void main(String[] args) {
		
		
		
		testAll("Rocknald", 100000);
		testAll("Walbow", 100000);
		testAll("Zen", 100000);
		testAll("Grubby", 100000);
		testAll("Sir Fuzzilot", 100000);

	}

	// Tests one fighter against every other fighter
	private static void testAll(String fighter_name, int numBattles) {
		System.out.println(fighter_name.toUpperCase());

		System.out.println("_______________________________________");
		for(int i = 0; i < fighters.getFighters().size(); i++) {
			if(fighter_name != fighters.getFighters().get(i).getName())
				test(fighter_name, fighters.getFighters().get(i).getName(), numBattles);
		}
		System.out.println("_______________________________________");

	}
	
	
	// Function for testing two given fighters
	private static void test(String fighter1_name, String fighter2_name, int numBattles) {
		f1_won = 0;
		f2_won = 0;
		System.out.println("Match-up: " + fighter1_name + " vs. " + fighter2_name);
		for(int i = 0; i < numBattles; i ++) {
			setFighters(fighter1_name, fighter2_name);
			
			while(f1.getHp() > 0 && f2.getHp() > 0) {
				takeTurn();
			}
			
			if(f1.getHp() > 0) {
				f1_won ++;
			} else {
				f2_won ++;
			}
		}	
		printResults(numBattles);
	}

	
	
	// HELPER METHODS FOR TEST
	
	// Prints whether the fighter being tests won or lost a majority of their battles
	// and displays the win-rate.
	private static void printResults(int numBattles) {
		double percentWon;
		percentWon = 100.0*f1_won/numBattles;
		System.out.println("\t" + f1.getName() + " wins " + f1_won + " / " +f2_won + " (" + percentWon + "%) of matches");
	}

	// One turn of combat, shortened and refined
	private static void takeTurn() {
		
		f1.setChosenAction((int)(Math.random()*4));
		f2.setChosenAction((int)(Math.random()*4));

		Fighter.compareAction(f1, f2);
	}

	// Looks for fighters with a name the same as the strings sent as parameters
	private static void setFighters(String fighter1_name, String fighter2_name) {
		for(int i = 0; i < fighters.getFighters().size(); i++) {
			if(fighters.getFighters().get(i).getName().equals(fighter1_name)) {
				f1 = new Fighter(fighters.getFighters().get(i));
			}
			if(fighters.getFighters().get(i).getName().equals(fighter2_name)) {
				f2 = new Fighter(fighters.getFighters().get(i));
			}
		}
	}
	
	
	
	
	
	
	
}
