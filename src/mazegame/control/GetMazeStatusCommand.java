package mazegame.control;

import mazegame.entity.Player;

public class GetMazeStatusCommand implements Command {
    private int mazeSizeM;
    private int numberOfShopsN;

    public GetMazeStatusCommand(int mazeSizeM, int numberOfShopsN) {
        this.mazeSizeM = mazeSizeM;
        this.numberOfShopsN = numberOfShopsN;
    }

    public CommandResponse execute(ParsedInput userInput, Player thePlayer) {
        String statusMessage = "Maze Status:\n";
        statusMessage += "Maze Size (M): " + mazeSizeM + "\n";
        statusMessage += "Number of Shops (N): " + numberOfShopsN + "\n";

        return new CommandResponse(statusMessage);
    }
}
