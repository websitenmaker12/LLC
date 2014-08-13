package llc.util;

import java.util.ArrayList;
import java.util.List;

import llc.entity.Entity;
import llc.logic.Cell;
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
		
		aStar();
	}
	public void setIgnoredEntity(Entity en) {
		ignore = en;
	}
	private void aStar() {
		boolean found = false;
		open.add(from);
		closed.clear();
		
		//While there still is a way and whe haven't found a solution yet, repeat
		while (open.size() > 0 || !(found)) {
			//First, select the most likely next step
			
		}
	}
	private Cell[] getNeighbours(Cell c) {
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
		}
		if (c.y == height) {
			//Top
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
		return null;
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
