package actions;

import game.Opponent;
import game.Resource;
import game.Threat;

//This class represents a magical blast against the opponent.
//
//The player can construct and return a MagicBlast object in their takeTurn() method
//
//The MagicBlast class *IS AN* Action class because it implements the Action interface.
public class MagicBlast implements Action {
	private Resource res; // the resource used to pay the cost of the action
	private int damage; // the amount of damage this blast does
	private int baseCost = 10; // the base resource cost of performing the blast
	private int cost; //the final adjusted cost of the blast

	// keeps count of all magic blasts created. Static because it refers to the same
	// number across
	// ALL magic blasts. It's not attached to an instance of magic blast, like the
	// other attributes.
	private static int used;

	public MagicBlast(Resource r) {
		res = r;
		cost = baseCost-used;	// They get 1 cheaper after each use!!!!
	}

	@Override

	//
	// returns true if cost is payable and paid.
	public boolean payCost() {

		return res.pay(cost);
	}

	@Override
	// Magic Blast blasts away a third of opponent's remaining hit points! (Plus or
	// minus damage from magical vulnerability!)
	public void resolve(Threat t, Opponent o) {
		// use math.ceil to always round up the damage to the nearest integer
		// if the opponent has 1 hp, magic blast should finish them off!
		damage = (int) Math.ceil(o.getHitPoints() / 3.0) + o.getMagicalVulnerablility();
		o.takeDamage(damage);

		// increment the static count of used Magic Blasts
		used++;
	}

	@Override
	public Resource getResource() {
		return res;
	}

	// when we start a new battle, we'll want to reset the used counter.
	// this is a public static method that will do that.
	public static void resetUses() {
		used = 0;
	}

	@Override
	public String toString() {
		return "casts Magic Blast for " + damage + " damage, at a cost of " + cost + ". " + res.toString();
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public String getName() {
	
		return "Blast";
	}

	@Override
	public String getDetail() {
		//magic blast has no detail parameteres
		return null;
	}

}
