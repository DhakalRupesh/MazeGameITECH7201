package mazegame.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Blacksmith {
	private HashMap<String, Armor> availableArmors;
    private ArrayList<Armor> upgradedArmors;
    private Inventory inventory;
    private Money gold;

    public Blacksmith() {
        availableArmors = new HashMap<>();
        upgradedArmors = new ArrayList<>();
        initializeArmors();
        inventory = new Inventory();
        gold = new Money();
        
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public Armor getArmorToSell(String armorLabel) {
        // Retrieve the armor from availableArmors using the provided label
        return availableArmors.get(armorLabel.toLowerCase());
    }
    
    // Check if the blacksmith's inventory contains an item with a given label
    public boolean containsItem(String itemLabel) {
        return inventory.containsItem(itemLabel);
    }
    
    // Remove an item from the blacksmith's inventory
    public Item removeItem(String itemLabel) {
        return inventory.removeItem(itemLabel);
    }
    
    // Add available armors to the blacksmith's inventory
    public void initializeArmors() 
    {
        Armor leatherArmor = new Armor("Leather Armor", 50, 5.0, "Basic leather armor", 2);
        Armor ironArmor = new Armor("Iron Armor", 150, 15.0, "Sturdy iron armor", 5);

        availableArmors.put(leatherArmor.getLabel().toLowerCase(), leatherArmor);
        availableArmors.put(ironArmor.getLabel().toLowerCase(), ironArmor);
    }

    // Sell the available armors from the blacksmith's inventory
    public boolean sellArmor(Player player, String armorLabel) 
    {
        Armor armorToSell = availableArmors.get(armorLabel.toLowerCase());

        if (armorToSell != null) {
            if (player.getGold().getTotal() >= armorToSell.getValue()) {
                player.getInventory().addMoney(-armorToSell.getValue());
                player.getInventory().addItem(armorToSell);
                System.out.println("You purchased " + armorToSell.getLabel());
                return true;
            } else {
                System.out.println("You don't have enough gold to purchase " + armorToSell.getLabel());
            }
        } else {
            System.out.println("Blacksmith does not have " + armorLabel);
        }

        return false;
    }

    public void upgradeArmor(Player player, String armorLabel) {
        Armor armorToUpgrade = (Armor) player.getInventory().findItem(armorLabel);

        if (armorToUpgrade != null) {
            // Check if the armor can be upgraded
            if (!upgradedArmors.contains(armorToUpgrade)) {
                // Apply upgrade logic based on your game's rules
                // For example, increase the armor's bonus or other attributes
                int currentBonus = armorToUpgrade.getBonus();
                armorToUpgrade.setBonus(currentBonus + 1);
                upgradedArmors.add(armorToUpgrade);

                System.out.println("You upgraded " + armorToUpgrade.getLabel() + " to +"
                        + armorToUpgrade.getBonus() + " bonus.");
            } else {
                System.out.println("You have already upgraded this armor.");
            }
        } else {
            System.out.println("You do not have " + armorLabel + " to upgrade.");
        }
    }
    
    

}
