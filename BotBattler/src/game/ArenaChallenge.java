package game;

import characters.BadBlocker;
import characters.DumbFighter;
import characters.GoodBlocker;
import characters.SadMage;
import optimization.BattleMageChampion;
import characters.Blaster;

public class ArenaChallenge {

	private static int num = 1000;

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
		int totalLevels = 0;
		
		for (int i = 0; i < num; i++) {
			int lev = runChallenge();
			totalLevels += lev;
			System.out.println("Died on level "+lev+"\n");

		}
		System.out.println("Average Level: "+(double)totalLevels/num);
	}
}
