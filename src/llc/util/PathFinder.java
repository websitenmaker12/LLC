package llc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import llc.logic.Cell;
import llc.logic.CellType;
import llc.logic.Grid;

/**
 * Util class implementing an A*-pathfinding
 * @author erdlof
 */

public class PathFinder {
	private static final int CONST_COST = 10;
	
	public static List<Cell> findPath(Grid grid, Cell from, Cell to) {
		
		List<Cell> openCells = new ArrayList<Cell>();
		List<Cell> closedCells = new ArrayList<Cell>();
		
		from.pathfFValue = manhattanDistance(from, to);
		from.pathfOriginCell = from;
		from.counter = 0;
		
		openCells.add(from);
		
		while (true) {
			Cell lowestFValue;
			
			if (openCells.size() > 1) {
				lowestFValue = openCells.get(0);
				
				for (Cell cell : openCells) {
					if (cell.pathfFValue < lowestFValue.pathfFValue) {
						lowestFValue = cell;
					}
				}
			} else {
				lowestFValue = openCells.get(0);
			}
			
			for (Cell cell : getNeighbours(lowestFValue, grid)) {
				if (!openCells.contains(cell) && !closedCells.contains(cell)) {
					cell.pathfOriginCell = lowestFValue;
					cell.counter = lowestFValue.counter + 1;
					cell.pathfFValue = manhattanDistance(cell, to) + CONST_COST*cell.counter;
				
					openCells.add(cell);
					openCells.remove(lowestFValue);
					closedCells.add(lowestFValue);
					
					if (cell == to) {
						List<Cell> finalPath = new ArrayList<Cell>();
						
						Cell node = cell;
						
						do {
							finalPath.add(node);
							node = node.pathfOriginCell;
						} while (node == from);
						
						Collections.reverse(finalPath);
						return finalPath;
					}
				}
			}
			
			
		}
		
		
	}
	
	/**
	 * Gets all cells that are inside the grid
	 * @param cell
	 * @param grid
	 * @return finalCells as List<Cell>
	 */
	private static List<Cell> getNeighbours(Cell cell, Grid grid) {
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
				CellType isWalkable = startCells[i].getType();
				if (x >= 0 && y >= 0 && y < grid.getHeigth() && x < grid.getWidth() && isWalkable == CellType.WALKABLE) {
					finalCells.add(startCells[i]);
				}
			}
		}
		
		return finalCells;
	}
	
	/**
	 * Gets the manhattan distance (Δx + Δy)
	 * @param from
	 * @param to
	 * @return distFinal as int (final manhattan distance)
	 */
	private static int manhattanDistance(Cell from, Cell to) {
		int distFinal = Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
		
		return distFinal;
	}
	
}