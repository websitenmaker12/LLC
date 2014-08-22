package llc.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import llc.logic.Cell;
import llc.logic.Logic;
import llc.logic.Player;
import llc.util.MathUtil;

import org.lwjgl.util.vector.Vector3f;

/**
 * The entity base class.
 * Contains player and health.
 * @author MaxiHoeve14
 */
public abstract class Entity {
	
	public int health;
	public int maxHealth;
	
	protected float x;
	protected float y;
	
	protected Player player;
	
	// Variables for move animation
	public Vector3f posVec;
	private Logic logic;
	private List<Vector3f> path = new ArrayList<Vector3f>();
	private int currentPos;
	private int origX, origY;
	private boolean countLastMove;
	private boolean shouldReturn;
	
	/**
	 * @return cost to pay for an Entity of this type
	 */
	public abstract int getCost();
	
	/**
	 * Creates a new entity with the amount of 100 health
	 */
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
		this.health = this.maxHealth = 100;
	}

	/**
	 * Creates a new entity with given amount of health
	 * @param maxHealth The amount of health
	 */
	public Entity(float x, float y, int health) {
		this.x = x;
		this.y = y;
		this.health = this.maxHealth = health;
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
	
	/**
	 * Starts the move animation
	 */
	public void initMoveRoutine(Logic logic, List<Cell> path, boolean countMove, boolean shouldReturn) {
		if(path == null) return;
		
		this.logic = logic;
		this.posVec = new Vector3f(this.x, this.y, 0);
		
		this.path.clear();
		for(Cell cell : path) this.path.add(cell.getCenterPos());
		
		this.currentPos = 0;
		this.origX = (int)this.x;
		this.origY = (int)this.y;
		this.countLastMove = countMove;
		this.shouldReturn = shouldReturn;
	}
	
	/**
	 * Updates the entity
	 */
	public void update(int delta) {
		if(this.logic == null || this.path == null) return;
		
		if(MathUtil.areEquals(this.posVec, this.path.get(this.currentPos), 0.1F)) {
			this.currentPos++;
			if(this.currentPos >= this.path.size()) {
				if(this.shouldReturn) {
					this.shouldReturn = false;
					Collections.reverse(this.path);
					this.path.add(new Vector3f(this.origX + 0.5F, this.origY + 0.5F, 0));
					this.currentPos = 0;
				} else {
					this.logic.finishEntityMove(this.origX, this.origY, this.countLastMove, this);
					this.logic = null;
					return;
				}
			}
		}
		
		this.posVec = MathUtil.minVector3f(MathUtil.lerpVector(this.posVec, this.path.get(this.currentPos), (float)delta / 100F), this.path.get(this.currentPos));
		this.x = (int)this.posVec.x;
		this.y = (int)this.posVec.y;
	}

	/**
	 * Returns the unlocalized name for this entity
	 */
	public abstract String getName();

}
