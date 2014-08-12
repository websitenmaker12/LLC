package llc.entity;

/**
 * The entity base class.
 * Contains team and health.
 * @author MaxiHoeve14
 */
public abstract class Entity {
	
	public int health;
	public final int maxHealth;
	
	private int team;
	
	public Entity() {
		this.maxHealth = 100;
	}

	/***
	 * Gets the entities team as an integer
	 * @return
	 */
	public int getTeam() {
		return team;
	}

	/***
	 * Sets the entities team
	 * @param team
	 */
	public void setTeam(int team) {
		this.team = team;
	}
}
