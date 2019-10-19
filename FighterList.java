import java.util.ArrayList;

public class FighterList {

	private ArrayList<Fighter> fighters = new ArrayList<>();
	
	
	public FighterList() {
		loadFighters();
	}
	
	

	// Handles fighter setup and character list
	public void loadFighters() {
		
		// Characters and stats for constructor go here
		
		Fighter rocknald = new Fighter("Rocknald", 35, 7, 5, 5, 50);
		rocknald.setSpriteFile("resources\\sprites\\Rocknald.gif");
		fighters.add(rocknald);

		Fighter walbow = new Fighter("Walbow", 24, 10, 9, 9, 100);
		walbow.setSpriteFile("resources\\sprites\\Walbow.gif");
		fighters.add(walbow);
		
		Fighter zen = new Fighter("Zen", 30, 5, 4, 7, 160);
		zen.setSpriteFile("resources\\sprites\\Zen.gif");
		fighters.add(zen);
		
		Fighter grubby = new Fighter("Grubby", 29, 7, 14, 6, 100);
		grubby.setSpriteFile("resources\\sprites\\Grubby.gif");
		fighters.add(grubby);
		
		Fighter sirFuzzilot = new Fighter("Sir Fuzzilot", 28, 6, 4, 13, 80);
		sirFuzzilot.setSpriteFile("resources\\sprites\\sirFuzzilot.gif");
		fighters.add(sirFuzzilot);
		
		
	}
	
	public ArrayList<Fighter> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<Fighter> fighters) {
		this.fighters = fighters;
	}
	
	
}
