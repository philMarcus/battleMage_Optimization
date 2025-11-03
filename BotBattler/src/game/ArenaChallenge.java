package game;

import java.util.ArrayList;
import java.util.List;

import characters.BadBlocker;
import characters.DumbFighter;
import characters.GoodBlocker;
import characters.SadMage;
import optimization.BattleMageChampion;
import optimization.OptimizingChampion;
import characters.Blaster;

public class ArenaChallenge {

	private static int num = 100000;

	// runs battles with increasing opponent level until player dies
	// returns the level on which the player died
	public static int runChallenge() {
		int level = 1;
		boolean isOver = false;
		while (!isOver) {
			OptimizingChampion player = new OptimizingChampion(0.25,0,1,1,0,0,0,0,0,0);
			
			Battle b = new Battle(player, level);
			if (b.doBattle(false, false))
				level++;
			else
				isOver = true;
		}
		return level;
	}

	public static void main(String[] args) {
		int totalLevels = 0;
		ArrayList<Integer> levelsReached = new ArrayList<Integer>();

		for (int i = 0; i < num; i++) {
			int lev = runChallenge();
			totalLevels += lev;
			System.out.println("Died on level " + lev + "\n");
			levelsReached.add(lev);

		}
		System.out.println("Average Level: " + (double) totalLevels / num +
				" Standard Deviation: "+ calculateStandardDeviation(levelsReached));
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
