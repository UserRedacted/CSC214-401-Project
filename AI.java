
public class AI extends Player {

	private int difficulty;
	private boolean areMovesAnalyzed = false;
	private int preferredMove; // Determines the AI's favorite move
	
	
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





	public int makeDecision(int difficulty) {
		if(difficulty == 1) {
			return decideEasy();
		}
		if(difficulty == 2) {
			return decideMedium();
		}
		if(difficulty == 3) {
			return decideHard();
		}
		return 0;
	}


	private int decideHard() {

		return 0;
	}


	private int decideMedium() {
		if(areMovesAnalyzed) {
			
			int choicePercent = (int)(Math.random()*101);
			
			if(choicePercent <= preferredBias) {
				return preferredMove;
			} else {
				return (int)((Math.random()*4)%preferredMove);
			}
			
			
		} else {
			analyzeMoves();
		}

		
		return 0;
	}
	// Helper method for Medium AI
	private void analyzeMoves() {
		preferredBias = (int)(25+Math.random()*50);
		
		int atk = super.getFighter().getAttack();
		int grb = super.getFighter().getGrab();
		int ctr = super.getFighter().getCounter();
		int dft = super.getFighter().getDeflect();

		int[] moves = {atk, grb, ctr, dft*opponent.getFighter().getGrab()};
		preferredMove = moves[0];
		for(int i = 1; i < moves.length; i++) {
			if(moves[i] > preferredMove)
				preferredMove = i;
		}
		areMovesAnalyzed = true;
	}
	

	
	

	private int decideEasy() {
		return (int)(Math.random()*4);
	}


	public static int checkDifficulty(AI a) {
		return a.getDifficulty();
	}
	
	

	
}
