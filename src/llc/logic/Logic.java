package llc.logic;

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
			if (clickedCell.getEntity().getPlayer() == gameState.getActivePlayer()) {
				// select
				selectEntity(clickedCell.getEntity());
			} else if (selectedEntity instanceof IAttacking) {
				// attack
				attackCell( clickX, clickY);
			}
		} else if (clickedCell.getType() == CellType.WALKABLE) {
			//move
			moveSelectedEntity(clickX, clickY);
		}
	}

	private void selectEntity(Entity toSelect) {
		if (toSelect instanceof EntityMovable) {
			this.selectedEntity = (EntityMovable) toSelect;
		}
	}

	private void attackCell(int destX, int destY) {
		Entity destEntity = gameState.getGrid().getCellAt(destX, destY).getEntity();
		if (destEntity.health > 0) {
			destEntity.health -= ((IAttacking) selectedEntity).getAttackDamage();

			if (destEntity.health <= 0) {
				moveSelectedEntity(destX, destY);
			}
		} else {
			moveSelectedEntity(destX, destY);
		}
	}

	private void moveSelectedEntity(int destX, int destY) {
		gameState.getGrid().getCellAt(destX, destY).setEntity(selectedEntity);
		gameState.getGrid().getCellAt((int) selectedEntity.getX(), (int) selectedEntity.getY()).setEntity(null);
		selectedEntity.setX(destX);
		selectedEntity.setY(destY);
	}

	/*
	 * TODO implement the event triggers from input.class
	 */

	private void gameOver(Player winner) {
		// TODO
	}
}
