import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BattleLog {

	PrintWriter writer;
	
	private ArrayList<String> battleTurns;
	
	public BattleLog() {
		battleTurns = new ArrayList<String>();
	}

	public ArrayList<String> getBattleTurns() {
		return battleTurns;
	}

	public void setBattleTurns(ArrayList<String> battles) {
		this.battleTurns = battles;
	}
	
	public void sendToFile(Player p) {
		String filename = p.getName() + "_battle_" + p.getNumBattles() + ".txt";
		String directory = "resources/players/" + p.getName() + "/";	
			try {

				writer = new PrintWriter(directory + filename, "UTF-8");
				
				for(int i = 0; i < p.getCurrentBattle().getBattleTurns().size()-1; i++) {
					writer.println("TURN " + (i+1));
					writer.println(p.getCurrentBattle().getBattleTurns().get(i));
				}
				writer.println(p.getCurrentBattle().getBattleTurns().get(p.getCurrentBattle().getBattleTurns().size()-1));
				writer.close();
				
			} catch (FileNotFoundException e) {
				// File does not need to be written in this case
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}	

		
	}
}
