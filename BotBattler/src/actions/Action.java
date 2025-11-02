package actions;

import game.Opponent;
import game.Resource;
import game.Threat;

public interface Action {

	// updates resource by decreasing cost of action,
	// returns true if cost is payable and paid.
	public boolean payCost();

	// do the action by having some effect on the opponent and/or threat
	public void resolve(Threat t, Opponent o);

	// return the resource paying for the action
	public Resource getResource();
	
	//return the cost of this action, in resources
	public int getCost();
	
	//return the simple name of the action
	public String getName();
	
	//return a string uniquely identifying any action sub-type
	public String getDetail();

}
