/**
 * Populates the BattleLogs for each user profile
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BattleLog {

    PrintWriter writer; //writer used to populate battleLog

    private ArrayList<String> battleTurns;
    private String name = "Default_BattleLog";

    public BattleLog() {
        battleTurns = new ArrayList<String>();
    }

    /**
     * 	Used for creating a BattleLog by reading from a file
     *  @param battleLog file to be written to
     *  **/
    public BattleLog(File battleLog) {
        name = battleLog.getName();
        readFromFile(battleLog);
    }


    @Override
    /**
     * return the name of the player
     * @return name
     * */
    public String toString() {
        return name;
    }

    /**
     * return the number of turns in the battle
     * @return battleTurns
     * */
    public ArrayList<String> getBattleTurns() {
        return battleTurns;
    }

    /**
     * set the battleturns in the battle
     * @param battles list of battles
     * */
    public void setBattleTurns(ArrayList<String> battles) {
        this.battleTurns = battles;
    }


    /**
     * Writes proper battle actions to battleLog
     * @param p Player to write battleLog for
     */
    public void sendToFile(Player p) {
        String filename = p.getName() + "_battle_" + p.getNumBattles() + ".txt";
        String directory = "resources/players/" + p.getName() + "/";
        try {

            writer = new PrintWriter(directory + filename, "UTF-8");

            for (int i = 0; i < p.getCurrentBattle().getBattleTurns().size() - 1; i++) {
                writer.println("TURN " + (i + 1));
                writer.println(p.getCurrentBattle().getBattleTurns().get(i));
            }
            writer.println(p.getCurrentBattle().getBattleTurns().get(p.getCurrentBattle().getBattleTurns().size() - 1));
            writer.close();

        } catch (FileNotFoundException e) {
            // File does not need to be written in this case
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


    /**
     * reads battleLogs from files
     * @param battleLog file to read from
     */
    public void readFromFile(File battleLog) {
        battleTurns = new ArrayList<String>();

        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(battleLog))) {


            while ((line = br.readLine()) != null) {

                battleTurns.add(line);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
