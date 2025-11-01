package optimization;

import actions.Action;

//this class stores an action, along with values to calculate it's
//delta-ratio
public class AnalyzedAction {

	Action a;
	int pi; // player's current hp
	int oi; // opponent's current hp
	int pf; // players hp after this action
	int of; // opponent's hp after this action
	int resCost; //resource cost of this action

	public boolean isBlast; // flag for blast to enable counter

	// keep track of total uses for (statistical purposes only)
	public boolean isBlock;
	public boolean isAttack;
	public boolean isShield;

	public AnalyzedAction(Action a, int pi, int oi, int pf, int of) {
		this.a = a;
		this.pi = pi;
		this.oi = oi;
		this.pf = pf;
		this.of = of;
		this.resCost = a.getCost();
	}

	int getScore() {
		// equivalent to the change in ratio of your to opp's HP as a result
		// of this action. Want to maximize.
		return pf * oi - pi * of;
	}

	public Action getA() {
		return a;
	}

}