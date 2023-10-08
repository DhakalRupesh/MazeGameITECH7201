package mazegame;

import mazegame.boundary.IMazeData;
import mazegame.entity.Exit;
import mazegame.entity.Location;

public class HardCodedData implements IMazeData {
	private Location startLocation;

    public HardCodedData() {
        createLocations();
    }

    public Location getStartingLocation() {
        return startLocation;
    }

    public String getWelcomeMessage() {
        return "Welcome to the [[[[[Realm of Secrets: Maze Explorer]]]]";
    }
	
	private void createLocations () 
	{
		// Create 8 locations
        Location locationForest = new Location("You are in a forest.", "Forest");
        Location locationCave = new Location("You are in a cave.", "Cave");
        Location locationMountain = new Location("You are on a mountain peak.", "Mountain");
        Location locationVillage = new Location("You are in a village.", "Village");
        Location locationCastle = new Location("You are in a castle.", "Castle");
        Location locationDesert = new Location("You are in a desert.", "Desert");
        Location locationSwamp = new Location("You are in a swamp.", "Swamp");
        Location locationGarden = new Location("You are in a hidden garden.", "Garden");
        
     // Connect the locations with exits
        locationForest.addExit("north", new Exit("You see a cave entrance to the north.", locationCave));
        locationForest.addExit("east", new Exit("You see a path leading to the east.", locationVillage));
        locationCave.addExit("south", new Exit("You exit the cave to the south.", locationForest));
        locationCave.addExit("east", new Exit("You find a hidden passage to the east.", locationCastle));
        locationMountain.addExit("south", new Exit("You descend the mountain to the south.", locationVillage));
        locationMountain.addExit("west", new Exit("You see a forest to the west.", locationForest));
        locationVillage.addExit("north", new Exit("You head north to the mountain peak.", locationMountain));
        locationVillage.addExit("west", new Exit("You enter the forest to the west.", locationForest));
        locationCastle.addExit("west", new Exit("You exit the castle to the west.", locationCave));
        locationCastle.addExit("north", new Exit("You see a desert to the north.", locationDesert));
        locationDesert.addExit("south", new Exit("You enter the castle to the south.", locationCastle));
        locationDesert.addExit("west", new Exit("You find a hidden path to the west.", locationMountain));
        locationVillage.addExit("east", new Exit("You enter the swamp to the east.", locationSwamp));
        locationSwamp.addExit("west", new Exit("You leave the swamp to the west.", locationVillage));
        locationSwamp.addExit("north", new Exit("You discover a hidden garden to the north.", locationGarden));
        locationGarden.addExit("south", new Exit("You return to the swamp to the south.", locationSwamp));

        // Add shops to some locations
        locationVillage.getInventory().addItem(new Shop("Armor Shop", "Armor", 50, 10, "Buy armor here!"));
        locationCastle.getInventory().addItem(new Shop("Weapon Shop", "Weapon", 75, 8, "Buy weapons here!"));

        // Set the starting location
        startLocation = locationForest;
			
	}
}
