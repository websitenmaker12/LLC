package llc.logic;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector3f;

import llc.entity.Entity;

/**
 * The cell class. Contains the entity and can be solid or walkable.
 * @author MaxiHoeve14
 */
public class Cell implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Entity entity;
	private CellType type;
	
	public Cell pathfOriginCell;
	public int pathfFValue;
	public int counter;
	
	public final int x,y;
	public final float height;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param height between 1 and -1
	 */
	public Cell(int x, int y, float height) {
		this.x = x;
		this.y = y;
		this.height = height;
		if (height < 0) {
			type = CellType.SOLID;
		}
		if (height >= 0) {
			type = CellType.WALKABLE;
		}
	}
	
	/**
	 * Returns true if there is an entity on the cell and false if the cell is empty.
	 * @return If there is an entity on the cell.
	 */
	public boolean containsEntity() {
		return entity != null;
	}
	
	/**
	 * Gets the {@link Entity} that's on the cell.
	 * @return	the cell's entity
	 */
	public Entity getEntity() {
		return entity;
	}
	
	/**
	 * Sets the {@link Entity} on the cell.
	 * @param {@link Entity} to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	/**
	 * Gets the {@link CellType}}
	 * @return The entity
	 */
	public CellType getType() {
		return type;
	}
	
	/**
	 * Sets the {@link CellType}
	 * @param type
	 */
	@Deprecated
	public void setType(CellType type) {
		this.type = type;
	}
	public float getHeight() {
		return height;
	}

	/**
	 * Returns the center point of the cell
	 */
	public Vector3f getCenterPos() {
		return new Vector3f(this.x + 0.5F, this.y + 0.5F, this.height);
	}
}
