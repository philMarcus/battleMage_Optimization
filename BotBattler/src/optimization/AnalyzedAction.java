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

	// Scale factors, determined in optimization phase zero.
	private static final double SF_COST = 79.0000;
	private static final double SF_RATIO_GAIN = 9373.0000;
	private static final double SF_RATIO_LOSS = 10500.0000;
	private static final double SF_PLAYER_HP_DELTA = 134.0000;
	private static final double SF_OPP_HP_DELTA = 52.0000;


	public AnalyzedAction(Action a, int pi, int oi, int pf, int of) {
		this.a = a;
		this.pi = pi;
		this.oi = oi;
		this.pf = pf;
		this.of = of;

	}

	int getScore() {
		// equivalent to the change in ratio of your to opp's HP as a result
		// of this action. Want to maximize.
		return pf * oi - pi * of;
	}

	// calculate heuristic score with parametric weights
	double getScore(double w_cost, double w_ratioGain,
			double w_ratioLoss, double w_playerHPdelta, double w_oppHPdelta,
			double w_attackBias, double w_blockBias, double w_blastBias,
			double w_shieldBias /*,String turnData*/) {  //in phase zero, we needed to log turnData

		// 1. Feature calculation
		double f_cost = a.getCost();
		double f_ratioGain = (double) pf * oi;
		double f_ratioLoss = (double) pi * of;
		double f_playerHPdelta = (double) pf - pi;
		double f_oppHPdelta = (double) of - oi;

		// --- 2. Scaled Feature (sc_) Calculation ---
		double sc_cost = f_cost / SF_COST;
		double sc_ratioGain = f_ratioGain / SF_RATIO_GAIN;
		double sc_ratioLoss = f_ratioLoss / SF_RATIO_LOSS;
		double sc_playerHPdelta = f_playerHPdelta / SF_PLAYER_HP_DELTA;
		double sc_oppHPdelta = f_oppHPdelta / SF_OPP_HP_DELTA;

		// 3. Calculate Score
		double score = (w_playerHPdelta * sc_playerHPdelta)
				- (w_oppHPdelta * sc_oppHPdelta) // a negative delta should
																								// increase score
				+ (w_ratioGain * sc_ratioGain)
				- (w_ratioLoss * sc_ratioLoss)
				- (w_cost * sc_cost); // cost is a penalty
		// add the correct action bias to the score // so subtract
		
		switch (a.getName()) {
		case "Block":
			score += w_blockBias;
			break;
		case "Attack":
			score += w_attackBias;
			break;
		case "Blast":
			score += w_blastBias;
			break;
		case "Shield":
			score += w_shieldBias;
			break;
		}

		//in phase zero, we logged every action analysis to determine scale factors
//		if (Optimizer_Phase_Zero.turnActionLogWriter != null) {
//		    Optimizer_Phase_Zero.turnActionLogWriter.println(turnData+ 
//		    		f_cost +","+f_ratioGain+","+f_ratioLoss+","+f_playerHPdelta+
//		    		","+f_oppHPdelta+","+a.getName()+","+a.getDetail()+": "+a.getResource().getName());
//		}
		
		return score;

	}

	public Action getA() {
		return a;
	}

}