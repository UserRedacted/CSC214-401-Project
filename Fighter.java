
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
		output += "Name: " + name;
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







	
}
