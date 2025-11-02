package actions;

import game.Opponent;
import game.Resource;
import game.Threat;


//This class represents a physical attack against the opponent.
//
//The player can construct and return an Attack object in their takeTurn() method
//
//The Attack class *IS AN* Action class because it implements the Action interface.
public class Attack implements Action {
	private int numHits; // how many hits the attack will make
	private Resource res; // The resource used to pay for the attack

	private int damage = 10; // attacks do 10 base damage

	private int cost; // the resource cost of performing the attack

	public Attack(int n, Resource r) {
		numHits = n;
		res = r;
		cost = (int) Math.pow(3, numHits) - 2;
	}

	@Override
	// Attacks cost (3^n-2) of any resource, where n is the number of hits
	// That's:
	// cost 1 for 1 hit!
	// cost 7 for 2 hits!!
	// cost 25 for 3 hits!!!
	// returns true if cost is payable and paid.
	public boolean payCost() {
		
		return res.pay(cost);
	}

	@Override
	// each hit in an attack damages for 10 plus the opponent's physical
	// vulnerability
	public void resolve(Threat t, Opponent o) {
		
		//calculate the damage, accounting for the vulnerability and the number of hits
		damage = numHits * (damage + o.getPhysicalVulnerablility());
		
		//call the opponent's takeDamage method!
		o.takeDamage(damage);
	}

	public int getNumHits() {
		return numHits;
	}

	public Resource getResource() {
		return res;
	}

	public int getDamage() {
		return damage;
	}

	public String toString() {
		return "hits for " + damage + " damage, at a cost of " + cost + ". " + res.toString();
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public String getName() {
		
		return "Attack";
	}

	@Override
	public String getDetail() {
		
		return numHits + "-HIT";
	}

}
