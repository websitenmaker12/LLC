package llc.logic;

import java.io.Serializable;


/**
 * Represents a loaded Grid
 */
public class Grid implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	private final int heigth;
	private final int width;
	
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
}
