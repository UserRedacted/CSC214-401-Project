
public class User extends Player {

	String password;
	String loggedIn;
	
	
	public User(String name) {
		super(name);
		loggedIn = "true";
	}

	public User(String name, String password) {
		super(name);
		this.password = password;
		loggedIn = "true";
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


}
