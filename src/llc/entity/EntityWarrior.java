package llc.entity;

public class EntityWarrior extends EntityMovable implements IAttacking {

	public EntityWarrior(float x, float y, int health) {
		super(x, y, health);
	}

	public EntityWarrior(float x, float y) {
		super(x, y);
	}
	
	@Override
	public int getAttackDamage() {
		return 50;
	}

	@Override
	public int getCost() {
		return 50;
	}
	
	@Override
	public String getName() {
		return "entity.warrior.name";
	}
	
}
