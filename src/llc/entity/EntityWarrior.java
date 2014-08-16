package llc.entity;

public class EntityWarrior extends EntityMovable implements IAttacking {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8L;
	@Override
	public int getAttackDamage() {
		return 50;
	}

	@Override
	public int getCost() {
		return 50;
	}
}
