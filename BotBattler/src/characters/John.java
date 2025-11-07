package characters;

import actions.Action;
import actions.Attack;
import actions.Block;
import actions.Direction;
import actions.MagicBlast;
import actions.MagicShield;
import game.Opponent;
import game.Resource;
import game.Threat;

//This example class represents a fighter who is very bad at blocking. 
//But they at least get a D- for compiling && not being copied from another student!!1
//
//The strategy is to block upwards if the total threat is greater 
//than 30, and to attack with a power of 1 otherwise. This fighter won't win many battles.
//You can do better!
//
public class John implements Character {

	// hitPoints resource. Gotta have one!
	private Resource hp;

	// will use stamina to perform Block and Attack actions
	private Resource stamina;

	public John() {
		// Bad blocker is bad, but not bad enough to use more than 200 points of total
		// resources!
		hp = new Resource("HP", 189);
		stamina = new Resource("Stamina", 11);

	}

	@Override
	// this will return an Attack action if the total threat is less than 30.
	// and will return a Block action in the UP direction otherwise.
	//
	// Bad blocker should look closer at Threat and learn to block.
	//
	// Both actions use the stamina resource.
	public Action takeTurn(Threat threatInfo, Opponent oppInfo) 
	{
		int upDam = threatInfo.getQuadrantThreat(1)+threatInfo.getQuadrantThreat(2);
		int leftDam = threatInfo.getQuadrantThreat(2)+threatInfo.getQuadrantThreat(3);
		int downDam = threatInfo.getQuadrantThreat(3)+threatInfo.getQuadrantThreat(4);
		int rightDam = threatInfo.getQuadrantThreat(1)+threatInfo.getQuadrantThreat(4);
		//int potentialMagicBlock = (int)(threatInfo.getTotalThreat()*0.25);
		/*
		if (oppInfo.getHitPoints()==30&&oppInfo.getPhysicalVulnerablility()>0)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==29&&oppInfo.getPhysicalVulnerablility()>1)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==28&&oppInfo.getPhysicalVulnerablility()>2)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==27&&oppInfo.getPhysicalVulnerablility()>3)
		{
			return new Attack(3, hp);
		}
		*/
		
		//THE following code is for win conditions where the player can win instantly with 1 turn, this skips checking for a block as it does not matter if the player character dies
		//10 HIGH 1 ATTACK
		if (oppInfo.getHitPoints()==14&&oppInfo.getPhysicalVulnerablility()==4)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==13&&oppInfo.getPhysicalVulnerablility()>=3)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==12&&oppInfo.getPhysicalVulnerablility()>=2)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==11&&oppInfo.getPhysicalVulnerablility()>=1)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==10&&oppInfo.getPhysicalVulnerablility()>=0)
		{
			return new Attack(1, hp);
		}
		//10 LOW 1 ATTACK
		if (oppInfo.getHitPoints()==9&&oppInfo.getPhysicalVulnerablility()>=-1)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==8&&oppInfo.getPhysicalVulnerablility()>=-2)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==7&&oppInfo.getPhysicalVulnerablility()>=-3)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==6)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==5)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==4)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==3)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==2)
		{
			return new Attack(1, hp);
		}
		if (oppInfo.getHitPoints()==1)
		{
			return new Attack(1, hp);
		}
		//20 HIGH 2 ATTACKS
		if (oppInfo.getHitPoints()==28&&oppInfo.getPhysicalVulnerablility()==4)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==27&&oppInfo.getPhysicalVulnerablility()==4)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==26&&oppInfo.getPhysicalVulnerablility()>=3)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==25&&oppInfo.getPhysicalVulnerablility()>=3)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==24&&oppInfo.getPhysicalVulnerablility()>=2)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==23&&oppInfo.getPhysicalVulnerablility()>=2)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==22&&oppInfo.getPhysicalVulnerablility()>=1)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==21&&oppInfo.getPhysicalVulnerablility()>=1)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==20&&oppInfo.getPhysicalVulnerablility()>=0)
		{
			return new Attack(2, hp);
		}
		//20 LOW 2 ATTACKS
		if (oppInfo.getHitPoints()==19&&oppInfo.getPhysicalVulnerablility()>=0)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==18&&oppInfo.getPhysicalVulnerablility()>=-1)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==17&&oppInfo.getPhysicalVulnerablility()>=-1)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==16&&oppInfo.getPhysicalVulnerablility()>=-2)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==15&&oppInfo.getPhysicalVulnerablility()>=-2)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==14&&oppInfo.getPhysicalVulnerablility()>=-3)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==13&&oppInfo.getPhysicalVulnerablility()>=-3)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==12)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==11)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==10)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==9)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==8)
		{
			return new Attack(2, hp);
		}
		if (oppInfo.getHitPoints()==7)
		{
			return new Attack(2, hp);
		}
		//30 HIGH 3 ATTACKS
		if (oppInfo.getHitPoints()==42&&oppInfo.getPhysicalVulnerablility()==4)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==41&&oppInfo.getPhysicalVulnerablility()==4)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==40&&oppInfo.getPhysicalVulnerablility()==4)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==39&&oppInfo.getPhysicalVulnerablility()>=3)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==38&&oppInfo.getPhysicalVulnerablility()>=3)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==37&&oppInfo.getPhysicalVulnerablility()>=3)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==36&&oppInfo.getPhysicalVulnerablility()>=2)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==35&&oppInfo.getPhysicalVulnerablility()>=2)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==34&&oppInfo.getPhysicalVulnerablility()>=2)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==33&&oppInfo.getPhysicalVulnerablility()>=1)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==32&&oppInfo.getPhysicalVulnerablility()>=1)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==31&&oppInfo.getPhysicalVulnerablility()>=1)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==30&&oppInfo.getPhysicalVulnerablility()>=0)
		{
			return new Attack(3, hp);
		}
		//30 LOW 3 ATTACKS
		if (oppInfo.getHitPoints()==29&&oppInfo.getPhysicalVulnerablility()>=0)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==28&&oppInfo.getPhysicalVulnerablility()>=0)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==27&&oppInfo.getPhysicalVulnerablility()>=-1)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==26&&oppInfo.getPhysicalVulnerablility()>=-1)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==25&&oppInfo.getPhysicalVulnerablility()>=-1)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==24&&oppInfo.getPhysicalVulnerablility()>=-2)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==23&&oppInfo.getPhysicalVulnerablility()>=-2)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==22&&oppInfo.getPhysicalVulnerablility()>=-2)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==21&&oppInfo.getPhysicalVulnerablility()>=-3)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==20&&oppInfo.getPhysicalVulnerablility()>=-3)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==19&&oppInfo.getPhysicalVulnerablility()>=-3)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==18&&oppInfo.getPhysicalVulnerablility()>=-4)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==17&&oppInfo.getPhysicalVulnerablility()>=-4)
		{
			return new Attack(3, hp);
		}
		if (oppInfo.getHitPoints()==16&&oppInfo.getPhysicalVulnerablility()>=-4)
		{
			return new Attack(3, hp);
		}
		//Checks if a block is a good idea
		if (upDam<=25 && leftDam<=25 &&  downDam <=25 && rightDam<=25)
			{
			if(oppInfo.getHitPoints()==100)
			{
				if(oppInfo.getPhysicalVulnerablility()>=4)
				{
					return new Attack(3, hp);
				}
				return new MagicBlast(hp);
			}
			if(oppInfo.getHitPoints()>60)
			{
				if(oppInfo.getPhysicalVulnerablility()>=4)
				{
					return new Attack(3, hp);
				}
				if(oppInfo.getMagicalVulnerablility()>0)
				{
					return new MagicBlast(hp);
				}
				if(oppInfo.getPhysicalVulnerablility()>=2)
				{
					return new Attack(2, hp);
				}
				return new Attack(2, hp);
				
			}
			return new Attack(2, hp);
			}
		Block ups = new Block(Direction.UP, stamina);
		Block lefts = new Block(Direction.LEFT, stamina);
		Block downs = new Block(Direction.DOWN, stamina);
		Block rights = new Block(Direction.RIGHT, stamina);
		Block whatsReturned = ups;
		int potentialMagicBlock = (int)(threatInfo.getTotalThreat()*0.125);
		if (potentialMagicBlock>=leftDam && upDam>=downDam && upDam>=rightDam && potentialMagicBlock>= upDam && potentialMagicBlock>24)
		{
			return new MagicShield(2, hp);
		}
		if (upDam>=leftDam && upDam>=downDam && upDam>=rightDam && upDam>25)
		{
			whatsReturned = ups;
		}
		if (leftDam>=upDam && leftDam>=downDam && leftDam>=rightDam && leftDam>25)
		{
			whatsReturned = lefts;
		}
		if (downDam>=upDam && downDam>=leftDam && downDam>=rightDam && downDam>25)
		{
			whatsReturned = downs;
		}
		if (rightDam>=upDam && rightDam>=leftDam && rightDam>=downDam && rightDam>25)
		{
			whatsReturned = rights;
		}
		return whatsReturned;

	}

	@Override
	// this method returns the Resource object to be used as hit points.
	// This is the resource that will be reduced by unblocked threat
	// Bad Blocker will die when he runs out of this resource.
	public Resource getHitPointResource() {
		return hp;
	}

	public Resource getStaminaResource() {
        return stamina;
    }
	
	@Override
	// This method returns The Goat's name
	public String toString() 
	{
		return "Student B";
	}

}