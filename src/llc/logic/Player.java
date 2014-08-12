package llc.logic;

/**
 * Player class.
 * @author MaxiHoeve14
 */
public class Player {
	
	private int minerals;
	
	private Logic logic;
	
	
	public Player(Logic logic) {
		this.logic = logic;
		this.minerals = 0;
	}
	
	public Player(Logic logic, int startMinerals) {
		this.logic = logic;
		this.minerals = startMinerals;
	}
	
	public void onPlayerDeath() {
		
	}

	public int getMinerals() {
		return minerals;
	}

	public void setMinerals(int minerals) {
		this.minerals = minerals;
	}
}
