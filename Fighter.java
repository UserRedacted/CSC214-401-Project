
public class Fighter {

	private String name;
	private String spriteFile;
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
	
	
	public Fighter(Fighter f) {
		name = f.name;
		spriteFile = f.spriteFile;
		hp = f.hp;
		attack = f.attack;
		grab = f.grab;
		counter = f.counter;
		deflect = f.deflect;
		chosenAction = f.chosenAction;
	}


	public String printStats() {
		String output = "";
		output += "HP:\t" + hp;
		output += "\nAtk:\t" + attack;
		output += "\nGrb:\t" + grab;
		output += "\nCtr:\t" + counter;
		output += "\nDft:\t" + deflect + "%";
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
	public void setSpriteFile(String spriteFile) {
		this.spriteFile = spriteFile;
	}
	public String getSpriteFile() {
		return spriteFile;
	}
	
	
	
	
	// Helper method for displaying actions taken during combat
	public static String printActions(Fighter a, Fighter b) {
		return a.name + " " + a.actionToString() + "s and " + b.name + " " + b.actionToString() + "s!\n";
	}
	
	// Helper method for printing results of a turn of combat
	public static String printDamage(Fighter loser, int damage) {
		return "\t" + loser.name + " takes " + damage + "dmg!\n";
	}	
	
	// The brains of the combat system. Determines the winner/loser of a pair of actions
	public static String compareAction(Fighter first, Fighter second) {
		
		StringBuilder output = new StringBuilder();
		int damage = 0;
		
		
		output.append(printActions(first,second)); // Printing what was done
		
		
		// If Player A and B both attack
		if(first.chosenAction == 0 && second.chosenAction == 0) {
			
				damage = first.attack;
				second.hp -= damage;
				output.append(printDamage(second, damage));
				
				damage = second.attack;
				first.hp -= damage;
				output.append(printDamage(first, damage));

		}
		
		// If Player A and B both grab
		else if(first.chosenAction == 1 && second.chosenAction == 1) {
			
			if(first.grab > second.grab) {
				damage = first.grab - second.grab;
				second.hp -= damage;
				output.append(printDamage(second, damage));
			} else if(second.grab > first.grab) {
				damage = second.grab - first.grab;
				first.hp -= damage;
				output.append(printDamage(first, damage));
			} else {
				output.append("\tThe grabs cancel out!\n");
			}
		}
		
		
		// If Player A and B both choose deflect or counter
		else if(first.chosenAction == second.chosenAction) {
			output.append("\t" + first.name + " and " + second.name + " glare at each other...\n");
		}
		
		// If Player A attacks and Player B grabs
		else if(first.chosenAction == 0 && second.chosenAction == 1) {
			
			damage = first.attack;
			second.hp -= damage;
					
			output.append(printDamage(second, damage));
		}
		
		// If Player A attacks and Player B counters
		else if(first.chosenAction == 0 && second.chosenAction== 2) {

			damage = second.counter;
			first.hp -= damage;
			
			output.append(printDamage(first, damage));
		}
		
		// If Player A attacks and Player B deflects
		else if(first.chosenAction == 0 && second.chosenAction == 3) {
			
			damage = first.attack;
			second.hp -= damage;

			
			output.append(printDamage(second, damage));
		}
		
		// If Player A grabs and Player B counters
		else if(first.chosenAction == 1 && second.chosenAction == 2) {
			
			damage = first.grab;
			second.hp -= damage;
			
			output.append(printDamage(second, damage));
		}
		
		// If Player A grabs and Player B deflects
		else if(first.chosenAction == 1 && second.chosenAction == 3) {
			
			damage = second.deflect*first.grab/100;
			first.hp -= damage;
			
			output.append(printDamage(first, damage));
		}
		
		// If Player A counters and Player B deflects
		else if(first.chosenAction == 2 && second.chosenAction == 3) {
			output.append("\t" + first.name + " and " + second.name + " stand around awkwardly...\n");
		}
		
		// If none of these conditions are met, the test will be run again with Player A and B swapping positions (recursive)
		else {
			return compareAction(second, first);
		}
		return output.toString();
		
	}



	
}
