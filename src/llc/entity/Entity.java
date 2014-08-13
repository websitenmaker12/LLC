package llc.entity;

import llc.logic.Player;

/**
 * The entity base class.
 * Contains player and health.
 * @author MaxiHoeve14
 */
public abstract class Entity {
	
	public int health;
	public int maxHealth;
	
	private float x;
	private float y;
	
	private Player player;
	
	/**
	 * Creates a new entity with the amount of 100 health
	 */
	public Entity() {
		this.maxHealth = 100;
	}

	/**
	 * Creates a new entity with given amount of health
	 * @param health The amount of health
	 */
	public Entity(int health) {
		this.health = health;
	}
	
	/**
	 * Gets the player the entity belongs to.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player the entity belongs to.
	 * @param player The player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Gets the entity x position
	 * @return X position
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Sets the entity x position
	 * @param x X position
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Gets the entity y position
	 * @return Y position
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the entity y position
	 * @param y Y position
	 */
	public void setY(float y) {
		this.y = y;
	}
}
