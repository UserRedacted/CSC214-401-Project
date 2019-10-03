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
	Player p1;
	Player p2;
	
	// Main method that loads printers and launches JavaFX window
	public static void main(String[] args) {
		launch(args);
	}
	// JavaFX Window Runner
	
	
	@Override
	public void start(Stage stage)  {
		
		// Set the initial scene to the main menu
		scene = new Scene(mainMenu(), 1000, 600);
			
		stage.setScene(scene);
		stage.setTitle("Project 50");
	
		stage.show();

	}
	
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
				
		Button loadUser = new Button("Load User");
		content.getChildren().add(loadUser);

		
				
		root.getChildren().add(body);
		
		return root;
	}
	
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
		
		startGame.setOnMouseClicked(e -> {
			scene.setRoot(matchInterface());
		});	
		
		// Left Panel
		VBox player1 = matchMenuLeft(p1Fighter, startGame);
		
		// Right Panel
		VBox player2 = matchMenuRight(p2Fighter, startGame);
		
		
		
		hbox.getChildren().add(player1);
		hbox.getChildren().add(centerDisplay);
		hbox.getChildren().add(player2);
		
		return root;
	}

	public VBox matchMenuLeft(ListView<Fighter> p1Fighter, Button start) {
		VBox player1 = new VBox();
		ChoiceBox<Player> p1Type = new ChoiceBox<>();
		player1.getChildren().add(p1Type);
		
		p1Type.getItems().add(new Player("Player 1", true));
		p1Type.getItems().add(new Player("AI Jerry", false));
			
		Text p1FighterHeader = new Text("FIGHTER STATS");
		player1.getChildren().add(p1FighterHeader);
		
		Text p1FighterStats = new Text();
		player1.getChildren().add(p1FighterStats);
		
		
		p1Type.getSelectionModel().selectedItemProperty().addListener(e -> {
			p1Fighter.setDisable(false);
			Player selected = p1Type.getSelectionModel().getSelectedItem();
			p1 = selected;
		});
		
		
		p1Fighter.getSelectionModel().selectedItemProperty().addListener(e -> {
			Fighter selected = p1Fighter.getSelectionModel().getSelectedItem();
			p1.setFighter(selected);
			p1FighterStats.setText(selected.printStats());	
			if(p2.getFighter() != null)
				start.setDisable(false);
		});
		
		return player1;
	}

	public VBox matchMenuRight(ListView<Fighter> p2Fighter, Button start) {
		VBox player2 = new VBox();
		
		ChoiceBox<Player> p2Type = new ChoiceBox<>();
		player2.getChildren().add(p2Type);
		
		p2Type.getItems().add(new Player("Player 2", true));
		p2Type.getItems().add(new Player("AI Carl", false));
		
		
		Text p2FighterHeader = new Text("FIGHTER STATS");
		player2.getChildren().add(p2FighterHeader);
		
		Text p2FighterStats = new Text();
		player2.getChildren().add(p2FighterStats);
		
		p2Type.getSelectionModel().selectedItemProperty().addListener(e -> {
			p2Fighter.setDisable(false);
			Player selected = p2Type.getSelectionModel().getSelectedItem();
			p2 = selected;
		});
		
		p2Fighter.getSelectionModel().selectedItemProperty().addListener(e -> {
			Fighter selected = p2Fighter.getSelectionModel().getSelectedItem();
			p2.setFighter(selected);
			p2FighterStats.setText(selected.printStats());
			if(p1.getFighter() != null)
				start.setDisable(false);
		});		
		
		return player2;
	}

	
	public Group matchInterface() {
		Group root = new Group();
		BorderPane display = new BorderPane();
		root.getChildren().add(display);
		VBox p1c = playerControls(p1);
		VBox p2c = playerControls(p2);
		display.setLeft(p1c);
		display.setRight(p2c);
		return root;
	}
	
	// Panel in game match for player control scheme
	public VBox playerControls(Player p) {
		VBox playerPanel = new VBox();
		
		Button attack = new Button("Attack (" + p.getFighter().getAttack() + ")");
		Button grab = new Button("Grab (" + p.getFighter().getGrab() + ")");
		Button counter = new Button("Counter (" + p.getFighter().getCounter() + ")");
		Button deflect = new Button("Deflect (" + p.getFighter().getDeflect() + "%)");
		playerPanel.getChildren().add(attack);
		playerPanel.getChildren().add(grab);
		playerPanel.getChildren().add(counter);
		playerPanel.getChildren().add(deflect);
		
		attack.setOnMouseClicked(e -> {
			p.getFighter().setChosenAction(0);
			playerPanel.getChildren().clear();
		});
		grab.setOnMouseClicked(e -> {
			p.getFighter().setChosenAction(1);
			playerPanel.getChildren().clear();
		});
		counter.setOnMouseClicked(e -> {
			p.getFighter().setChosenAction(2);
			playerPanel.getChildren().clear();

		});
		deflect.setOnMouseClicked(e -> {
			p.getFighter().setChosenAction(3);
			playerPanel.getChildren().clear();
		});
		
		
		return playerPanel;
	}
	
	
}
