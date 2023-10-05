package mazegame.control;

import mazegame.entity.Exit;
import mazegame.entity.Player;

public class LookCommand implements Command {
	
	private CommandResponse response;
	
	public CommandResponse execute(ParsedInput userInput, Player thePlayer) {
		response = new CommandResponse("Can't find that to look at here!");
		if(userInput.getArguments().size() == 0) {
			response.setMessage(thePlayer.getCurrentLocation().toString());
			return response;
		}
		for (Object argument: userInput.getArguments()) {
			if (thePlayer.getCurrentLocation().getExitCollection().containsExit(argument.toString())) {
				Exit theExit = thePlayer.getCurrentLocation().getExitCollection().getExit((String)argument);
				return new CommandResponse(theExit.getDescription());
			}
		}
		return response;
	}
}
