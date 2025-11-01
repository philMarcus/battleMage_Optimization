package game;

import characters.BadBlocker;
import characters.DumbFighter;
import characters.GoodBlocker;
import characters.SadMage;
import optimization.BattleMageChampion;
import characters.Blaster;

public class ArenaTester {
	private static int wins;
	private static int num = 1000000;

	public static void main(String[] args) {
		for (int i = 0; i < num; i++) {
			BattleMageChampion player = new BattleMageChampion();
			Battle b = new Battle(player,1);
			if (b.doBattle(false, false)) {
				wins++;
				System.out.println(player.toString() + " gets win " + wins + " in game " + (i + 1) + "  "
						+ ((double) wins / (i + 1)));
			}
		}
		System.out.println("Final score:" + wins + "/" + num + "  " + ((double) wins / num));

	}
}
