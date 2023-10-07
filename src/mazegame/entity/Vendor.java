package mazegame.entity;

import java.util.HashMap;
import java.util.Map;
import mazegame.entity.Item;

public class Vendor {

    private Map<String, Integer> itemPrices;
    private Map<String, Integer> itemValues;
    private Map<String, Integer> availableItems;

    public Vendor() {
        // Initialize item prices, values, and available items
        itemPrices = new HashMap<>();
        itemValues = new HashMap<>();
        availableItems = new HashMap<>();
        
        // Add sample items with their prices and values
        addItem("Sword", 100, 50, 5);
        addItem("Health Potion", 20, 10, 10);
    }

    public boolean isItemAvailable(String itemName) {
        return availableItems.containsKey(itemName);
    }

    public int getItemPrice(String itemName) {
        return itemPrices.getOrDefault(itemName, 0);
    }

    public int getItemValue(String itemName) {
        return itemValues.getOrDefault(itemName, 0);
    }

    public Item sellItem(String itemName) {
        if (isItemAvailable(itemName)) {
            // Remove the sold item from available items
            availableItems.remove(itemName);
            
            // Create and return the sold item
            return new Item(itemName);
        }
        return null; // Item not available
    }

    // Helper method to add an item to the vendor's inventory
    private void addItem(String itemName, int price, int value, int quantity) {
        itemPrices.put(itemName, price);
        itemValues.put(itemName, value);
        availableItems.put(itemName, quantity);
    }
}
