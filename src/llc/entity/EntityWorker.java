package llc.entity;

public class EntityWorker extends EntityMovable implements Repairer{

	private static final long serialVersionUID = 9L;
	public static final int cost = 25;

	@Override
	public int getCost() {
		return 40;
	}
	
	@Override
	//It heals so much so that the enemy has to kill the workers first
	public int getRepairHealth() {
		return 60;
	}

	@Override
	public int getRepairCost() {
		return 20;
	}
}
