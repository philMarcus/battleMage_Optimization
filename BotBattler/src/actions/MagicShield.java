package actions;

import game.Opponent;
import game.Resource;
import game.Threat;

//This class represents a magical shield that absorbs the opponent's threat.
//Each level of shield absorbs half of the remaining threat.
//
//The player can construct and return a MagicShield object in their takeTurn() method
//
//The MagicShield class *IS AN* Action class because it implements the Action interface.
public class MagicShield implements Action {

	private Resource res;// The resource used to pay for the attack
	private int power; // how many times the shield removes half of remaining threat
	private int reducedDmg; // the amount of threat this shield actually blocked
	private int cost; // the resource cost of this shield

	public MagicShield(int pow, Resource r) {
		power = pow;
		res = r;
		cost = (int) Math.pow(2, power);
	}

	@Override
	// Magic Shields cost 2^power, where power is the number of times the player
	// gets
	// "shielded"
	// power 1 costs 2
	// power 2 costs 4
	// power 3 costs 8
	// power 4 costs 16
	// returns true if cost is payable and paid.
	public boolean payCost() {

		return res.pay(cost);
	}

	@Override
	// Magic Shield reduces all remaining threat by half, once for each unit of
	// power.
	// That's:
	// power 1 cuts threat to 50%
	// power 2 cuts threat to 25%
	// power 3 cuts threat to 12.5%
	// power 4 cuts threat to 6.25%
	//
	// "reducedDmg" attribute is set to the amount of damage the shield blocked
	public void resolve(Threat t, Opponent o) {

		int[] allQuadrants = { 1, 2, 3, 4 }; // Magic Shield acts on threat from all quadrants
		reducedDmg = t.reduceThreat(allQuadrants, Math.pow(0.5, power));

	}

	public Resource getResource() {
		return res;
	}

	public String toString() {
		return "casts a level " + power + " Magic Shield, reducing threat by " + reducedDmg + ", at a cost of " + cost
				+ ". " + res.toString();
	}

	@Override
	public int getCost() {

		return cost;
	}

}
