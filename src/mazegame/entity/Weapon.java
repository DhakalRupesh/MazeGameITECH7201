package mazegame.entity;

public class Weapon extends Item {
	
	public Dice m_Dice;

    public Weapon(String label, int value, double weight, String description) {
         super (label, value, weight, description);
    }
}
