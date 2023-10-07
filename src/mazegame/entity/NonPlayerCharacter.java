package mazegame.entity;

public class NonPlayerCharacter extends Character {
	private Boolean hostile;
	private Armor equippedArmor; // Add a field to store equipped armor


    public NonPlayerCharacter(String name, int agility, int lifePoints, int strength, boolean hostile)
    {
    	super(name);
        setAgility(agility);
        setLifePoints(lifePoints);
        setStrength(strength);
        this.hostile = hostile;
        equippedArmor = null; // Initialize equippedArmor to null initially
    }


    public boolean getHostile () {
    	return this.hostile;
    }
    
    public void setHostile (boolean hostile) {
    	this.hostile = hostile;
    }
    
    public int getStrength() {
        return super.getStrength(); // Use the inherited strength attribute from the base Character class
    }
    
   // Get the NPC's equipped armor
    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    // Allow the NPC to take damage
    public void takeDamage(int damage) {
        // Reduce the NPC's life points by the damage amount
        int currentLifePoints = getLifePoints();
        currentLifePoints -= damage;

        // Ensure that life points do not go below 0
        if (currentLifePoints < 0) {
            currentLifePoints = 0;
        }

        // Update the NPC's life points
        setLifePoints(currentLifePoints);
    }
    
    // Add a flee() method to allow the NPC to attempt to flee
    public boolean flee() {
    	// Implement logic for NPC fleeing behavior
        // You can use random chance (e.g., a random number generator)
        // to determine if the NPC successfully escapes.
        // Adjust the escapeChance value based on your game's balance.
        double escapeChance = 0.2; // 20% chance of successfully escaping for NPCs
        boolean successfullyEscaped = Math.random() < escapeChance;

        if (successfullyEscaped) {
            // NPC successfully fled
            System.out.println(getName() + " fled from battle!");
        } else {
            // NPC failed to flee
            System.out.println(getName() + " failed to flee and remains in battle!");
        }
        return successfullyEscaped;
    }
}
