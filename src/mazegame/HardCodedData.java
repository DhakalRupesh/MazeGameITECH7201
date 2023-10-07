package mazegame;

import mazegame.boundary.IMazeData;
import mazegame.entity.Exit;
import mazegame.entity.Location;
import mazegame.entity.Inventory;
import mazegame.entity.Item;

public class HardCodedData implements IMazeData {
    private Location startUp;

    public HardCodedData() {
        createLocations();
    }

    public Location getStartingLocation() {
        return startUp;
    }

    public String getWelcomeMessage() {
        return "Welcome to the Custom Maze Game!";
    }

    private void createLocations() {
        startUp = new Location("You are at the Village.", "Start");

        // Create additional locations
        Location forest = new Location("You are in a dense forest.", "Forest");
        Location cave = new Location("You are inside a dark cave.", "Cave");
        Location village = new Location("You are in a peaceful village.", "Village");
        Location castle = new Location("You stand before a majestic castle.", "Castle");
        Location desert = new Location("You find yourself in a scorching desert.", "Desert");

        // Create exits between locations
        startUp.addExit("north", new Exit("You enter the forest.", forest));
        forest.addExit("south", new Exit("You leave the forest.", startUp));

        forest.addExit("east", new Exit("You enter the cave.", cave));
        cave.addExit("west", new Exit("You leave the cave.", forest));

        village.addExit("west", new Exit("You approach the castle.", castle));
        castle.addExit("east", new Exit("You leave the castle.", village));

        village.addExit("south", new Exit("You journey into the desert.", desert));
        desert.addExit("north", new Exit("You leave the desert.", village));

    }
}
