import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mazegame.HardCodedData;
import mazegame.boundary.IMazeData;
import mazegame.control.CommandHandler;
import mazegame.control.CommandResponse;
import mazegame.entity.Armor;
import mazegame.entity.Blacksmith;
import mazegame.entity.Inventory;
import mazegame.entity.Item;
import mazegame.entity.Player;
import mazegame.entity.utility.AgilityTable;
import mazegame.entity.utility.WeightLimit;
import mazegame.entity.Location;
import mazegame.entity.NonPlayerCharacter;


public class JunitTest {
	private CommandHandler commandHandler;
	private Player player;
    private Blacksmith blacksmith;
    private Armor armor;
    private Inventory inventory;
    private NonPlayerCharacter npc;
    private AgilityTable agilityTable;
    private WeightLimit weightLimit;
    
    private IMazeData mazeData;
    
	@Test
	public void test_JUnit() {
		System.out.println("check this string");
		String str1 = "lets's check the string";
		assertEquals("lets's check the string", str1);
	}
	
	@Before
    public void setUp() {
        player = new Player("TestPlayer");
        blacksmith = new Blacksmith();
        
        //   map and hardcoded data
        mazeData = new HardCodedData();
        
        // 	command handler
        commandHandler = new CommandHandler();
        
        // blacksmith
        blacksmith = new Blacksmith();
        player.getInventory().addMoney(200); // Give player enough gold for testing
        
        // Inventory
        inventory = new Inventory();
        
        // NPC
        npc = new NonPlayerCharacter("Goblin", 5, 20, 10, true);
        
        // AgilityTable
        agilityTable = AgilityTable.GetInstance();
        
        // weightlimit
        weightLimit = WeightLimit.getInstance();
       
    }
		
	@Test
    public void testPlayerArmorBonus() {
        Armor armor = new Armor("Steel Armor", 70, 12.0, "Strong steel armor", 0);
        armor.getBonus();
        assertEquals(armor.getBonus(), player.getArmorBonus());
    }
	
	//	hardcoded data
	@Test
    public void testGetStartingLocation() {
        // Test whether the starting location is not null
        assertNotNull(mazeData.getStartingLocation());
    }

	@Test
    public void testGetWelcomeMessage() {
        // Test whether the welcome message is not null
        assertNotNull(mazeData.getWelcomeMessage());
    }

    @Test
    public void testCreateLocations() {
        // Test whether locations are created and connected correctly
        Location startLocation = mazeData.getStartingLocation();
        assertNotNull(startLocation);
        assertEquals("The starting location should be 'Forest'", "Forest", startLocation.getLabel());

        // Test some exits
        Location forestExitNorth = startLocation.getExit("north").getDestination();
        Location forestExitEast = startLocation.getExit("east").getDestination();
        assertNotNull(forestExitNorth);
        assertNotNull(forestExitEast);
        assertEquals("Exit to the north should lead to 'Cave'", "Cave", forestExitNorth.getLabel());
        assertEquals("Exit to the east should lead to 'Village'", "Village", forestExitEast.getLabel());
    }
    
    //    command handler
    @Test
    public void testProcessTurnInvalidCommand() {
        // Test processing an invalid command
        String userInput = "move";
        CommandResponse response = commandHandler.processTurn(userInput, player);
        assertNotNull(response);
        assertFalse(response.isFinishedGame());
    }

   	// blacksmith
    @Test
    public void testInitializeArmors() {
        // Test the initializeArmors method to ensure availableArmors is populated
        blacksmith.initializeArmors();
        assertTrue("Leather Armor should be available", blacksmith.getArmorToSell("Leather Armor") != null);
        assertTrue("Iron Armor should be available", blacksmith.getArmorToSell("Iron Armor") != null);
    }
    
    @Test
    public void testSellArmor_InsufficientGold() {
        // Test the sellArmor method when the player has insufficient gold
        player.getInventory().addMoney(-200); // Remove player's gold
        assertFalse("Armor purchase should fail due to insufficient gold", blacksmith.sellArmor(player, "Iron Armor"));
        assertNull("Player should not have Iron Armor", player.getInventory().findItem("Iron Armor"));
    }
    
    
    // Inventory   
    @Test
    public void testContainsItem_NonExistingItem() {
        // Test if containsItem method correctly identifies a non-existing item
        assertFalse(inventory.containsItem("NonExistingItem"));
    }
    
    @Test
    public void testAddMoney() {
        // Test if addMoney method correctly adds gold to the inventory
        inventory.addMoney(50);
        assertEquals(50, inventory.getGoldTotal());
    }
    
    @Test
    public void testRemoveMoney_EnoughGold() {
        // Test if removeMoney method correctly deducts gold from the inventory when enough gold is available
        inventory.addMoney(100);
        assertTrue(inventory.removeMoney(50));
        assertEquals(50, inventory.getGoldTotal());
    }
    
    @Test
    public void testRemoveMoney_InsufficientGold() {
        // Test if removeMoney method does not deduct gold when there is insufficient gold available
        inventory.addMoney(20);
        assertFalse(inventory.removeMoney(50));
        assertEquals(20, inventory.getGoldTotal());
    }
    
    @Test
    public void testRemoveItem_NonExistingItem() {
        // Test if removeItem method correctly handles a non-existing item
        assertNull(inventory.removeItem("NonExistingItem"));
    }

    @Test
    public void testPrintItemList_NoItems() {
        // Test if printItemList method correctly handles an empty inventory
        assertEquals("No items here", inventory.printItemList());
    }
    

    @Test
    public void testFindItem_NonExistingItem() {
        // Test if findItem method correctly handles a non-existing item
        assertNull(inventory.findItem("NonExistingItem"));
    }

    @Test
    public void testGetGold() {
        // Test if getGold method returns the gold object
        assertNotNull(inventory.getGold());
    }

    @Test
    public void testGetGoldTotal() {
        // Test if getGoldTotal method returns the total gold amount
        inventory.addMoney(75);
        assertEquals(75, inventory.getGoldTotal());
    }
    
    @Test
    public void testGetHostile() {
        // Test the getHostile method
        assertTrue("NPC should be hostile", npc.getHostile());
    }
    
    @Test
    public void testSetHostile() {
        // Test the setHostile method
        npc.setHostile(false);
        assertFalse("NPC should not be hostile after setting", npc.getHostile());
    }

    @Test
    public void testGetStrength() {
        // Test the getStrength method
        assertEquals("NPC's strength should match", 10, npc.getStrength());
    }
    
    @Test
    public void testGetEquippedArmor_NullArmor() {
        // Test the getEquippedArmor method when no armor is equipped
        assertNull("Equipped armor should be null", npc.getEquippedArmor());
    }

    @Test
    public void testTakeDamage() {
        // Test the takeDamage method
        npc.takeDamage(5);
        assertEquals("NPC's life points should be reduced", 15, npc.getLifePoints());
    }

    @Test
    public void testTakeDamage_ZeroDamage() {
        // Test the takeDamage method with zero damage
        npc.takeDamage(0);
        assertEquals("NPC's life points should remain the same", 20, npc.getLifePoints());
    }

    @Test
    public void testTalkToCharacter() {
        // Test the talkToCharacter method for NPC-specific conversation logic
        assertEquals("NPC's response should match", "Greetings, Brave Gamer!", npc.talkToCharacter(null));
    }

    @Test
    public void testRespondToCharacter_HowAreYou() {
        // Test the respondToCharacter method for a specific player message
        assertEquals("NPC's response should match", "I'm here to serve.", npc.respondToCharacter("How are you?"));
    }

    @Test
    public void testRespondToCharacter_GenericMessage() {
        // Test the respondToCharacter method for a generic player message
        assertEquals("NPC's response should match", "I have no time for chit-chat.", npc.respondToCharacter("Hello"));
    }
    
    @Test
    public void testGetInstance_Singleton() {
        // Test if AgilityTable follows the Singleton pattern
        AgilityTable instance1 = AgilityTable.GetInstance();
        AgilityTable instance2 = AgilityTable.GetInstance();
        assertSame("AgilityTable instances should be the same", instance1, instance2);
    }
    
    @Test
    public void testSetModifier() {
        // Test the SetModifier method
        agilityTable.SetModifier(10, 2);
        assertEquals("Modifier for agility 10 should be set to 2", 2, agilityTable.getModifier(10));
    }

    @Test
    public void testGetModifier_ValidAgility() {
        // Test the GetModifier method with a valid agility value
        agilityTable.SetModifier(15, 3);
        assertEquals("Modifier for agility 15 should be 3", 3, agilityTable.getModifier(15));
    }

    @Test
    public void testGetModifier_MaxAgility() {
        // Test the GetModifier method for the maximum agility in the table
        agilityTable.SetModifier(30, 6);
        assertEquals("Modifier for max agility should be the set modifier", 6, agilityTable.getModifier(30));
    }
    
    @Test
    public void testSetAndGetModifier() {
        // Test the setModifier and getModifier methods

        // Set modifiers for different strengths
        weightLimit.setModifier(10, 50);
        weightLimit.setModifier(15, 75);

        // Check if modifiers are correctly set
        assertEquals(50, weightLimit.getModifier(10));
        assertEquals(75, weightLimit.getModifier(15));
    }

    @Test
    public void testGetModifierUnknownStrength() {
        // Test getModifier method when strength is not found in the lookup

        // Set modifiers for different strengths
        weightLimit.setModifier(10, 50);
        weightLimit.setModifier(15, 75);

        // Check if -1 is returned for an unknown strength
        assertEquals(-1, weightLimit.getModifier(12));
    }

    @Test
    public void testSingletonPattern() {
        // Test if WeightLimit follows the Singleton design pattern

        // Get two instances
        WeightLimit instance1 = WeightLimit.getInstance();
        WeightLimit instance2 = WeightLimit.getInstance();

        // Check if they are the same instance
        assertSame(instance1, instance2);
    }
}
	