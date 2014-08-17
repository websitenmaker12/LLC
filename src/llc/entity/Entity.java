package llc.entity;

import java.io.Serializable;
import java.util.List;

import llc.logic.Cell;
import llc.logic.Logic;

/**
 * The entity base class.
 * Contains player and health.
 * @author MaxiHoeve14
 */
public abstract class Entity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int health;
	public int maxHealth;
	
	private float x;
	private float y;
	
	private int player;
	
	// Vars for move animation
	private Logic logic;
	private List<Cell> path;
	private int currentPos;
	private long timeout;
	private int origX, origY;
	private boolean countLastMove;
	/**
	 * @return cost to pay for an Entity of this type
	 */
	public abstract int getCost();
	
	/**
	 * Creates a new entity with the amount of 100 health
	 */
	public Entity() {
		this.maxHealth = 100;
		this.health = maxHealth;
	}

	/**
	 * Creates a new entity with given amount of health
	 * @param maxHealth The amount of health
	 */
	public Entity(int health) {
		this.health = maxHealth;
	}
	
	/**
	 * Gets the player the entity belongs to.
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * Sets the player the entity belongs to.
	 * @param player The player
	 */
	public void setPlayer(int player) {
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
	
	/**
	 * Starts the move animation
	 */
	public void initMoveRoutine(Logic logic, List<Cell> path, boolean countMove) {
		this.logic = logic;
		this.path = path;
		this.currentPos = -1;
		this.timeout = 0L;
		this.origX = (int)this.x;
		this.origY = (int)this.y;
		this.countLastMove = countMove;
	}
	
	/**
	 * Updates the entity
	 */
	public void update(int delta) {
		if(this.logic == null || this.path == null) return;
		
		if((this.timeout += delta) % 4L == 0L) {
			this.currentPos++;
			if(this.currentPos >= this.path.size()) {
				this.logic.finishEntityMove(this.origX, this.origY, countLastMove);
				this.logic = null;
				return;
			}
			this.x = this.path.get(this.currentPos).x;
			this.y = this.path.get(this.currentPos).y;
		}
	}
}
