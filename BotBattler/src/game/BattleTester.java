package game;





import characters.BattleMageChampion;

//The game of battleMage is played by writing a Java class representing a Player Character
//that can analyze an incoming threat and make a decision about what action to take.
//
//Designed for Revere High School AP CS A students who have completed through Unit 5 (writing classes)
//
//WHO WILL WRITE THE STRONGEST BATTLEMAGE?!?!?
//
//by Marcus
//
//This class tests a character in a battle.
public class BattleTester {

	public static void main(String[] args) {

		// create a player of the DumbFighter class
		// ******************!!!************************
		// Replace DumbFighter with your class to play!!!
		// ******************!!!************************
		BattleMageChampion player = new BattleMageChampion();

		// create a new battle object feat. player
		Battle battle = new Battle(player,1);

		// do the battle, printing turn logs, and waiting for the user to hit Enter
		// between turns
		battle.doBattle(true, true);

	}

}
