package mazegame.control;

import mazegame.entity.Player;

public class SellCommand implements Command {
	public CommandResponse execute (ParsedInput input, Player thePlayer) {
		return new CommandResponse ("You entered the sell command");
	}
}
