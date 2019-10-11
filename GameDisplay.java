import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameDisplay extends Application {

	
	Scene scene;
	
	// Placeholder Player objects for whoever is fighting in a match
	Player p1;
	Player p2;
	
	// Global turn number of a match
	int turnNum = 1;
	
	boolean battleFinished = false;
	boolean readyToAdvance = false; // Once the match is over, determines whether players can return to the main menu
	// Main method that loads printers and launches JavaFX window
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	// JavaFX Window Runner
	@Override
	public void start(Stage stage)  {
		
		// Set the initial scene to the main menu
		scene = new Scene(mainMenu(), 1000, 600);
		scene.getStylesheets().add("customStyle.css");
		
		stage.setScene(scene);
		stage.setTitle("Project 50");
	
		stage.show();

	}
	
	
	// Function for controlling main menu interface
	public Group mainMenu() {
	    
		
		Group root = new Group();
		
		BorderPane body = new BorderPane();

		VBox content = new VBox();
		body.setCenter(content);
	
		Text title = new Text("PROJECT 50");
		title.setStyle("-fx-font: 24 arial;");
		content.getChildren().add(title);
		
		
		//		BUTTON CONTROLS			
		Button beginMatch = new Button("Start a Match!");
		content.getChildren().add(beginMatch);
		beginMatch.setOnMouseClicked(e -> {
			scene.setRoot(matchMenu());
		});
				
		Button loadUser = new Button("Load/Create UserName");
		content.getChildren().add(loadUser);
		
		
		Button playerInfo = new Button("View Player Information");
		content.getChildren().add(playerInfo);	
				
		root.getChildren().add(body);
		
		return root;
	}
	
	
	// Battle setup menu interface
	public Group matchMenu() {

		Group root = new Group();
		HBox hbox = new HBox();
		root.getChildren().add(hbox);
		
		
		// Middle Panel
		VBox centerDisplay = new VBox();
		
		Text choose = new Text("CHOOSE YOUR FIGHTER");
		centerDisplay.getChildren().add(choose);
		
		HBox listContainer = new HBox();
		centerDisplay.getChildren().add(listContainer);
		
		
		//List Views
		
		FighterList fighterList = new FighterList();
	
		ListView<Fighter> p1Fighter = new ListView<>();
		for(int i = 0; i < fighterList.getFighters().size(); i++) {
			p1Fighter.getItems().add(fighterList.getFighters().get(i));
		}	
		listContainer.getChildren().add(p1Fighter);
		p1Fighter.setDisable(true);
		
		
		
		ListView<Fighter> p2Fighter = new ListView<>();
		for(int i = 0; i < fighterList.getFighters().size(); i++) {
			p2Fighter.getItems().add(fighterList.getFighters().get(i));
		}
		listContainer.getChildren().add(p2Fighter);
		p2Fighter.setDisable(true);

		
		// Buttons
		
		Button startGame = new Button("START GAME");
		centerDisplay.getChildren().add(startGame);
		startGame.setDisable(true);
		
		
		Button returnToMenu = new Button("Back to Menu");
		centerDisplay.getChildren().add(returnToMenu);
		
		returnToMenu.setOnMouseClicked(e -> {
			scene.setRoot(mainMenu());
		});	
		
		// Event Listener for Start Game Button. Will change scene root to the match interface
		// and clear all necessary match conditions
		startGame.setOnMouseClicked(e -> {
			scene.setRoot(matchInterface());
			battleFinished = false;
			readyToAdvance = false;
			p1.setHasActed(false);
			p2.setHasActed(false);
		});	
		
		// Left Panel
		VBox player1 = matchPlayerPanel(p1Fighter, startGame, true);
		
		// Right Panel
		VBox player2 = matchPlayerPanel(p2Fighter, startGame, false);
		
		
		
		hbox.getChildren().add(player1);
		hbox.getChildren().add(centerDisplay);
		hbox.getChildren().add(player2);
		
		return root;
	}

	
	// Panel for controlling player setup for a match
	public VBox matchPlayerPanel(ListView<Fighter> fighterChoice, Button start, boolean isLeftPanel) {
				
		VBox playerVBox = new VBox();
		
		// ChoiceBox for choosing a player profile from a list of User Names and public profiles
		ChoiceBox<Player> playerProfile = new ChoiceBox<>();
		playerVBox.getChildren().add(playerProfile);
		
		//Set up the ChoiceBox to be filled with player profiles
		PlayerList playerList = new PlayerList();
		
		for(Player p: playerList.getPlayers()) {
			playerProfile.getItems().add(p);
		}
			
		// Displaying the details for the selected fighter in the playerVBox
		Text fighterHeader = new Text("FIGHTER STATS");
		playerVBox.getChildren().add(fighterHeader);
		
		Text fighterStats = new Text();
		playerVBox.getChildren().add(fighterStats);

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
			
			if(isLeftPanel) {
				p1.setFighter(fighterChoice.getSelectionModel().getSelectedItem());
			} else {
				p2.setFighter(fighterChoice.getSelectionModel().getSelectedItem());
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
	public Group matchInterface() {

		Group root = new Group();
		BorderPane display = new BorderPane();
		root.getChildren().add(display);
		
		Button advance = new Button("Continue");
		if(battleFinished)
			advance.setDisable(false);
		else
			advance.setDisable(true);

		VBox p1c = playerControls(p1, advance);
		VBox p2c = playerControls(p2, advance);
		
		Text turnDisplay = new Text("TURN " + turnNum);
		
		ListView<String> battleLog = new ListView<>();
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
				String hmm = Fighter.compareAction(p1.getFighter(), p2.getFighter());
				System.out.println(hmm);
				p1.getCurrentBattle().getBattleTurns().add(hmm);
				turnNum ++;
				turnDisplay.setText("TURN " + turnNum);
			}

			
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
		
		display.setTop(turnDisplay);
		display.setCenter(battleLog);
		display.setLeft(p1c);
		display.setRight(p2c);
		display.setBottom(advance);

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
		VBox playerPanel = new VBox();
		
		String fighterStats = p.getFighter().getName() + "\nHP: " + p.getFighter().getHp();
		
		Text fighterDisplay = new Text(fighterStats);
		
		Button attack = new Button("Attack (" + p.getFighter().getAttack() + ")");
		Button grab = new Button("Grab (" + p.getFighter().getGrab() + ")");
		Button counter = new Button("Counter (" + p.getFighter().getCounter() + ")");
		Button deflect = new Button("Deflect (" + p.getFighter().getDeflect() + "%)");
		
		playerPanel.getChildren().add(fighterDisplay);
		playerPanel.getChildren().add(attack);
		playerPanel.getChildren().add(grab);
		playerPanel.getChildren().add(counter);
		playerPanel.getChildren().add(deflect);
		
		
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
