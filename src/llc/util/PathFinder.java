package llc.util;

import java.util.ArrayList;
import java.util.List;

import llc.logic.Cell;
import llc.logic.Grid;

/**
 * Util class implementing an A*-pathfinding
 * @author erdlof
 * @author simolus3
 */

public class PathFinder {
	public Cell[] findPath(Grid grid, Cell from, Cell to) {
		List<Cell> openCells = new ArrayList<Cell>();
		List<Cell> closedCells = new ArrayList<Cell>();
		
		openCells.add(from);
		
		
		return null;
		
	}
	
	/**
	 * Gets all cells that are inside the grid
	 * @param cell
	 * @param grid
	 * @return
	 */
	private List<Cell> getNeighbours(Cell cell, Grid grid) {
		Cell[] startCells = new Cell[4];
		List<Cell> finalCells = new ArrayList<Cell>();
		
		startCells[0] = grid.getCellAt(cell.x, cell.y + 1);
		startCells[1] = grid.getCellAt(cell.x + 1, cell.y);
		startCells[2] = grid.getCellAt(cell.x, cell.y - 1);
		startCells[3] = grid.getCellAt(cell.x - 1, cell.y);
		
		for (int i = 0; i <= startCells.length - 1; i++) {
			if (startCells[i] != null) {
				int x = startCells[i].x;
				int y = startCells[i].y;
				if (x >= 0 && y >= 0 && y < grid.getHeigth() && x < grid.getWidth()) {
					finalCells.add(startCells[i]);
				}
			}
		}
		
		return finalCells;
	}
	
	
}
