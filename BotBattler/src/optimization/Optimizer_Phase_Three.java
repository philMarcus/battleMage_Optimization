package optimization;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import characters.Character;

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
	public static void runChallenge(Character player) {
		for(int level = 1; level <=60; level++) {
		for(int i=0;i<100;i++) {
			
			Battle b = new Battle(player, level);

			b.doBattle(false, false); //run the battle
			
			// log the battle outcome
			battleOutcomeLogWriter.println(b.getBattleId() + "," + b.isPlayerWin());
		}
		}

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
			System.out.println("Starting Phase Three data generation...");



			// ==================================================
			// Mining Phase 1 (Levels 1-10)...
			// ==================================================
			// Bot closest to Level 1.0 (Actual: 1.0000)
			OptimizingChampion player1 = new OptimizingChampion(0.1615, 0.0000, 0.1222, 0.0244, 0.0300, 0.7892, -0.7956, -0.4842, -0.3334, 0.4774);
			player1.setName("Level 1");
			runChallenge(player1);
			// -----------------------------------------
			// Bot closest to Level 2.0 (Actual: 2.0000)
			OptimizingChampion player2 = new OptimizingChampion(0.9860, 0.0000, 0.0192, 0.7003, 0.6327, 0.7663, 0.4651, -0.2727, -0.9291, 0.9040);
			player2.setName("Level 2");
			runChallenge(player2);
			// -----------------------------------------
			// Bot closest to Level 3.0 (Actual: 3.0000)
			OptimizingChampion player3 = new OptimizingChampion(0.5923, 0.0000, 0.6493, 0.4024, 0.2870, 0.2024, 0.3493, -0.5488, -0.5771, -0.9023);
			player3.setName("Level 3");
			runChallenge(player3);
			// -----------------------------------------
			// Bot closest to Level 4.0 (Actual: 4.0000)
			OptimizingChampion player4 = new OptimizingChampion(0.2046, 0.0000, 0.1175, 0.9816, 0.9767, 0.3008, 0.4697, -0.8376, -0.2119, -0.8753);
			player4.setName("Level 4");
			runChallenge(player4);
			// -----------------------------------------
			// Bot closest to Level 5.0 (Actual: 5.0000)
			OptimizingChampion player5 = new OptimizingChampion(0.6296, 0.0000, 0.9522, 0.7460, 0.8699, 0.5017, 0.4978, -0.7135, 0.2524, -0.0804);
			player5.setName("Level 5");
			runChallenge(player5);
			// -----------------------------------------
			// Bot closest to Level 6.0 (Actual: 6.0000)
			OptimizingChampion player6 = new OptimizingChampion(0.5605, 0.0000, 0.0149, 0.1359, 0.3368, 0.1482, -0.7999, 0.0237, 0.5414, -0.7805);
			player6.setName("Level 6");
			runChallenge(player6);
			// -----------------------------------------
			// Bot closest to Level 7.0 (Actual: 7.0000)
			OptimizingChampion player7 = new OptimizingChampion(0.2065, 0.0000, 0.6858, 0.9522, 0.3808, 0.7972, 0.4145, 0.1308, 0.1388, -0.5288);
			player7.setName("Level 7");
			runChallenge(player7);
			// -----------------------------------------
			// Bot closest to Level 8.0 (Actual: 8.0000)
			OptimizingChampion player8 = new OptimizingChampion(0.1678, 0.0000, 0.6690, 0.4230, 0.9290, 0.1666, -0.6077, 0.8220, 0.4333, 0.0701);
			player8.setName("Level 8");
			runChallenge(player8);
			// -----------------------------------------
			// Bot closest to Level 9.0 (Actual: 9.0000)
			OptimizingChampion player9 = new OptimizingChampion(0.4829, 0.0000, 0.9085, 0.8050, 0.0743, 0.0282, -0.4056, -0.2496, -0.2208, -0.3516);
			player9.setName("Level 9");
			runChallenge(player9);
			// -----------------------------------------
			// Bot closest to Level 10.0 (Actual: 10.0000)
			OptimizingChampion player10 = new OptimizingChampion(0.0073, 0.0000, 0.5203, 0.5824, 0.3494, 0.4708, 0.8359, -0.6324, -0.0442, 0.9052);
			player10.setName("Level 10");
			runChallenge(player10);
			// -----------------------------------------

			// ==================================================
			// Mining Phase 1 Refined (Levels 11-20)...
			// ==================================================

			// Bot closest to Level 11.0 (Actual: 11.0000)
			OptimizingChampion player11 = new OptimizingChampion(0.3550, 0.0000, 0.8856, 0.0488, 0.8185, 0.5510, 0.6000, 0.5320, 0.5431, -0.9188);
			player11.setName("Level 11");
			runChallenge(player11);
			// -----------------------------------------
			// Bot closest to Level 12.0 (Actual: 12.0000)
			OptimizingChampion player12 = new OptimizingChampion(0.8894, 0.0000, 0.7235, 0.1327, 0.6263, 0.7403, 0.3098, 0.3399, 0.0712, -0.6613);
			player12.setName("Level 12");
			runChallenge(player12);
			// -----------------------------------------
			// Bot closest to Level 13.0 (Actual: 13.0000)
			OptimizingChampion player13 = new OptimizingChampion(0.7919, 0.0000, 0.7944, 0.3728, 0.7171, 0.7780, 0.3762, 0.5236, 0.6930, -0.9358);
			player13.setName("Level 13");
			runChallenge(player13);
			// -----------------------------------------
			// Bot closest to Level 14.0 (Actual: 14.0000)
			OptimizingChampion player14 = new OptimizingChampion(0.3575, 0.0000, 0.9913, 0.2469, 0.7973, 0.6445, -0.0681, 0.0984, 0.0679, -0.4397);
			player14.setName("Level 14");
			runChallenge(player14);
			// -----------------------------------------
			// Bot closest to Level 15.0 (Actual: 15.0000)
			OptimizingChampion player15 = new OptimizingChampion(0.3330, 0.0000, 0.7067, 0.4990, 0.7139, 0.5122, -0.0050, 0.7615, 0.9725, -0.0639);
			player15.setName("Level 15");
			runChallenge(player15);
			// -----------------------------------------
			// Bot closest to Level 16.0 (Actual: 16.0000)
			OptimizingChampion player16 = new OptimizingChampion(0.3597, 0.0000, 0.7319, 0.4552, 0.8787, 0.5788, 0.5260, 0.3910, 0.4309, -0.6593);
			player16.setName("Level 16");
			runChallenge(player16);
			// -----------------------------------------
			// Bot closest to Level 17.0 (Actual: 17.0000)
			OptimizingChampion player17 = new OptimizingChampion(0.8489, 0.0000, 0.9732, 0.3468, 0.6154, 0.6410, 0.3691, 0.8417, 0.8464, -0.7891);
			player17.setName("Level 17");
			runChallenge(player17);
			// -----------------------------------------
			// Bot closest to Level 18.0 (Actual: 18.0000)
			OptimizingChampion player18 = new OptimizingChampion(0.6362, 0.0000, 0.7898, 0.5651, 0.5656, 0.4891, 0.4962, 0.5588, 0.6822, -0.4730);
			player18.setName("Level 18");
			runChallenge(player18);
			// -----------------------------------------
			// Bot closest to Level 19.0 (Actual: 19.0000)
			OptimizingChampion player19 = new OptimizingChampion(0.2475, 0.0000, 0.8856, 0.3463, 0.6268, 0.5372, 0.6740, 0.7418, 0.9784, -0.9338);
			player19.setName("Level 19");
			runChallenge(player19);
			// -----------------------------------------
			// Bot closest to Level 20.0 (Actual: 20.0000)
			OptimizingChampion player20 = new OptimizingChampion(0.8626, 0.0000, 0.9442, 0.2399, 0.7235, 0.6399, 0.1353, 0.2879, 0.3349, -0.9419);
			player20.setName("Level 20");
			runChallenge(player20);
			// -----------------------------------------

			// ==================================================
			// Mining Phase 2 (Levels 20.0-24.5)...
			// ==================================================
			// Bot closest to Level 20.0 (Actual: 20.0000)
			OptimizingChampion player20_0 = new OptimizingChampion(0.3813, 0.0000, 0.9828, 0.4634, 0.9091, 0.7339, -0.3588, 0.0526, 0.1554, -0.1486);
			player20_0.setName("Level 20.0");
			runChallenge(player20_0);
			// -----------------------------------------
			// Bot closest to Level 20.5 (Actual: 20.5000)
			OptimizingChampion player20_5 = new OptimizingChampion(0.3300, 0.0000, 0.9068, 0.4235, 0.8552, 0.7631, -0.3472, 0.4328, 0.5895, -0.3032);
			player20_5.setName("Level 20.5");
			runChallenge(player20_5);
			// -----------------------------------------
			// Bot closest to Level 21.0 (Actual: 21.0000)
			OptimizingChampion player21_0 = new OptimizingChampion(0.3381, 0.0000, 0.8800, 0.4917, 0.9965, 0.7185, -0.1708, 0.2456, 0.2760, -0.3216);
			player21_0.setName("Level 21.0");
			runChallenge(player21_0);
			// -----------------------------------------
			// Bot closest to Level 21.5 (Actual: 21.5000)
			OptimizingChampion player21_5 = new OptimizingChampion(0.4514, 0.0000, 0.8274, 0.4941, 0.8830, 0.6911, 0.3178, 0.4734, 0.5425, -0.3303);
			player21_5.setName("Level 21.5");
			runChallenge(player21_5);
			// -----------------------------------------
			// Bot closest to Level 22.0 (Actual: 22.0000)
			OptimizingChampion player22_0 = new OptimizingChampion(0.2126, 0.0000, 0.9967, 0.5835, 0.9654, 0.7627, 0.5030, 0.5104, 0.5104, 0.0694);
			player22_0.setName("Level 22.0");
			runChallenge(player22_0);
			// -----------------------------------------
			// Bot closest to Level 22.5 (Actual: 22.5000)
			OptimizingChampion player22_5 = new OptimizingChampion(0.2299, 0.0000, 0.9135, 0.3830, 0.9486, 0.5652, 0.2487, -0.0354, 0.1490, -0.4048);
			player22_5.setName("Level 22.5");
			runChallenge(player22_5);
			// -----------------------------------------
			// Bot closest to Level 23.0 (Actual: 23.0000)
			OptimizingChampion player23_0 = new OptimizingChampion(0.2172, 0.0000, 0.9240, 0.4934, 0.9786, 0.5656, 0.0355, -0.0922, -0.0074, -0.0050);
			player23_0.setName("Level 23.0");
			runChallenge(player23_0);
			// -----------------------------------------
			// Bot closest to Level 23.5 (Actual: 23.5000)
			OptimizingChampion player23_5 = new OptimizingChampion(0.2665, 0.0000, 0.9219, 0.5025, 0.9630, 0.7189, 0.5634, 0.3305, 0.5431, -0.3569);
			player23_5.setName("Level 23.5");
			runChallenge(player23_5);
			// -----------------------------------------
			// Bot closest to Level 24.0 (Actual: 23.9990)
			OptimizingChampion player24_0 = new OptimizingChampion(0.2767, 0.0000, 0.9099, 0.4400, 0.9999, 0.6228, 0.4293, 0.2981, 0.4884, -0.5067);
			player24_0.setName("Level 24.0");
			runChallenge(player24_0);
			// -----------------------------------------
			// Bot closest to Level 24.5 (Actual: 24.4990)
			OptimizingChampion player24_5 = new OptimizingChampion(0.2839, 0.0000, 0.8306, 0.4096, 0.8641, 0.5632, 0.5077, 0.4124, 0.4720, 0.0725);
			player24_5.setName("Level 24.5");
			runChallenge(player24_5);
			// -----------------------------------------

			// ==================================================
			// Mining Phase 2 Refined (Levels 24.6-26.1)...
			// ==================================================
			// -----------------------------------------
			// Bot closest to Level 24.6 (Actual: 24.6000)
			OptimizingChampion player24_6 = new OptimizingChampion(0.2250, 0.0000, 0.8466, 0.4567, 0.9145, 0.5557, 0.1919, 0.0054, 0.1714, -0.1834);
			player24_6.setName("Level 24.6");
			runChallenge(player24_6);
			// -----------------------------------------
			// Bot closest to Level 24.7 (Actual: 24.7000)
			OptimizingChampion player24_7 = new OptimizingChampion(0.2289, 0.0000, 0.9336, 0.4386, 0.9913, 0.5365, -0.0110, -0.1589, 0.0520, -0.1466);
			player24_7.setName("Level 24.7");
			runChallenge(player24_7);
			// -----------------------------------------
			// Bot closest to Level 24.8 (Actual: 24.8000)
			OptimizingChampion player24_8 = new OptimizingChampion(0.2991, 0.0000, 0.8557, 0.4116, 0.8113, 0.7138, -0.0382, -0.0498, 0.0125, -0.2089);
			player24_8.setName("Level 24.8");
			runChallenge(player24_8);
			// -----------------------------------------
			// Bot closest to Level 24.9 (Actual: 24.9000)
			OptimizingChampion player24_9 = new OptimizingChampion(0.2820, 0.0000, 0.8398, 0.5771, 0.9925, 0.8314, 0.2184, 0.2461, 0.1910, -0.1151);
			player24_9.setName("Level 24.9");
			runChallenge(player24_9);
			// -----------------------------------------
			// Bot closest to Level 25.0 (Actual: 25.0000)
			OptimizingChampion player25_0 = new OptimizingChampion(0.2910, 0.0000, 0.9297, 0.5072, 0.9501, 0.5724, 0.1160, -0.0164, 0.1583, 0.0292);
			player25_0.setName("Level 25.0");
			runChallenge(player25_0);
			// -----------------------------------------
			// Bot closest to Level 25.1 (Actual: 25.1000)
			OptimizingChampion player25_1 = new OptimizingChampion(0.2534, 0.0000, 0.8908, 0.5106, 0.9073, 0.6357, -0.0834, -0.2378, -0.0493, -0.1952);
			player25_1.setName("Level 25.1");
			runChallenge(player25_1);
			// -----------------------------------------
			// Bot closest to Level 25.2 (Actual: 25.2000)
			OptimizingChampion player25_2 = new OptimizingChampion(0.3228, 0.0000, 0.9716, 0.5836, 0.9242, 0.6135, 0.1279, -0.0017, 0.1643, -0.0542);
			player25_2.setName("Level 25.2");
			runChallenge(player25_2);
			// -----------------------------------------
			// Bot closest to Level 25.3 (Actual: 25.3000)
			OptimizingChampion player25_3 = new OptimizingChampion(0.2368, 0.0000, 0.9913, 0.4961, 0.8442, 0.6829, 0.0046, -0.0390, 0.0131, 0.0177);
			player25_3.setName("Level 25.3");
			runChallenge(player25_3);
			// -----------------------------------------
			// Bot closest to Level 25.4 (Actual: 25.4000)
			OptimizingChampion player25_4 = new OptimizingChampion(0.2675, 0.0000, 0.9214, 0.4035, 0.9296, 0.8678, -0.0192, -0.1126, -0.0209, -0.0948);
			player25_4.setName("Level 25.4");
			runChallenge(player25_4);
			// -----------------------------------------
			// Bot closest to Level 25.5 (Actual: 25.5000)
			OptimizingChampion player25_5 = new OptimizingChampion(0.3250, 0.0000, 0.9627, 0.3823, 0.8851, 0.5439, 0.1273, -0.0349, 0.1510, -0.0015);
			player25_5.setName("Level 25.5");
			runChallenge(player25_5);
			// -----------------------------------------
			// Bot closest to Level 25.6 (Actual: 25.6000)
			OptimizingChampion player25_6 = new OptimizingChampion(0.2590, 0.0000, 0.8273, 0.5440, 0.9917, 0.7938, -0.0320, -0.1280, -0.0778, -0.1076);
			player25_6.setName("Level 25.6");
			runChallenge(player25_6);
			// -----------------------------------------
			// Bot closest to Level 25.7 (Actual: 25.7001)
			OptimizingChampion player25_7 = new OptimizingChampion(0.2345, 0.0000, 0.9762, 0.3991, 0.8448, 0.6115, 0.0505, -0.1069, 0.0584, -0.0654);
			player25_7.setName("Level 25.7");
			runChallenge(player25_7);
			// -----------------------------------------
			// Bot closest to Level 25.8 (Actual: 25.8008)
			OptimizingChampion player25_8 = new OptimizingChampion(0.3163, 0.0000, 0.8521, 0.4710, 0.9486, 0.7159, 0.0440, -0.0418, 0.0265, -0.0020);
			player25_8.setName("Level 25.8");
			runChallenge(player25_8);
			// -----------------------------------------
			// Bot closest to Level 25.9 (Actual: 25.8999)
			OptimizingChampion player25_9 = new OptimizingChampion(0.3051, 0.0000, 0.8249, 0.5173, 0.9177, 0.7214, 0.0040, -0.0166, -0.0023, 0.0137);
			player25_9.setName("Level 25.9");
			runChallenge(player25_9);
			// -----------------------------------------
			// Bot closest to Level 26.0 (Actual: 26.0018)
			OptimizingChampion player26_0 = new OptimizingChampion(0.3069, 0.0000, 0.9188, 0.6062, 0.7993, 0.6612, 0.0265, 0.0173, 0.0274, 0.0312);
			player26_0.setName("Level 26.0");
			runChallenge(player26_0);
			// -----------------------------------------
			// Bot closest to Level 26.1 (Actual: 26.0706)
			OptimizingChampion player26_1 = new OptimizingChampion(0.2310, 0.0000, 0.8356, 0.5778, 0.8330, 0.6438, -0.0614, -0.1009, -0.0642, -0.0861);
			player26_1.setName("Level 26.1");
			runChallenge(player26_1);
			// -----------------------------------------
			
			
			
			

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
