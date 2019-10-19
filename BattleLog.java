import java.io.File;
import java.io.PrintWriter;
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
		File battle = new File(p.getName() + "_battle_" + p.getNumBattles() + ".txt");
			try {
				writer = new PrintWriter(battle.getName(), "UTF-8");
				
				for(int i = 0; i < p.getCurrentBattle().getBattleTurns().size()-1; i++) {
					writer.println("TURN " + (i+1));
					writer.println(p.getCurrentBattle().getBattleTurns().get(i));
				}
				writer.println(p.getCurrentBattle().getBattleTurns().get(p.getCurrentBattle().getBattleTurns().size()-1));
				writer.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

		
	}
}
