package mazegame.entity;

public abstract class Item {
	
	private String label;
	private int value;
	private double weight;
	private String description;
	private boolean equippable; // Added field to determine if the item is equippable
    private EquipmentSlot equipmentSlot; // Added field to specify the equipment slot
	
    public Item (String label, int value, double weight, String description)
	{
		this.label = label;
		this.value = value;
		this.weight = weight;
		this.description = description;
	}

	public String getLabel() {

		return label;
	}
	
	public int getValue() 
	{
		return value;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public String getDescription()
	{
		return description;
	}

	public boolean isEquippable() {
        return equippable;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }
	
    
}


