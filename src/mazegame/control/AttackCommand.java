package mazegame.control;

import mazegame.entity.Player;

public class AttackCommand implements Command {
	public CommandResponse execute (ParsedInput input, Player thePlayer) {
		return new CommandResponse ("You entered the attack command");
	}
}
