package llc.logic;

import llc.entity.Entity;
import llc.entity.EntityBuildingBase;
import llc.entity.EntityMovable;
import llc.entity.IAttacking;
import llc.input.Input;
import llc.input.Input.Direction;

/**
 * Logic class
 * handles changes to the gamestate
 * @author PetaByteBoy
 * @author erdlof
 */
public class Logic {

	private GameState gameState;
	private EntityMovable selectedEntity;
	private Input input;
	
	public int subTurns = 4;

	public Logic(GameState state, Input input) {
		this.setGameState(state);
		this.input = input;
		
		this.input.addFireListener(new Input.LogicListener() {

			@Override
			public void onCellClicked(int cell_x, int cell_y) {
				clickCell(cell_x, cell_y);
			}

			@Override
			public void onScroll(Direction d) {}

			@Override
			public void onNewCellHovered(int cell_x, int cell_y) {
				hoverCell(cell_x, cell_y);
			}
		});
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
	 * Handles if the mouse hovers above a cell
	 * @param hoverX
	 * @param hoverY
	 */
	public void hoverCell(int hoverX, int hoverY) {
		if (0 <= hoverY && hoverY < gameState.getGrid().getHeigth() && 0 <= hoverX && hoverX < gameState.getGrid().getWidth()) {
			Cell hoveredCell = gameState.getGrid().getCellAt(hoverX, hoverY);
			gameState.hoveredCell = hoveredCell;
		}
	}

	/**
	 * This method is called when a player clicks at a cell on the grid.
	 * It decides whether the clicked cell contains an entity that can be attacked
	 * or it contains an entity that can be selected. 
	 * @param clickX The x coord of the clicked cell.
	 * @param clickY The y coord of the clicked cell.
	 */
	private void clickCell(int clickX, int clickY) {
		if (0 <= clickY && clickY < gameState.getGrid().getHeigth() && 0 <= clickX && clickX < gameState.getGrid().getWidth()) {
			Cell clickedCell = gameState.getGrid().getCellAt(clickX, clickY);
			gameState.selectedCell = clickedCell;
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
				if (destEntity instanceof EntityBuildingBase) gameOver(gameState.getActivePlayer());
			}
		}
		countMove();
	}

	private void gameOver(Player winner) {
		gameState.isGameOver = true;
		gameState.winner = winner;
		System.out.println("Player " + winner.playerID + " won!");
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
		selectedEntity = null;
		gameState.selectedCell = null;
	}
	
	/**
	 * This method counts a player move and sets the other player active if the action counter of the current player is
	 * equal the max action number per round.
	 */
	private void countMove() {
		gameState.moveCount++;
		if (gameState.moveCount >= subTurns) {
			System.out.println("Player " + gameState.getActivePlayer().playerID + "'s turn is now over!");
			gameState.getActivePlayer().addMinerals(50);
			gameState.setActivePlayer(gameState.getInActivePlayer());
			gameState.moveCount = 0;
			gameState.selectedCell = null;
		}
	}

	public void buyEntity(Entity entity) {
		int cx = gameState.getActivePlayerTownHallLocation().x;
		int cy = gameState.getActivePlayerTownHallLocation().y;
		Cell spawnCell = null;
		
		for(int x = -2; x <= 2; x++) {
			for(int y = -2; y <= 2; y++) {
				spawnCell = gameState.getGrid().getCellAt(cx + x, cy + y);
				if(spawnCell == null || spawnCell.containsEntity() || spawnCell.getType() == CellType.SOLID) continue;
				else break;
			}
		}
		
		if (spawnCell != null && !spawnCell.containsEntity() && entity.getCost() > 0 && gameState.getActivePlayer().getMinerals() >= entity.getCost()) {
			gameState.getActivePlayer().removeMinerals(entity.getCost());
			entity.setPlayer(gameState.activePlayer);
			spawnCell.setEntity(entity);
			entity.setX(spawnCell.x);
			entity.setY(spawnCell.y);
			clickCell(spawnCell.x,spawnCell.y);
		}
	}
}