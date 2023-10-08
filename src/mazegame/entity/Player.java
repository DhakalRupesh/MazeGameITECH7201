package mazegame.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Character {
private Armor equippedArmor;
	
private Location currentLocation;
private Inventory inventory;
private Money gold;

	public Player() {
		inventory = new Inventory();
        gold = new Money();
	}
	

	public Player(String name) {
		super(name);
        inventory = new Inventory();
        gold = new Money();
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public Inventory getInventory() {
        return inventory;
    }

    public Money getGold() {
        return gold;
    }
    
    public int getGoldTotal() {
        return gold.getTotal();
    }

    public void addMoney(int goldPieces) {
        gold.Add(goldPieces);
    }

    public boolean addItem(Item theItem) {
        return inventory.addItem(theItem);
    }
	
    public Item findItem(String itemLabel) {
        return inventory.findItem(itemLabel);
    }
    
	public void equipArmor(Armor armor) {
        if (armor != null) {
            // Check if the player can equip this armor based on their attributes
            if (canEquipArmor(armor)) {
                equippedArmor = armor;
                applyArmorBonus();
                System.out.println("Equipped armor: " + armor.getLabel());
            } else {
                System.out.println("You cannot equip this armor.");
            }
        } else {
            System.out.println("Invalid armor.");
        }
    }
	
	private boolean canEquipArmor(Armor armor) {
        // Implement your logic here to determine if the player can equip the armor.
        // You can consider player's strength, agility, and other factors.
        // For simplicity, we're assuming that the player can equip any armor.
        return true;
    }
	
	private void applyArmorBonus() {
        if (equippedArmor != null) {
            int armorBonus = equippedArmor.getBonus();
            // Apply the armor bonus to player's attributes as needed
            this.setLifePoints(this.getLifePoints() + armorBonus);
            // You can apply the bonus to other attributes as well
            // Example: this.setStrength(this.getStrength() + armorBonus);
        }
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }
    
    public boolean purchaseArmor(Blacksmith blacksmith, Armor armor) {
        // Check if the player has enough gold to buy the armor
        int armorCost = armor.getValue();
        if (gold.getTotal() >= armorCost) {
            // Check if the blacksmith has the armor in stock
            if (blacksmith.getInventory().containsItem(armor.getLabel())) {
                // Remove the gold from the player's inventory
                gold.Subtract(armorCost);

                // Add the armor to the player's inventory
                inventory.addItem(armor);

                // Remove the armor from the blacksmith's inventory
                blacksmith.getInventory().removeItem(armor.getLabel());

                return true; // Armor purchased successfully
            } else {
                System.out.println("The blacksmith doesn't have this armor.");
            }
        } else {
            System.out.println("You don't have enough gold to buy this armor.");
        }
        return false; // Armor purchase failed
    }
    
    public int getArmorBonus() {
        if (equippedArmor != null) {
            return equippedArmor.getBonus();
        }
        return 0; // Return 0 if no armor is equipped
    }
    public void takeDamage(int damage) {
        // Subtract the damage from the player's life points
        int remainingLifePoints = this.getLifePoints() - damage;
        
        // Ensure the player's life points don't go below 0
        if (remainingLifePoints < 0) {
            remainingLifePoints = 0;
        }

        this.setLifePoints(remainingLifePoints);
        
        // You can add additional logic here, such as checking for player's death
        if (remainingLifePoints == 0) {
            System.out.println("You have been defeated!");
            // Implement any game-over logic here
        }
    }
    
    // Add a flee() method to allow the player to attempt to flee
    public boolean flee() {
    	// Implement logic for fleeing
        // You can use random chance (e.g., a random number generator)
        // to determine if the player successfully escapes.
        // Adjust the escapeChance value based on your game's balance.
        double escapeChance = 0.3; // 30% chance of successfully escaping
        boolean successfullyEscaped = Math.random() < escapeChance;
        if (successfullyEscaped) {
            // Player successfully fled
            System.out.println("You fled from battle!");
        } else {
            // Player failed to flee
            System.out.println("You failed to flee and remain in battle!");
        }
        return successfullyEscaped;
    }
    
    
    // Method to list items in the player's inventory
    public String listItems() {
    	StringBuilder itemList = new StringBuilder("Inventory:\n");
        HashMap<String, Item> items = inventory.getItemList();

        if (items.isEmpty()) {
            itemList.append("No items in inventory.");
        } else {
            for (String itemLabel : items.keySet()) {
                itemList.append(itemLabel).append("\n");
            }
        }

        return itemList.toString();
    }
    
    // Method to get an item from the player's inventory by label
    public Item getItem(String itemLabel) {
        return inventory.findItem(itemLabel);
    }

    // Method to drop an item from the player's inventory by label
    public boolean dropItem(String itemLabel) {
        Item itemToDrop = inventory.findItem(itemLabel);
        if (itemToDrop != null) {
            inventory.removeItem(itemLabel);
            return true;
        }
        return false; // Item not found in inventory
    }


}
