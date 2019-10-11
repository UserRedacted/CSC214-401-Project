
public class Fighter {

	private String name;
	private int hp;
	private int attack;
	private int grab;
	private int counter;
	private int deflect;
	private int chosenAction;
	
	public Fighter(String name, int hp, int attack, int grab, int counter, int deflect) {
		this.setName(name);
		this.hp = hp;
		this.attack = attack;
		this.grab = grab;
		this.counter = counter;
		this.deflect = deflect;
	}
	
	
	public String printStats() {
		String output = "";
		output += "\nHP: " + hp;
		output += "\nAttack: " + attack;
		output += "\nGrab: " + grab;
		output += "\nCounter Attack: " + counter;
		output += "\nDeflect: " + deflect;
		return output;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String actionToString() {
		switch(chosenAction) {
		case 0:
			return "attack";
		case 1:
			return "grab";
		case 2:
			return "counter";
		case 3:
			return "deflect";
		}
		return null;
	}


	public int getChosenAction() {
		return chosenAction;
	}
	public void setChosenAction(int chosenAction) {
		this.chosenAction = chosenAction;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getGrab() {
		return grab;
	}
	public void setGrab(int grab) {
		this.grab = grab;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public int getDeflect() {
		return deflect;
	}
	public void setDeflect(int deflect) {
		this.deflect = deflect;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}

	// Helper method for displaying actions taken during combat
	public static String printActions(Fighter a, Fighter b) {
		return a.name + " " + a.actionToString() + "s and " + b.name + " " + b.actionToString() + "s!\n";
	}
	
	// Helper method for printing results of a turn of combat
	public static String printResults(Fighter loser, Fighter winner, int damage) {
		return loser.name + " takes " + damage + "dmg from " + winner.name + "'s " + winner.actionToString() +"\n";
	}	
	
	// The brains of the combat system. Determines the winner/loser of a pair of actions
	//TODO: Figure out why the game clones Fighter b's moves if a and b are the same Fighter
	public static String compareAction(Fighter a, Fighter b) {
		StringBuilder output = new StringBuilder();
		int damage = 0;
		int newHp = 0;
		// If Player A and B choose the same move
		if(a.getChosenAction() == b.getChosenAction()) {
			output.append(printActions(a,b));
			output.append("The actions cancel out! No one gets hurt...");
		}
		
		// If Player A attacks and Player B grabs
		else if(a.getChosenAction() == 0 && b.getChosenAction() == 1) {
			
			damage = a.attack;
			newHp = b.hp - damage;	
			b.hp = newHp;
					
			output.append(printActions(a,b));
			output.append(printResults(b, a, damage));
		}
		
		// If Player A attacks and Player B counters
		else if(a.getChosenAction() == 0 && b.getChosenAction() == 2) {

			damage = b.counter;
			newHp = a.hp - damage;
			a.hp = newHp;
			
			output.append(printActions(a,b));
			output.append(printResults(a, b, damage));
		}
		
		// If Player A attacks and Player B deflects
		else if(a.getChosenAction() == 0 && b.getChosenAction() == 3) {
			
			damage = a.attack;
			newHp = b.hp - damage;
			b.hp = newHp;
			
			output.append(printActions(a,b));
			output.append(printResults(b, a, damage));
		}
		
		// If Player A grabs and Player B counters
		else if(a.getChosenAction() == 1 && b.getChosenAction() == 2) {
			
			damage = a.grab;
			newHp = b.hp - damage;
			b.hp = newHp;
			
			output.append(printActions(a,b));
			output.append(printResults(b, a, damage));
		}
		
		// If Player A grabs and Player B deflects
		else if(a.getChosenAction() == 1 && b.getChosenAction() == 3) {
			
			damage = b.deflect*a.grab/100;
			newHp = a.hp - damage;
			a.hp = newHp;
			
			output.append(printActions(a,b));
			output.append(printResults(a, b, damage));
		}
		
		// If Player A counters and Player B deflects
		else if(a.getChosenAction() == 2 && b.getChosenAction() == 3) {
			output.append(printActions(a,b));
			output.append("Nothing happens...\n");
		}
		
		// If none of these conditions are met, the test will be run again with Player A and B swapping positions (recursive)
		else {
			return compareAction(b, a);
		}
		return output.toString();
		
	}





	
}
