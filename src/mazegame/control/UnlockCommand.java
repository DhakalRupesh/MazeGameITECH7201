package mazegame.control;

import mazegame.entity.Player;

public class UnlockCommand implements Command {
	public CommandResponse execute (ParsedInput input, Player thePlayer) {
		return new CommandResponse ("You entered the unlock command");
	}
}
