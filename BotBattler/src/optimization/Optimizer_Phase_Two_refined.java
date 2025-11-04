package optimization;

import java.io.File;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import game.Battle;

public class Optimizer_Phase_Two_refined {


	private static int numSets = 5000;

	public static PrintWriter challengeOutcomeLogWriter;
	public static final String OUTCOME_LOG = "data/phase_two_refined_outcomes.csv";

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
	
	/*
	 * New "Champion" Cutoffs (Benchmark = 25.708) Determined from phase 2 test run found champion
	 * This table shows the "Aggressive" (3-sigma) and "Safe" (4-sigma) cutoffs.
	 * Gate N      Standard Error (SE)  3-Sigma Cutoff (L)      4-Sigma Cutoff (L)
	 * C3   1,000  0.18                 25.17                   24.99
	 * C4   10,000 0.06                 25.53                   25.47
	 * C5   50,000 0.026                25.63                   25.60
	 */

	private static double goldStandard = 25.708; //level of best confirmed bot so far
	private static final double S3 = 0.18, S4 = 0.06, S5 = 0.026; //measured std devs for given N.
	private static final double SIGMA = 3.8; //how many SDs off new champions to set new cutoff levels
	//define cutoff values for funnel
	private static final int N1 = 10, L1 = 12; //Cut 1: SE 1.8 levels
	private static final int N2 = 100;
	private static final double L2 = 23.5; //Cut 2: SE 0.6 levels
	private static final int N3 = 1000;
	private static double L3 = 25; //Cut 3: SE 0.18 levels
	private static final int N4 = 10000; //Cut 4: SE 0.06 levels
	private static double L4 = 25.5;
	private static final int N5 = 50000;
	private static double L5 = 25.6; //Cut 5: SE 0.026 levels
	private static final int N_CONFIRM = 500000; //Confirmed champion: SE: 0.008 lev
	

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
			System.out.println("Starting Phase 2 data generation...");

			double maxLevel = 0;
			int c0 =0, c1=0, c2=0, c3=0, c4=0, c5=0; //count how many pass each cutoff
			for (int n = 0; n < numSets; n++) {
	
				// MONTE CARLO!
				Random rand = new Random();

				// --- Generate 10 Weights in Range determined in Phase 2 test ---
				//Determined from top 26 runs of 50K phase 2 test runs
				//All 26 are > 25.11 +/- 0.06.
				//
				// The "Robust - No shrink" Features:
				w_ratioGain = getRandomDouble(rand, 0.814, 1.000);
				w_playerHPdelta = getRandomDouble(rand, 0.788, 1.000);
				w_ratioLoss = getRandomDouble(rand, 0.370,0.613);
				w_oppHPdelta = getRandomDouble(rand, 0.497, 0.888);
				//The "Sensitive - 2SD peak" features:
				w_alloc = getRandomDouble(rand, 0.215, 0.339);
				w_attackBias = getRandomDouble(rand, -0.163,0.309);
				w_blockBias = getRandomDouble(rand, -0.250,0.257);
				w_blastBias = getRandomDouble(rand, -0.096,0.311);
				w_shieldBias = getRandomDouble(rand, -0.240, 0.083);
				
				// The removed Feature:
				//w_cost = getRandomDouble(rand, 0, 1.000);

				

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
					
					if (i==N3 && avgLevel < L3) {c2++; break;}
					
					if (i==N4 && avgLevel < L4) {c3++; break;}
					
					if (i==N5 && avgLevel < L5) {c4++; break;}
					
					if (i==N_CONFIRM) {
						c5++;
						//update cutoff levels if we have a new champ
						if(avgLevel > goldStandard) {
							
							System.out.println("!!! NEW CHAMPION FOUND !!!");
					        System.out.println("Old Gold Standard: " + goldStandard);
					        System.out.println("New Gold Standard: " + avgLevel);
					        
							L3 = avgLevel - SIGMA*S3;
							L4 = avgLevel - SIGMA*S4;
							L5 = avgLevel - SIGMA*S5;
							goldStandard = avgLevel;
						}
					}
					
										
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
				System.out.println("Cutoff 3 at level "+L3+" Passed: "+c3);
				System.out.println("Cutoff 4 at level "+L4+" Passed: "+c4);
				System.out.println("Cutoff 5 at level "+L5+" Passed: "+c5);
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
