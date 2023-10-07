package mazegame.entity;

public class Armor extends Item {
	private int bonus;	

	public Armor (String label, int value, double weight, String description, int bonus) {
		super (label, value, weight, description);
		this.bonus = bonus;
	}
	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
}