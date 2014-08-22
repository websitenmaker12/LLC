package llc.logic;

import de.teamdna.databundle.DataBundle;
import llc.loading.ISavable;

/**
 * Player class.
 * @author MaxiHoeve14
 */
public class Player implements ISavable{

	private Cell townHall;
	private int minerals;
	private final String name;
	private final int playerID;
	
	/**
	 * Initializes the player with 100 minerals.
	 */
	public Player(String name, Cell townHall, int playerID) {
		this.playerID = playerID;
		this.townHall = townHall;
		this.name = name;
		this.minerals = 100;
		townHall.getEntity().setPlayer(this);
	}
	
	/**
	 * Initializes the player with given minerals.
	 * @param startMinerals The amount of minerals that is given to the player at the beginning of a game.
	 */
	public Player(String name, int startMinerals, Cell townHall, int playerID) {
		this.playerID = playerID;
		this.townHall = townHall;
		this.name = name;
		this.minerals = startMinerals;
		townHall.getEntity().setPlayer(this);
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

	/**
	 * Adds minerals to the player
	 * @param The amount of minerals to add.
	 */
	public void addMinerals(int minerals) {
		this.minerals += minerals;
	}

	/**
	 * Removes minerals from the player
	 * @param The amount of minerals to remove.
	 */
	public void removeMinerals(int minerals) {
		this.minerals -= minerals;
	}

	public String getName() {
		return name;
	}

	@Override
	public DataBundle writeToDataBundle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readFromDataBundle(DataBundle data) {
		// TODO Auto-generated method stub
		
	}

	public Cell getTownHall() {
		return townHall;
	}

	public int getPlayerID() {
		return playerID;
	}
}
