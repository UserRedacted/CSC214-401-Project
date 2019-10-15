
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
	
	// Takes an integer 0-3 representing an action and changes it to
	// a string to print to the screen
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
	public static String compareAction(Fighter first, Fighter second) {
		StringBuilder output = new StringBuilder();
		int damage = 0;
		int newHp = 0;
		// If Player A and B choose the same move
		if(first.getChosenAction() == second.getChosenAction()) {
			output.append(printActions(first,second));
			output.append("The actions cancel out! No one gets hurt...");
		}
		
		// If Player A attacks and Player B grabs
		else if(first.getChosenAction() == 0 && second.getChosenAction() == 1) {
			
			damage = first.attack;
			newHp = second.hp - damage;	
			second.hp = newHp;
					
			output.append(printActions(first,second));
			output.append(printResults(second, first, damage));
		}
		
		// If Player A attacks and Player B counters
		else if(first.getChosenAction() == 0 && second.getChosenAction() == 2) {

			damage = second.counter;
			newHp = first.hp - damage;
			first.hp = newHp;
			
			output.append(printActions(first,second));
			output.append(printResults(first, second, damage));
		}
		
		// If Player A attacks and Player B deflects
		else if(first.getChosenAction() == 0 && second.getChosenAction() == 3) {
			
			damage = first.attack;
			newHp = second.hp - damage;
			second.hp = newHp;
			
			output.append(printActions(first,second));
			output.append(printResults(second, first, damage));
		}
		
		// If Player A grabs and Player B counters
		else if(first.getChosenAction() == 1 && second.getChosenAction() == 2) {
			
			damage = first.grab;
			newHp = second.hp - damage;
			second.hp = newHp;
			
			output.append(printActions(first,second));
			output.append(printResults(second, first, damage));
		}
		
		// If Player A grabs and Player B deflects
		else if(first.getChosenAction() == 1 && second.getChosenAction() == 3) {
			
			damage = second.deflect*first.grab/100;
			newHp = first.hp - damage;
			first.hp = newHp;
			
			output.append(printActions(first,second));
			output.append(printResults(first, second, damage));
		}
		
		// If Player A counters and Player B deflects
		else if(first.getChosenAction() == 2 && second.getChosenAction() == 3) {
			output.append(printActions(first,second));
			output.append("Nothing happens...\n");
		}
		
		// If none of these conditions are met, the test will be run again with Player A and B swapping positions (recursive)
		else {
			return compareAction(second, first);
		}
		return output.toString();
		
	}





	
}
