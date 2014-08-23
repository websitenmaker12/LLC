package llc.entity;

import java.util.List;

import llc.logic.Player;
import de.teamdna.databundle.DataBundle;

/**
 * @author MaxiHoeve14
 */
public abstract class EntityBuilding extends Entity {

	public EntityBuilding(float x, float y, int health) {
		super(x, y, health);
	}

	public EntityBuilding(DataBundle data, List<Player> players) {
		super(data, players);
	}

	public EntityBuilding(float x, float y) {
		super(x, y);
	}
}
