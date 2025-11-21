package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import characters.BattleMageChampion;
import characters.OptimizingChampion;



public class ArenaChallenge {

	private static int num = 10000;

	public static PrintWriter finalLevelWriter;

	// filenames for the data logs
	public static final String CHALLENGE_LOG = "data/noise_calibration.csv";

	// runs battles with increasing opponent level until player dies
	// returns the level on which the player died
	public static int runChallenge() {
		int level = 1;
		boolean isOver = false;
		while (!isOver) {
			BattleMageChampion player = new BattleMageChampion();

			Battle b = new Battle(player, level);
			if (b.doBattle(false, false))
				level++;
			else
				isOver = true;
		}
		return level;
	}

	public static void main(String[] args) {

		try {
			// --- Setup for Turn/Action Log ---
			File actionFile = new File(CHALLENGE_LOG);
			boolean isActionFileNew = !actionFile.exists(); // Check if file is new

			// Create a FileWriter in "append" mode (that's what 'true' does)
			FileWriter fwActions = new FileWriter(actionFile, true);
			finalLevelWriter = new PrintWriter(fwActions);

			// If the file was just created, write the header row.
			if (isActionFileNew) {
				finalLevelWriter.println("final_level");
				System.out.println("Created new action log with header.");
			}

			int totalLevels = 0;
			ArrayList<Integer> levelsReached = new ArrayList<Integer>();

			for (int i = 0; i < num; i++) {
				int lev = runChallenge();
				totalLevels += lev;
				System.out.println("Died on level " + lev + "\n");
				levelsReached.add(lev);
				finalLevelWriter.println(lev);

			}
			System.out.println("Average Level: " + (double) totalLevels / num + " Standard Deviation: "
					+ calculateStandardDeviation(levelsReached));
			

		} catch (IOException e) {
			System.err.println("Error: Could not initialize log files.");
			e.printStackTrace();
		} finally {
			// Close writers ---
			// This ensures all buffered data is written to the disk.

			if (finalLevelWriter != null) {
				finalLevelWriter.close();
			}

			System.out.println("Logging complete. Writers closed.");
		}
	}

	/**
	 * Calculates the standard deviation of a list of integer values.
	 *
	 * @param numbers The list of integers (e.g., your Final_Level for each game).
	 * @return The standard deviation as a double.
	 */
	public static double calculateStandardDeviation(List<Integer> numbers) {
		if (numbers == null || numbers.size() < 2) {
			// Standard deviation requires at least 2 data points.
			return 0.0;
		}

		// 1. Calculate the mean (average)
		double sum = 0.0;
		for (int num : numbers) {
			sum += num;
		}
		double mean = sum / numbers.size();

		// 2. Calculate the sum of squared differences from the mean
		double squaredDifferenceSum = 0.0;
		for (int num : numbers) {
			squaredDifferenceSum += Math.pow(num - mean, 2);
		}

		// 3. Calculate the variance
		// We use "n-1" for sample standard deviation, which is correct here.
		double variance = squaredDifferenceSum / (numbers.size() - 1);

		// 4. Return the square root of the variance (Standard Deviation)
		return Math.sqrt(variance);
	}
}
