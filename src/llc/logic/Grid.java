package llc.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import llc.entity.Entity;
import de.teamdna.databundle.DataBundle;
import de.teamdna.databundle.ISavable;

/**
 * Represents a loaded Grid
 */
public class Grid {
	
	private final int heigth;
	private final int width;
	
	private List<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * Two dimensional array of cells. The first index represents the y-axis, the second one the x-axis
	 * Starts at 0!
	 */
	private Cell[][] cells;
	
	public Grid(int height, int width) {
		cells = new Cell[height][width];
		this.heigth = height;
		this.width = width;
	}
	
	/**
	 * Gets all the cells in this grid
	 * @return all cells
	 */
	public Cell[][] getCells() {
		return cells;
	}
	/**
	 * Gets the cell at the given coordinates
	 * @param x the x-value
	 * @param y the y-value
	 * @return the cell
	 */
	public Cell getCellAt(int x, int y) {
		if(x < 0 || y < 0 || x >= this.width || y >= this.heigth) return null;
		return cells[y][x];
	}
	
	public void setCellAt(Cell c, int x, int y) {
		cells[y][x] = c;
	}

	public int getHeigth() {
		return heigth;
	}

	public int getWidth() {
		return width;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	public void save(DataBundle data) {
		data.setInt("entitiesSize", entities.size());
		for (int i = 0; i < entities.size(); i++){
			data.setBundle("entity" + i, entities.get(i).writeToDataBundle());
		}
	}

	public void read(DataBundle data, List<Player> players) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (int i = 0; i < data.getInt("entitiesSize"); i++) {
			Entity e = (Entity) Class.forName(data.getBundle("entity" + i).getString("type").substring(6)).asSubclass(Entity.class).getConstructor( DataBundle.class, List.class ).newInstance(new Object[] { data.getBundle("entity" + i), players });
			getCellAt((int) e.getX(), (int) e.getY()).setEntity(e);
		}
	}
}
