package mazegame.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mazegame.SimpleConsoleClient;
import mazegame.boundary.IMazeClient;
import mazegame.boundary.IMazeData;
import mazegame.entity.Armor;
import mazegame.entity.Blacksmith;
import mazegame.entity.Exit;
import mazegame.entity.Inventory;
import mazegame.entity.Item;
import mazegame.entity.Location;
import mazegame.entity.Player;
import mazegame.entity.Money;
import mazegame.entity.NonPlayerCharacter;
import mazegame.entity.utility.NonPlayerCharacterCollection;


public class DungeonMaster {
	private IMazeClient gameClient;
	private IMazeData gameData;
	private Player thePlayer;
	private Blacksmith blacksmith;
	private boolean continueGame;
	private ArrayList<String> commands;
	private Parser theParser;
	private CommandHandler playerTurnHandler;
	private NonPlayerCharacterCollection npcCollection;
	
	 public DungeonMaster(IMazeData gameData, IMazeClient gameClient) {
         this.gameData = gameData;
         this.gameClient = gameClient;
         this.continueGame = true;
         commands = new ArrayList<String>();
         commands.add("quit");
         commands.add("move");
         commands.add("buy");
         commands.add("flee");
         commands.add("listItems");
         commands.add("getItem");
         commands.add("dropItem");
         theParser = new Parser (commands);
         playerTurnHandler = new CommandHandler();
         npcCollection = new NonPlayerCharacterCollection();
         initializeNPCs();
     }

     public void printWelcome() {
         gameClient.playerMessage(gameData.getWelcomeMessage());
     }

     public void setupPlayer() {
         String playerName = gameClient.getReply("What name do you choose to be known by?");
         thePlayer = new Player(playerName);
         Blacksmith blacksmith = new Blacksmith();
         
         thePlayer.setAgility(10); // Set player's agility
         thePlayer.setLifePoints(100); // Set player's life points
         thePlayer.setStrength(15); // Set player's strength
         
         thePlayer.setCurrentLocation(gameData.getStartingLocation());
         gameClient.playerMessage("Welcome " + playerName + "\n\n");
         gameClient.playerMessage("You find yourself looking at ");
         gameClient.playerMessage(gameData.getStartingLocation().getDescription() + "\n\n");

         // gameClient.getReply("<<Hit Enter to exit>>");
         
     }
     
  // Initialize non-player characters
     private void initializeNPCs() {
         NonPlayerCharacter npc1 = new NonPlayerCharacter("Goblin", 5, 20, 10, true);
         NonPlayerCharacter npc2 = new NonPlayerCharacter("Orc", 8, 30, 15, true);

         npcCollection.put(npc1.getName().toLowerCase(), npc1);
         npcCollection.put(npc2.getName().toLowerCase(), npc2);
     }

     
     public void runGame() {
    	    printWelcome();
    	    setupPlayer();
    	    setupNPCs();
    	    while (continueGame) {
    	        continueGame = processPlayerTurn();
//    	        runNPCs(); // Run NPC turns after the player's turn
    	    }
    	    gameClient.getReply("\n\n<<Hit enter to exit>>");
    	}

     
     private boolean handlePlayerTurn() {
    	 CommandResponse playerResponse = playerTurnHandler.processTurn(gameClient.getCommand(), thePlayer);
    	 gameClient.playerMessage(playerResponse.getMessage());
    	 return !playerResponse.isFinishedGame();
     }  
     
     public boolean processPlayerTurn() {
	    ParsedInput userInput = theParser.parse(gameClient.getCommand());
	    if (commands.contains(userInput.getCommand())) {
	        if (userInput.getCommand().equals("quit")) {
	            return false;
	        } else if (userInput.getCommand().equals("move")) {
	            if (validateMoveCommand(userInput)) {
	                processMove(userInput);
	                runNPCs(); // Run NPC turns after the player's turn
	            } else {
	                gameClient.playerMessage("Invalid move direction. Please enter 'move' followed by a valid direction (east, west, north, or south).");
	            }
	        } else if (userInput.getCommand().equals("buy")) {
	            processBuy(userInput);
	        } else if (userInput.getCommand().equals("flee")) {
	            return playerFlee();
	        } else if (userInput.getCommand().equals("listItems")) {
	            handleListItems();
	        } else if (userInput.getCommand().equals("getItem")) {
	            handleGetItem(userInput.getCommand());
	        } else if (userInput.getCommand().equals("dropItem")) {
	            handleDropItem(userInput.getCommand());
	        }
	    } else {
	        gameClient.playerMessage("We don't recognize that command - try again!");
	    }
	    return true;
	}
//     validation players movement
     private boolean validateMoveCommand(ParsedInput userInput) {
	    if (userInput.getArguments().size() == 1) {
	        String exitLabel = (String) userInput.getArguments().get(0);
	        // Check if the exitLabel is a valid direction (east, west, north, or south)
	        if (exitLabel.equalsIgnoreCase("east") || exitLabel.equalsIgnoreCase("west")
	            || exitLabel.equalsIgnoreCase("north") || exitLabel.equalsIgnoreCase("south")) {
	            return true; // Valid move command
	        }
	    }
	    return false; // Invalid move command
	}
    	   	 
     private void processMove(ParsedInput userInput) {
	    String exitLabel = (String) userInput.getArguments().get(0);
	    Exit desiredExit = thePlayer.getCurrentLocation().getExit(exitLabel);
	    if (desiredExit == null) {
	        gameClient.playerMessage("There is no exit there . . . try moving somewhere else");
	        return;
	    }
	    thePlayer.setCurrentLocation(desiredExit.getDestination());
	    gameClient.playerMessage("You find yourself looking at ");
	    gameClient.playerMessage(thePlayer.getCurrentLocation().getDescription());
     }
    
      private void processBuy(ParsedInput userInput) {
    	    // Check if the command includes "armor" to specify buying armor
    	    if (userInput.getArguments().contains("armor")) {
    	    	thePlayer.getInventory().addMoney(100);
    	    	Blacksmith blacksmith = new Blacksmith();
    	        Armor armorToBuy = blacksmith.getArmorToSell("Iron Armor"); // Get available armor from the blacksmith
    	        boolean bought = blacksmith.sellArmor(thePlayer, "Iron Armor");
    	        if (bought) {
    	            int armorCost = armorToBuy.getValue();

    	            // Check if the player has enough gold to purchase the armor
    	            if (thePlayer.getGold().getTotal() <= armorCost) {
    	                // Remove the gold from the player
    	                thePlayer.getGold().Subtract(armorCost);

    	                // Add the armor to the player's inventory
    	                thePlayer.getInventory().addItem(armorToBuy);

    	                // Remove the armor from the blacksmith's inventory
    	                blacksmith.getInventory().removeItem(armorToBuy.getLabel());

    	                gameClient.playerMessage("You successfully purchased the armor: " + armorToBuy.getLabel());
    	            } else {
    	                gameClient.playerMessage("You don't have enough gold to purchase the armor.");
    	            }
    	        } else {
    	            gameClient.playerMessage("The blacksmith has no armor to sell.\n");
    	        }
    	    } else {
    	        gameClient.playerMessage("You can't buy that.\n");
    	    }
    	}
      
      private void setupNPCs() {
          // You can use the npcCollection to access and set up NPCs
    	  for (NonPlayerCharacter npc : npcCollection.values()) {
    	        // Customize NPC setup logic as needed
    	        // For example, you can set NPC starting locations, items, etc.
    	        // npc.setCurrentLocation(gameData.getRandomLocation());

    	        // Set NPC attributes (agility, lifePoints, strength)
    	        npc.setAgility(10); // Customize NPC's agility
    	        npc.setLifePoints(100); // Customize NPC's lifePoints
    	        npc.setStrength(8); // Customize NPC's strength

    	        // Set NPC hostility
    	        npc.setHostile(true); // Customize whether the NPC is hostile or not
    	    }
      }
      
      private void runNPCs() {
          // Logic to run NPC turns can be implemented here
          // You can iterate through npcCollection and simulate NPC actions
    	  boolean playerEscaped = false;
          for (NonPlayerCharacter npc : npcCollection.values()) {
        	  if (!playerEscaped) {
	              if (npc.getHostile()) {
					  // Implement hostile NPC behavior, e.g., attacking the player
					  // Adjust NPC actions based on your game's rules
					  int npcDamage = npc.getStrength();
					
					  // Check if the player has equipped armor, and if so, subtract the armor bonus
					  Armor equippedArmor = thePlayer.getEquippedArmor();
					  if (equippedArmor != null) {
						  npcDamage -= equippedArmor.getBonus();
					  }
					
					  // Make sure damage is at least 0 to prevent negative damage values
					  npcDamage = Math.max(0, npcDamage);   
		                  
		              if (npcDamage > 0) {
		                  thePlayer.takeDamage(npcDamage);
		                  gameClient.playerMessage("\n WAR!!! \n" + 
		                		  "------------****-------------- \n" +
		                		  "(V) HAHAHAHA!! You are always under attack!!" + 
		                		  "\t" + npc.getName() + " attacks you for " + npcDamage + " damage!!! \n \n");              
		              }
		              
			          }else {
			              // Implement non-hostile NPC behavior
			              // Adjust NPC actions based on your game's rules
	
			              // Example: Non-hostile NPCs can perform other actions here
			          }
	
			          // Now, let's simulate the player's turn to attack the NPC (assuming player's turn follows)
			          // Calculate player's attack damage on the NPC
			          int playerDamage = thePlayer.getStrength();
	
			          // Check if the NPC has equipped armor, and if so, subtract the armor bonus
			          Armor npcArmor = npc.getEquippedArmor();
			          if (npcArmor != null) {
			              playerDamage -= npcArmor.getBonus();
			          }
	
			          // Make sure damage is at least 0 to prevent negative damage values
			          playerDamage = Math.max(0, playerDamage);
	
			          if (playerDamage > 0) {
			              npc.takeDamage(playerDamage);
			              gameClient.playerMessage("(H) HERO!!! ATTACK!!!!! You attack " + npc.getName() + " for " + playerDamage + " damage! \n"
			              		+ "------------****-------------- \n");
			          }
		      }
          }
      
      }
      
      private boolean playerFlee() {
    	  /**
    	    boolean successfullyEscaped = thePlayer.flee();
    	    if (successfullyEscaped) {
    	        // Player successfully fled from battle
    	        // Implement any additional logic or message you want here.
    	    } else {
    	        // Player failed to flee, continue with the NPC's turn
    	        // Implement any additional logic or message you want here.
    	    }
    	    return successfullyEscaped;
    	   **/
    	  
    	  if (thePlayer.getCurrentLocation().allowsFlee()) {
    	        // The current location allows the player to flee
    	        if (thePlayer.flee()) {
    	            // Player successfully fled from battle
    	            gameClient.playerMessage("You successfully fled from battle!");
    	            return true;
    	        } else {
    	            // Player failed to flee
    	            gameClient.playerMessage("You tried to flee but were unsuccessful.");
    	            return false;
    	        }
    	    } else {
    	        // The current location does not allow fleeing
    	        gameClient.playerMessage("You cannot flee from this location.");
    	        return false;
    	    }
    	}
      
     // Method to handle the "listItems" command
      private void handleListItems() {
          String playerInventory = thePlayer.listItems();
          gameClient.playerMessage(playerInventory);
      }

      // Method to handle the "getItem" command
      private void handleGetItem(String command) {
          Pattern pattern = Pattern.compile("getItem\\s+(\\w+)");
          Matcher matcher = pattern.matcher(command);

          if (matcher.find()) {
              String itemLabel = matcher.group(1);
              Item item = thePlayer.getItem(itemLabel);

              if (item != null) {
                  thePlayer.getInventory().addItem(item);
                  gameClient.playerMessage("You got the item: " + item.getLabel());
              } else {
                  gameClient.playerMessage("Item not found in the location or invalid item label.");
              }
          } else {
              gameClient.playerMessage("Invalid getItem command. Use 'getItem [itemLabel]'.");
          }
      }

      // Method to handle the "dropItem" command
      private void handleDropItem(String command) {
          Pattern pattern = Pattern.compile("dropItem\\s+(\\w+)");
          Matcher matcher = pattern.matcher(command);

          if (matcher.find()) {
              String itemLabel = matcher.group(1);

              if (thePlayer.dropItem(itemLabel)) {
                  gameClient.playerMessage("You dropped the item: " + itemLabel);
              } else {
                  gameClient.playerMessage("Item not found in your inventory or invalid item label.");
              }
          } else {
              gameClient.playerMessage("Invalid dropItem command. Use 'dropItem [itemLabel]'.");
          }
      }
}
