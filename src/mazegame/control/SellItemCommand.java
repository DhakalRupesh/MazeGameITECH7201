package mazegame.control;

import mazegame.entity.Item;
import mazegame.entity.Player;
import mazegame.entity.Vendor;

public class SellItemCommand implements Command {
    public CommandResponse execute(ParsedInput userInput, Player thePlayer) {
        // Implement logic to interact with a vendor and sell items
        String itemName = userInput.getArguments().isEmpty() ? "" : userInput.getArguments().get(0).toString();
        
        // Check if there's a vendor in the current location
        Vendor currentVendor = thePlayer.getCurrentLocation().getVendor();
        
        if (currentVendor != null) {
            // Check if the player has the item to sell
            Item itemToSell = thePlayer.getInventory().findItem(itemName);
            if (itemToSell != null) {
                int itemValue = currentVendor.getItemPrice(itemName);
                
                // Add money to the player's inventory
                thePlayer.decreaseMoney(itemValue);
                
                // Remove the sold item from the player's inventory
                thePlayer.getInventory().removeItem(itemName);
                
                return new CommandResponse("You sold " + itemToSell.getLabel() + " for " + itemValue + " coins.");
            } else {
                return new CommandResponse("You don't have that item to sell.");
            }
        } else {
            return new CommandResponse("There's no vendor here to sell to.");
        }
    }
}




