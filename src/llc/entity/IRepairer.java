package llc.entity;

/**
 * @author simolus3
 */
public interface IRepairer {
	
	/**
	 * Gets the amount of health this repairer can restore in one move
	 */
	public int getRepairHealth();
	public int getRepairCost();
}
