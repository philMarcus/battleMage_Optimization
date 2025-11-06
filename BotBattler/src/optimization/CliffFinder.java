package optimization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import characters.BadBlocker;
import game.Battle;
import characters.DumbFighter;
import characters.GoodBlocker;
import characters.SadMage;
import optimization.BattleMageChampion;
import characters.Blaster;

public class CliffFinder {
	private static int wins;
	private static int num = 10000;

	public static PrintWriter outcomeLogWriter;
	public static final String OUTCOME_LOG = "data/cliff_finder_outcomes.csv";

	public static void main(String[] args) {

		// --- Setup for Challenge Outcome Log ---
		File outcomeFile = new File(OUTCOME_LOG);
		boolean isOutcomeFileNew = !outcomeFile.exists(); // Check if file is new

		// Create a FileWriter
		FileWriter fwOutcomes;
		try {
			fwOutcomes = new FileWriter(outcomeFile);

			outcomeLogWriter = new PrintWriter(fwOutcomes);

			// If the file was just created, write the header row.

			outcomeLogWriter.println("Level,Win_Rate");
			System.out.println("Created new outcome log with header.");

			for (int level = 1; level <= 60; level++) {
				wins = 0;
				for (int i = 0; i < num; i++) {
					BattleMageChampion player = new BattleMageChampion();
					Battle b = new Battle(player, level);
					if (b.doBattle(false, false)) {
						wins++;
						System.out.println(player.toString() + " gets win " + wins + " in game " + (i + 1) + "  "
								+ ((double) wins / (i + 1)));
					}
				}
				System.out.println("Final score:" + wins + "/" + num + "  " + ((double) wins / num));
				outcomeLogWriter.println(level + "," + ((double) wins / num));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// --- THIS IS THE FIX ---
			// This block runs no matter what.
			// It ensures the buffer is flushed and the file is closed.
			if (outcomeLogWriter != null) {
				outcomeLogWriter.close();
				System.out.println("Log file closed.");
			}
		}
	}
}
