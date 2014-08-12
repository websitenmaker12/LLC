package llc.logic;

import llc.entity.Entity;

/**
 * 
 * @author MaxiHoeve14
 *
 */
public class Cell {
	
	private Entity entity;
	private CellType type;
	
	/***
	 * Gets the {@link Entity} that's on the cell.
	 * @return	the cell's entity
	 */
	public Entity getEntity() {
		return entity;
	}
	
	/***
	 * Sets the {@link Entity} on the cell.
	 * @param {@link Entity} to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	/***
	 * Gets the {@link CellType}}
	 * @return The entity
	 */
	public CellType getType() {
		return type;
	}
	
	/***
	 * Sets the {@link CellType}
	 * @param type
	 */
	public void setType(CellType type) {
		this.type = type;
	}
}
