/**
 * Responsible for playing sounds in the game
 * */
import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {

	MediaPlayer soundPlayer;


	/**
	 * Constructor
	 */
	public SoundPlayer(){
	}
	
	// Special event listeners for control structures
	public void playOnHover(Button b) {
		b.setOnMouseEntered(e -> {
			playHover();
		});
	}
	
	
	
	
	// General use sound playing
	
	public void playDamageLeft() {
		File sound = new File("resources\\sound\\damageLeft.wav");
		play(sound);
	}
	public void playDamageRight() {
		File sound = new File("resources\\sound\\damageRight.wav");
		play(sound);
	}
	public void playUndamagedLeft() {
		File sound = new File("resources\\sound\\undamagedLeft.wav");
		play(sound);
	}
	public void playUndamagedRight() {
		File sound = new File("resources\\sound\\undamagedRight.wav");
		play(sound);
	}
	public void playNextRound() {
		File sound = new File("resources\\sound\\nextRound.wav");
		play(sound);
	}
	public void playFailure() {
		File sound = new File("resources\\sound\\failure.wav");
		play(sound);
	}	
	public void playHover() {
		File sound = new File("resources\\sound\\hover.wav");
		play(sound);
	}		
	public void playClick() {
		File sound = new File("resources\\sound\\click.wav");
		play(sound);
	}	
	public void playClick2() {
		File sound = new File("resources\\sound\\click2.wav");
		play(sound);
	}	
	public void playSuccess() {
		File sound = new File("resources\\sound\\success.wav");
		play(sound);
	}	
	
	// Function that takes in any sound file and simply plays it at default settings
	private void play(File sound) {
		Media media = new Media(sound.toURI().toString());
		soundPlayer = new MediaPlayer(media);
		soundPlayer.setVolume(1);
		soundPlayer.setAutoPlay(false);
		soundPlayer.setCycleCount(1);
		soundPlayer.play();
	}
	
}
