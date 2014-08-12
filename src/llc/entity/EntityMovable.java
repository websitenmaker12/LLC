package llc.entity;

/**
 * Base class for movable entities.
 * @author MaxiHoeve14
 */
public abstract class EntityMovable extends Entity {

	private float moveSpeed = 1;
	private int moveRange = 4;

	/**
	 * Gets the {@link EntityMoveable} move speed.
	 * @return Move Speed
	 */
	public float getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * Sets the {@link EntityMoveable} move speed.
	 * @param moveSpeed The move speed (Standard is 1)
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	/**
	 * Gets the {@link EntityMoveable} move range.
	 * @return Move range
	 */
	public float getMoveRange() {
		return moveRange;
	}

	/**
	 * Sets the {@link EntityMoveable} move range.
	 * @param moveRange The move range (Standard is 4)
	 */
	public void setMoveRange(int moveRange) {
		this.moveRange = moveRange;
	}
}
