import java.util.ArrayList;

public class FighterList {

	private ArrayList<Fighter> fighters = new ArrayList<>();
	
	
	public FighterList() {
		loadFighters();
	}
	
	

	// Handles fighter setup and character list
	public void loadFighters() {
		
		// Characters and stats for constructor go here
		
		Fighter rocknald = new Fighter("Rocknald", 350, 70, 50, 50, 50);
		rocknald.setSpriteFile("resources\\sprites\\Rocknald.gif");
		fighters.add(rocknald);

		Fighter walbow = new Fighter("Walbow", 240, 95, 90, 90, 100);
		walbow.setSpriteFile("resources\\sprites\\Walbow.gif");
		fighters.add(walbow);
		
		Fighter zen = new Fighter("Zen", 285, 50, 50, 70, 200);
		zen.setSpriteFile("resources\\sprites\\Zen.gif");
		fighters.add(zen);
		
		Fighter grubby = new Fighter("Grubby", 290, 45, 135, 60, 100);
		grubby.setSpriteFile("resources\\sprites\\Grubby.gif");
		fighters.add(grubby);
		
		Fighter sirFuzzilot = new Fighter("Sir Fuzzilot", 305, 65, 45, 105, 80);
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
