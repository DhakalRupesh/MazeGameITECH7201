package mazegame.control;

import mazegame.entity.Exit;
import mazegame.entity.Player;

public class MoveCommand implements Command {

	public CommandResponse execute (ParsedInput userInput, Player thePlayer) {
		if (userInput.getArguments().size() == 0) {
			return new CommandResponse ("If you want to move you need to tell me where.");
		}
		String exitLabel = (String) userInput.getArguments().get(0);
   	 	Exit desiredExit = thePlayer.getCurrentLocation().getExitCollection().getExit(exitLabel);
   	 	if (desiredExit == null) {
   	 		return new CommandResponse("There is no exit there . . . try moving somewhere else!");
   	 	}
   	 	thePlayer.setCurrentLocation(desiredExit.getDestination());
   	 	return new CommandResponse("You successfully move " + exitLabel + " and find yourself somewhere else\n\n" + thePlayer.getCurrentLocation().toString());
	}
}
