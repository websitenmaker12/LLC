package llc.logic;

/**
 * @author MaxiHoeve14
 */
public class Player {
	
	private int baseXPos;
	private int baseYPos;
	
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

	public int getBaseXPos() {
		return baseXPos;
	}

	public void setBaseXPos(int baseXPos) {
		this.baseXPos = baseXPos;
	}

	public int getBaseYPos() {
		return baseYPos;
	}

	public void setBaseYPos(int baseYPos) {
		this.baseYPos = baseYPos;
	}

	public int getMinerals() {
		return minerals;
	}

	public void setMinerals(int minerals) {
		this.minerals = minerals;
	}
}
