/**
 * Sets logic for the login system
 * */
import java.io.File;

public class User extends Player {

	private String password;
	private String loggedIn;
	
	/**
	 * Constructor
	 * @param name name of user
	 * */
	public User(String name) {
		super(name);
		loggedIn = "true";
		createFolder();
	}

	/**
	 * constructor that creates a folder for the user
	 * @param name name of user
	 * @param password
	 */
	public User(String name, String password) {
		super(name);
		this.password = password;
		loggedIn = "true";
		createFolder();
	}

	/**
	 * constructor that creates a folder for the user
	 * @param name
	 * @param password
	 * @param numBattles
	 */
	public User(String name, String password, String numBattles) {
		super(name);
		this.password = password;
		this.setNumBattles(Integer.parseInt(numBattles));
		loggedIn = "true";
		createFolder();
	}

	/* Getters and setters below */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(String loggedIn) {
		this.loggedIn = loggedIn;
	}
	/**
	 * Creates a folder for the user
	 * */
	public void createFolder() {
			File dir = new File("resources/players/" + this.getName());
			dir.mkdir();
	}
	
	
}
