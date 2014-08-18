package llc.entity;

public class EntityWorker extends EntityMovable implements IRepairer {

	private static final long serialVersionUID = 9L;
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
		return 20;
	}
}
