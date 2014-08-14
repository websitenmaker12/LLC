package llc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import llc.entity.Entity;
import llc.logic.Cell;
import llc.logic.CellType;
import llc.logic.Grid;

public class Pathfinder {
	
	private Cell from, to;
	private Entity ignore;
	private Grid g;
	
	private List<Cell> open = new ArrayList<Cell>();
	private List<Cell> closed = new ArrayList<Cell>();
	
	public Pathfinder(Grid g, Cell from, Cell to) {
		this.g = g;
		this.from = from;
		this.to = to;
		
	}
	public void setIgnoredEntity(Entity en) {
		ignore = en;
	}
	public List<Cell> aStar() {
		boolean found = false;
		open.add(from);
		closed.clear();
		
		//While there still is a way and whe haven't found a solution yet, repeat
		while (open.size() > 0 || !(found)) {
			//Get all neighbors
			Cell[] neighbors = getNeighbors(open.get(open.size()-1));
			//Nearest, possible step
			List<Cell> possible = new ArrayList<Cell>(Arrays.asList(neighbors));
			for (Cell c : neighbors) {
				//Walkable?
				if (closed.contains(c)) {
					continue;
				}
				if (c.getType() == CellType.SOLID) {
					continue;
				}
				//If the cell has an entity and that entity is not ignored
				if (c.containsEntity() && c.getEntity() != ignore) {
					continue;
				}
				possible.add(c);
			}
			//Nothing found? Then there is no way
			if (possible.size() == 0) {
				open.clear();
				break;
			}
			//Only possible cell is already in the way? Block Cell
			if (possible.size() == 1 && open.contains(possible.get(0))) {
				closed.add(possible.get(0));
			}
			Cell closest = possible.get(0);
			for (Cell c : possible) {
				if (manhattanDistance(c) < manhattanDistance(closest)) {
					closest = c;
				}
			}
			System.out.println("Closest: (" + closest.x + "|" + closest.y + ")");
			open.add(closest);
		}
		if (open.isEmpty()) {
			throw new IllegalStateException("Impossible way!");
		}
		return open;
	}
	private Cell[] getNeighbors(Cell c) {
		System.out.println("(" + c.x + "|" + c.y + ")");
		//Is the cell at the border?
		int width = g.getWidth();
		int height = g.getHeigth();
		
		if (c.x == 0) {
			//Left
			if (c.y == 0) {
				//Bottom-Left corner
				Cell[] cells = new Cell[2];
				cells[0] = g.getCellAt(c.x, c.y+1);
				cells[1] = g.getCellAt(c.x+1, c.y);
				return cells;
			}
			else if (c.y == height) {
				//Top-Left corner
				Cell[] cells = new Cell[2];
				cells[0] = g.getCellAt(c.x+1, c.y);
				cells[1] = g.getCellAt(c.x, c.y-1);
				return cells;
			}
			else {
				//Not a corner, but left border
				Cell[] cells = new Cell[3];
				cells[0] = g.getCellAt(c.x+1, c.y);
				cells[1] = g.getCellAt(c.x, c.y+1);
				cells[2] = g.getCellAt(c.x, c.y-1);
			}
		}
		if (c.x == width) {
			//Right
			if (c.y == 0) {
				//Bottom-Right corner
				Cell[] cells = new Cell[2];
				cells[0] = g.getCellAt(c.x, c.y+1);
				cells[1] = g.getCellAt(c.x-1, c.y);
				return cells;
			}
			else if (c.y == height) {
				//Top-Right corner
				Cell[] cells = new Cell[2];
				cells[0] = g.getCellAt(c.x-1, c.y);
				cells[1] = g.getCellAt(c.x, c.y-1);
				return cells;
			}
			else {
				//Not a corner, but right border
				Cell[] cells = new Cell[3];
				cells[0] = g.getCellAt(c.x-1, c.y);
				cells[1] = g.getCellAt(c.x, c.y+1);
				cells[2] = g.getCellAt(c.x, c.y-1);
			}
		}
		if (c.y == 0) {
			//Bottom
			//Bottom-Right and Bottom-Left Corner already checked
			Cell[] cells = new Cell[3];
			cells[0] = g.getCellAt(c.x-1, c.y);
			cells[0] = g.getCellAt(c.x+1, c.y);
			cells[0] = g.getCellAt(c.x, c.y+1);
			return cells;
		}
		if (c.y == height) {
			//Top
			//Top-Right and Top-Left Corner already checked
			Cell[] cells = new Cell[3];
			cells[0] = g.getCellAt(c.x-1, c.y);
			cells[0] = g.getCellAt(c.x+1, c.y);
			cells[0] = g.getCellAt(c.x, c.y-1);
			return cells;
		}
		else {
			//Not border
			Cell[] cells = new Cell[4];
			cells[0] = g.getCellAt(c.x+1, c.y);
			cells[1] = g.getCellAt(c.x-1, c.y);
			cells[2] = g.getCellAt(c.x, c.y+1);
			cells[3] = g.getCellAt(c.x, c.y-1);
			return cells;
		}
	}
	
	private float manhattanDistance(Cell from) {
		int dx = Math.abs(to.x - from.x);
		int dy = Math.abs(to.y - from.y);
		
		return dx+dy; 
	}
	public boolean isReachable(int maxDistance) {
		return false;
	}
	public Cell getWayByMaxDistance(int maxDistance) {
		return null;
	}
}
