package mazegame.control;

import mazegame.entity.Item;
import mazegame.entity.Player;
import mazegame.entity.Vendor;

public class PurchaseItemCommand implements Command {
	public CommandResponse execute(ParsedInput userInput, Player thePlayer) {
        // Implement logic to interact with a vendor and purchase items
        String itemName = userInput.getArguments().isEmpty() ? "" : userInput.getArguments().get(0).toString();
        
        // Check if there's a vendor in the current location and if the item is available
        Vendor currentVendor = thePlayer.getCurrentLocation().getVendor();
        
        if (currentVendor != null && currentVendor.isItemAvailable(itemName)) {
            int itemPrice = currentVendor.getItemPrice(itemName);
            
            // Check if the player has enough money
            if (thePlayer.getMoney() >= itemPrice) {
                // Deduct money from the player's inventory
                thePlayer.decreaseMoney(itemPrice);
                
                // Add the purchased item to the player's inventory
                Item purchasedItem = currentVendor.sellItem(itemName);
                thePlayer.getInventory().addItem(purchasedItem);
                
                return new CommandResponse("You purchased " + purchasedItem.getLabel() + ".");
            } else {
                return new CommandResponse("You don't have enough money to purchase the item.");
            }
        } else {
            return new CommandResponse("The item is not available from the vendor here.");
        }
    }
}
