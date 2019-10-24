
public class Player {
	
	protected boolean isHuman;
	protected String name;
	protected Fighter fighter;
	protected boolean hasActed = false;
	
	protected int numBattles;
	protected BattleLog currentBattle = new BattleLog();
	
	
	public Player(String name) {
		this.name = name;
		isHuman = true;
		numBattles = 0;
		hasActed = false;
	}
	
	
	@Override
	public String toString() {
		if(name.length() >= 11) {
			return name.substring(0, 9) + "...";
		} else {
			return name;
		}
	}

	// Setters and Getters for private data fields
	public boolean isHuman() {
		return isHuman;
	}
	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Fighter getFighter() {
		return fighter;
	}
	public void setFighter(Fighter fighter) {
		this.fighter = fighter;
	}
	public boolean hasActed() {
		return hasActed;
	}
	public void setHasActed(boolean hasActed) {
		this.hasActed = hasActed;
	}
	public BattleLog getCurrentBattle() {
		return currentBattle;
	}
	public void setCurrentBattle(BattleLog currentBattle) {
		this.currentBattle = currentBattle;
	}
	public int getNumBattles() {
		return numBattles;
	}
	public void setNumBattles(int numbattles) {
		this.numBattles = numbattles;
	}

}


