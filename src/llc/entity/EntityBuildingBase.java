package llc.entity;

/**
 * @author MaxiHoeve14
 */
public class EntityBuildingBase extends EntityBuilding {
	
	public EntityBuildingBase() {
		this.maxHealth = 250;
	}

	@Override
	public int getCost() {
		return 0;
	}
}
