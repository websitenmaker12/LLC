package llc.entity;

/**
 * Base class for movable entities.
 * @author MaxiHoeve14
 */
public abstract class EntityMovable extends Entity {

	private float moveSpeed = 1;

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
}
