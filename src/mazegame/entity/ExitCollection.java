package mazegame.entity;

import java.util.HashMap;

public class ExitCollection {
	private HashMap <String, Exit>exits;
	
	public ExitCollection () {
		exits = new HashMap<String, Exit> ();
	}
	
	public boolean addExit (String exitLabel, Exit theExit) {
		if (exits.containsKey(exitLabel))
			return false;
		exits.put(exitLabel, theExit);
		return true;
	}

	public Exit getExit (String exitLabel) {
		return (Exit) exits.get(exitLabel);
	}
	
	public boolean containsExit (String exitLabel) {
		return exits.containsKey(exitLabel);
	}
	
	public String availableExits () {
		StringBuilder returnMsg = new StringBuilder();
		for (Object label: this.exits.keySet()) {
			returnMsg.append("[ " + label.toString() + " ]");
		}
		return returnMsg.toString();
	}
}
