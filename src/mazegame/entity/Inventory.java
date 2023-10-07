package mazegame.entity;

import java.util.HashMap;

public class Inventory {
	private Money gold;
    private HashMap<String, Item> itemList;

    public Inventory() {
        gold = new Money();
        itemList = new HashMap<String, Item>();
    }

    // Check if the inventory contains an item with a given label
    public boolean containsItem(String itemLabel) {
        return itemList.containsKey(itemLabel);
    }
    
    
    
    public void addMoney(int goldPieces) {
        gold.Add(goldPieces);
    }

    public boolean removeMoney(int goldPieces) {
        return gold.Subtract(goldPieces);
    }

    public boolean addItem(Item theItem) {
        itemList.put(theItem.getLabel(), theItem);
        return true;
    }
    
    // Remove an item from the inventory by label
    public Item removeItem(String itemLabel) {
        if (containsItem(itemLabel)) {
            return itemList.remove(itemLabel);
        }
        return null;
    }

    /**
	    public Item removeItem(String itemName) {
	        if (itemList.containsKey(itemName))
	        {
	        	itemList.remove(itemName);
	            return itemList.get(itemName);
	        }
	      //  else return ("That item isn't here to remove");
	        else 
	        	return null;
	    }
	**/
    
    
    public String printItemList() {
        if (itemList.size() == 0)
            return "No items here";
        StringBuilder returnMsg = new StringBuilder();
        returnMsg.append("Items here ::");
        for (String item: itemList.keySet()) {
            returnMsg.append(" [" + item + "]");
        }
        return returnMsg.toString();
    }

    public String toString() {
        return printItemList() + "\n" + gold.toString();
    }
    
    public HashMap<String, Item> getItemList () {
    	return itemList;
    }
    
    public Item findItem(String itemLabel)
    {
    	if (itemList.containsKey(itemLabel))
    	{
    		return itemList.get(itemLabel);
    	}
    	else
    	{
    		return null;
    	}
    	
    }
    
    public Money getGold() {
        return gold;
    }
    
    public int getGoldTotal() {
        return gold.getTotal();
    }
	
}