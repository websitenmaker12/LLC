package llc.entity;

import java.util.List;

import llc.logic.Player;
import de.teamdna.databundle.DataBundle;

/**
 * @author MaxiHoeve14
 */
public class EntityBuildingBase extends EntityBuilding {

	public EntityBuildingBase(float x, float y) {
		super(x, y, 250);
	}

	public EntityBuildingBase(DataBundle data, List<Player> players) {
		super(data, players);
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
