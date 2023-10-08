package mazegame.entity;

public class Character {
	private int agility;
    private int lifePoints;
    private String name;
    private int strength;
    
//    public Mazegame.Entity.Dice m_Dice;
//    public Mazegame.Entity.Party m_Party;
//    public Mazegame.Entity.Item m_Item;
//    public Mazegame.Entity.Shield m_Shield;
//    public Mazegame.Entity.Weapon m_Weapon;
//    public Mazegame.Entity.Armor m_Armor;
    
    public Character()
    {
    }

    public Character(String name)
    {
        this.setName(name);
    }

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public String talkToCharacter(Character character) {
        // Implement conversation logic here
        return "Hello, " + character.getName() + "! How are you?";
    }
    
    // Add a method to respond to conversations from other characters
    public String respondToCharacter(String message) {
        // Implement your character's response logic here
        return "I'm doing well, thank you!";
    }
}
