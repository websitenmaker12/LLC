package llc.logic;

import llc.entity.Entity;
import llc.entity.EntityBuildingBase;
import llc.entity.EntityMovable;
import llc.entity.IAttacking;

public class Logic {

	private GameState gameState;
	private EntityMovable selectedEntity;

	public Logic(GameState state) {
		this.setGameState(state);
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public void clickCell(int clickX, int clickY) {
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
			moveSelectedEntity(clickX, clickY, true);
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
			// do damage
			destEntity.health -= ((IAttacking) selectedEntity).getAttackDamage();

			if (destEntity.health <= 0) {
				moveSelectedEntity(destX, destY, false);
				// if a base was destroyed, the game is over
				if (destEntity instanceof EntityBuildingBase) gameState.isGameOver = true;
			}
		}
		countMove();
	}

	private void moveSelectedEntity(int destX, int destY, boolean countMove) {
		gameState.getGrid().getCellAt(destX, destY).setEntity(selectedEntity);
		gameState.getGrid().getCellAt((int) selectedEntity.getX(), (int) selectedEntity.getY()).setEntity(null);
		selectedEntity.setX(destX);
		selectedEntity.setY(destY);
		if (countMove) countMove();
	}
	
	private void countMove() {
		gameState.moveCount++;
		if (gameState.moveCount >= 1) {
			gameState.setActivePlayer(gameState.getInActivePlayer());
			gameState.moveCount = 0;
		}
	}
}