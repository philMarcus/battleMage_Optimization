package optimization;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import game.Battle;



public class Optimizer_Phase_Three {

	private static int num = 100;

	// create the printWriters to print our detailed data log and battle outcome log
	public static PrintWriter turnLogWriter;
	public static PrintWriter battleOutcomeLogWriter;

	// filenames for the data logs
	public static final String TURN_LOG = "data/phase_three_turns.csv";
	public static final String OUTCOME_LOG = "data/phase_three_outcomes.csv";

	// runs battles with increasing opponent level until player dies
	// returns the level on which the player died
	public static int runChallenge() {
		int level = 1;
		boolean isOver = false;
		while (!isOver) {
			// In PHASE ZERO:
			// Use best-guess default values for parameters
			// in order to generate feature scaling data
			OptimizingChampion player = new OptimizingChampion(5, 0.5, 3, 3, 0.5, 0.5, 0, 0, 0, 0);

			Battle b = new Battle(player, level);

			player.setBattleId(b.getBattleId()); // give the battlID to the optimizer for logging

			if (b.doBattle(false, false)) // battles return "true" when won.
				level++;
			else
				isOver = true;
			// log the battle outcome
			battleOutcomeLogWriter.println(b.getBattleId() + "," + b.isPlayerWin());
		}

		return level;
	}

	public static void main(String[] args) {

		try {
			// --- Setup for Turn/Action Log ---
			File turnFile = new File(TURN_LOG);
			boolean isTurnFileNew = !turnFile.exists(); // Check if file is new

			// Create a FileWriter in "append" mode (that's what 'true' does)
			FileWriter fwTurns = new FileWriter(turnFile, true);
			turnLogWriter = new PrintWriter(fwTurns);

			// If the file was just created, write the header row.
			if (isTurnFileNew) {
				turnLogWriter.println("battleId,botName,level,turnNum,playerHpStart,playerStaminaStart,"
						+ "oppHpStart,oppVulnerability,usedMagicBlasts,threatQ1,threatQ2,threatQ3,threatQ4,"
						+ "actionName,actionDetail,playerHpEnd,oppHpEnd");
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
				System.out.println("Died on level " + lev + "\n");

			}
			System.out.println("Average Level: " + (double) totalLevels / num);
			
			
		} catch (IOException e) {
			System.err.println("Error: Could not initialize log files.");
			e.printStackTrace();
		} finally {
			// Close writers ---
			// This ensures all buffered data is written to the disk.

			if (turnLogWriter != null) {
				turnLogWriter.close();
			}
			if (battleOutcomeLogWriter != null) {
				battleOutcomeLogWriter.close();
			}
			System.out.println("Logging complete. Writers closed.");
		}
	}
}
