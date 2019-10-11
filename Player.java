
public class Player {
	
	public static int numAnonPlayers = 0;
	private boolean isHuman;
	private String name;
	private Fighter fighter;
	private boolean hasActed = false;

	private int numBattles;
	private BattleLog currentBattle = new BattleLog();
	
	
	public Player(String name, boolean isHuman) {
		this.name = name;
		this.isHuman = isHuman;
		numBattles = 0;
		hasActed = false;
	}
	
	
	@Override
	public String toString() {
		return name;
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


	public void importPlayer(Player selected) {
		try {
			isHuman = selected.isHuman();
			name = selected.getName();
			hasActed = selected.hasActed();
			numBattles = selected.getNumBattles();	
		} catch (NullPointerException e) {
			//idk
		}

	}
	


	
}


