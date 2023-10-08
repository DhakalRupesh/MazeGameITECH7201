package mazegame.entity;

public abstract class Item {
	
	private String label;
	private int value;
	private double weight;
	private String description;
	private String name;
	private String type;

	public Item (String label, int value, double weight, String description)
	{
		this.label = label;
		this.value = value;
		this.weight = weight;
		this.description = description;
	}

	public Item(String name, String type) {
		this.name = name;
		this.type = type;
		
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
	
}
