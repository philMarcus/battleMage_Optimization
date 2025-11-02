package game;

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
			OptimizingChampion player = new OptimizingChampion(0.4102,0.3672,0.5928,0.1494,0.8963,0.3854,0.6099,0.5082,0.6125,-0.3737);
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
