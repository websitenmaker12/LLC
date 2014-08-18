package llc.entity;

/**
 * @author simolus3
 */
public interface Repairer {
	/**
	 * Gets the amount of health this repairer can restore in one move
	 * @return
	 */
	public int getRepairHealth();
	public int getRepairCost();
}
