package mazegame.entity;

import java.util.HashMap;
import java.util.Map;

public class Player extends Character {
	
private Location currentLocation;
private Inventory inventory;
private Map<Object, Item> equippedItems; // Map to store equipped items by slot
private int money;

	public Player() {
		inventory = new Inventory();
        equippedItems = new HashMap<>();
        money = 100; 
	}

	public Player(String name) {
	    super (name);
	    inventory = new Inventory();
        equippedItems = new HashMap<>();
        money = 100; // Initialize player with 100 coins (adjust as needed)
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	// changes
	public Inventory getInventory() {
        return inventory;
    }

	public void unequipItemFromSlot(Object equipmentSlot) {
        // Remove the item from the equipped slot
        equippedItems.remove(equipmentSlot);
    }

    public void equipItem(Item itemToEquip) {
        // Equip the item to the appropriate slot based on item's equipment slot
        equippedItems.put(itemToEquip.getEquipmentSlot(), itemToEquip);
    }

    public boolean isItemEquipped(String itemName) {
        // Check if the player has the item equipped
        for (Item equippedItem : equippedItems.values()) {
            if (equippedItem.getLabel().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public Item unequipItem(String itemName) {
        // Unequip the item and return it to the inventory
        for (Item equippedItem : equippedItems.values()) {
            if (equippedItem.getLabel().equalsIgnoreCase(itemName)) {
                equippedItems.remove(equippedItem.getEquipmentSlot());
                return equippedItem;
            }
        }
        return null;
    }

    public int getMoney() {
        return money;
    }

    public void decreaseMoney(int itemPrice) {
        money -= itemPrice;
    }

    public void increaseMoney(int amount) {
        money += amount;
    }
}
