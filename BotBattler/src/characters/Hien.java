
package characters;
import actions.Action;
import actions.MagicShield;
import actions.MagicBlast;
import actions.Attack;
import actions.Block;
import actions.Direction;
import game.Opponent;
import game.Resource;
import game.Threat;

//by Hien Hoang
public class Hien implements Character {
	private Resource hp;
	private Resource stamina;
	
	public Hien() {
		hp = new Resource("HP", 193);
		stamina = new Resource("Stamina", 7);
	}
	//Basic strategy: Whenever the total threat is less than or equal to 25, the character will take the trade in order to find the opportunity to kill the opp.
	public Action takeTurn(Threat threatInfo, Opponent oppInfo) {
		//Variables for the direction of threat.
		int basicAttack = 20+(2*oppInfo.getPhysicalVulnerablility());
		int up1 = threatInfo.getQuadrantThreat(1) + threatInfo.getQuadrantThreat(2);
		int down1 = threatInfo.getQuadrantThreat(3) + threatInfo.getQuadrantThreat(4);
		int left1 = threatInfo.getQuadrantThreat(2) + threatInfo.getQuadrantThreat(3);
		int right1 = threatInfo.getQuadrantThreat(1) + threatInfo.getQuadrantThreat(4);
		Block up = new Block(Direction.UP, stamina);
		Block down = new Block(Direction.DOWN, stamina);
		Block left = new Block(Direction.LEFT, stamina);
		Block right = new Block(Direction.RIGHT, stamina);
		//Split the opp into 2 phases:
		//First phase is when the opp's hp is greater than or equal to 57 since the highest dmg we can dealt to the opp is 56.
		if(oppInfo.getHitPoints() >= 57) {
			//The character will always cast magic blast when the opp's hp is greater than 100
			//Since we will dealt at least 29 dmg to the opp with the cost of 10.
			if(oppInfo.getHitPoints() >= 90 && this.hp.getValue() > 100) {
				if(oppInfo.getMagicalVulnerablility() >= 3 && threatInfo.getTotalThreat() <= 50) {
					//In this case, the character will take the trade in order to get more opportunities to kill the opp.
					if(threatInfo.getTotalThreat() >= 35) {
						/*if(oppInfo.getMagicalVulnerablility() >= 3) {
							return new MagicBlast(hp);
						}*/
						if((int) Math.ceil(oppInfo.getHitPoints() / 3.0) + oppInfo.getMagicalVulnerablility() >= threatInfo.getTotalThreat()) {
							return new MagicBlast(hp);
						}
						return new MagicShield(3, hp);
					}
					return new MagicBlast(hp);
				}
				if(basicAttack < (int) Math.ceil(oppInfo.getHitPoints() / 3.0) + oppInfo.getMagicalVulnerablility() && threatInfo.getTotalThreat() <= 30) {
					return new MagicBlast(hp);
				}
				if(basicAttack >= (int) Math.ceil(oppInfo.getHitPoints() / 3.0) + oppInfo.getMagicalVulnerablility() && threatInfo.getTotalThreat() <= 30) {
					return new Attack(2,hp);
				}
			}
			if(oppInfo.getHitPoints() <= 80 && threatInfo.getTotalThreat() <= 30) {
				if(basicAttack < (int) Math.ceil(oppInfo.getHitPoints() / 3.0) + oppInfo.getMagicalVulnerablility()) {
					return new MagicBlast(hp);
				}
				else {
					return new Attack(2, hp);
				}
			}
			if(this.stamina.getValue()>0 && threatInfo.getTotalThreat() >= 40) {
				if(up1 >= 32+(oppInfo.getLevel()/10) && up1 >= down1 && up1 >= left1 && up1 >= right1) {
					return up;
				}
				if(down1 >= 32+(oppInfo.getLevel()/10) && down1 >= up1 && down1 >= left1 && down1>= right1) {
					return down;
				}
				if( left1 >= 32+(oppInfo.getLevel()/10) && left1 >= up1 && left1 >= right1 && left1 >= down1) {
					return left;
				}
				if(right1 >= 32+(oppInfo.getLevel()/10) && right1 >= left1 && right1 >= up1 && right1 >= down1) {
					return right;
				}
				
			}
			if((threatInfo.getTotalThreat() >= 75)) {
				return new MagicShield(3, hp);
			}
			
			
			/*if(oppInfo.getPhysicalVulnerablility() >= 3 && oppInfo.getHitPoints() < 80){
					return new Attack(2, hp);
				}
			if(oppInfo.getPhysicalVulnerablility() >= 0 && oppInfo.getHitPoints() <= 62) {
				return new Attack(2, hp);
			}*/
			if(basicAttack < (int) Math.ceil(oppInfo.getHitPoints() / 3.0) + oppInfo.getMagicalVulnerablility()) {
				return new MagicBlast(hp);
			}
			else {
				return new Attack(2,hp);
			}
		}
		//2nd Phase is when the opp is lower than 57 since now we can one shot them.
		else if(oppInfo.getHitPoints() < 57) {
			//One shot Strategy: If the opp's hp is lower than the num hit taken, then it'll return an attack.
			if(oppInfo.getHitPoints() <= 10+(1*oppInfo.getPhysicalVulnerablility()) && this.hp.getValue() >=1) {
				return new Attack(1, hp);
			}
			if(oppInfo.getHitPoints() <= 20+(2*oppInfo.getPhysicalVulnerablility()) && this.stamina.getValue() >= 7){
				return new Attack(2, stamina);
			}
			if(oppInfo.getHitPoints() <= 20+(2*oppInfo.getPhysicalVulnerablility()) && this.hp.getValue() >= 7){
				return new Attack(2, hp);
			}
			if(oppInfo.getHitPoints() <= 30+(3*oppInfo.getPhysicalVulnerablility()) && this.hp.getValue() >= 25) {
				return new Attack(3, hp);
			}
			if(oppInfo.getHitPoints() <= 40+(4*oppInfo.getPhysicalVulnerablility()) && this.hp.getValue() >= 79) {
				return new Attack(4, hp);
			}
			if(threatInfo.getTotalThreat() <= 25) {
				if(basicAttack < (int) Math.ceil(oppInfo.getHitPoints() / 3.0) + oppInfo.getMagicalVulnerablility()) {
					return new MagicBlast(hp);
				}
				else {
					return new Attack(2,hp);
				}
			}
			if((int) Math.ceil(oppInfo.getHitPoints() / 3.0) + oppInfo.getMagicalVulnerablility() >= threatInfo.getTotalThreat()) {
				if(this.hp.getValue() - threatInfo.getTotalThreat() - 10 >= 7) {
					return new MagicBlast(hp);
				}
			}
			if(this.hp.getValue() <= threatInfo.getTotalThreat()) {
				if(this.stamina.getValue()>0) {
					if(up1 >= 32+(oppInfo.getLevel()/10) && up1 >= down1 && up1 >= left1 && up1 >= right1) {
						return up;
					}
					if(down1 >= 32+(oppInfo.getLevel()/10) && down1 >= up1 && down1 >= left1 && down1>= right1) {
						return down;
					}
					if( left1 >= 32+(oppInfo.getLevel()/10) && left1 >= up1 && left1 >= right1 && left1 >= down1) {
						return left;
					}
					if(right1 >= 32+(oppInfo.getLevel()/10) && right1 >= left1 && right1 >= up1 && right1 >= down1) {
						return right;
					}
				}
				return new MagicShield(2, hp);
			}
			
			if(threatInfo.getTotalThreat() >= 39) {
				if(this.stamina.getValue()>0) {
					if(up1 >= 32+(oppInfo.getLevel()/10) && up1 >= down1 && up1 >= left1 && up1 >= right1) {
						return up;
					}
					if(down1 >= 32+(oppInfo.getLevel()/10) && down1 >= up1 && down1 >= left1 && down1>= right1) {
						return down;
					}
					if( left1 >= 32+(oppInfo.getLevel()/10) && left1 >= up1 && left1 >= right1 && left1 >= down1) {
						return left;
					}
					if(right1 >= 32+(oppInfo.getLevel()/10) && right1 >= left1 && right1 >= up1 && right1 >= down1) {
						return right;
					}
				}
				else {
					return new MagicShield(3,hp);
				}
			}
			
			else if((threatInfo.getTotalThreat() >= 75)) {
				return new MagicShield(3, hp);
			}
			if(basicAttack < (int) Math.ceil(oppInfo.getHitPoints() / 3.0) + oppInfo.getMagicalVulnerablility()) {
				return new MagicBlast(hp);
			}
			else {
					return new Attack(2, hp);
			}
		}
		if(this.hp.getValue() < 7) {
			return new Attack(1, hp);
		}
		if(basicAttack < (int) Math.ceil(oppInfo.getHitPoints() / 3.0) + oppInfo.getMagicalVulnerablility()) {
			return new MagicBlast(hp);
		}
		else {
			return new Attack(2,hp);
		}
	}
	
	public Resource getHitPointResource() {
		return hp;
	}

	@Override
	// This method returns XDXDC's name
	public String toString() {
		return "Hien XDDCC";
	}
	
	public Resource getStaminaResource() {
        return stamina;
    }
}
