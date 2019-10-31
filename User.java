import java.io.File;

public class User extends Player {

	String password;
	String loggedIn;
	
	
	public User(String name) {
		super(name);
		loggedIn = "true";
		createFolder();
	}
	public User(String name, String password) {
		super(name);
		this.password = password;
		loggedIn = "true";
		createFolder();
	}
	public User(String name, String password, String numBattles) {
		super(name);
		this.password = password;
		this.setNumBattles(Integer.parseInt(numBattles));
		loggedIn = "true";
		createFolder();
	}

	
	
	
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


	
	
	public void createFolder() {
			File dir = new File("resources/players/" + this.getName());
			dir.mkdir();
	}
	
	
}
