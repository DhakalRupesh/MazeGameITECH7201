package mazegame.entity.utility;

import java.util.HashMap;

import mazegame.entity.NonPlayerCharacter;

public class NonPlayerCharacterCollection extends HashMap<String, NonPlayerCharacter> {
	
	public NonPlayerCharacterCollection () {
		super ();
	}
	
	public String toString () {
		StringBuilder returnMsg = new StringBuilder();
        returnMsg.append("Characters ::");
        if (this.size() == 0)
            returnMsg.append(" No characters");
        else
        {
            for (String name: this.keySet())
                returnMsg.append(" << " + name + " >>");
        }
        
        return returnMsg.toString();
	}
}
