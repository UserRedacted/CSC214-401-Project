import java.util.ArrayList;

public class FighterList {

	private ArrayList<Fighter> fighters = new ArrayList<>();
	
	
	public FighterList() {
		loadFighters();
	}
	
	

	// Handles fighter setup and character list
	public void loadFighters() {
		
		// Characters and stats for constructor go here
		
		Fighter rocknald = new Fighter("Rocknald", 35, 8, 4, 6, 40);
		fighters.add(rocknald);
		
		Fighter walbow = new Fighter("Walbow", 21, 9, 7, 10, 70);
		fighters.add(walbow);

		Fighter kurt = new Fighter("Kurt", 29, 5, 4, 10, 60);
		fighters.add(kurt);

		Fighter zen = new Fighter("Zen", 30, 4, 4, 7, 150);
		fighters.add(zen);
		
		Fighter grubby = new Fighter("Grubby", 30, 5, 10, 5, 100);
		fighters.add(grubby);
		
		Fighter sirFuzzilot = new Fighter("Sir Fuzzilot", 28, 6, 5, 11, 90);
		fighters.add(sirFuzzilot);
		
	}
	
	public ArrayList<Fighter> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<Fighter> fighters) {
		this.fighters = fighters;
	}
	
	
}
