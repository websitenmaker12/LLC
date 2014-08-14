package llc.logic;

/**
 * Player class.
 * @author MaxiHoeve14
 */
public class Player {
	
	private int minerals;
	
	public int playerID;
	
	/**
	 * Initializes the player with 0 minerals.
	 */
	public Player(int playerID) {
		this.minerals = 0;
		this.playerID = playerID;
	}
	
	/**
	 * Initializes the player with given minerals.
	 * @param startMinerals The amount of minerals that is given to the player at the beginning of a game.
	 */
	public Player(int playerID, int startMinerals) {
		this.playerID = playerID;
		this.minerals = startMinerals;
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
		System.out.println("Player " + this.playerID + " has " + this.minerals + " minerals left.");
	}

	/**
	 * Adds minerals to the player
	 * @param The amount of minerals to add.
	 */
	public void addMinerals(int minerals) {
		this.minerals += minerals;
		System.out.println("Player " + this.playerID + " has " + this.minerals + " minerals left.");
	}

	/**
	 * Removes minerals from the player
	 * @param The amount of minerals to remove.
	 */
	public void removeMinerals(int minerals) {
		this.minerals -= minerals;
		System.out.println("Player " + this.playerID + " has " + this.minerals + " minerals left.");
	}
}
