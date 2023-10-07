package mazegame.control;

import mazegame.entity.Inventory;
import mazegame.entity.Item;
import mazegame.entity.Player;
// changes
public class DropItemCommand implements Command {
	public CommandResponse execute(ParsedInput userInput, Player thePlayer) {
		// Implement logic to drop an item
        String itemName = userInput.getArguments().isEmpty() ? "" : userInput.getArguments().get(0).toString();
        Inventory playerInventory = thePlayer.getInventory();
        Item droppedItem = playerInventory.removeItem(itemName);
        if (droppedItem != null) {
            return new CommandResponse("You dropped " + droppedItem.getLabel() + ".");
        } else {
            return new CommandResponse("Item not found in your inventory.");
        }
	}
}
