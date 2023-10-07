package mazegame.control;

import java.util.HashMap;
import mazegame.entity.Inventory;
import mazegame.entity.Item;
import mazegame.entity.Player;

public class ListItemsCommand implements Command {
    public CommandResponse execute(ParsedInput userInput, Player thePlayer) {
        // Implement logic to list held items
        Inventory playerInventory = thePlayer.getInventory(); // to be completed
        StringBuilder responseMessage = new StringBuilder("Held items:\n");
        HashMap<String, Item> itemList = playerInventory.getItemList();
        if (itemList.isEmpty()) {
            responseMessage.append("No items held.");
        } else {
            for (String itemName : itemList.keySet()) {
                responseMessage.append(itemName).append("\n");
            }
        }
        return new CommandResponse(responseMessage.toString());
    }
}