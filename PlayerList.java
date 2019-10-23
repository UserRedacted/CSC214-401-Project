import java.util.ArrayList;

public class PlayerList {

	
	ArrayList<Player> players = new ArrayList<>();
	

	public PlayerList() {
		loadPlayers();
	}

	private void loadPlayers() {

		Player player1 = new Player("Player 1");
		players.add(player1);
		
		Player player2 = new Player("Player 2");
		players.add(player2);
		
		Player aiEasy1 = new AI("AI Jerry (Easy)", 1);
		players.add(aiEasy1);
		
		Player aiEasy2 = new AI("AI Jarry (Easy)", 1);
		players.add(aiEasy2);
		
		Player aiMedium1 = new AI("AI Gerry (Medium)", 2);
		players.add(aiMedium1);
		
		Player aiMedium2 = new AI("AI Garry (Medium)", 2);
		players.add(aiMedium2);
	}


	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	
	
	
	
}
