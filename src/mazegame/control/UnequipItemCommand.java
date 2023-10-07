package mazegame.control;

import mazegame.entity.Item;
import mazegame.entity.Player;

public class UnequipItemCommand implements Command {
	public CommandResponse execute(ParsedInput userInput, Player thePlayer) {
        String itemName = userInput.getArguments().isEmpty() ? "" : userInput.getArguments().get(0).toString();
        
        // Check if the player has the item equipped
        if (thePlayer.isItemEquipped(itemName)) {
            // Unequip the item and return it to the inventory
            Item unequippedItem = thePlayer.unequipItem(itemName);
            if (unequippedItem != null) {
                return new CommandResponse("You unequipped " + unequippedItem.getLabel() + ".");
            } else {
                return new CommandResponse("Failed to unequip the item.");
            }
        } else {
            return new CommandResponse("You don't have that item equipped.");
        }
    }
}
