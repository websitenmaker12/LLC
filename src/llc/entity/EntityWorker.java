package llc.entity;

public class EntityWorker extends EntityMovable implements IRepairer {

	public EntityWorker(float x, float y, int health) {
		super(x, y, health);
	}

	public EntityWorker(float x, float y) {
		super(x, y);
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