package optimization;

import game.Battle;

public class Optimizer_Phase_Zero {

	private static int num = 10000;

	// runs battles with increasing opponent level until player dies
	// returns the level on which the player died
	public static int runChallenge() {
		int level = 1;
		boolean isOver = false;
		while (!isOver) {
			//Use best-guess default values for parameters 
			//in order to generate feature scaling data
			OptimizingChampion player = new OptimizingChampion(5,0.5, 3, 3, 0.5,
					0.5, 0, 0, 0, 0);
			//BattleMageChampion player = new BattleMageChampion();
			Battle b = new Battle(player, level);
			if (b.doBattle(false, false)) //battles return "true" when won.
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
