

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GameDisplay extends Application {	
	

	Scene scene;
	
	// Objects for playing sounds through JavaFX
	static MediaPlayer musicPlayer;
	static SoundPlayer soundPlayer = new SoundPlayer();
	static double musicVolume = 0.3;
	static File customFont = new File("resources/fixedsys.ttf"); // Custom font
	static String fixedsys = customFont.toURI().toString(); //URI path to the custom font, Fixedsys Regular
	// Placeholder Player objects for whoever is fighting in a match
	
	
	Player p1;
	Player p2;
	PlayerList playerList = new PlayerList();

	String turnDetails;
	
	// Global turn number of a match
	int turnNum = 1;
	boolean battleFinished = false; // True when one player's fighter has <= 0 HP
	
	// Main method that loads printers and launches JavaFX window
	public static void main(String[] args) {
		launch(args);
	}
	
	
	// JavaFX Window Runner
	@Override
	public void start(Stage stage)  {

		stage.setMaximized(true);
		stage.setFullScreen(true);
		
		// NOTE: This can be used as a template for playing music and sound effects
		File song = new File("resources\\music\\Theme.wav");
		Media media = new Media(song.toURI().toString()); // Media class requires a URI file path. This allows it to work on any computer.
		musicPlayer = new MediaPlayer(media);
		musicPlayer.setVolume(musicVolume);
		musicPlayer.setAutoPlay(true);
		musicPlayer.setCycleCount(1000);
		
		// Set the initial scene to the main menu
		scene = new Scene(mainMenu(), 1600, 900);
		scene.getStylesheets().add("customStyle.css");
		
		stage.setScene(scene);
		stage.setTitle("Project 50");
		
		
		// Setting up application icon
		FileInputStream input;
		try {
			input = new FileInputStream(new File("resources\\images\\p50.png"));
			Image image = new Image(input);
			stage.getIcons().add(image);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		stage.show();

	}
	
	
	// Function for controlling main menu interface
	public BorderPane mainMenu() {

		int buttonWidth = 500; // int for controlling width of all buttons

		BorderPane frame = new BorderPane();
		frame.setPadding(new Insets(50, 50, 50, 50));

		
		HBox content = new HBox();
		content.setAlignment(Pos.CENTER);
		content.setSpacing(50);
		
		// Children of HBox content
		
		// FighterList used exclusively to display a random sprite on main menu
		FighterList spriteView = new FighterList();
			
			// Left image
			File randomFighterLeft = new File(spriteView.getFighters().get((int)(Math.random()*spriteView.getFighters().size())).getSpriteIdle());
			Image spriteLeft = new Image(randomFighterLeft.toURI().toString());
			ImageView spriteLeftContainer = new ImageView();
			spriteLeftContainer.setImage(spriteLeft);
			
			// Right image
			File randomFighterRight = new File(spriteView.getFighters().get((int)(Math.random()*spriteView.getFighters().size())).getSpriteIdle());
			Image spriteRight = new Image(randomFighterRight.toURI().toString());
			ImageView spriteRightContainer = new ImageView();
			spriteRightContainer.setImage(spriteRight);
			
			
			VBox menu = new VBox();
			menu.setId("panel");
			menu.setAlignment(Pos.TOP_CENTER);
			menu.setMaxWidth(450);
			
			// Children of VBox menu
			
				// Image Display
				File image = new File("resources\\images\\logo.gif");
				Image logo = new Image(image.toURI().toString());
				ImageView titleHolder = new ImageView();
				titleHolder.setImage(logo);
				menu.getChildren().add(titleHolder);
				
				
				//		BUTTON CONTROLS			
				Button beginMatch = new Button("Start a Match!");
				beginMatch.setMaxWidth(buttonWidth);		
				soundPlayer.playOnHover(beginMatch);
				
				beginMatch.setOnMouseClicked(e -> {
					soundPlayer.playClick();
					scene.setRoot(matchSetupMenu());
				});
						
				Button loadUser = new Button("Load/Create UserName");
				loadUser.setMaxWidth(buttonWidth);
				soundPlayer.playOnHover(loadUser);

				loadUser.setOnMouseClicked(e -> {
					soundPlayer.playClick();
					scene.setRoot(loginMenu());
				});
					
				
				Button playerInfo = new Button("View Player Information");
				playerInfo.setMaxWidth(buttonWidth);
				soundPlayer.playOnHover(playerInfo);

				playerInfo.setOnMouseClicked(e -> {
					soundPlayer.playClick();
					scene.setRoot(informationMenu());
				});
				
				
				
				Button tutorial = new Button("Tutorial");
				tutorial.setMaxWidth(buttonWidth);
				soundPlayer.playOnHover(tutorial);

				tutorial.setOnMouseClicked(e -> {
					soundPlayer.playClick();
					scene.setRoot(tutorialMenu());
				});
				
				
				
				Button quit = new Button("Quit");
				quit.setMaxWidth(buttonWidth);
				soundPlayer.playOnHover(quit);

				quit.setOnAction(actionEvent -> Platform.exit());
				
		
		
		menu.getChildren().add(beginMatch);
		menu.getChildren().add(loadUser);
		menu.getChildren().add(playerInfo);	
		menu.getChildren().add(tutorial);	
		menu.getChildren().add(quit);	

		
		content.getChildren().add(spriteLeftContainer);
		content.getChildren().add(menu);
		content.getChildren().add(spriteRightContainer);

		frame.setCenter(content);

		
		return frame;
	}
	
	
	// Menu for users to log in, out, create user profiles, etc.
	public BorderPane loginMenu() {
		
		
		BorderPane frame = new BorderPane();
		
		
		int buttonWidth = 300;
		
		// Main panel
		VBox body = new VBox();
		body.setAlignment(Pos.CENTER);
		body.setMaxWidth(800);
		body.setSpacing(10);
		body.setPadding(new Insets(20, 20, 20, 20));
		
		
		
		// Header
		
		VBox header = new VBox();
		header.setId("panel");
		header.setAlignment(Pos.CENTER);
		
		Text title = new Text("Load/Create a Player Profile");
		title.setFont(Font.loadFont(fixedsys, 72));
		
		Text subHeading = new Text("View battle logs, track win rates, and more!\n");
		subHeading.setFont(Font.loadFont(fixedsys, 24));

		Text messageIndicator = new Text("");
		messageIndicator.setFont(Font.loadFont(fixedsys, 24));

		
		// Logging in
		
		HBox logInPanel = new HBox();
		logInPanel.setId("panel");
		logInPanel.setAlignment(Pos.CENTER);
		
		VBox logIn = new VBox();
		logIn.setSpacing(10);
		logIn.setMinWidth(300);

		Text loginHeader = new Text("LOG IN");
		loginHeader.setFont(Font.loadFont(fixedsys, 32));
		
		TextField userLogin = new TextField();
		userLogin.setPromptText("Enter UserName");
		
		PasswordField userPass = new PasswordField();
		userPass.setPromptText("Enter Password");

		
		// Logging out
		
		HBox logOutPanel = new HBox();
		logOutPanel.setId("panel");
		logOutPanel.setAlignment(Pos.CENTER);
		
		VBox logOut = new VBox();
		logOut.setSpacing(10);
		logOut.setMinWidth(300);

		Text logoutHeader = new Text("LOG OUT");
		logoutHeader.setFont(Font.loadFont(fixedsys, 32));
		
		TextField userLogout = new TextField();
		userLogout.setPromptText("Enter UserName");
		
		
		
		// Creating a new profile
		
		HBox createUserPanel = new HBox();
		createUserPanel.setId("panel");
		createUserPanel.setAlignment(Pos.CENTER);
	
		VBox createUser = new VBox();
		createUser.setSpacing(10);
		createUser.setMinWidth(300);

		Text creationHeader = new Text("CREATE A NEW USER");
		creationHeader.setFont(Font.loadFont(fixedsys, 32));
		
		TextField newUsername = new TextField();
		newUsername.setPromptText("Enter a UserName");
		
		PasswordField userPassSetup = new PasswordField();
		userPassSetup.setPromptText("Enter a Password");
		
		PasswordField userPassConfirm = new PasswordField();
		userPassConfirm.setPromptText("Confirm Password");
		

		// Buttons, with event listeners
		
		
		
		// Log in button
		Button confirmLogIn = new Button("Confirm Log-in");
		confirmLogIn.setMinWidth(buttonWidth);
		soundPlayer.playOnHover(confirmLogIn);

		confirmLogIn.setOnMouseClicked(e -> {
			String username = userLogin.getText();
			String password = userPass.getText();
			
			boolean userFound = false;
			boolean passwordCorrect = true;
			
			for(int i = 0; i < playerList.getUsers().size(); i++) {
				if(playerList.getUsers().get(i).getName().equals(username)) {
					userFound = true;
					if (playerList.getUsers().get(i).getPassword().equals(password)) {
						passwordCorrect = true;
						playerList.getUsers().get(i).setLoggedIn("true");
						playerList.loadPrivatePlayers();
						messageIndicator.setText("User \"" + username + "\" successfully logged in!");
						soundPlayer.playSuccess();

					} else {
						passwordCorrect = false;
					}
				}
			}
			
			if(!userFound) {
				messageIndicator.setText("No user with name \"" + username + "\" found.");
				soundPlayer.playFailure();
			}
			if(!passwordCorrect) {
				messageIndicator.setText("Password for user \"" + username + "\" was incorrect.");
				soundPlayer.playFailure();
			}
		});
		
		
		
		
		// Log out button
		Button confirmLogOut = new Button("Confirm Log-out");
		confirmLogOut.setMinWidth(buttonWidth);
		soundPlayer.playOnHover(confirmLogOut);

		confirmLogOut.setOnMouseClicked(e -> {
			String username = userLogout.getText();
			boolean userFound = false;
			
			for(int i = 0; i < playerList.getUsers().size(); i ++) {
				if(playerList.getUsers().get(i).getName().equals(username)) {
					playerList.getUsers().get(i).setLoggedIn("false");
					messageIndicator.setText("Successfully logged out user " + username + "!");
					soundPlayer.playSuccess();

					playerList.loadPrivatePlayers();
					userFound = true;
				}
			}
			
			if(!userFound) {
				messageIndicator.setText("No user with name \"" + username + "\" found.");
				soundPlayer.playFailure();
			}
			
		});
		
		
		
		// Action to be taken when user attempts to create a new user profile
		Button confirmCreation = new Button("Confirm Creation");
		confirmCreation.setMinWidth(buttonWidth);
		soundPlayer.playOnHover(confirmCreation);

		confirmCreation.setOnMouseClicked(e -> {
			String username = newUsername.getText();
			String password1 = userPassSetup.getText();
			String password2 = userPassConfirm.getText();
			
			if(!playerList.isUsernameTaken(username)) {
				messageIndicator.setText("USER CREATION FAILED. ERROR: Username is already taken.");
				soundPlayer.playFailure();
			}
			else if(!PlayerList.isValidUsername(username)) {
				soundPlayer.playFailure();
				String output = "USER CREATION FAILED. ERROR: Username not acceptable.\n";
				output += "\t1. Username must be 3-12 characters long\n";
				output += "\t2. Username must start with a letter\n";
				output += "\t3. Username must not contain special characters/spaces";
				messageIndicator.setText(output);
			}
			else if(!password1.equals(password2)) {
				soundPlayer.playFailure();
				messageIndicator.setText("USER CREATION FAILED. ERROR: Passwords do not match.");
			} 
			else if(!PlayerList.isValidPassword(password1)) {
				soundPlayer.playFailure();
				String output = "USER CREATION FAILED. ERROR: Password not acceptable.\n";
				output += "\t1. Password must be 4-20 characters long\n";
				output += "\t2. Password must not contain commas or spaces";
				messageIndicator.setText(output);	
			}
			else {
				soundPlayer.playSuccess();
				messageIndicator.setText("User successfully created! You can now select your user from the battle setup menu!");
				playerList.addUser(username, password1);
				playerList.loadPrivatePlayers();
			}
		});
		
		
		
		
		Button backToMenu = new Button("Back to Menu");
		backToMenu.setMinWidth(buttonWidth);
		soundPlayer.playOnHover(backToMenu);

		backToMenu.setOnMouseClicked(e -> {
			soundPlayer.playClick2();
			scene.setRoot(mainMenu());
		});
		
		
		
		// Setup

		header.getChildren().add(title);
		header.getChildren().add(subHeading);
		header.getChildren().add(messageIndicator);
		
		logIn.getChildren().add(loginHeader);
		logIn.getChildren().add(userLogin);
		logIn.getChildren().add(userPass);
		
		logInPanel.getChildren().add(logIn);
		logInPanel.getChildren().add(confirmLogIn);
		
		logOut.getChildren().add(logoutHeader);
		logOut.getChildren().add(userLogout);
		
		logOutPanel.getChildren().add(logOut);
		logOutPanel.getChildren().add(confirmLogOut);

		createUser.getChildren().add(creationHeader);
		createUser.getChildren().add(newUsername);
		createUser.getChildren().add(userPassSetup);
		createUser.getChildren().add(userPassConfirm);

		createUserPanel.getChildren().add(createUser);
		createUserPanel.getChildren().add(confirmCreation);

		body.getChildren().add(header);
		body.getChildren().add(logInPanel);
		body.getChildren().add(logOutPanel);
		body.getChildren().add(createUserPanel);
		body.getChildren().add(backToMenu);

		frame.setCenter(body);
		return frame;
	}
	
	
	
	// Menu for users to view information on a specific user profile
	public BorderPane informationMenu() {
		
		BorderPane frame = new BorderPane();
		frame.setPadding(new Insets(20, 20, 20, 20));
		
		VBox content = new VBox();
		content.setId("panel");
		content.setMaxWidth(800);
		content.setAlignment(Pos.CENTER);
		
		// Children of Content
			
			HBox userHBox = new HBox();
			userHBox.setSpacing(20);
			userHBox.setAlignment(Pos.CENTER);
				
			// Children of userHBox
			
				Text userSelect = new Text("Select user: ");
				userSelect.setFont(Font.loadFont(fixedsys, 32));
	
				ChoiceBox<User> users = new ChoiceBox<User>();
				users.setMinWidth(200);

				for(int i = 0; i < playerList.getUsers().size(); i++) {
					if(playerList.getUsers().get(i).getLoggedIn().equals("true")) {
						users.getItems().add(playerList.getUsers().get(i));
					}
				}
		
			
			HBox battleHBox = new HBox();
			battleHBox.setSpacing(20);
			battleHBox.setAlignment(Pos.CENTER);
			
			// Children of battleHBox
				Text battleSelect = new Text("Select battle: ");
				battleSelect.setFont(Font.loadFont(fixedsys, 32));

				ChoiceBox<BattleLog> battleLogs = new ChoiceBox<BattleLog>();
				battleLogs.setMinWidth(200);
				
				
				
			HBox information = new HBox();
			information.setAlignment(Pos.CENTER);
			information.setSpacing(10);
			
			// Children of HBox information
		
				ListView<String> battleLogText = new ListView<String>();
				
				
				VBox statistics = new VBox();
				statistics.setSpacing(5);
				
				// Children of VBox statistics
					Text header = new Text("Statistics:\n");
					header.setFont(Font.loadFont(fixedsys, 32));
	
					Text username = new Text("Username: ");
					username.setFont(Font.loadFont(fixedsys, 32));
	
					Text playedGames = new Text("Played Games: ");
					playedGames.setFont(Font.loadFont(fixedsys, 32));
	
					Text wonGames = new Text("Won Games: ");
					wonGames.setFont(Font.loadFont(fixedsys, 32));
	
					Text wlRatio = new Text("Win/Loss Ratio: ");
					wlRatio.setFont(Font.loadFont(fixedsys, 32));

			
			
			Button backToMenu = new Button("Back to Menu");
			backToMenu.setMinWidth(300);
			soundPlayer.playOnHover(backToMenu);
			backToMenu.setOnMouseClicked(e -> {
				soundPlayer.playClick2();
				scene.setRoot(mainMenu());
			});
		

			
		userHBox.getChildren().add(userSelect);
		userHBox.getChildren().add(users);

		battleHBox.getChildren().add(battleSelect);
		battleHBox.getChildren().add(battleLogs);

		
		statistics.getChildren().add(header);
		statistics.getChildren().add(username);
		statistics.getChildren().add(playedGames);
		statistics.getChildren().add(wonGames);
		statistics.getChildren().add(wlRatio);
		
		
		information.getChildren().add(battleLogText);
		information.getChildren().add(statistics);
		
		content.getChildren().add(userHBox);
		content.getChildren().add(battleHBox);
		content.getChildren().add(information);
		content.getChildren().add(backToMenu);
		
		frame.setCenter(content);
		
		return frame;
	}
	
	
	public BorderPane tutorialMenu() {
		BorderPane frame = new BorderPane();
		frame.setPadding(new Insets(25, 25, 25, 25));
		
		
		VBox content = new VBox();
		content.setId("panel");
		content.setAlignment(Pos.CENTER);
		content.setMaxWidth(800);
		
		
		Text header = new Text("Welcome to Project50!\n");
		header.setFont(Font.loadFont(fixedsys, 48));
		
		String generalInfo = "";
		generalInfo += "Project50 is a turn based combat game like Rock, Paper, Scissors.\n";
		generalInfo += "The key is understanding what the moves are, and what each move does.\n\n";
		generalInfo += "Some important information:\n";
		generalInfo += "1.\tYou can take 4 actions: Attack, Grab, Counter, and Deflect.\n";
		generalInfo += "2.\tEach action does a base amount of damage.\n";
		generalInfo += "3.\tEvery time you use an action, its base damage decreases.\n";
		generalInfo += "4.\tThe goal is to get your opponent to 0 HP (Health Points)\n";
		
		
		String moves = "";
		moves += "THE MATCH-UPS:\n";
		moves += "1.\tAttack > Attack :: The stronger attack lands, but does less damage\n";
		moves += "2.\tGrab > Grab :: The stronger grab lands, but does less damage\n";
		moves += "3.\tAttack == Grab :: Both actions deal their full damage\n";
		moves += "4.\tCounter > Attack :: Counter only lands when your opponent attacks.\n"; 
		moves += "5.\tDeflect > Grab :: Deflect only lands when your opponent grabs.\n"; 
		moves += "6.\tCounter-Counter, Counter-Deflect, and Deflect-Counter do nothing...\n";
		
		Text info = new Text(generalInfo);
		info.setFont(Font.loadFont(fixedsys, 24));
		
		Text movePairs = new Text(moves);
		movePairs.setFont(Font.loadFont(fixedsys, 24));
		
		Button backToMenu = new Button("Back to Menu");
		backToMenu.setMinWidth(300);
		soundPlayer.playOnHover(backToMenu);
		backToMenu.setOnMouseClicked(e -> {
			soundPlayer.playClick2();
			scene.setRoot(mainMenu());
		});
		

		content.getChildren().add(header);
		content.getChildren().add(info);
		content.getChildren().add(movePairs);
		content.getChildren().add(backToMenu);
		frame.setCenter(content);

		return frame;
	}
	
	
	
	// Battle setup menu interface
	public HBox matchSetupMenu() {
		HBox root = new HBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(15);
		root.setPadding(new Insets(20,20,20,20));
	
		
		// Middle Panel
		VBox centerDisplay = new VBox();
		centerDisplay.setId("panel"); // fetching CSS data
		centerDisplay.setAlignment(Pos.CENTER);
		centerDisplay.setMinWidth(500);
		centerDisplay.setMaxWidth(700);
		
		Text choose = new Text("CHOOSE YOUR FIGHTER");
		
		choose.setFont(Font.loadFont(fixedsys, 48));
		choose.setTextAlignment(TextAlignment.RIGHT);
		
		HBox hboxFighter = new HBox();
		hboxFighter.setAlignment(Pos.CENTER);
		hboxFighter.setSpacing(10);
		hboxFighter.setPadding(new Insets(5, 5, 5, 5));
		
		//List Views
		
		FighterList fighterList = new FighterList();
		//Fighter used exclusively for being mutated into another fighter in a match setup menu
		Fighter random = new Fighter("Random", 0, 0, 0, 0, 0);
		random.setSpriteIdle("resources\\sprites\\Random.gif");
		fighterList.getFighters().add(0, random);
		
		
		
		ListView<Fighter> p1Fighter = new ListView<>();
		p1Fighter.setMaxWidth(200);
		
		for(int i = 0; i < fighterList.getFighters().size(); i++) {
			p1Fighter.getItems().add(fighterList.getFighters().get(i));
		}	
		p1Fighter.setDisable(true);
		
		
		
		ListView<Fighter> p2Fighter = new ListView<>();
		p2Fighter.setMaxWidth(200);

		for(int i = 0; i < fighterList.getFighters().size(); i++) {
			p2Fighter.getItems().add(fighterList.getFighters().get(i));
		}
		p2Fighter.setDisable(true);

		


		// Buttons
		
		HBox lowerButtons = new HBox();
		lowerButtons.setSpacing(5);
		lowerButtons.setAlignment(Pos.CENTER);
		
		
		Button startGame = new Button("START GAME");
		soundPlayer.playOnHover(startGame);
		startGame.setDisable(true);
		startGame.setMinWidth(250);
		
		
		// Back to menu button
		Button backToMenu = new Button("Back to Menu");
		soundPlayer.playOnHover(backToMenu);
		backToMenu.setMinWidth(250);
	
		backToMenu.setOnMouseClicked(e -> {
			soundPlayer.playClick2();
			scene.setRoot(mainMenu());
		});	
		
		// Event Listener for Start Game Button. Will change scene root to the match interface
		// and clear all necessary match conditions
		startGame.setOnMouseClicked(e -> {
			
			// Resetting player currentBattles
			p1.setCurrentBattle(new BattleLog());
			p2.setCurrentBattle(new BattleLog());

			// Resetting battle boolean
			battleFinished = false;
			
			// Setting up AI for use
			if(p1.isHuman())
				p1.setHasActed(false);
			else {
				p1.setHasActed(true);
				AI a = (AI)p1;
				a.setOpponent(p2);
			}

			if(p2.isHuman())
				p2.setHasActed(false);
			else {
				p2.setHasActed(true);
				AI a = (AI)p2;
				a.setOpponent(p1);
			}
			
			
			scene.setRoot(matchInterface());
			
			
			//Playing the battle intro music and looping the battle theme music when the battle begins
			
			File song = new File("resources\\music\\BattleIntro.wav");
			Media intro = new Media(song.toURI().toString()); // Media class requires a URI file path. This allows it to work on any computer.
			musicPlayer.stop();
			musicPlayer = new MediaPlayer(intro);
			musicPlayer.setCycleCount(1);		
			musicPlayer.setVolume(musicVolume);
			musicPlayer.play();
			musicPlayer.setOnEndOfMedia(new Runnable() {

				@Override
				public void run() {
					File song = new File("resources\\music\\Battle.wav");
					Media battle = new Media(song.toURI().toString());
					musicPlayer = new MediaPlayer(battle);
					musicPlayer.setVolume(musicVolume);
					musicPlayer.play();
					musicPlayer.setCycleCount(1000);
				}
				
			});
		});	


		// Left Panel
		VBox p1VBox = playerSetup(p1Fighter, startGame, true);
		// Right Panel
		VBox p2VBox = playerSetup(p2Fighter, startGame, false);
		
		
		hboxFighter.getChildren().add(p1Fighter);
		hboxFighter.getChildren().add(p2Fighter);
		
		centerDisplay.getChildren().add(choose);
		centerDisplay.getChildren().add(hboxFighter);
		centerDisplay.getChildren().add(lowerButtons);
		
		lowerButtons.getChildren().add(startGame);
		lowerButtons.getChildren().add(backToMenu);

		root.getChildren().add(p1VBox);
		root.getChildren().add(centerDisplay);
		root.getChildren().add(p2VBox);

		
		return root;
	}

	
	// Panel for controlling player setup for a match
	public VBox playerSetup(ListView<Fighter> fighterChoice, Button start, boolean isLeftPanel) {		
		VBox playerVBox = new VBox();
		playerVBox.setId("panel");
		playerVBox.setAlignment(Pos.CENTER);
		playerVBox.setMinWidth(200);
		playerVBox.setMaxWidth(400);
		playerVBox.setSpacing(5);
		
		// ChoiceBox for choosing a player profile from a list of User Names and public profiles
		ChoiceBox<Player> playerProfile = new ChoiceBox<>();
		playerProfile.setMinWidth(300);
		playerProfile.setCenterShape(true);
		//playerProfile
		playerVBox.getChildren().add(playerProfile);
		
		//Set up the ChoiceBox to be filled with player profiles		
		for(Player p: playerList.getPlayers()) {
			playerProfile.getItems().add(p);
		}
			
		// Displaying the details for the selected fighter in the playerVBox
		Text fighterName = new Text("");
		fighterName.setFont(Font.loadFont(fixedsys, 48));
		playerVBox.getChildren().add(fighterName);
		
		
		Text fighterStats = new Text();
		fighterStats.setFont(Font.loadFont(fixedsys, 32));
		playerVBox.getChildren().add(fighterStats);
		
		
		// Setting up an ImageView for displaying GIFs of sprites
		ImageView fighterSprite = new ImageView();
		playerVBox.getChildren().add(fighterSprite); // Container in place; content would be determined by ListView selection
		

		// Listener for ChoiceBox of Players. Enables choosing a fighter and sets the player based on whether the left or right panel is being created
		playerProfile.getSelectionModel().selectedItemProperty().addListener(e -> {
			fighterChoice.setDisable(false);
			if(isLeftPanel) {
				p1 = playerProfile.getSelectionModel().getSelectedItem();
				giveFighter(p1, fighterChoice);
			} else {
				p2 = playerProfile.getSelectionModel().getSelectedItem();
				giveFighter(p1, fighterChoice);
			}
			checkSetup(start);
		});
		
		//Event listener for the a player's ListView
		fighterChoice.getSelectionModel().selectedItemProperty().addListener(e -> {
			Fighter selected = fighterChoice.getSelectionModel().getSelectedItem();
			fighterName.setText(selected.getName()); // Changes text of FighterHeader to reflect character choice
			
			// Setting sprite for display

				File sprite = new File(selected.getSpriteIdle());
				Image image = new Image(sprite.toURI().toString());
				fighterSprite.setImage(image);
			
			
			// Assigning fighter to player
			if(isLeftPanel) {
				giveFighter(p1, fighterChoice);
			} else {
				giveFighter(p2, fighterChoice);
			}
			fighterStats.setText(selected.printStats());	
			checkSetup(start);
		});
		
		
		
		return playerVBox;
	}
	
	// Helper Method for playerSetup
	// 	Assigns a fighter to a given player from the selected list of fighters, including a "Random" option which is not itself a character
	public void giveFighter(Player p, ListView<Fighter> fighters) {
		Fighter chosenFighter = fighters.getSelectionModel().getSelectedItem();
		try {
			if(chosenFighter.getName().equals("Random")) {
				p.setFighter(new Fighter (fighters.getItems().get(  (int)(1+  Math.random() * (fighters.getItems().size()-2)  )  )));
			} else {
				p.setFighter(new Fighter(chosenFighter));	
			}
		} catch (NullPointerException n) {
			// Do nothing
		}
	}
	

	// Helper method for determining if the match setup is ready to progress;
	// Enables the start button if the check is good
	// Called when CheckBox and ListView of Fighters are updated
	private void checkSetup(Button start) {
		try {
			if(p1.getName().equals(p2.getName()))
				start.setDisable(true);
			else if(p1.getFighter() != null && p2.getFighter() != null) {
				start.setDisable(false);
			}	
		} catch (NullPointerException e) {
			start.setDisable(true);
		}

	}
	
		
	
	
	
	/// MAIN INTERFACE CODE BELOW
	
	
		
	// Main interface for the turn of a match. Called recursively until
	// match is over
	public HBox matchInterface() {
		
		Button advance = new Button("[C] Continue");
		soundPlayer.playOnHover(advance);

		advance.setMinWidth(250);
		
		if(battleFinished)
			advance.setDisable(false);
		else
			advance.setDisable(true);

		
		HBox root = new HBox();
		root.setSpacing(15);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(30, 30, 30, 30));
		 
		VBox p1c = playerControls(p1, advance, true);
		VBox p2c = playerControls(p2, advance, false);
				
		
		VBox display = new VBox();
		display.setId("panel");
		display.setSpacing(10);
		display.setAlignment(Pos.CENTER);
		
		
		// Used at the very start of a round to display results dynamically
		Text countdown = new Text("");
		countdown.setFont(Font.loadFont(fixedsys, 72));
		

		// Display for center panel
		Text turnDisplay = new Text("TURN " + turnNum);
		turnDisplay.setFont(Font.loadFont(fixedsys, 64));
		
		
		// Display for turn read out. Updated via TimerTask extension UpdateTurns.
		ListView<String> battleLog = new ListView<>();
		battleLog.setMinWidth(600);
		battleLog.setMinHeight(500);
		


		// Event listener for round advancing and key presses
		root.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode()==KeyCode.C && !advance.isDisable()) {
				soundPlayer.playNextRound();
				advanceRound(turnDisplay);
			}
			
			//Player 1 Controls
			if(key.getCode() == KeyCode.A) {
				readKeys(p1, p1c, 0, advance);
			}
			if(key.getCode() == KeyCode.S) {
				readKeys(p1, p1c, 1, advance);
			}
			if(key.getCode() == KeyCode.D) {
				readKeys(p1, p1c, 2, advance);
			}		
			if(key.getCode() == KeyCode.F) {
				readKeys(p1, p1c, 3, advance);
			}		
			
			//Player 2 Controls
			if(key.getCode() == KeyCode.U) {
				readKeys(p2, p2c, 0, advance);
			}
			if(key.getCode() == KeyCode.I) {
				readKeys(p2, p2c, 1, advance);
			}
			if(key.getCode() == KeyCode.O) {
				readKeys(p2, p2c, 2, advance);
			}	
			if(key.getCode() == KeyCode.P) {
				readKeys(p2, p2c, 3, advance);
			}			
		});
		advance.setOnMouseClicked(e -> {
			soundPlayer.playNextRound();
			advanceRound(turnDisplay);	
		});
		
		// Center VBox structure
		display.getChildren().add(countdown);
		display.getChildren().add(turnDisplay);
		display.getChildren().add(battleLog);
		display.getChildren().add(advance);
		
		// Root content structure
		root.getChildren().add(p1c);
		root.getChildren().add(display);
		root.getChildren().add(p2c);

		
		// Very important timer tasks
		Timer timer = new Timer();

		if(battleFinished) {
			timer.schedule(new ChangeText(turnDisplay, "MATCH FINISHED!!"), 1500);
			timer.schedule(new ChangeMusic(), 1500);
		}
		
		if(turnNum > 1) {
			countdown.setText(" . ");
			timer.schedule(new ChangeText(countdown, ". ."), 500);
			timer.schedule(new ChangeText(countdown, ". . ."), 1000);
			timer.schedule(new ChangeText(countdown, "FIGHT!"), 1500);
			
			
			timer.schedule(new ChangeText((Text)p1c.getChildren().get(1), ("HP: " + p1.getFighter().getHp())), 1500);
			timer.schedule(new ChangeText((Text)p2c.getChildren().get(1), ("HP: " + p2.getFighter().getHp())), 1500);
			
			timer.schedule(new UpdateSprites(
					(ImageView)p1c.getChildren().get(p1c.getChildren().size()-1),
					(ImageView)p2c.getChildren().get(p1c.getChildren().size()-1)), 
					1500);
			
			timer.schedule(new UpdateTurns(battleLog), 1500);
			timer.schedule(new ChangeText(countdown, ""), 3500);
		}
		
		// Scrolling to the last item on the battle log (most recent turn)
		battleLog.scrollTo(battleLog.getItems().size()-1);
		
		return root;
	}

	
	// TIMER TASK FOR MATCH INTERFACE
	
	// Nested class used simply to count down a text display. Used in the Match Interface
	class ChangeText extends TimerTask {
		String text;
		Text num;
		
		public ChangeText(Text num, String text) {
			this.num = num;
			this.text = text;
		}
		@Override
		public void run() {
			num.setText(text);
		}
	}
	
	// Used to update sprites for damage taken - also plays sound accordingly
	class UpdateSprites extends TimerTask {
		ImageView sprite1;
		ImageView sprite2;
		
		public UpdateSprites(ImageView sprite1, ImageView sprite2) {
			this.sprite1 = sprite1;
			this.sprite2 = sprite2;
		}
		
		@Override
		public void run() {
			if(p1.getFighter().tookDamage()) {
				soundPlayer.playDamageLeft();
				File spriteHurt = new File(p1.getFighter().getSpriteHurt());
				Image image = new Image(spriteHurt.toURI().toString());
				sprite1.setImage(image);
				
			} else {
				soundPlayer.playUndamagedLeft();
			}
			
			if(p2.getFighter().tookDamage()) {
				soundPlayer.playDamageRight();
				File spriteHurt = new File(p2.getFighter().getSpriteHurt());
				Image image = new Image(spriteHurt.toURI().toString());
				sprite2.setImage(image);
			} else {
				soundPlayer.playUndamagedRight();
			}
		}
	}
		
	// Used to add turn print-out to the list view
	class UpdateTurns extends TimerTask {
		
		ListView<String> battleLog;
		
		public UpdateTurns(ListView<String> battleLog) {
			this.battleLog = battleLog;
		}

		@Override
		public void run() {
			try {
				for(int i = 0; i < p1.getCurrentBattle().getBattleTurns().size(); i++) {
					battleLog.getItems().add(p1.getCurrentBattle().getBattleTurns().get(i));
				}
			} catch (NullPointerException e) {
				//Nothing needs to be added to the in-game battle log
			}
		}
	}
	
	//Method for scheduling end of battle music
	class ChangeMusic extends TimerTask {

		@Override
		public void run() {
			musicPlayer.stop();
			File song = new File("resources\\music\\BattleEnd.wav");
			Media media = new Media(song.toURI().toString());
			musicPlayer = new MediaPlayer(media);
			musicPlayer.setVolume(musicVolume);
			musicPlayer.setAutoPlay(true);
			musicPlayer.setCycleCount(1000);
		}
		
	}
	
	
	// Helper method for reading key inputs of players during match
	private void readKeys(Player p, VBox pVBox, int action, Button advance) {
		soundPlayer.playSuccess();

		p.getFighter().setChosenAction(action);
		p.setHasActed(true);
		checkActions(advance);
		// For loop for disabling buttons (entire player panel for safety)
		for(int i = 0; i < pVBox.getChildren().size(); i++)
			pVBox.getChildren().get(i).setDisable(true);		
	}

	// Helper method for match interface. Called when "Continue" button is pressed or Space key is pressed
	public void advanceRound(Text turnDisplay) {
		if(!battleFinished) {
			if(p1.isHuman())
				p1.setHasActed(false);
			if(p2.isHuman())
				p2.setHasActed(false);
			
			turnDetails = Fighter.compareAction(p1.getFighter(), p2.getFighter());
			p1.getCurrentBattle().getBattleTurns().add(turnDetails);			
			checkIfFinished();

			turnNum ++;
			turnDisplay.setText("TURN " + turnNum);
			scene.setRoot(matchInterface());
		}

		//If battle is finished, perform necessary operations	
		else {

			// Changing to the main theme music
			File song = new File("resources\\music\\Theme.wav");
			Media theme = new Media(song.toURI().toString()); // Media class requires a URI file path. This allows it to work on any computer.
			musicPlayer.stop();
			musicPlayer = new MediaPlayer(theme);
			musicPlayer.setCycleCount(1000);
			musicPlayer.setVolume(musicVolume);
			musicPlayer.play();
			
			
			scene.setRoot(mainMenu());
			
			// Reset turn for next battle
			turnNum = 1;
			
			// File saving to player profiles
			p1.setNumBattles(p1.getNumBattles()+1);
			p2.setNumBattles(p2.getNumBattles()+1);
			p2.setCurrentBattle(p1.getCurrentBattle());
			p1.getCurrentBattle().sendToFile(p1);
			p2.getCurrentBattle().sendToFile(p2);
			
			// Updating the number of battles users have played
			playerList.updateUsers(p1);
			playerList.updateUsers(p2);
			playerList.updateCSV();
		} 
	}
	
	// Helper method for match interface. Compares health of
	// fighters to see if the match is over
	private void checkIfFinished() {
		if(p1.getFighter().getHp() <= 0 || p2.getFighter().getHp() <= 0) {
			battleFinished = true;
			if(p1.getFighter().getHp() <= 0 && p2.getFighter().getHp() <= 0) {
				p1.getFighter().setHp(0);
				p2.getFighter().setHp(0);
				p1.getCurrentBattle().getBattleTurns().add("DOUBLE K.O.!! NO WINNER!!");
			}
			else if(p1.getFighter().getHp() > 0) {
				p2.getFighter().setHp(0);
				p1.getCurrentBattle().getBattleTurns().add(p1.getName() + " WINS THE BATTLE!!");
			}
			else {
				p1.getFighter().setHp(0);
				p1.getCurrentBattle().getBattleTurns().add(p2.getName() + " WINS THE BATTLE!!");			
			}
		}	
	}
	
	// Panel in game match for player control scheme and AI functionality
	// Key layout determined by whether the panel is right or left
	public VBox playerControls(Player p, Button advance, boolean isLeft) {
		// Setting up visual display for buttons
		KeyCode[] keys = new KeyCode[4];
		if(isLeft) {
			keys[0] = KeyCode.A;
			keys[1] = KeyCode.S;
			keys[2] = KeyCode.D;
			keys[3] = KeyCode.F;
		} else {
			keys[0] = KeyCode.U;
			keys[1] = KeyCode.I;
			keys[2] = KeyCode.O;
			keys[3] = KeyCode.P;
		}
		
		int buttonWidth = 350;
	
		VBox playerPanel = new VBox();
		playerPanel.setId("panel");
		playerPanel.setAlignment(Pos.CENTER);
		playerPanel.setMinWidth(275);
		playerPanel.setMaxWidth(400);

		
		Text name = new Text();
		name.setFont(Font.loadFont(fixedsys, 48));
		name.setTextAlignment(TextAlignment.CENTER);
		name.setText(p.getName() + "\n" + p.getFighter().getName());


		String health = "HP: " + p.getFighter().getPrevHp();

		Text fighterHealth = new Text(health);
		fighterHealth.setFont(Font.loadFont(fixedsys, 48));

		Button attack = new Button(keys[0] + ":Attack[" + p.getFighter().getAttack() + "]");
		Button grab = new Button(keys[1] + ":Grab[" + p.getFighter().getGrab() + "]");
		Button counter = new Button(keys[2] + ":Counter[" + p.getFighter().getCounter() + "]");
		Button deflect = new Button();
		
		soundPlayer.playOnHover(attack);
		soundPlayer.playOnHover(grab);
		soundPlayer.playOnHover(counter);
		soundPlayer.playOnHover(deflect);

		// Changing the deflect stat to a number based on the calculation
		if(isLeft) {
			int damage = (int)(0.01* p1.getFighter().getDeflect() * p2.getFighter().getGrab());
			deflect.setText(keys[3] + ":Deflect[" + damage + "]");
		}
		else {
			int damage = (int)(0.01* p2.getFighter().getDeflect() * p1.getFighter().getGrab());
			deflect.setText(keys[3] + ":Deflect[" + damage + "]");
		}
		
		attack.setMaxWidth(buttonWidth);
		grab.setMaxWidth(buttonWidth);
		counter.setMaxWidth(buttonWidth);
		deflect.setMaxWidth(buttonWidth);

		ImageView fighterSprite = new ImageView();

		File sprite = new File(p.getFighter().getSpriteIdle());
		Image image = new Image(sprite.toURI().toString());
		fighterSprite.setImage(image);

		
		
		// Assembly order
		playerPanel.getChildren().add(name);
		playerPanel.getChildren().add(fighterHealth);
		playerPanel.getChildren().add(attack);
		playerPanel.getChildren().add(grab);
		playerPanel.getChildren().add(counter);
		playerPanel.getChildren().add(deflect);
		playerPanel.getChildren().add(fighterSprite);
		
		
		// Logic for disabling action buttons if battle is finished
		if(battleFinished || !p.isHuman()) {
			setButtons(attack, grab, counter, deflect, true);
		} else {
			setButtons(attack, grab, counter, deflect, false);

		}
		
		attack.setOnMouseClicked(e -> {	
			soundPlayer.playSuccess();
			p.getFighter().setChosenAction(0);
			p.setHasActed(true);
			setButtons(attack, grab, counter, deflect, true);
			checkActions(advance);	
		});
		grab.setOnMouseClicked(e -> {
			soundPlayer.playSuccess();
			p.getFighter().setChosenAction(1);
			p.setHasActed(true);
			setButtons(attack, grab, counter, deflect, true);
			checkActions(advance);
		});
		counter.setOnMouseClicked(e -> {
			soundPlayer.playSuccess();
			p.getFighter().setChosenAction(2);
			p.setHasActed(true);
			setButtons(attack, grab, counter, deflect, true);
			checkActions(advance);
		});
		deflect.setOnMouseClicked(e -> {
			soundPlayer.playSuccess();
			p.getFighter().setChosenAction(3);
			p.setHasActed(true);
			setButtons(attack, grab, counter, deflect, true);
			checkActions(advance);
		});
		
		
		
		// AI Decision Making
		if(!p.isHuman()) {
			AI a = (AI)p;
			int difficulty = AI.checkDifficulty((AI)p);
			p.getFighter().setChosenAction(a.makeDecision(difficulty));
			p.setHasActed(true);
		}
		
		// Ensuring that rounds can be manually advanced if two AI battle each other
		if(!p1.isHuman() && !p2.isHuman()) {
			checkActions(advance);
		}
		
		return playerPanel;
	}
	
	// Helper method for quickly enabling or disabling buttons in match interface
	public void setButtons(Button atk, Button grb, Button ctr, Button dft, boolean active) {
		atk.setDisable(active);
		grb.setDisable(active);
		ctr.setDisable(active);
		dft.setDisable(active);
	}
	
	//Helper method for player controls; Enables/disables "Continue"

	
	// button based on whether both players have acted
	public void checkActions(Button advance) {	
		if(p1.hasActed() && p2.hasActed())
			advance.setDisable(false);
		else
			advance.setDisable(true);
	}	
	
	
}
