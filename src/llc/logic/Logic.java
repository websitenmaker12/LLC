package llc.logic;

import java.util.ArrayList;
import java.util.List;

import llc.entity.Entity;
import llc.entity.EntityMovable;
import llc.entity.IAttacking;

public class Logic {

	private GameState gameState;
	private EntityMovable selectedEntity;

	public Logic(Grid grid) {
		this.setGameState(new GameState(grid));
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	private void clickCell(int clickX, int clickY) {
		Cell clickedCell = gameState.getGrid().getCellAt(clickX, clickY);
		if (clickedCell.containsEntity()) {
			if (clickedCell.getEntity().getPlayer() == gameState
					.getActivePlayer()) {
				// select
				selectEntity(clickedCell.getEntity());
			} else if (selectedEntity instanceof IAttacking) {
				// attack
				// attackCell(selectedEntity, clickX, clickY);
			}
		} else {
			// unselect
			unSelect();
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

	private void attackCell(EntityMovable entity, int destX, int destY) {
		moveSelectedEntity(entity, destX, destY + 1);
		// TODO
	}

	private void moveSelectedEntity(EntityMovable entity, int destX, int destY) {
		// TODO
	}

	/*
	 * TODO implement the event triggers from input.class
	 */
}
