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
// Define a class named "John" implementing the "Character" interface
public class Adam implements Character {
   // Declare private member variables for hit points and stamina
   private Resource hp;
   private Resource stamina;
   // Constructor for initializing hit points and stamina resources
   public Adam() {
       hp = new Resource("HP", 190);
       stamina = new Resource("Stamina", 10);
   }
   public Action takeTurn(Threat threatInfo, Opponent oppInfo) {
       int opponentHP = oppInfo.getHitPoints();
       int MBlock = threatInfo.getTotalThreat() / 2;
       int topThreat = threatInfo.getQuadrantThreat(1) + threatInfo.getQuadrantThreat(2);
       int bottomThreat = threatInfo.getQuadrantThreat(3) + threatInfo.getQuadrantThreat(4);
       int leftThreat = threatInfo.getQuadrantThreat(2) + threatInfo.getQuadrantThreat(3);
       int rightThreat = threatInfo.getQuadrantThreat(1) + threatInfo.getQuadrantThreat(4);
       Direction blockDirection = Direction.UP;
       // Fine-tuning thresholds and formulas
       int magicDamage = (int) (opponentHP * 0.25); // Adjusted magic damage threshold
       int physicalVulnerability = oppInfo.getPhysicalVulnerablility();
       // Refine physical attack conditions
       int move = 0;
       if (topThreat < 20 && bottomThreat < 20 && leftThreat < 20 && rightThreat < 20) {
           if (opponentHP == 100 || (opponentHP == 66 || move == 2 || move == 4 || move == 6 && physicalVulnerability >= 4)) {
               move += 2;
               if (move == 2) {
                   if (magicDamage < (physicalVulnerability + 4)) {
                       return new Attack(2, hp);
                   }
               }
               if (move == 4 || move == 6) {
                   return new Attack(2, hp);
               }
           }
           if (opponentHP <= 30 || physicalVulnerability >= 3) {
               return new Attack(2, hp);
           }
           if (oppInfo.getMagicalVulnerablility() <= 4) {
               if (opponentHP >= 60) {
                   return new MagicBlast(hp);
               } else {
                   return new Attack(2, hp);
               }
           }
           if (opponentHP <= 60) {
               return new Attack(2, hp);
           }
           if (opponentHP >= 60) {
               return new MagicBlast(hp);
           }
       }
       // Improve defensive strategy
       if (MBlock >= Math.max(rightThreat, Math.max(topThreat, Math.max(bottomThreat, leftThreat))) && MBlock >= 20) {
           return new MagicShield(2, stamina);
       }
       // Determine the direction to block based on the highest threat
       if (leftThreat >= rightThreat && leftThreat >= bottomThreat && leftThreat >= topThreat && leftThreat >= 20) {
           blockDirection = Direction.LEFT;
       }
       if (rightThreat >= leftThreat && rightThreat >= bottomThreat && rightThreat >= topThreat && rightThreat >= 20) {
           blockDirection = Direction.RIGHT;
       }
       if (bottomThreat >= rightThreat && bottomThreat >= leftThreat && bottomThreat >= topThreat && bottomThreat >= 20) {
           blockDirection = Direction.DOWN;
       }
       if (topThreat >= rightThreat && topThreat >= leftThreat && topThreat >= bottomThreat && topThreat >= 20) {
           blockDirection = Direction.UP;
       }
       // Prioritize special strategies based on opponent behavior
       if (oppInfo.getMagicalVulnerablility() >= 4) {
           return new MagicBlast(hp); // Use magic attack against opponents with high magical vulnerability
       }
       if (opponentHP <= 20) {
           return new Attack(2, hp); // Prioritize finishing off opponents with low hit points
       }
       // Exploit opponent weaknesses based on vulnerabilities
       if (oppInfo.getPhysicalVulnerablility() >= 4) {
           return new Attack(2, hp); // Prioritize physical attacks against opponents with high physical vulnerability
       }
       // Dynamic decision-making based on current game state
       if (hp.getValue() <= 10 && opponentHP > 10) {
           return new Block(blockDirection, stamina); // Prioritize defense when character's health is critically low
       }
      // Combo attacks for increased effectiveness
       if (hp.getValue() >= 80 && stamina.getValue() >= 3 && topThreat >= 10) {
           // Perform a sequence of actions: Attack, MagicBlast, and then Block
           new Attack(1, hp).resolve(threatInfo, oppInfo);
           new MagicBlast(hp).resolve(threatInfo, oppInfo);
           new Block(blockDirection, stamina).resolve(threatInfo, oppInfo);
           // Return any other action as needed after the combo
           return new Attack(2, hp);
       }
       // Adaptive defense considering the type and intensity of threats
       if (opponentHP >= 80 && stamina.getValue() >= 3 && topThreat >= 10) {
           return new MagicShield(2, stamina); // Activate magic shield if opponent's health is high, sufficient stamina is available, and top threat is significant
       }
       // Cooldown management to ensure availability of crucial abilities
       int cooldowns = 1000000;
       cooldowns --;
      
       if ((cooldowns % 2) == 0 && opponentHP <= 30) {
           return new MagicBlast(hp); // Use MagicBlast if it's off cooldown and opponent's health is low
       }
      
       // Default action: Return the action to block in the determined direction
       return new Block(blockDirection, stamina);
   }
   // Method to predict opponent's action
  
   // Method to get the hit point resource
   public Resource getHitPointResource() {
       return hp;
   }
   
   public Resource getStaminaResource() {
       return stamina;
   }
   // Method to provide a string representation of the object
   public String toString() {
       return "Adam";
   }
  
}




