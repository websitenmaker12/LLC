package llc.logic;

/**
 * Player class.
 * @author MaxiHoeve14
 */
public class Player {
	
	private int minerals;
	
	private Logic logic;
	
	/**
	 * Initializes the player with 0 minerals.
	 * @param logic The game logic class used in calculations
	 */
	public Player(Logic logic) {
		this.logic = logic;
		this.minerals = 0;
	}
	
	/**
	 * Initializes the player with given minerals.
	 * @param logic The game logic class used in calculations
	 * @param startMinerals The amount of minerals that is given to the player at the beginning of a game.
	 */
	public Player(Logic logic, int startMinerals) {
		this.logic = logic;
		this.minerals = startMinerals;
	}
	
	/**
	 * Is triggered when the players base is destroyed and tha game is over.
	 */
	public void onPlayerDeath() {
		
	}

	/**
	 * Gets the players minerals.
	 * @return Amount of minerals
	 */
	public int getMinerals() {
		return minerals;
	}

	/**
	 * Sets the players minerals.
	 * @param The amount of minerals.
	 */
	public void setMinerals(int minerals) {
		this.minerals = minerals;
	}
}
