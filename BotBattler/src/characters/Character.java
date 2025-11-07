package characters;

import actions.Action;
import game.Opponent;
import game.Resource;
import game.Threat;

// The game of battleMage is played by writing a Java class to
// implement this character interface and then constructing an
// instance of your character in the BattleTester class to see how many increasingly
// tough opponents it can survive.
//
// WHO WILL WRITE THE STRONGEST BATTLEMAGE?!?!?
//
//You don't need to have "learned" interfaces.
//Just make sure of two things:
//
// 1) your class header has "implements Character"
// e.g. "public class YourClass implements Character{..."
// and
// 2) Your class must have the methods listed below, using the signatures exactly
// as written here.
//
// Everything else is up to you!
//
// by Marcus
public interface Character {

	// This method is your character's decision-making process. Using the passed
	// information about the
	// opponent and the current threat, decide which action to take.
	// Construct and return your chosen action!
	//
	// Examine the Threat and Opponent classes to see which public methods you can call.
	//
	// Examine the action classes (Attack, Block, MagicBlast, MagicShield) to see
	// how they work and how to construct them
	//
	// Remember, each action has a resource cost. You can initialize (in your
	// constructor) as many different resources as you
	// like, but the rule of the game is if you try to use resources that add up to
	// more than 200 in total, your character will be disqualified!
	public Action takeTurn(Threat threatInfo, Opponent oppInfo);

	// This method is where you declare which resource represents your character's
	// hit Points
	// This is the resource that will be reduced by unblocked threat
	// When this resource runs out, your character loses.
	public Resource getHitPointResource();
	
	public Resource getStaminaResource();

	// This method just returns your charcter's name
	public String toString();

}
