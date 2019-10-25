
public class AI extends Player {

	private int difficulty;
	private int preferredMove; // Determines the AI's favorite move
	
	private int[] opponentActions = {-1, -1, -1, -1, -1}; // Tracks an opponent's five most recent moves
	private int front = 0;
	
	
	// percentage used to determine which move the AI prefers to use. MEDIUM AI ONLY
	private int preferredBias;
	private Player opponent;
	
	public AI(String name, int difficulty) {
		super(name);
		super.isHuman = false;
		this.difficulty = difficulty;
	}

	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getPreferredMove() {
		return preferredMove;
	}

	public void setPreferredMove(int preferredMove) {
		this.preferredMove = preferredMove;
	}

	public int getPreferredBias() {
		return preferredBias;
	}

	public void setPreferredBias(int preferredBias) {
		this.preferredBias = preferredBias;
	}

	public Player getOpponent() {
		return opponent;
	}

	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	
	
	//Needed for very specific context
	public static int checkDifficulty(AI a) {
		return a.getDifficulty();
	}

	// Makes a decision based on AI difficulty
	// Each difficulty has a unique thought process
	public int makeDecision(int difficulty) {
		opponentActions[front%5] = new Integer(opponent.getFighter().getChosenAction());
		front++;
		
		if(difficulty == 1) {
			return decideNormal();
		}
		if(difficulty == 2) {
			return decideHard();
		}
		return 0;
	}

	
	//Stupid unpredictability
	private int decideNormal() {
		return (int)(Math.random()*4);
	}

	// Decision archetypes with room for unpredictability
	private int decideHard() {
		int favoriteAction = calculateFavoriteAction();
		System.out.println("My opponent's favorite action is " + Fighter.actionToString(favoriteAction));
		int choice = (int)(Math.random()*5);
		
		switch(choice) {
		case 0:
			System.out.println("I will counter my opponent's last move, " + Fighter.actionToString(opponentActions[opponentActions.length-1]));
			return counterLast();
		case 1:
			System.out.println("I will counter my opponent's favorite move, " + Fighter.actionToString(favoriteAction));
			return counterFavorite(favoriteAction);
		case 2:
			System.out.println("I will copy my opponent's last move, " + Fighter.actionToString(opponentActions[opponentActions.length-1]));
			return mimicLast();
		case 3:
			System.out.println("I will use my strongest move, " + Fighter.actionToString(strongestMove()));
			return strongestMove();
		case 4:
			System.out.println("I will perform a random move...");
			return (int)(Math.random()*4);
		}
		return 0;		
		
	}

	// Based on the opponent's last 5 actions, returns the most commonly used action
	private int calculateFavoriteAction() {
		int favoriteAction = 0; // Integer equivalent of favorite action type
		int[] numAction = {0, 0, 0, 0}; // The number of times a player has chosen each action, initialized to zero
		int quantityActionAtIndex = 0; // Number of times player has selected an action at a given indexs of numAction

		//Loading the number of times a player has chosen each action
			for(int action = 0; action < opponentActions.length; action++) {
				if(opponentActions[action] > 0)
					numAction[opponentActions[action]] += 1;

			}	
		
		// Determining the favorite action
		for(int a = 0; a < numAction.length; a++) {
			System.out.println("\t" + numAction[a]);
			if(numAction[a] > quantityActionAtIndex) {
				favoriteAction = a;
				quantityActionAtIndex = numAction[a];
			}
		}
		


		return favoriteAction;
	}
	
	// Returns the option that deals the most damage if landed successfully
	private int strongestMove() {
		Fighter f = new Fighter(this.fighter);
		// List of numerical moves 
		int[] actions = {f.getAttack(), f.getGrab(), f.getCounter(), f.getDeflect()*opponent.getFighter().getGrab()};
		int strongestAction = 0;
	
			for(int i = 1; i < actions.length; i++) {
				if(actions[i] > f.getChosenActionStat(strongestAction)) {
					strongestAction = i;
				}
			}	
		return strongestAction;
	}

	// Returns whatever move the opponent selected previously
	private int mimicLast() {
		int action = opponent.getFighter().getChosenAction();
		return action;
	}

	// Counters the opponent's most commonly used action
	private int counterFavorite(int favoriteAction) {
		return (favoriteAction + 2) % 4;
	}
	
	// Counters whatever move the opponent selected previously
	private int counterLast() {
		int action = opponent.getFighter().getChosenAction();
		// The below equation selects the direct counter to the move selected
		// Example, 0 becomes 2 (attack becomes counter)
		return (action + 2) % 4;
	}

	
}
