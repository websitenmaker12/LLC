package llc.entity;

public class EntityWarrior extends EntityMovable implements IAttacking {

	public static final int cost = 50;
	
	@Override
	public int getAttackDamage() {
		return 50;
	}
	
}
