package llc.loading;

import llc.logic.Cell;

/**
 * Represents a loaded Grid
 */
public class Grid {
	
	private Cell[][] cells;
	
	public Grid(int height, int width) {
		cells = new Cell[height][width];
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
		return cells[x][y];
	}
	
	public void setCellAt(Cell c, int x, int y) {
		cells[x][y] = c;
	}
}
