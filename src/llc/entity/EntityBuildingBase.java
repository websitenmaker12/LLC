package llc.entity;

/**
 * @author MaxiHoeve14
 */
public class EntityBuildingBase extends EntityBuilding {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;

	public EntityBuildingBase() {
		this.maxHealth = 250;
		this.health = maxHealth;
	}

	@Override
	public int getCost() {
		return 0;
	}
}
