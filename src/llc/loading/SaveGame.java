package llc.loading;

import java.util.List;

/**
 * Represents a saved game which can be used to load/save the map and it's entites
 *
 */
public class SaveGame {
	
	private String mapName;
	private List entities;
	
	
	public SaveGame(String mapName, List entities) {
		this.mapName = mapName;
		this.entities = entities;
	}
	
	/**
	 * Gets the list of entities in this world
	 * @return The entities, inside an ArrayList
	 */
	public List getEntities() {
		return entities;
	}
	/**
	 * Gets the map-name of this world.
	 * @return
	 */
	public String getMapName() {
		return mapName;
	}
}
