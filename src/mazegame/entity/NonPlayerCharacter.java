package mazegame.entity;

public class NonPlayerCharacter extends Character {
	private Boolean hostile;

    public NonPlayerCharacter()
    {
    }


    public boolean getHostile () {
    	return this.hostile;
    }
    
    public void setHostile (boolean hostile) {
    	this.hostile = hostile;
    }
}
