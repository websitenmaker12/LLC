package llc.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import llc.logic.Cell;
import llc.logic.CellType;
import llc.logic.Grid;

/**
 * Util class implementing an A*-pathfinding
 * @author erdlof
 */

public class PathFinder {
	private static final int CONST_COST = 10;
	private static final int CONST_COST_DIAGONAL = 14;
	
	public static List<Cell> findPath(Grid grid, Cell from, Cell to) {
		List<Cell> openCells = new ArrayList<Cell>();
		List<Cell> closedCells = new ArrayList<Cell>();
		
		from.pathfFValue = manhattanDistance(from, to);
		from.pathfOriginCell = from;
		from.counter = 0;
		
		openCells.add(from);
		
		while (true) {
			if(openCells.size() == 0) break;

			Cell lowestFValue = openCells.get(0);
			if(openCells.size() > 1) for(Cell cell : openCells) if(cell.pathfFValue < lowestFValue.pathfFValue && !closedCells.contains(cell)) lowestFValue = cell;
			
			for (Cell cell : getNeighbours(lowestFValue, grid)) {
				if (!openCells.contains(cell) && !closedCells.contains(cell)) {
					cell.pathfOriginCell = lowestFValue;
					cell.counter = lowestFValue.counter + 1;
					cell.pathfFValue = manhattanDistance(cell, to) + (isDiagonalNeighbour(lowestFValue, cell) ? CONST_COST_DIAGONAL : CONST_COST) * cell.counter;
				
					openCells.add(cell);
					
					if (cell == to) {
						List<Cell> finalPath = new ArrayList<Cell>();
						
						Cell node = cell;
						
						do {
							finalPath.add(node);
							node = node.pathfOriginCell;
						} while (node != from);
						
						Collections.reverse(finalPath);
						return finalPath;
					}
				}
			}

			openCells.remove(lowestFValue);
			closedCells.add(lowestFValue);
		}
		
		return null;
	}
	
	/**
	 * Gets all cells that are inside the grid
	 * @param cell
	 * @param grid
	 * @return finalCells as List<Cell>
	 */
	private static List<Cell> getNeighbours(Cell cell, Grid grid) {
		List<Cell> finalCells = new ArrayList<Cell>();
		
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				Cell iCell = grid.getCellAt(cell.x + x, cell.y + y);
				if(iCell != cell && iCell != null && iCell.x >= 0 && iCell.y >= 0 && iCell.y < grid.getHeigth() && iCell.x < grid.getWidth()
						&& iCell.getType() == CellType.WALKABLE && !iCell.containsEntity()) finalCells.add(iCell);
			}
		}

		return finalCells;
	}
	
	private static boolean isDiagonalNeighbour(Cell cell, Cell neighbourCell) {
		return (cell.x != neighbourCell.x && cell.y != neighbourCell.y);
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