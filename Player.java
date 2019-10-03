
public class Player {
	
	public static int numAnonPlayers = 0;
	private boolean isHuman;
	private String name;
	private Fighter fighter;
	
	public Player(String name, boolean isHuman) {
		this.name = name;
		this.isHuman = isHuman;
	}

	
	
	@Override
	public String toString() {
		return name;
	}



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
	
	
	
	
}


