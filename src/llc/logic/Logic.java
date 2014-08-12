package llc.logic;

import java.util.ArrayList;
import java.util.List;

import llc.entity.Entity;
import llc.entity.EntityMovable;

public class Logic {

	private GameState gameState;
	private EntityMovable selectedEntity;
	
	private Player player1;
	private Player player2;
	
	public Logic(Grid grid) {
		this.setGameState(new GameState(grid));
		player1 = new Player();
		player2 = new Player();
	}
	
	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	private void clickCell(int x, int y) {
		Cell clickedCell = gameState.getGrid().getCellAt(x, y);
		if (selectedEntity != null) {
			
		} else if (clickedCell.containsEntity() && clickedCell.getEntity().getTeam() == ) {
			
		}
	}

	private void selectEntity(Entity toSelect) {
		if (toSelect instanceof EntityMovable) {
			this.selectedEntity = (EntityMovable) toSelect;
		}
	}

	private void unSelect() {
		this.selectedEntity = null;
	}

	private void attackCell(EntityMovable entity, int startX, int startY, int destX, int destY) {
		moveSelectedEntity(entity, startX, startY, destX, destY);
		//TODO
	}

	private void moveSelectedEntity(EntityMovable entity, int startX, int startY, int destX, int destY) {
		//TODO
	}

	/*
	 * TODO implement the event triggers from input.class
	 */
}
