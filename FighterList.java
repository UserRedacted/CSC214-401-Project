import java.util.ArrayList;

public class FighterList {

	private ArrayList<Fighter> fighters = new ArrayList<>();
	
	
	public FighterList() {
		loadFighters();
	}
	
	

	// Handles fighter setup and character list
	public void loadFighters() {
		
		// Characters and stats for constructor go here
		
		Fighter rocknald = new Fighter("Rocknald", 350, 80, 40, 60, 40);
		fighters.add(rocknald);
		
		Fighter walbow = new Fighter("Walbow", 210, 90, 70, 100, 70);
		fighters.add(walbow);

		Fighter kurt = new Fighter("Kurt", 290, 50, 40, 100, 60);
		fighters.add(kurt);

		Fighter zen = new Fighter("Zen", 300, 40, 40, 70, 150);
		fighters.add(zen);
		
		Fighter grubby = new Fighter("Grubby", 300, 50, 90, 50, 100);
		fighters.add(grubby);
		
	}
	
	public ArrayList<Fighter> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<Fighter> fighters) {
		this.fighters = fighters;
	}
	
	
}
