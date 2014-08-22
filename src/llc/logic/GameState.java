package llc.logic;

import java.io.File;
import java.io.Serializable;

import llc.LLC;
import llc.engine.Camera;
import llc.entity.EntityBuildingBase;

public class GameState implements Serializable {

	private static final long serialVersionUID = 4L;
	
	private Grid grid;
	private Camera camera;
	
	private Player player1, player2;
	private Cell townHall1, townHall2;
	public Cell hoveredCell, selectedCell;
	
	public int activePlayer;
	public int moveCount = 0;
	
	public final String levelName;
	public final String levelPath;
	
	public boolean isGameOver = false;
	public Player winner;
	
	public GameState(Grid grid, Camera camera, File level) {
		this.grid = grid;
		this.camera = camera;
		
		player1 = new Player(1, 100);
		player2 = new Player(2, 100);
		
		this.levelName = level.getName();
		this.levelPath = level.getPath();
	}
	
	public Grid getGrid() {
		return grid;
	}

	public void setActivePlayer(Player active) {
		if (player1.equals(active)) {
			activePlayer = 1;
		} else if (player2.equals(active)) {
			activePlayer = 2;
		} else {
			throw new IllegalArgumentException("Given Player argument does not exist");
		}
		
		//If the settings are loaded and deny townhall focus switching, don't do it...
		if (LLC.getLLC().getSettings() == null || LLC.getLLC().getSettings().getFocusBaseOnPlayerToggle()) {
			this.camera.focusCell(this.getActivePlayerTownHallLocation(), true);
		}
	}
	public void focusCell(Cell c, boolean animate) {
		this.camera.focusCell(c, animate);
	}
	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	public Player getActivePlayer() {
		if (activePlayer == 1) {
			return player1;
		} else {
			return player2;
		}
	}
	public Player getInActivePlayer() {
		if (activePlayer == 1) {
			return player2;
		} else {
			return player1;
		}
	}

	public Cell getTownHall1Cell() {
		return townHall1;
	}

	public void setTownHall1Cell(Cell townHall1) {
		this.townHall1 = townHall1;
	}

	public Cell getTownHall2Cell() {
		return townHall2;
	}

	public void setTownHall2Cell(Cell townHall2) {
		this.townHall2 = townHall2;
	}
	
	public Cell getActivePlayerTownHallLocation() {
		if (activePlayer == 1) {
			return townHall1;
		}
		else {
			return townHall2;
		}
	}
	public EntityBuildingBase getActivePlayerTownHall() {
		if (activePlayer == 1) {
			return (EntityBuildingBase) townHall1.getEntity();
		} else {
			return (EntityBuildingBase) townHall2.getEntity();
		}
	}
}
