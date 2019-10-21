import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	static double musicVolume = 0.5;
	static MediaPlayer soundPlayer;
	static FileInputStream imageViewer;
	static File customFont = new File("resources/fixedsys.ttf"); // Custom font
	static String fixedsys = customFont.toURI().toString(); //URI path to the custom font, Fixedsys Regular
	// Placeholder Player objects for whoever is fighting in a match
	Player p1;
	Player p2;
	
	
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
		
		VBox content = new VBox();
		content.setId("panel");
		content.setAlignment(Pos.TOP_CENTER);
		content.setSpacing(10);
		content.setPadding(new Insets(10, 10, 10, 10));
		content.setMaxWidth(450);
		
		frame.setPadding(new Insets(50, 50, 50, 50));
		frame.setCenter(content);

		
		// Image Experimentation
		try {
			// Logo GIF
			imageViewer = new FileInputStream(new File("resources\\images\\p50.gif"));
			Image image = new Image(imageViewer);
			ImageView titleHolder = new ImageView();
			titleHolder.setImage(image);
			content.getChildren().add(titleHolder);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		//		BUTTON CONTROLS			
		Button beginMatch = new Button("Start a Match!");
		beginMatch.setMaxWidth(buttonWidth);		
		content.getChildren().add(beginMatch);
		
		beginMatch.setOnMouseClicked(e -> {
			scene.setRoot(matchSetupMenu());
		});
				
		Button loadUser = new Button("Load/Create UserName");
		loadUser.setMaxWidth(buttonWidth);
		content.getChildren().add(loadUser);
		
		loadUser.setOnMouseClicked(e -> {
			scene.setRoot(loginMenu());
		});
			
		
		Button playerInfo = new Button("View Player Information");
		playerInfo.setMaxWidth(buttonWidth);
		content.getChildren().add(playerInfo);	
		
		
		return frame;
	}
	
	
	// Menu for users to log in, out, create user profiles, etc.
	public HBox loginMenu() {
		
		int buttonWidth = 300;
		
		
		HBox root = new HBox();
		root.setId("panel");
		root.setAlignment(Pos.CENTER);
		
		// Panel for TextFields
		VBox dataEntry = new VBox();
		dataEntry.setId("panel");
		
		
		//Logging in
		Text loginHeader = new Text("LOG IN");
		
		TextField userLogin = new TextField();
		userLogin.setPromptText("Enter UserName");
		
		TextField userPass = new TextField();
		userPass.setPromptText("Enter Password");
		
		//Logging out
		Text loguoutHeader = new Text("LOG OUT");
		
		TextField userLogout = new TextField();
		userLogout.setPromptText("Enter UserName");
		
		// Creating a new profile
		Text creationHeader = new Text("CREATE A NEW USER");
		
		TextField userCreate = new TextField();
		userCreate.setPromptText("Enter a UserName");
		
		TextField userPassSetup = new TextField();
		userPassSetup.setPromptText("Enter a Password");
		
		TextField userPassConfirm = new TextField();
		userPassConfirm.setPromptText("Confirm Password");
		
		dataEntry.getChildren().add(loginHeader);
		dataEntry.getChildren().add(userLogin);
		dataEntry.getChildren().add(userPass);
		dataEntry.getChildren().add(loguoutHeader);
		dataEntry.getChildren().add(userLogout);
		dataEntry.getChildren().add(creationHeader);
		dataEntry.getChildren().add(userCreate);
		dataEntry.getChildren().add(userPassSetup);
		dataEntry.getChildren().add(userPassConfirm);

		//Panel for Button controls
		VBox actionConfirm = new VBox();
		actionConfirm.setId("panel");
		
		Button confirmLogin = new Button("Confirm Log-in");
		confirmLogin.setMaxWidth(buttonWidth);
		
		Button confirmLogout = new Button("Confirm Log-out");
		confirmLogout.setMaxWidth(buttonWidth);
		
		Button confirmCreation = new Button("Confirm Creation");
		confirmCreation.setMaxWidth(buttonWidth);

		Button backToMenu = new Button("Back to Menu");
		backToMenu.setMaxWidth(buttonWidth);

		backToMenu.setOnMouseClicked(e -> {
			scene.setRoot(mainMenu());
		});
		
		actionConfirm.getChildren().add(confirmLogin);
		actionConfirm.getChildren().add(confirmLogout);
		actionConfirm.getChildren().add(confirmCreation);
		actionConfirm.getChildren().add(backToMenu);

	
		root.getChildren().add(dataEntry);
		root.getChildren().add(actionConfirm);
		
		return root;
	}
	
	
	// Battle setup menu interface
	//TODO: Add option to select random fighter
	public HBox matchSetupMenu() {
		HBox root = new HBox();
		root.setId("panel");
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20,20,20,20));
	
		
		// Middle Panel
		VBox centerDisplay = new VBox();
		centerDisplay.setId("panel"); // fetching CSS data
		centerDisplay.setAlignment(Pos.CENTER);
		centerDisplay.setMinWidth(700);
		centerDisplay.setMaxWidth(900);
		
		Text choose = new Text("CHOOSE YOUR FIGHTER");
		
		choose.setFont(Font.loadFont(fixedsys, 48));
		choose.setTextAlignment(TextAlignment.RIGHT);
		
		HBox hboxFighter = new HBox();
		hboxFighter.setId("panel");
		hboxFighter.setAlignment(Pos.CENTER);
		hboxFighter.setSpacing(10);
		hboxFighter.setPadding(new Insets(5, 5, 5, 5));
		
		//List Views
		
		FighterList fighterList = new FighterList();
		
		
		ListView<Fighter> p1Fighter = new ListView<>();
		p1Fighter.setMinWidth(300);

		for(int i = 0; i < fighterList.getFighters().size(); i++) {
			p1Fighter.getItems().add(fighterList.getFighters().get(i));
		}	
		p1Fighter.setDisable(true);
		
		
		
		ListView<Fighter> p2Fighter = new ListView<>();
		p2Fighter.setMinWidth(300);

		for(int i = 0; i < fighterList.getFighters().size(); i++) {
			p2Fighter.getItems().add(fighterList.getFighters().get(i));
		}
		p2Fighter.setDisable(true);

		


		// Buttons
		
		HBox lowerButtons = new HBox();
		lowerButtons.setId("panel");
		lowerButtons.setSpacing(5);
		lowerButtons.setAlignment(Pos.CENTER);
		
		
		Button startGame = new Button("START GAME");
		lowerButtons.getChildren().add(startGame);
		startGame.setDisable(true);
		startGame.setMinWidth(300);
		
		Button returnToMenu = new Button("Back to Menu");
		returnToMenu.setMinWidth(300);

		lowerButtons.getChildren().add(returnToMenu);
		
		returnToMenu.setOnMouseClicked(e -> {
			scene.setRoot(mainMenu());
		});	
		
		// Event Listener for Start Game Button. Will change scene root to the match interface
		// and clear all necessary match conditions
		startGame.setOnMouseClicked(e -> {
			
			battleFinished = false;
			p1.setHasActed(false);
			p2.setHasActed(false);
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
		playerProfile.setMinWidth(200);
		playerProfile.setCenterShape(true);
		//playerProfile
		playerVBox.getChildren().add(playerProfile);
		
		//Set up the ChoiceBox to be filled with player profiles
		PlayerList playerList = new PlayerList();
		
		for(Player p: playerList.getPlayers()) {
			playerProfile.getItems().add(p);
		}
			
		// Displaying the details for the selected fighter in the playerVBox
		Text fighterHeader = new Text("");
		fighterHeader.setFont(Font.loadFont(fixedsys, 48));
		playerVBox.getChildren().add(fighterHeader);
		
		
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
			} else {
				p2 = playerProfile.getSelectionModel().getSelectedItem();
			}
			checkSetup(start);
		});
		
		//Event listener for the a player's ListView
		fighterChoice.getSelectionModel().selectedItemProperty().addListener(e -> {
			Fighter selected = fighterChoice.getSelectionModel().getSelectedItem();
			fighterHeader.setText(selected.getName()); // Changes text of FighterHeader to reflect character choice
			
			// Setting sprite for display
			try {
				imageViewer = new FileInputStream(new File(selected.getSpriteFile()));
				Image image = new Image(imageViewer);
				fighterSprite.setImage(image);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			
			// Assigning fighter to player
			if(isLeftPanel) {
				p1.setFighter(new Fighter(fighterChoice.getSelectionModel().getSelectedItem()));
			} else {
				p2.setFighter(new Fighter(fighterChoice.getSelectionModel().getSelectedItem()));
			}
			fighterStats.setText(selected.printStats());	
			checkSetup(start);
		});
		
		
		
		return playerVBox;
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
		
		
	// Main interface for the turn of a match. Called recursively until
	// match is over
	public HBox matchInterface() {
		
		Button advance = new Button("Continue");
		advance.setMinWidth(250);
		
		if(battleFinished)
			advance.setDisable(false);
		else
			advance.setDisable(true);

		
		HBox root = new HBox();
		root.setId("panel");
		root.setAlignment(Pos.CENTER);
		
		VBox p1c = playerControls(p1, advance);
		VBox p2c = playerControls(p2, advance);
		
		
		VBox display = new VBox();
		display.setId("panel");
		display.setSpacing(10);
		display.setAlignment(Pos.CENTER);
		
		Text turnDisplay = new Text("TURN " + turnNum);
		
		turnDisplay.setFont(Font.loadFont(fixedsys, 64));
		
		
		ListView<String> battleLog = new ListView<>();
		battleLog.setMinWidth(600);
		battleLog.setMinHeight(500);
		
		try {
			for(int i = 0; i < p1.getCurrentBattle().getBattleTurns().size(); i++) {
				battleLog.getItems().add(p1.getCurrentBattle().getBattleTurns().get(i));
			}
		} catch (NullPointerException e) {
			//Nothing needs to be added to the in-game battle log
		}

		// Event listener for round advancing
		advance.setOnMouseClicked(e -> {

			if(!battleFinished) {

				p1.setHasActed(false);
				p2.setHasActed(false);
				
				String turnText = Fighter.compareAction(p1.getFighter(), p2.getFighter());
				p1.getCurrentBattle().getBattleTurns().add(turnText);
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
			} 

			
		});
		
		// Center VBox structure
		display.getChildren().add(turnDisplay);
		display.getChildren().add(battleLog);
		display.getChildren().add(advance);
		
		// Root content stucture
		root.getChildren().add(p1c);
		root.getChildren().add(display);
		root.getChildren().add(p2c);

		return root;
	}

	// Helper method for match interface. Compares health of
	// fighters to see if the match is over
	private void checkIfFinished() {
		if(p1.getFighter().getHp() <= 0 || p2.getFighter().getHp() <= 0) {
			battleFinished = true;
			if(p1.getFighter().getHp() > 0) {
				p2.getFighter().setHp(0);
				p1.getCurrentBattle().getBattleTurns().add(p1.getName() + " WINS THE BATTLE!");
			}
			else {
				p1.getFighter().setHp(0);
				p1.getCurrentBattle().getBattleTurns().add(p2.getName() + " WINS THE BATTLE!");			}
		}	
	}
	
	
	// Panel in game match for player control scheme. 
	//TODO: Fix bug where AI match prevents advancing after game is over
	public VBox playerControls(Player p, Button advance) {

		int buttonWidth = 250;
	
		VBox playerPanel = new VBox();
		playerPanel.setId("panel");
		playerPanel.setAlignment(Pos.CENTER);
		playerPanel.setSpacing(10);
		playerPanel.setPadding(new Insets(10, 10, 10, 10));

		String fighterStats = p.getFighter().getName() + "\nHP: " + p.getFighter().getHp();

		Text fighterDisplay = new Text(fighterStats);
		fighterDisplay.setFont(Font.loadFont(fixedsys, 48));

		Button attack = new Button("Attack [" + p.getFighter().getAttack() + "]");
		Button grab = new Button("Grab [" + p.getFighter().getGrab() + "]");
		Button counter = new Button("Counter [" + p.getFighter().getCounter() + "]");
		Button deflect = new Button("Deflect [" + p.getFighter().getDeflect() + "%]");
		
		attack.setMaxWidth(buttonWidth);
		grab.setMaxWidth(buttonWidth);
		counter.setMaxWidth(buttonWidth);
		deflect.setMaxWidth(buttonWidth);

		ImageView fighterSprite = new ImageView();
		try {
			imageViewer = new FileInputStream(new File(p.getFighter().getSpriteFile()));
			Image image = new Image(imageViewer);
			fighterSprite.setImage(image);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		playerPanel.getChildren().add(fighterDisplay);
		playerPanel.getChildren().add(attack);
		playerPanel.getChildren().add(grab);
		playerPanel.getChildren().add(counter);
		playerPanel.getChildren().add(deflect);
		playerPanel.getChildren().add(fighterSprite);
		
		// Logic for disabling action buttons if battle is finished
		if(battleFinished || !p.isHuman()) {
			attack.setDisable(true);
			grab.setDisable(true);
			counter.setDisable(true);
			deflect.setDisable(true);
		} else {
			attack.setDisable(false);
			grab.setDisable(false);
			counter.setDisable(false);
			deflect.setDisable(false);
		}
		
		attack.setOnMouseClicked(e -> {	
			p.getFighter().setChosenAction(0);
			p.setHasActed(true);
			attack.setDisable(true);
			grab.setDisable(true);
			counter.setDisable(true);
			deflect.setDisable(true);
			checkActions(advance);
			
		});
		grab.setOnMouseClicked(e -> {
			p.getFighter().setChosenAction(1);
			p.setHasActed(true);
			attack.setDisable(true);
			grab.setDisable(true);
			counter.setDisable(true);
			deflect.setDisable(true);
			checkActions(advance);

		});
		counter.setOnMouseClicked(e -> {
			p.getFighter().setChosenAction(2);
			p.setHasActed(true);
			attack.setDisable(true);
			grab.setDisable(true);
			counter.setDisable(true);
			deflect.setDisable(true);
			checkActions(advance);

		});
		deflect.setOnMouseClicked(e -> {
			p.getFighter().setChosenAction(3);
			p.setHasActed(true);
			attack.setDisable(true);
			grab.setDisable(true);
			counter.setDisable(true);
			deflect.setDisable(true);
			checkActions(advance);

		});
		
		if(!p.isHuman()) {
			p.getFighter().setChosenAction((int)(Math.random()*4));
			p.setHasActed(true);
			checkActions(advance);
		}
		
		return playerPanel;
	}
	
	//Helper method for player controls; Enables/disables "Continue"
	// button based on whether both players have acted
	public void checkActions(Button advance) {
		if(p1.hasActed() && p2.hasActed() && !battleFinished)
			advance.setDisable(false);
		else
			advance.setDisable(true);
	}	
	
	
}
