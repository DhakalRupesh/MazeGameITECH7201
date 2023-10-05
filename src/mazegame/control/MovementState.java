package mazegame.control;

import mazegame.entity.Blacksmith;
import mazegame.entity.Player;

public class MovementState extends CommandState {
    public MovementState()
    {
        this.getAvailableCommands().put("go", new MoveCommand());
        this.getAvailableCommands().put("quit", new QuitCommand());
        this.getAvailableCommands().put("move", new MoveCommand());
        this.getAvailableCommands().put("look", new LookCommand());
        this.getAvailableCommands().put("unlock", new UnlockCommand());
        this.getAvailableCommands().put("attack", new AttackCommand());
    }

    public CommandState update(Player thePlayer)
    {
        if (thePlayer.getCurrentLocation() != null && Blacksmith.class.isAssignableFrom(thePlayer.getCurrentLocation().getClass()))
            return new CommerceState();
        return this;
    }
}
