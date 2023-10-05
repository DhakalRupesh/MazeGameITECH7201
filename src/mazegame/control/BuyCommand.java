package mazegame.control;

import mazegame.entity.Player;

public class BuyCommand implements Command {
	public CommandResponse execute (ParsedInput input, Player thePlayer) {
		return new CommandResponse ("You entered the buy command");
	}
}
