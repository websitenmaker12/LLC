package llc.entity;

import java.util.List;

import llc.logic.Player;
import de.teamdna.databundle.DataBundle;

public class EntityWorker extends EntityMovable implements IRepairer {

	public EntityWorker(float x, float y, int health) {
		super(x, y, health);
	}

	public EntityWorker(float x, float y) {
		super(x, y);
	}

	public EntityWorker(DataBundle data, List<Player> players) {
		super(data, players);
	}

	public static final int cost = 25;

	@Override
	public int getCost() {
		return 40;
	}
	
	@Override
	public int getRepairHealth() {
		return 60;
	}

	@Override
	public int getRepairCost() {
		return 100;
	}
	
	@Override
	public String getName() {
		return "entity.worker.name";
	}
	
}