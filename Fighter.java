
/**
 * Logic that creates the fighters in the game
 * */
public class Fighter {

    private String name;
    private String spriteIdle;
    private String spriteHurt;
    private int hp;
    private int prevHp;
    private int attack;
    private int grab;
    private int counter;
    private int deflect;
    private int chosenAction;
    private boolean tookDamage;

	/**
	 * Constructor used from FighterList
	 * @param name
	 * @param hp
	 * @param attack
	 * @param grab
	 * @param counter
	 * @param deflect
	 */
    public Fighter(String name, int hp, int attack, int grab, int counter, int deflect) {
        this.name = name;
        this.hp = hp;
        this.prevHp = hp;
        this.attack = attack;
        this.grab = grab;
        this.counter = counter;
        this.deflect = deflect;
    }


	/**
	 * Constructor used for copying data from a character to another object
	 * @param f Fighter to be passed to the constructor
	 */
    public Fighter(Fighter f) {
        name = f.name;
        spriteIdle = f.spriteIdle;
        spriteHurt = f.spriteHurt;
        hp = f.hp;
        prevHp = f.prevHp;
        attack = f.attack;
        grab = f.grab;
        counter = f.counter;
        deflect = f.deflect;
        chosenAction = f.chosenAction;
    }


	/**
	 * print the stats of a character
	 * @return string of stats
	 */
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
	/**
	 * toString method
	 * @return name
	 * */
    public String toString() {
        return name;
    }

    /**
	 * get the chosen action of the fighter
	 * @return action
	 * */
    public int getChosenActionStat(int chosenAction) {
        if (chosenAction == 0) {
            return attack;
        }
        if (chosenAction == 1) {
            return grab;
        }
        if (chosenAction == 2) {
            return counter;
        }
        if (chosenAction == 3) {
            return deflect;
        }
        return 0;
    }

    /**
	 * get the chosen stats of the fighter
	 * @return chosen stat
	 * */
    public String getChosenActionStat() {
        String stat = "";
        if (chosenAction == 0) {
            stat += attack;
        }
        if (chosenAction == 1) {
            stat += grab;
        }
        if (chosenAction == 2) {
            stat += counter;
        }
        if (chosenAction == 3) {
            stat += deflect + "%";
        }
        return stat;
    }


	/**
	 * Debuffs characters move after selected; incentivizes diverse combat style
	 */
    public void debuff() {
        double percentDebuff = 0.9;

        if (this.chosenAction == 0) {
            this.attack *= percentDebuff;
            this.attack += 1;
        }
        if (this.chosenAction == 1) {
            this.grab *= percentDebuff;
            this.grab += 1;
        }
        if (this.chosenAction == 2) {
            this.counter *= percentDebuff;
            this.counter += 1;
        }
        if (this.chosenAction == 3) {
            this.deflect *= percentDebuff;
            this.deflect += 1;

        }
    }


    // STATIC METHODS


    public static String actionToString(int chosenAction) {
        switch (chosenAction) {
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


	/**
	 * The brains of the combat system. Determines the winner/loser of a pair of actions
	 * @param first first fighter
	 * @param second fighter
	 * @return winner/loser of a pair of actions
	 */
    public static String compareAction(Fighter first, Fighter second) {

        StringBuilder output = new StringBuilder();

        int damage = 0;
        first.prevHp = first.hp;
        second.prevHp = second.hp;


        output.append(printActions(first, second)); // Printing what was done


        // If Player A and B both attack
        if (first.chosenAction == 0 && second.chosenAction == 0) {

            if (first.attack > second.attack) {

                first.tookDamage = false;
                second.tookDamage = true;

                damage = first.attack - second.attack;
                second.hp -= damage;
                output.append(printDamage(second, damage));
            } else if (second.attack > first.attack) {

                first.tookDamage = true;
                second.tookDamage = false;

                damage = second.attack - first.attack;
                first.hp -= damage;
                output.append(printDamage(first, damage));
            } else {
                first.tookDamage = false;
                second.tookDamage = false;
                output.append("\tThe attacks cancel out!\n");
            }

        }

        // If Player A and B both grab
        else if (first.chosenAction == 1 && second.chosenAction == 1) {

            if (first.grab > second.grab) {

                first.tookDamage = false;
                second.tookDamage = true;

                damage = first.grab - second.grab;
                second.hp -= damage;
                output.append(printDamage(second, damage));
            } else if (second.grab > first.grab) {

                first.tookDamage = true;
                second.tookDamage = false;

                damage = second.grab - first.grab;
                first.hp -= damage;
                output.append(printDamage(first, damage));
            } else {
                first.tookDamage = false;
                second.tookDamage = false;
                output.append("\tThe grabs cancel out!\n");
            }
        }


        // If Player A and B both choose deflect or counter
        else if (first.chosenAction == second.chosenAction) {
            first.tookDamage = false;
            second.tookDamage = false;
            output.append(printFailState(first, second));
        }

        // If Player A attacks and Player B grabs
        else if (first.chosenAction == 0 && second.chosenAction == 1) {


            first.tookDamage = true;
            second.tookDamage = true;

            damage = first.attack;
            second.hp -= damage;
            output.append(printDamage(second, damage));

            damage = second.grab;
            first.hp -= damage;
            output.append(printDamage(first, damage));

        }

        // If Player A attacks and Player B counters
        else if (first.chosenAction == 0 && second.chosenAction == 2) {

            first.tookDamage = true;
            second.tookDamage = false;

            damage = second.counter;
            first.hp -= damage;

            output.append(printDamage(first, damage));
        }

        // If Player A attacks and Player B deflects
        else if (first.chosenAction == 0 && second.chosenAction == 3) {

            first.tookDamage = false;
            second.tookDamage = true;

            damage = first.attack;
            second.hp -= damage;


            output.append(printDamage(second, damage));
        }

        // If Player A grabs and Player B counters
        else if (first.chosenAction == 1 && second.chosenAction == 2) {

            first.tookDamage = false;
            second.tookDamage = true;

            damage = first.grab;
            second.hp -= damage;

            output.append(printDamage(second, damage));
        }

        // If Player A grabs and Player B deflects
        else if (first.chosenAction == 1 && second.chosenAction == 3) {

            first.tookDamage = true;
            second.tookDamage = false;

            damage = second.deflect * first.grab / 100;
            first.hp -= damage;

            output.append(printDamage(first, damage));
        }

        // If Player A counters and Player B deflects
        else if (first.chosenAction == 2 && second.chosenAction == 3) {
            first.tookDamage = false;
            second.tookDamage = false;
            output.append(printFailState(first, second));
        }

        // If none of these conditions are met, the test will be run again with Player A and B swapping positions (recursive)
        else {
            return compareAction(second, first);
        }

        first.debuff();
        second.debuff();

        return output.toString();

    }


	/**
	 * Helper method. Private method for printing a single action
	 * @return action to print
	 */

    private String printAction() {
        switch (chosenAction) {
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

	/**
	 * Helper method for displaying actions taken during combat
	 * @return action to display
	 * */
    private static String printActions(Fighter a, Fighter b) {
        String output = "";
        output += a.name + " " + a.printAction() + "s [" + a.getChosenActionStat() + "] and ";
        output += b.name + " " + b.printAction() + "s [" + b.getChosenActionStat() + "]\n";
        return output;
    }

	/**
	 * Helper method for printing results of a turn of combat
	 * @param loser loser of the action
	 * @param damage damage taken
	 * @return amount of damage to print
	 */
    private static String printDamage(Fighter loser, int damage) {
        return "\t" + loser.name + " takes " + damage + "dmg!\n";
    }

    //Helper method. Prints one of many humorous messages indicating that nothing happened on a given round.
    private static String printFailState(Fighter first, Fighter second) {
        String output = "\t";


        Fighter subject;
        int blame = (int) (Math.random() * 2);
        if (blame == 0) {
            subject = first;
        } else {
            subject = second;
        }

        int option = (int) (Math.random() * 20);

        switch (option) {
            case 0:
                output += "I'm pretty sure " + subject.name + " isn't even trying.";
                break;
            case 1:
                output += first.name + " and " + second.name + " glare at each other...";
                break;
            case 2:
                output += first.name + " and " + second.name + " stand around awkwardly...";
                break;
            case 3:
                output += "Well this is eventful.";
                break;
            case 4:
                output += "Anyone have the time?";
                break;
            case 5:
                output += "I blame " + subject.name + ".";
                break;
            case 6:
                output += "10 points to neither of you.";
                break;
            case 7:
                output += "My disappointment is immeasureable, and my day is ruined.";
                break;
            case 8:
                output += "You did your best. A is for affort!";
                break;
            case 9:
                output += "Let's be a little more creative than that...";
                break;
            case 10:
                output += "Use the force, " + subject.name;
                break;
            case 11:
                output += "\"Oh, you're approaching me?\"";
                break;
            case 12:
                output += "Wake me up when something happens...";
                break;
            case 13:
                output += subject.name + " is definitely hacking.";
                break;
            case 14:
                output += "How hard is it to hit each other?";
                break;
            case 15:
                output += "Playing a little too defensively, aren't we?";
                break;
            case 16:
                output += "You both lose 10 gamer points for annoying me.";
                break;
            case 17:
                output += subject.name + " ate some chips when no one was looking.";
                break;
            case 18:
                output += "If you won't hurt each other, I'll do it myself.";
                break;
            case 19:
                output += "Here's an idea: Attack each other!";
                break;
        }


        return output + "\n";
    }


    /**
	 * Setters and Getters for variables below
	 * */

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

    public void setSpriteIdle(String spriteIdle) {
        this.spriteIdle = spriteIdle;
    }

    public String getSpriteIdle() {
        return spriteIdle;
    }

    public String getSpriteHurt() {
        return spriteHurt;
    }

    public void setSpriteHurt(String spriteHurt) {
        this.spriteHurt = spriteHurt;
    }

    public boolean tookDamage() {
        return tookDamage;
    }

    public void setTookDamage(boolean tookDamage) {
        this.tookDamage = tookDamage;
    }

    public int getPrevHp() {
        return prevHp;
    }

    public void setPrevHp(int prevHp) {
        this.prevHp = prevHp;
    }


}
