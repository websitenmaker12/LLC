package llc.entity;

public class EntityWarrior extends EntityMovable implements IAttacking {

	@Override
	public int getAttackDamage() {
		return 50;
	}
	
}
