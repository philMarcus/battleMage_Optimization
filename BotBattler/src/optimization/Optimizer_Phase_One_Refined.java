package optimization;

import java.io.File;
import java.util.Random;

import characters.OptimizingChampion;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import game.Battle;

public class Optimizer_Phase_One_Refined {

	// we can use n=100 to quickly spot the terrible bots and only
	// run to n=1000 for bots that reach level 20+
	private static final int numSets = 100;
	
	private static final int N1 = 10, L1 = 10; //Cut 1: SE 1.8 levels
	private static final int N2 = 100, L2 = 22; //Cut 2: SE 0.6 levels
	private static final int N_CONFIRM = 1000; // SE: 0.18 level

	public static PrintWriter challengeOutcomeLogWriter;
	public static final String OUTCOME_LOG = "data/phase_one_refined_outcomes.csv";

	// define parameters
	static double w_alloc;
	static double w_cost;
	static double w_ratioGain;
	static double w_ratioLoss;
	static double w_playerHPdelta;
	static double w_oppHPdelta;
	static double w_attackBias;
	static double w_blockBias;
	static double w_blastBias;
	static double w_shieldBias;

	// runs battles with increasing opponent level until player dies
	// returns the level on which the player died
	public static int runChallenge() {
		int level = 1;
		boolean isOver = false;
		while (!isOver) {

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

	// Helper method to get a random double within a specific [min, max] range
	// This is more robust than the Phase 1 method as it works for any range.
	public static double getRandomDouble(Random rand, double min, double max) {
		// rand.nextDouble() returns a value in [0.0, 1.0)
		// (max - min) scales the range
		// + min shifts the range to start at 'min'
		return min + (rand.nextDouble() * (max - min));
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
			System.out.println("Starting Refined Phase One data generation...");

			double maxLevel = 0;
			int c0 =0, c1=0, c2=0; //count how many pass each cutoff
			for (int n = 0; n < numSets; n++) {
				// MONTE CARLO!
				Random rand = new Random();

				// --- Generate 10 Weights in Range determined in Phase 1 ---
				// Classify features according to distributions of top 0.05%
				//of 1,000,000 phase 1 runs
				// see juypiter notebook
				// The "Ramp Up" Features are top 75%:
				w_ratioGain = getRandomDouble(rand, 0.653, 1.000);
				w_playerHPdelta = getRandomDouble(rand, 0.459, 1.000);
				w_oppHPdelta = getRandomDouble(rand, 0.482, 1);
				w_attackBias = getRandomDouble(rand, -0.121, 1.000);
				w_blockBias = getRandomDouble(rand, -0.148, 1.0);
				w_blastBias = getRandomDouble(rand, -0.096, 1.000);
				// The "Ramp Down" Features are bottom 75%:
				w_ratioLoss = getRandomDouble(rand, 0.0, 0.613);
				w_shieldBias = getRandomDouble(rand, -1.0, 0.083);
				// The "Central Peak" features:
				//			--
				// The "Flat/Bimodal" Features:
				w_alloc = getRandomDouble(rand, 0.2, 1);  //bimodal, clipped by hand.
				w_cost = 0; //Justifiably removed after phase 1.
				
				int totalLevels = 0;
				double avgLevel=0;
				
				//looop up to N_CONFIRM times for bots that pass all cutoffs
				int i;
				for (i = 1; i <= N_CONFIRM; i++) {

					int lev = runChallenge();
					totalLevels += lev;
					avgLevel = (double) totalLevels / i;
					
					//the funnel. only good bots will run many times.
					//counts how many fail each cutoff
					if (i==N1 && avgLevel < L1) {c0++; break;}
					
					if (i==N2 && avgLevel < L2) {c1++; break;}
									
					if (i==N_CONFIRM) c2++;
																	
				}
				//write the data!
				String datastr = w_alloc + "," + w_cost + "," + w_ratioGain + "," + w_ratioLoss + "," + w_playerHPdelta
						+ "," + w_oppHPdelta + "," + w_attackBias + "," + w_blockBias + "," + w_blastBias + ","
						+ w_shieldBias + "," + avgLevel;
				challengeOutcomeLogWriter.println(datastr);
				//write to console
				System.out.println("Challenge Set " + n + ": " + datastr);
				if (avgLevel > maxLevel)
					maxLevel = avgLevel;
				System.out.println("Max Level so far: " + maxLevel);
				System.out.println("Failed Bots: "+c0);
				System.out.println("Cutoff 1 Passed: "+c1);
				System.out.println("Cutoff 2 Passed: "+c2);

			}
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
