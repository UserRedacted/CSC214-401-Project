/**
 * Represents the Artificial Intelligence (AI) used to determine computer actions during gameplay
 */
public class AI extends Player {

    private int difficulty;
    private int preferredMove; // Determines the AI's favorite move

    private int[] opponentActions = {-1, -1, -1, -1, -1}; // Tracks an opponent's five most recent moves
    private int front = 0;


    // percentage used to determine which move the AI prefers to use. MEDIUM AI ONLY
    private int preferredBias;
    private Player opponent;

    /**
     * Creates a new AI with the given name and difficulty
     *
     * @param name       AI name
     * @param difficulty AI difficulty
     */
    public AI(String name, int difficulty) {
        super(name);
        super.setHuman(false);
        this.difficulty = difficulty;
    }


    /**
     * Gets the difficulty of the AI
     *
     * @return difficulty of the AI
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Set the difficulty of the AI
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Get the preferred move of the AI
     *
     * @return preferred move of the AI
     */
    public int getPreferredMove() {
        return preferredMove;
    }

    /**
     * Set the preferred move of the AI
     */
    public void setPreferredMove(int preferredMove) {
        this.preferredMove = preferredMove;
    }

    /**
     * Get the preferred bias of the AI
     *
     * @return preferred bias
     */
    public int getPreferredBias() {
        return preferredBias;
    }

    /**
     * Set the preferred bias of the AI
     */
    public void setPreferredBias(int preferredBias) {
        this.preferredBias = preferredBias;
    }

    /**
     * Get the opponent of the AI
     *
     * @return oponnent
     */
    public Player getOpponent() {
        return opponent;
    }

    /**
     * Set the opponnent of the AI
     */
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }


    /**
     * Makes a decision based on AI difficulty
     * Each difficulty has a unique thought process
     *
     * @return decision made by the AI
     **/
    public int makeDecision() {
        opponentActions[front % 5] = new Integer(opponent.getFighter().getChosenAction());
        front++;
        if (difficulty == 1) {
            return decideNormal();
        }
        if (difficulty == 2) {
            return decideHard();
        }
        return 0;
    }


    /**
     * Logic for normal difficulty AI, adds unpredictability
     *
     * @return integer that determines decision
     */
    private int decideNormal() {
        return (int) (Math.random() * 4);
    }

    /**
     * Logic for hard difficulty AI, adds unpredictability
     *
     * @return integer that determines decision
     */
    private int decideHard() {
        int favoriteAction = calculateFavoriteAction();
        int choice = (int) (Math.random() * 5);

        switch (choice) {
            case 0:
                return counterLast();
            case 1:
                return counterFavorite(favoriteAction);
            case 2:
                return mimicLast();
            case 3:
                return strongestMove();
            case 4:
                return (int) (Math.random() * 4);
        }
        return 0;

    }

    /**
     * Based on the opponent's last 5 actions, returns the most commonly used action
     *
     * @return favorite action of the opponnent
     */
    private int calculateFavoriteAction() {
        int favoriteAction = 0; // Integer equivalent of favorite action type
        int[] numAction = {0, 0, 0, 0}; // The number of times a player has chosen each action, initialized to zero
        int quantityActionAtIndex = 0; // Number of times player has selected an action at a given indexs of numAction

        //Loading the number of times a player has chosen each action
        for (int i = 0; i < opponentActions.length; i++) {
            if (opponentActions[i] >= 0)
                numAction[opponentActions[i]]++;
        }

        // Determining the favorite action
        for (int i = 0; i < numAction.length; i++) {
            if (numAction[i] > quantityActionAtIndex) {
                favoriteAction = i;
                quantityActionAtIndex = numAction[i];
            }
        }

        return favoriteAction;
    }

    /**
     * Returns the option that deals the most damage if landed successfully
     *
     * @return action that deals the most damage
     */
    private int strongestMove() {
        Fighter f = new Fighter(this.getFighter());
        // List of numerical moves
        int[] actions = {f.getAttack(), f.getGrab(), f.getCounter(), (int) (0.01 * f.getDeflect() * opponent.getFighter().getGrab())};
        int strongestAction = 0;

        for (int i = 1; i < actions.length; i++) {
            if (actions[i] > f.getChosenActionStat(strongestAction)) {
                strongestAction = i;
            }
        }
        return strongestAction;
    }

    /**
     * Returns whatever move the opponent selected previously
     *
     * @return previous action
     */
    private int mimicLast() {
        int action = opponent.getFighter().getChosenAction();
        return action;
    }

    /**
     * Counters the opponent's most commonly used action
     *
     * @return counter to opponent action
     */
    private int counterFavorite(int favoriteAction) {
        return (favoriteAction + 2) % 4;
    }

    /**
     * Counters whatever move the opponent selected previously
     *
     * @return counter to last opponent move
     */
    private int counterLast() {
        int action = opponent.getFighter().getChosenAction();
        // The below equation selects the direct counter to the move selected
        // Example, 0 becomes 2 (attack becomes counter)
        return (action + 2) % 4;
    }


}
