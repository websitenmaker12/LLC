package llc.logic;

import llc.entity.Entity;
import llc.entity.EntityBuildingBase;
import llc.entity.EntityMovable;
import llc.entity.IAttacking;

/**
 * Logic class
 * handles changes to the gamestate
 * @author PetaByteBoy
 */
public class Logic {

	private GameState gameState;
	private EntityMovable selectedEntity;

	public Logic(GameState state) {
		this.setGameState(state);
	}

	/**
	 * Gets the current {@link GameState}.
	 * @return Current {@link GameState}
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Sets the {@link GameState}
	 * @param gameState The new gameState
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * This method is called when a player clicks at a cell on the grid.
	 * It decides whether the clicked cell contains an entity that can be attacked
	 * or it contains an entity that can be selected. 
	 * @param clickX The x coord of the clicked cell.
	 * @param clickY The y coord of the clicked cell.
	 */
	public void clickCell(int clickX, int clickY) {
		
		if (0 <= clickY && clickY <= gameState.getGrid().getHeigth() && 0 <= clickX && clickX <= gameState.getGrid().getWidth()) {
			Cell clickedCell = gameState.getGrid().getCellAt(clickX, clickY);
			if (clickedCell.containsEntity()) {
				if (clickedCell.getEntity().getPlayer() == gameState.activePlayer) {
					// select
					selectEntity(clickedCell.getEntity());
				} else if (selectedEntity instanceof IAttacking && selectedEntity.isCellInRange(clickX, clickY)) {
					// attack
					attackCell( clickX, clickY);
				}
			} else if (clickedCell.getType() == CellType.WALKABLE && selectedEntity != null && selectedEntity.isCellInRange(clickX, clickY)) {
				//move
				moveSelectedEntity(clickX, clickY, true);
			}
		}
	}

	/**
	 * This method selects a given entity for later work.
	 * @param toSelect The entity to be selected.
	 */
	private void selectEntity(Entity toSelect) {
		if (toSelect instanceof EntityMovable) {
			this.selectedEntity = (EntityMovable) toSelect;
		}
	}

	/**
	 * This method is called from clickCell() and attacks the entity at given coords and
	 * checks if the game is over.
	 * @param destX The x coord of the cell.
	 * @param destY The y coord of the cell.
	 */
	private void attackCell(int destX, int destY) {
		Entity destEntity = gameState.getGrid().getCellAt(destX, destY).getEntity();
		if (destEntity.health > 0) {
			// do damage
			destEntity.health -= ((IAttacking) selectedEntity).getAttackDamage();

			if (destEntity.health <= 0) {
				moveSelectedEntity(destX, destY, false);
				gameState.getActivePlayer().addMinerals(25);
				// if a base was destroyed, the game is over
				if (destEntity instanceof EntityBuildingBase) gameState.isGameOver = true;
			}
		}
		countMove();
	}

	/**
	 * This method moves an entity
	 * @param destX The new x position
	 * @param destY The new y position
	 * @param countMove Does the move count as player action.
	 */
	private void moveSelectedEntity(int destX, int destY, boolean countMove) {
		gameState.getGrid().getCellAt(destX, destY).setEntity(selectedEntity);
		gameState.getGrid().getCellAt((int) selectedEntity.getX(), (int) selectedEntity.getY()).setEntity(null);
		selectedEntity.setX(destX);
		selectedEntity.setY(destY);
		if (countMove) countMove();
	}
	
	/**
	 * This method counts a player move and sets the other player active if the action counter of the current player is
	 * equal the max action number per round.
	 */
	private void countMove() {
		gameState.moveCount++;
		if (gameState.moveCount >= 1) {
			gameState.getActivePlayer().addMinerals(50);
			gameState.setActivePlayer(gameState.getInActivePlayer());
			gameState.moveCount = 0;
		}
	}

	public void buyEntity(Entity entity) {
		
		Cell spawnCell = gameState.getGrid().getCellAt(gameState.getActivePlayerTownHallLocation().x + 1, gameState.getActivePlayerTownHallLocation().y);
		
		if (!spawnCell.containsEntity() && entity.getCost() > 0) {
			gameState.getActivePlayer().removeMinerals(entity.getCost());
			entity.setPlayer(gameState.activePlayer);
			spawnCell.setEntity(entity);
		}
	}
}