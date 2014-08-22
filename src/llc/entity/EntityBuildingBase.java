package llc.entity;

/**
 * @author MaxiHoeve14
 */
public class EntityBuildingBase extends EntityBuilding {

	public EntityBuildingBase(float x, float y) {
		super(x, y, 250);
	}

	@Override
	public int getCost() {
		return 0;
	}

	@Override
	public String getName() {
		return "entity.base.name";
	}
}
