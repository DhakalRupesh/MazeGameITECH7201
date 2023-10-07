package mazegame.control;

import mazegame.entity.Inventory;
import mazegame.entity.Item;
import mazegame.entity.Player;

public class EquipItemCommand implements Command {
	public CommandResponse execute(ParsedInput userInput, Player thePlayer) { 
	    String itemName = userInput.getArguments().isEmpty() ? "" : userInput.getArguments().get(0).toString();
        Inventory playerInventory = thePlayer.getInventory();
        Item itemToEquip = playerInventory.findItem(itemName);
        
        if (itemToEquip != null) {
            // Check if the item is equippable (e.g., based on item type)
            if (itemToEquip.isEquippable()) {
                // Unequip the previous item from the same slot, if any
                thePlayer.unequipItemFromSlot(itemToEquip.getEquipmentSlot());
                
                // Equip the item to the appropriate slot
                thePlayer.equipItem(itemToEquip);
                
                return new CommandResponse("You equipped " + itemToEquip.getLabel() + ".");
            } else {
                return new CommandResponse("You can't equip that item.");
            }
        } else {
            return new CommandResponse("Item not found in your inventory.");
        }
	}
	
}
