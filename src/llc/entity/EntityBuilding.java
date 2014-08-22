package llc.entity;

/**
 * @author MaxiHoeve14
 */
public abstract class EntityBuilding extends Entity {

	public EntityBuilding(float x, float y, int health) {
		super(x, y, health);
	}

	public EntityBuilding(float x, float y) {
		super(x, y);
	}
}
