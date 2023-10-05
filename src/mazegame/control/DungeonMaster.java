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
    			 processMove(userInput);
    			 return true;
    		 }
    	 }
    	 gameClient.playerMessage("We don't recognise that command - try again!");
    	 return true;    	 
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

}
