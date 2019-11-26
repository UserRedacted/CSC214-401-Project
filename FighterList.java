/**
 * Populate the list of available fighters for players to choose from
 * */

import java.util.ArrayList;

public class FighterList {

	private ArrayList<Fighter> fighters = new ArrayList<>();
	
	
	public FighterList() {
		loadFighters();
	}


	/**
	 * Handles fighter setup and character list
	 */
	public void loadFighters() {
		
		// Characters and stats for constructor go here
		
		Fighter rocknald = new Fighter("Rocknald", 350, 65, 65, 60, 80);
		rocknald.setSpriteIdle("resources\\sprites\\Rocknald.gif");
		rocknald.setSpriteHurt("resources\\sprites\\RocknaldHurt.gif");
		fighters.add(rocknald);

		Fighter walbow = new Fighter("Walbow", 240, 90, 90, 90, 100);
		walbow.setSpriteIdle("resources\\sprites\\Walbow.gif");
		walbow.setSpriteHurt("resources\\sprites\\WalbowHurt.gif");
		fighters.add(walbow);
		
		Fighter blobert = new Fighter("Blobert", 300, 55, 85, 75, 120);
		blobert.setSpriteIdle("resources\\sprites\\Blobert.gif");
		blobert.setSpriteHurt("resources\\sprites\\BlobertHurt.gif");
		fighters.add(blobert);
		
		Fighter zen = new Fighter("Zen", 285, 60, 70, 75, 190);
		zen.setSpriteIdle("resources\\sprites\\Zen.gif");
		zen.setSpriteHurt("resources\\sprites\\ZenHurt.gif");
		fighters.add(zen);
		
		Fighter grubby = new Fighter("Grubby", 290, 45, 125, 50, 100);
		grubby.setSpriteIdle("resources\\sprites\\Grubby.gif");
		grubby.setSpriteHurt("resources\\sprites\\GrubbyHurt.gif");
		fighters.add(grubby);
		
		Fighter boomer = new Fighter("Boomer", 275, 70, 75, 85, 150);
		boomer.setSpriteIdle("resources\\sprites\\Boomer.gif");
		boomer.setSpriteHurt("resources\\sprites\\BoomerHurt.gif");
		fighters.add(boomer);
		
		Fighter sirFuzzilot = new Fighter("Cat Crusader", 305, 70, 60, 110, 80);
		sirFuzzilot.setSpriteIdle("resources\\sprites\\SirFuzzilot.gif");
		sirFuzzilot.setSpriteHurt("resources\\sprites\\SirFuzzilotHurt.gif");
		fighters.add(sirFuzzilot);
		
		Fighter shifty = new Fighter("Shifty", 255, 85, 75, 70, 130);
		shifty.setSpriteIdle("resources\\sprites\\Shifty.gif");
		shifty.setSpriteHurt("resources\\sprites\\ShiftyHurt.gif");
		fighters.add(shifty);
		
		Fighter zoomer = new Fighter("Zoomer", 269, 77, 82, 86, 95);
		zoomer.setSpriteIdle("resources\\sprites\\Zoomer.gif");
		zoomer.setSpriteHurt("resources\\sprites\\ZoomerHurt.gif");
		fighters.add(zoomer);
		
		
	}
	
	public ArrayList<Fighter> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<Fighter> fighters) {
		this.fighters = fighters;
	}
	
	
}
