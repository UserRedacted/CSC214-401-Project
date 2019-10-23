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
		rocknald.setSpriteIdle("resources\\sprites\\Rocknald.gif");
		rocknald.setSpriteHurt("resources\\sprites\\RocknaldHurt.gif");
		fighters.add(rocknald);

		Fighter walbow = new Fighter("Walbow", 240, 95, 90, 90, 100);
		walbow.setSpriteIdle("resources\\sprites\\Walbow.gif");
		walbow.setSpriteHurt("resources\\sprites\\WalbowHurt.gif");
		fighters.add(walbow);
		
		Fighter blobert = new Fighter("Blobert", 300, 40, 95, 75, 120);
		blobert.setSpriteIdle("resources\\sprites\\Blobert.gif");
		blobert.setSpriteHurt("resources\\sprites\\BlobertHurt.gif");
		fighters.add(blobert);
		
		Fighter zen = new Fighter("Zen", 285, 50, 50, 70, 200);
		zen.setSpriteIdle("resources\\sprites\\Zen.gif");
		zen.setSpriteHurt("resources\\sprites\\ZenHurt.gif");
		fighters.add(zen);
		
		Fighter grubby = new Fighter("Grubby", 290, 45, 135, 60, 100);
		grubby.setSpriteIdle("resources\\sprites\\Grubby.gif");
		grubby.setSpriteHurt("resources\\sprites\\GrubbyHurt.gif");
		fighters.add(grubby);
		
		Fighter sirFuzzilot = new Fighter("Sir Fuzzilot", 305, 65, 45, 105, 80);
		sirFuzzilot.setSpriteIdle("resources\\sprites\\SirFuzzilot.gif");
		sirFuzzilot.setSpriteHurt("resources\\sprites\\SirFuzzilotHurt.gif");
		fighters.add(sirFuzzilot);
		
		
	}
	
	public ArrayList<Fighter> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<Fighter> fighters) {
		this.fighters = fighters;
	}
	
	
}
