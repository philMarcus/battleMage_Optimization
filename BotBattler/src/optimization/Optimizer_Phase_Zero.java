package optimization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import characters.OptimizingChampion;
import game.Battle;


//This class runs a series of battles for a representative bot and logs detailed data so that we can find the range 
//of each feature and calculate a scaling factor. This ensures each featuregets a simialr amount of
//variation during the optimization process
public class Optimizer_Phase_Zero {

	private static int num = 1000;
	
	//create the printWriters to print our detailed data log and battle outcome log
	public static PrintWriter turnActionLogWriter;
    public static PrintWriter battleOutcomeLogWriter;
    
    // filenames for the data logs
    public static final String TURN_LOG = "data/phase_zero_actions.csv";
    public static final String OUTCOME_LOG= "data/phase_zero_outcomes.csv";

	// runs battles with increasing opponent level until player dies
	// returns the level on which the player died
	public static int runChallenge() {
		int level = 1;
		boolean isOver = false;
		while (!isOver) {
			//In  PHASE ZERO:
			//Use best-guess default values for parameters 
			//in order to generate feature scaling data
			OptimizingChampion player = new OptimizingChampion(5,0.5, 3, 3, 0.5,
					0.5, 0, 0, 0, 0);

			Battle b = new Battle(player, level);
			
			player.setBattleId(b.getBattleId()); //give the battlID to the optimizer for logging
			
			if (b.doBattle(false, false)) //battles return "true" when won.
				level++;
			else
				isOver = true;
			//log the battle outcome
			battleOutcomeLogWriter.println(b.getBattleId() + "," +b.isPlayerWin());
		}

		return level;
	}

	public static void main(String[] args) {
		
		try {
            // --- Setup for Turn/Action Log ---
            File actionFile = new File(TURN_LOG);
            boolean isActionFileNew = !actionFile.exists(); // Check if file is new
            
            // Create a FileWriter in "append" mode (that's what 'true' does)
            FileWriter fwActions = new FileWriter(actionFile, true);
            turnActionLogWriter = new PrintWriter(fwActions);

            // If the file was just created, write the header row.
            if (isActionFileNew) {
                turnActionLogWriter.println("battle_id,level,turn,player_hp_start,opp_hp_start,threat_1,threat_2,threat_3,threat_4,f_alloc,f_cost,f_ratioGain,f_ratioLoss,f_playerHPdelta,f_oppHPdelta,action_type,action_detail");
                System.out.println("Created new action log with header.");
            }

            // --- Setup for Battle Outcome Log ---
            File outcomeFile = new File(OUTCOME_LOG);
            boolean isOutcomeFileNew = !outcomeFile.exists(); // Check if file is new

            // Create a FileWriter in "append" mode
            FileWriter fwOutcomes = new FileWriter(outcomeFile, true);
            battleOutcomeLogWriter = new PrintWriter(fwOutcomes);

            // If the file was just created, write the header row.
            if (isOutcomeFileNew) {
                battleOutcomeLogWriter.println("battle_id,player_win");
                System.out.println("Created new outcome log with header.");
            }

            // -3 Start Experiment ---
            System.out.println("Starting Phase 0 data generation...");
   
		
		int totalLevels = 0;
		
		for (int i = 0; i < num; i++) {
			int lev = runChallenge();
			totalLevels += lev;
			System.out.println("Died on level "+lev+"\n");

		}
		System.out.println("Average Level: "+(double)totalLevels/num);
		} catch (IOException e) {
            System.err.println("Error: Could not initialize log files.");
            e.printStackTrace();
        } finally {
            //  Close  writers ---
            // This ensures all buffered data is written to the disk.
            
            if (turnActionLogWriter != null) {
                turnActionLogWriter.close();
            }
            if (battleOutcomeLogWriter != null) {
                battleOutcomeLogWriter.close();
            }
            System.out.println("Logging complete. Writers closed.");
        }
    }
	}

