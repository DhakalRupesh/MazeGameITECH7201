package mazegame.control;

import java.io.IOException;
import java.util.ArrayList;

import mazegame.SimpleConsoleClient;
import mazegame.boundary.IMazeClient;
import mazegame.boundary.IMazeData;
import mazegame.entity.Exit;
import mazegame.entity.Player;

public class DungeonMaster {
	private IMazeClient gameClient;
	private IMazeData gameData;
	private Player thePlayer;
	private boolean continueGame;
	private ArrayList<String> commands;
	private Parser theParser;
	private CommandHandler playerTurnHandler;
	
	 public DungeonMaster(IMazeData gameData, IMazeClient gameClient) {
         this.gameData = gameData;
         this.gameClient = gameClient;
         this.continueGame = true;
         commands = new ArrayList<String>();
         commands.add("quit");
         commands.add("move");
         commands.add("attack");
         commands.add("buy");
         theParser = new Parser (commands);
         playerTurnHandler = new CommandHandler();
     }

     public void printWelcome() {
         gameClient.playerMessage(gameData.getWelcomeMessage());
     }

     public void setupPlayer() {
         String playerName = gameClient.getReply("What name do you choose to be known by?");
         thePlayer = new Player(playerName);
         thePlayer.setCurrentLocation(gameData.getStartingLocation());
         gameClient.playerMessage("Welcome " + playerName + "\n\n");
         gameClient.playerMessage("You find yourself looking at ");
         gameClient.playerMessage(gameData.getStartingLocation().getDescription());

        // gameClient.getReply("<<Hit Enter to exit>>");
     }


     public void runGame() {
         printWelcome();
         setupPlayer();
         while (continueGame) {
        	 continueGame = processPlayerTurn();
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
    		 if (userInput.getCommand().equals("quit"))
    			 return false;
    		 if (userInput.getCommand().equals("move")) {
                 if (userInput.getArguments().size() > 0) {
                     String direction = (String) userInput.getArguments().get(0);
                     processMove(direction);
                 } else {
                     gameClient.playerMessage("Specify a direction to move (e.g., 'move north').");
                 }
                 return true;
             }
    	 }
    	 gameClient.playerMessage("We don't recognise that command - try again!");
    	 return true;    	 
      }
     
     private void processMove(String direction) {
         if (isValidDirection(direction)) {
             Exit desiredExit = thePlayer.getCurrentLocation().getExit(direction);
             if (desiredExit != null) {
                 thePlayer.setCurrentLocation(desiredExit.getDestination());
                 gameClient.playerMessage("You move " + direction + " and find yourself at:\n");
                 gameClient.playerMessage(thePlayer.getCurrentLocation().getDescription());
             } else {
                 gameClient.playerMessage("There is no exit in that direction.");
             }
         } else {
             gameClient.playerMessage("Invalid direction. Use 'north,' 'south,' 'east,' or 'west'.");
         }
     }
          
     private boolean isValidDirection(String direction) {
         // Implement validation logic here (e.g., check if direction is one of 'north', 'south', 'east', 'west')
         return direction.equalsIgnoreCase("north")
             || direction.equalsIgnoreCase("south")
             || direction.equalsIgnoreCase("east")
             || direction.equalsIgnoreCase("west");
     }
}
