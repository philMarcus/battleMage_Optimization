package optimization;

import java.io.File;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import game.Battle;

public class Optimizer_Phase_One {

	private static int num = 100;

	public static PrintWriter challengeOutcomeLogWriter;
	public static final String OUTCOME_LOG = "data/phase_one_challenge_outcomes.csv";

	// runs battles with increasing opponent level until player dies
	// returns the level on which the player died
	public static int runChallenge() {
		int level = 1;
		boolean isOver = false;
		while (!isOver) {

			// MONTE CARLO!
			Random rand = new Random();
			// In PHASE ONE:
			// use exploratory values for weights:

			// --- 1. Generate 6 Feature Weights (Range [0.0, 1.0]) ---
			// rand.nextDouble() already returns a value between 0.0 (inclusive)
			// and 1.0 (exclusive), which is exactly what you want.
			double w_alloc = rand.nextDouble();
			double w_cost = rand.nextDouble();
			double w_ratioGain = rand.nextDouble();
			double w_ratioLoss = rand.nextDouble();
			double w_playerHPdelta = rand.nextDouble();
			double w_oppHPdelta = rand.nextDouble();

			// --- 2. Generate 4 Bias Weights (Range [-1.0, 1.0]) ---
			// To get a range of [-1.0, 1.0], we use: (rand.nextDouble() * 2.0) - 1.0
			// This scales the [0, 1] range to [0, 2] and then shifts it to [-1, 1].
			double w_attackBias = (rand.nextDouble() * 2.0) - 1.0;
			double w_blockBias = (rand.nextDouble() * 2.0) - 1.0;
			double w_blastBias = (rand.nextDouble() * 2.0) - 1.0;
			double w_shieldBias = (rand.nextDouble() * 2.0) - 1.0;
			//
			OptimizingChampion player = new OptimizingChampion(w_alloc, w_cost, w_ratioGain, w_ratioLoss,
					w_playerHPdelta, w_oppHPdelta, w_attackBias, w_blockBias, w_blastBias, w_shieldBias);

			Battle b = new Battle(player, level);

			player.setBattleId(b.getBattleId()); // give the battlID to the optimizer for logging

			if (b.doBattle(false, false)) // battles return "true" when won.
				level++;
			else
				isOver = true;
		}

		return level;
	}

	public static void main(String[] args) {

		// Create a single Random object (e.g., as a static field)
		// This will be used for all random number generation.

		try {

			// --- Setup for Challenge Outcome Log ---
			File outcomeFile = new File(OUTCOME_LOG);
			boolean isOutcomeFileNew = !outcomeFile.exists(); // Check if file is new

			// Create a FileWriter in "append" mode
			FileWriter fwOutcomes = new FileWriter(outcomeFile, true);
			challengeOutcomeLogWriter = new PrintWriter(fwOutcomes);

			// If the file was just created, write the header row.
			if (isOutcomeFileNew) {
				challengeOutcomeLogWriter.println(
						"w_alloc,w_cost,w_ratioGain,w_ratioLoss," + "w_playerHPdelta,w_oppHPdelta,w_attackBias,"
								+ "w_blockBias,w_blastBias,w_shieldBias,Avg_Final_Level");
				System.out.println("Created new outcome log with header.");
			}

			// ---Start Experiment ---
			System.out.println("Starting Phase 1 data generation...");

			int totalLevels = 0;

			for (int i = 0; i < num; i++) {
				int lev = runChallenge();
				totalLevels += lev;
				System.out.println("Died on level " + lev + "\n");

			}
			double avgLevel = (double) totalLevels / num;
			challengeOutcomeLogWriter.println(avgLevel);
			System.out.println("Average Level: " + avgLevel);
		}

		catch (IOException e) {
			System.err.println("Error: Could not initialize log files.");
			e.printStackTrace();
		} finally {
			// Close writer ---
			// This ensures all buffered data is written to the disk.

			if (challengeOutcomeLogWriter != null) {
				challengeOutcomeLogWriter.close();
			}
			System.out.println("Logging complete. Writers closed.");
		}
	}
}
