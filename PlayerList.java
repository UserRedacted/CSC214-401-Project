import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PlayerList {

	
	ArrayList<Player> players = new ArrayList<>(); // The list of profiles accessed by the game setup menu
	ArrayList<User> users = new ArrayList<>(); // The list of user-created profiles and associated data
	
	static int encryptionkey = 50;

	
	// CSV to save user data
    File profiles = new File("resources/players/profiles.csv");

    
	public PlayerList() {
		loadPublicPlayers();
		loadPrivatePlayers();
	}

	// Loads generic profiles Player 1, Player 2, and AI players
	private void loadPublicPlayers() {

		Player player1 = new Player("Player 1");
		players.add(player1);
		
		Player player2 = new Player("Player 2");
		players.add(player2);
		
		Player aiEasy1 = new AI("AI Jerry", 1);
		players.add(aiEasy1);
		
		Player aiEasy2 = new AI("AI Jarry", 1);
		players.add(aiEasy2);
		
		Player aiMedium1 = new AI("AI Gerry", 2);
		players.add(aiMedium1);
		
		Player aiMedium2 = new AI("AI Garry", 2);
		players.add(aiMedium2);
	}


	// Loads any user created profiles
	public void loadPrivatePlayers() {
		
		if(users.size() > 0)
			updateCSV();
		
		String line = "";
      
        try (BufferedReader br = new BufferedReader(new FileReader(profiles))) {
        	
            /*
             	Format for each line:
             	1. Username
             	2. Password
             	3. Boolean loggedIn (true/false)
      
             */
            while ((line = br.readLine()) != null) {
            	// use comma as separator
                String[] playerDetails = line.split(",");

                User temp = new User(decrypt(playerDetails[0]), decrypt(playerDetails[1]), decrypt(playerDetails[2]));
                temp.loggedIn = decrypt(playerDetails[3]);
                
               
                
                // Method for adding user profiles to the full list of playable users (public + private)
                // and to the list of private user profiles
                
                boolean addToPlayers = true;
                boolean addToUsers = true;
                
                if(temp.loggedIn.equals("false")) {
                	addToPlayers = false;
                }
                for(int i = 0; i < players.size(); i++) {
                	if(players.get(i).getName().equals(temp.getName())) {
                		addToPlayers = false;
                	}
                }
                for(int i = 0; i < users.size(); i++) {
                	if(users.get(i).getName().equals(temp.getName())) {
                		addToUsers = false;
                	}
                }
                
                if(addToPlayers) {
                	players.add(temp);
                } else {
                	for(int i = 0; i < players.size(); i++) {
                		if(players.get(i).getName().equals(temp.getName())) {
                			players.remove(i);
                		}
                	}
                }
                if(addToUsers)
                	users.add(temp);
                
                
            }
        } catch (IOException e) {
			System.out.println("Error: Player profile file could not be read");
		}
                
		
	}
	
	
	// Checks for user profile creation system
	public static boolean isValidUsername(String username) {
		if(!(username.length() >= 3 && username.length() <= 12)) {
			return false;
		}
		if(!Character.isLetter(username.charAt(0))) {
			return false;
		}
		for(int i = 0; i < username.length(); i++) {
			char c = username.charAt(i);
			if(!(Character.isDigit(c) || Character.isLetter(c))) {
				return false;
			}
		}
		return true;
	}
		
	public static boolean isValidPassword(String password) {
		if(!(password.length() >= 4 && password.length() <= 20)) {
			return false;
		}
		for(int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);
			if(c == ' ' || c == ',') {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isUsernameTaken(String username) {
		for(int i = 0; i < players.size(); i++) {
			if(username.equals(players.get(i).getName()))
				return false;
		}
		return true;
	}
	

	
	//Adds a user to the list of users, NOT the list of playable profiles
	public void addUser(String username, String password) {
		users.add(new User(username, password));
		updateCSV();
	}
	
	
	// Used to make sure that the CSV containing user profiles is current and accurate
	public void updateCSV() {
		try {
			PrintWriter writer = new PrintWriter(profiles, "UTF-8");

			for(int i = 0; i < users.size(); i++) {
				writer.println(encrypt(users.get(i).getName()) 
				+ "," + encrypt(users.get(i).getPassword()) 
				+ "," + encrypt(Integer.toString(users.get(i).getNumBattles()))
				+ "," + encrypt(users.get(i).getLoggedIn()));
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	// Updates the number of battles a users has fought if needed
	public void updateUsers(Player p) {
		for(int i = 0; i < users.size(); i++) {
			if(p.getName().equals(users.get(i).getName())) {
				users.get(i).setNumBattles(p.getNumBattles());
			}
		}
	}
	
	
	
	// Algorithms for encrypting/decrypting user data for security. A simple Caesar Cipher shift based on the encryption key.
	private static String encrypt(String word) {
		// Range is 32 to 126
		String encryptedWord = "";
		
		for(int i = 0; i < word.length(); i++) {
			int ascii = word.charAt(i);
			// change ascii
			ascii -= encryptionkey;
			if(ascii < 0) {
				ascii = ascii + 122;
			}
			encryptedWord += Character.toString((char) ascii);
		}
		return encryptedWord;
	}
	
	private static String decrypt(String encryptedWord) {
		// Range is 32 to 126
		String decryptedWord = "";
		for(int i = 0; i < encryptedWord.length(); i++) {
			int ascii = encryptedWord.charAt(i);
			ascii += encryptionkey;
			if(ascii > 122) {
				ascii = ascii - 122;
			}
			decryptedWord += Character.toString((char) ascii);
		}
		return decryptedWord;
	}
	
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}



	
	
	
}
