import java.util.ArrayList;

public class Player {
	
	public static int numAnonPlayers = 0;
	private boolean isHuman;
	private String name;
	private Fighter fighter;
	private boolean hasActed = false;
	
	private ArrayList<String> battleLog = new ArrayList<String>();
	
	public Player(String name, boolean isHuman) {
		this.name = name;
		this.isHuman = isHuman;
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
	public boolean isHasActed() {
		return hasActed;
	}
	public void setHasActed(boolean hasActed) {
		this.hasActed = hasActed;
	}
	public ArrayList<String> getBattleLog() {
		return battleLog;
	}
	public void setBattleLog(ArrayList<String> battleLog) {
		this.battleLog = battleLog;
	}

	
	public static String printResult(Player a, Player b) {
		return a.name + " " + a.fighter.actionToString() + "s and " + b.name + " " + b.fighter.actionToString() + "s!";
	}
	
	public static String compareAction(Player a, Player b) {
		StringBuilder output = new StringBuilder();
		
		// If Player A and B choose the same move
		if(a.fighter.getChosenAction() == b.fighter.getChosenAction()) {
			output.append(printResult(a,b) + "\n");
			output.append("The actions cancel out! No one gets hurt...");
		}
		
		// If Player A attacks and Player B grabs
		else if(a.fighter.getChosenAction() == 0 && b.fighter.getChosenAction() == 1) {
			output.append(printResult(a,b));

		}
		
		// If Player A attacks and Player B counters
		else if(a.fighter.getChosenAction() == 0 && b.fighter.getChosenAction() == 2) {
			output.append(printResult(a,b));

		}
		
		// If Player A attacks and Player B deflects
		else if(a.fighter.getChosenAction() == 0 && b.fighter.getChosenAction() == 3) {
			output.append(printResult(a,b));

		}
		
		// If Player A grabs and Player B counters
		else if(a.fighter.getChosenAction() == 1 && b.fighter.getChosenAction() == 2) {
			output.append(printResult(a,b));

		}
		
		// If Player A grabs and Player B deflects
		else if(a.fighter.getChosenAction() == 1 && b.fighter.getChosenAction() == 3) {
			output.append(printResult(a,b));

		}
		
		// If Player A grabs and Player B deflects
		else if(a.fighter.getChosenAction() == 2 && b.fighter.getChosenAction() == 3) {
			output.append(printResult(a,b));

		}
		
		// If none of these conditions are met, the test will be run again with Player A and B swapping positions (recursive)
		else {
			return compareAction(b, a);
		}
		return output.toString();
		
	}


	
}


