package llc.logic;

import llc.entity.Entity;
import llc.entity.EntityMovable;

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

	private void clickCell(int x, int y) {
		Cell clickedCell = gameState.getGrid().getCellAt(x, y);
		if (clickedCell.containsEntity()) {
			selectEntity(clickedCell.getEntity());
		} else {
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

	private void attackCell(EntityMovable entity, int startX, int startY, int destX, int destY) {
		
	}

	private void moveSelectedEntity(EntityMovable entity, int startX, int startY, int destX, int destY) {

	}

	/**
	 * TODO implement the event triggers from {@link Input.class}
	 */
}
