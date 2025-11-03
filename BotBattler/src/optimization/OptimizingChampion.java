package optimization;

import java.util.ArrayList;

import actions.Action;
import actions.Attack;
import actions.Block;
import actions.Direction;
import actions.MagicBlast;
import actions.MagicShield;
import characters.Character;
import game.Opponent;
import game.Resource;
import game.Threat;

public class OptimizingChampion implements Character {

	// We have 10 strategic parameters that we'll optimize.
	double w_cost;
	double w_ratioGain;
	double w_ratioLoss;
	double w_playerHPdelta;
	double w_oppHPdelta;
	double w_attackBias;
	double w_blockBias;
	double w_blastBias;
	double w_shieldBias;
	double w_alloc; // how many hp to allocate to stamina

	// We plan to use HP for all actions except blocks.
	private Resource hp;
	private Resource stamina;

	int blockableDmg; // the largest amount of dmg that could be blocked

	// We'll track how many blasts we've used so that we can determine if we can
	// afford them
	int usedMagicBlasts = 0;

	// track actions used for statistical purposes
	static int totalBlasts;
	static int totalBlocks;
	static int totalShields;
	static int totalAttacks;
	static int totalDefaults;
	static boolean print = false; // whether to print action totals for statistical purposes

	// our list of possible actions, with their outcome on our and opponent's HP
	ArrayList<AnalyzedAction> actions;

	String battleId; // hold the uniqur battle ID, for data logging
	int turnNum; //turn number - for data logging
	
	String turnData; //turn-level data for logging 

	public OptimizingChampion(double w_alloc, double w_cost, double w_ratioGain, double w_ratioLoss,
			double w_playerHPdelta, double w_oppHPdelta, double w_attackBias, double w_blockBias, double w_blastBias,
			double w_shieldBias) {
		
		final int maxStaminaAllocation = 20;

		this.w_cost = w_cost;
		this.w_ratioGain = w_ratioGain;
		this.w_ratioLoss = w_ratioLoss;
		this.w_playerHPdelta = w_playerHPdelta;
		this.w_oppHPdelta = w_oppHPdelta;
		this.w_attackBias = w_attackBias;
		this.w_blockBias = w_blockBias;
		this.w_blastBias = w_blastBias;
		this.w_shieldBias = w_shieldBias;
		this.w_alloc = w_alloc;

		hp = new Resource("HP", 200 - (int) Math.round(w_alloc*maxStaminaAllocation));
		stamina = new Resource("Stamina", (int) Math.round(w_alloc*maxStaminaAllocation));
		
		turnNum=0; //set turn counter

	}

	@Override
	public Action takeTurn(Threat threatInfo, Opponent oppInfo) {
		turnNum++; //increment turn counter
		
		// calculate calculable variables for analysis,
		// the things we want to know to make a good decision.

		// get the best direction to block, also sets blockableDmg;
		Direction dir = chooseDir(threatInfo);
		int threat = threatInfo.getTotalThreat();
		int unblockableDmg = threat - blockableDmg;

		int myHP = hp.getValue();
		int oppHP = oppInfo.getHitPoints(); // opponent hp

		int dmgPerHit = 10 + oppInfo.getPhysicalVulnerablility();
		int dmgPerBlast = (int) Math.ceil(oppHP / 3.0) + oppInfo.getMagicalVulnerablility();

		actions = new ArrayList<AnalyzedAction>();
		// add block analysis
		if (canBlock(stamina, 0)) {
			AnalyzedAction aa = new AnalyzedAction(new Block(dir, stamina), myHP, oppHP, myHP - unblockableDmg, oppHP);

			actions.add(aa);
		}
		// there's a slight possiblity to want to block with HP
		if (canBlock(hp, 1)) {
			AnalyzedAction aa = new AnalyzedAction(new Block(dir, hp), myHP, oppHP, myHP - unblockableDmg, oppHP);

			actions.add(aa);
		}

		// add shield analysis
		for (int i = 1; i <= affordShield(hp, 1); i++) {
			int unShieldedDmg = (int) (threatInfo.getTotalThreat() * Math.pow(0.5, i));
			AnalyzedAction aa = new AnalyzedAction(new MagicShield(i, hp), myHP, oppHP,
					myHP - unShieldedDmg - (int) Math.pow(2, i), oppHP);

			actions.add(aa);
		}

		// add attack analysis
		for (int i = 1; i <= affordAttack(hp, 0); i++) {
			AnalyzedAction aa = new AnalyzedAction(new Attack(i, hp), myHP, oppHP,
					myHP - threat - (int) Math.pow(3, i) + 2, oppHP - i * dmgPerHit);

			// check for death blow & return this action if will kill the opponent
			if (oppHP - i * dmgPerHit <= 0)
				return aa.getA();

			// add to list only if we can survive it.
			if (i <= affordAttack(hp, 1))
				actions.add(aa);
		}
		
		// add attack analysis (STAMINA)
				for (int i = 1; i <= affordAttack(stamina, 0); i++) {
					// Player HP is only reduced by the threat, not the action cost
					AnalyzedAction aa = new AnalyzedAction(new Attack(i, stamina), myHP, oppHP,
							myHP - threat, oppHP - i * dmgPerHit);

					// check for death blow & return this action if will kill the opponent
					if (oppHP - i * dmgPerHit <= 0)
						return aa.getA();

					// add to list only if we can survive the threat
					// (Stamina cost doesn't affect HP)
					if (myHP - threat > 0) 
						actions.add(aa);
				}

		// add blast analysis
		if (canAffordBlast(hp, 0)) {
			AnalyzedAction aa = new AnalyzedAction(new MagicBlast(hp), myHP, oppHP,
					myHP - threat - 10 + usedMagicBlasts, oppHP - dmgPerBlast);

			// check for death blow & return this action if will kill the opponent
			if (oppHP - dmgPerBlast <= 0)
				return aa.getA();
			// add to list only if we can survive it.
			if (canAffordBlast(hp, 1))
				actions.add(aa);
		}
		
		//create string for turn level data
		turnData = battleId +","+ oppInfo.getLevel()+","+turnNum+","+hp.getValue()
		+ "," + oppInfo.getHitPoints()+","+threatInfo.getQuadrantThreat(1)+","+
		threatInfo.getQuadrantThreat(2)+","+threatInfo.getQuadrantThreat(3)+","+
		threatInfo.getQuadrantThreat(4)+","+(int) Math.round(w_alloc)+",";
		
		// *****find the highest-scoring action in the list*******
		int maxIndex = 0;
		double maxScore = -1000000000;

		for (AnalyzedAction a : actions) {
			double score = a.getScore(w_cost, w_ratioGain, w_ratioLoss, w_playerHPdelta, w_oppHPdelta, w_attackBias,
					w_blockBias, w_blastBias, w_shieldBias/*,turnData*/); //turnData for phase zero
			if (score > maxScore) {
				maxIndex = actions.indexOf(a);
				maxScore = score;
			}
		}

		if (actions.size() > 0) {
			if (actions.get(maxIndex).getA().getName() == "Blast") {
				usedMagicBlasts++;
				totalBlasts++;
			}

			if (actions.get(maxIndex).getA().getName() == "Shield")
				totalShields++;

			if (actions.get(maxIndex).getA().getName() == "Block")
				totalBlocks++;

			if (actions.get(maxIndex).getA().getName() == "Attack")
				totalAttacks++;
			if (print) {
				System.out.println("\nBlasts: " + totalBlasts + "\nShields: " + totalShields + "\nAttacks: "
						+ totalAttacks + "\nBlocks: " + totalBlocks + "\nDefaults: " + totalDefaults);
				System.out.println("\nTotal Actions: "
						+ (totalBlasts + totalBlocks + totalShields + totalAttacks + totalDefaults));
			}
			return actions.get(maxIndex).getA();
		}

		totalDefaults++;

		return new Attack(1, hp);

	}

	// calculate the largest attack power that will keep
	// a minimum remaining value of the resource
	public static int affordAttack(Resource r, int minValue) {
		// increases n until
		for (int n = 0; n < 6; n++) {
			// n is not affordable
			if ((r.getValue() - minValue) < Math.pow(3, n) - 2)
				// and returns the last affordable n
				return n - 1;
		}
		return 0;
	}

	// calculate the largest shield power that will keep
	// a minimum remaining value of the resource
	public static int affordShield(Resource r, int minValue) {
		// increases n until
		for (int n = 0; n < 9; n++) {
			// n is not affordable
			if ((r.getValue() - minValue) < Math.pow(2, n))
				// and returns the last affordable n
				return n - 1;
		}
		return 0;
	}

	// determine if the Magic Blast is can be afforded with a minimum
	// value of the resource remaining.
	public boolean canAffordBlast(Resource r, int minValue) {
		return (r.getValue() - minValue >= 10 - usedMagicBlasts);
	}

	// determines if we can block and have a minimum value of the resource remaining
	public static boolean canBlock(Resource r, int minValue) {
		// must have 20 or less of the resource as well as being able to afford the cost
		// of 1.
		return ((r.getValue() <= 20) && (r.getValue() - minValue >= 1));
	}

	// returns the best direction to block and sets the blockableDmg attribute
	private Direction chooseDir(Threat t) {
		int q1 = t.getQuadrantThreat(1);
		int q2 = t.getQuadrantThreat(2);
		int q3 = t.getQuadrantThreat(3);
		int q4 = t.getQuadrantThreat(4);

		int[] ts = { q1, q2, q3, q4 };
		int maxIndex = 0;
		// find biggest quadrantial threat
		for (int i = 1; i < 4; i++) {
			if (ts[i] > ts[maxIndex])
				maxIndex = i;
		}
		blockableDmg = ts[maxIndex];
		int maxQ = maxIndex + 1;
		switch (maxQ) {
		case 1:
			if (q2 > q4) {
				blockableDmg += ts[1];
				return Direction.UP;
			} else {
				blockableDmg += ts[3];
				return Direction.RIGHT;
			}
		case 2:
			if (q3 > q1) {
				blockableDmg += ts[2];
				return Direction.LEFT;
			} else {
				blockableDmg += ts[0];
				return Direction.UP;
			}
		case 3:
			if (q2 > q4) {
				blockableDmg += ts[1];
				return Direction.LEFT;
			} else {
				blockableDmg += ts[3];
				return Direction.DOWN;
			}
		default:
			if (q3 > q1) {
				blockableDmg += ts[2];
				return Direction.DOWN;
			} else {
				blockableDmg += ts[0];
				return Direction.RIGHT;
			}
		}

	}

	@Override
	public Resource getHitPointResource() {

		return hp;
	}

	@Override
	public String toString() {
		return "Optimizing Champion Phase Zero";
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}


}
