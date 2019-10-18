import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.geometry.Insets;
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
	static MediaPlayer soundPlayer;
	static FileInputStream imageViewer;
	// Placeholder Player objects for whoever is fighting in a match
	Player p1;
	Player p2;
	
	// Global turn number of a match
	int turnNum = 1;
	
	boolean battleFinished = false; // True when one player's fighter has <= 0 HP
	boolean readyToAdvance = false; // Once the match is over, determines whether players can return to the main menu
	
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
		musicPlayer.setAutoPlay(true);
		musicPlayer.setCycleCount(1000);
		
		// Set the initial scene to the main menu
		scene = new Scene(mainMenu(), 1200, 800);
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
		frame.setPadding(new Insets(0, 50, 0, 50));
		frame.setCenter(content);
		content.setSpacing(5);
		content.setPadding(new Insets(10, 10, 10, 10));
		
		// Image Experimentation
		try {
			imageViewer = new FileInputStream(new File("resources\\images\\project50.png"));
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
	public BorderPane loginMenu() {
		
		int buttonWidth = 300;
		
		
		BorderPane root = new BorderPane();
		// Panel for TextFields
		VBox dataEntry = new VBox();
		
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

		root.setLeft(dataEntry);
		root.setRight(actionConfirm);
		return root;
	}
	
	
	// Battle setup menu interface
	public BorderPane matchSetupMenu() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(20,20,20,20));
	
		
		// Middle Panel
		VBox centerDisplay = new VBox();
		centerDisplay.setSpacing(5);
		centerDisplay.setMinWidth(500);
		Text choose = new Text("CHOOSE YOUR FIGHTER");
		choose.setFont(new Font("Impact", 32));
		choose.setTextAlignment(TextAlignment.RIGHT);
		centerDisplay.getChildren().add(choose);
		
		HBox listContainer = new HBox();
		centerDisplay.getChildren().add(listContainer);
		
		
		//List Views
		
		FighterList fighterList = new FighterList();
		
		
		ListView<Fighter> p1Fighter = new ListView<>();
		p1Fighter.setMinWidth(300);
		p1Fighter.setMaxWidth(800);

		for(int i = 0; i < fighterList.getFighters().size(); i++) {
			p1Fighter.getItems().add(fighterList.getFighters().get(i));
		}	
		listContainer.getChildren().add(p1Fighter);
		p1Fighter.setDisable(true);
		
		
		
		ListView<Fighter> p2Fighter = new ListView<>();
		p2Fighter.setMinWidth(300);

		for(int i = 0; i < fighterList.getFighters().size(); i++) {
			p2Fighter.getItems().add(fighterList.getFighters().get(i));
		}
		listContainer.getChildren().add(p2Fighter);
		p2Fighter.setDisable(true);

		
		// Buttons
		
		HBox lowerButtons = new HBox();
		lowerButtons.setSpacing(5);
		
		
		Button startGame = new Button("START GAME");
		lowerButtons.getChildren().add(startGame);
		startGame.setDisable(true);
		startGame.setMinWidth(250);
		
		Button returnToMenu = new Button("Back to Menu");
		returnToMenu.setMinWidth(250);

		lowerButtons.getChildren().add(returnToMenu);
		
		returnToMenu.setOnMouseClicked(e -> {
			scene.setRoot(mainMenu());
		});	
		
		// Event Listener for Start Game Button. Will change scene root to the match interface
		// and clear all necessary match conditions
		startGame.setOnMouseClicked(e -> {
			battleFinished = false;
			readyToAdvance = false;
			p1.setHasActed(false);
			p2.setHasActed(false);
			scene.setRoot(matchInterface());
		});	
		
		// Left Panel
		VBox p1VBox = matchPlayerPanel(p1Fighter, startGame, true);
		// Right Panel
		VBox p2VBox = matchPlayerPanel(p2Fighter, startGame, false);

		
		root.setLeft(p1VBox);
		root.setCenter(centerDisplay);
		root.setRight(p2VBox);
		root.setBottom(lowerButtons);
		
		return root;
	}

	
	// Panel for controlling player setup for a match
	public VBox matchPlayerPanel(ListView<Fighter> fighterChoice, Button start, boolean isLeftPanel) {		
		VBox playerVBox = new VBox();
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
		Text fighterHeader = new Text("FIGHTER STATS");
		fighterHeader.setFont(new Font("Impact", 32));;
		playerVBox.getChildren().add(fighterHeader);
		
		Text fighterStats = new Text();
		fighterStats.setFont(new Font("Tahoma", 24));
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
	public BorderPane matchInterface() {
		
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		
		
		Button advance = new Button("Continue");
		if(battleFinished)
			advance.setDisable(false);
		else
			advance.setDisable(true);

		VBox p1c = playerControls(p1, advance);
		VBox p2c = playerControls(p2, advance);
		
		Text turnDisplay = new Text("TURN " + turnNum);
		turnDisplay.setFont(new Font("Impact", 32));
		
		ListView<String> battleLog = new ListView<>();
		battleLog.setMinWidth(600);
		
		try {
			for(int i = 0; i < p1.getCurrentBattle().getBattleTurns().size(); i++) {
				battleLog.getItems().add(p1.getCurrentBattle().getBattleTurns().get(i));
			}
		} catch (NullPointerException e) {
			//Nothing needs to be added to the in-game battle log
		}

		// Event listener for round advancing
		advance.setOnMouseClicked(e -> {
			
			p1.setHasActed(false);
			p2.setHasActed(false);
			
			if(!battleFinished) {
				
				String turnText = Fighter.compareAction(p1.getFighter(), p2.getFighter());
				p1.getCurrentBattle().getBattleTurns().add(turnText);
				turnNum ++;
				turnDisplay.setText("TURN " + turnNum);
			}

			
			//If battle is finished, perform necessary file save operations
			if(battleFinished){
				if(readyToAdvance) {
					scene.setRoot(mainMenu());
				} else {
					p1.setNumBattles(p1.getNumBattles()+1);
					p2.setNumBattles(p2.getNumBattles()+1);
					p2.setCurrentBattle(p1.getCurrentBattle());
					p1.getCurrentBattle().sendToFile(p1);
					p2.getCurrentBattle().sendToFile(p2);
					readyToAdvance = true;
				}
			} else {
				checkIfFinished();
				scene.setRoot(matchInterface());
			}
			
		});
		
		root.setTop(turnDisplay);
		root.setCenter(battleLog);
		root.setLeft(p1c);
		root.setRight(p2c);
		root.setBottom(advance);

		return root;
	}

	// Helper method for match interface. Compares health of
	// fighters to see if the match is over
	private void checkIfFinished() {
		if(p1.getFighter().getHp() <= 0 || p2.getFighter().getHp() <= 0) {
			battleFinished = true;

			if(p1.getFighter().getHp() > 0) {
				p1.getCurrentBattle().getBattleTurns().add(p1.getName() + " WINS THE BATTLE!");
				p2.getFighter().setHp(0);
			}
			else {
				p2.getCurrentBattle().getBattleTurns().add(p2.getName() + " WINS THE BATTLE!");
				p1.getFighter().setHp(0);
			}
		}		
	}
	
	
	// Panel in game match for player control scheme. 
	//TODO: Add AI functionality through separate method or some other incorporation
	public VBox playerControls(Player p, Button advance) {

		int buttonWidth = 200;
	
		VBox playerPanel = new VBox();
		playerPanel.setSpacing(5);

		String fighterStats = p.getFighter().getName() + "\nHP: " + p.getFighter().getHp();

		Text fighterDisplay = new Text(fighterStats);
		fighterDisplay.setFont(new Font("Tahoma", 24));

		Button attack = new Button("Attack (" + p.getFighter().getAttack() + ")");
		Button grab = new Button("Grab (" + p.getFighter().getGrab() + ")");
		Button counter = new Button("Counter (" + p.getFighter().getCounter() + ")");
		Button deflect = new Button("Deflect (" + p.getFighter().getDeflect() + "%)");
		
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
		if(battleFinished) {
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
