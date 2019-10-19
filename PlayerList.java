import java.util.ArrayList;

public class PlayerList {

	
	ArrayList<Player> players = new ArrayList<>();
	

	public PlayerList() {
		loadPlayers();
	}

	private void loadPlayers() {

		Player player1 = new Player("Player 1", true);
		players.add(player1);
		
		Player player2 = new Player("Player 2", true);
		players.add(player2);
		
		Player aiEasy1 = new Player("AI Jerry (Easy)", false);
		players.add(aiEasy1);
		
		Player aiEasy2 = new Player("AI Jarry (Easy)", false);
		players.add(aiEasy2);
	}


	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	
	
	
	
}
