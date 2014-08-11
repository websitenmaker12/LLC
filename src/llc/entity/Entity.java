package llc.entity;

public abstract class Entity {
	
	public int health;
	public final int maxHealth;
	
	private int team;
	
	public Entity() {
		this.maxHealth = 100;
	}

	public int getTeam() {
		return team;
	}

	/***
	 * 
	 * @param team
	 */
	public void setTeam(int team) {
		this.team = team;
	}
	
	public void Attack(int attackingTeam, int damage) {
		
	}
}
