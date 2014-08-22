package llc.logic;

import java.util.Random;

import llc.LLC;
import llc.entity.Entity;
import llc.entity.EntityBuildingBase;
import llc.entity.EntityMovable;
import llc.entity.IAttacking;
import llc.entity.IRepairer;
import llc.input.Input;
import llc.input.Input.Direction;
import llc.util.PathFinder;

/**
 * Logic class
 * handles changes to the gamestate
 * @author PetaByteBoy
 * @author erdlof
 * @author websitenmaker12
 */
public class Logic {
	
	private GameState gameState;
	private EntityMovable selectedEntity;
	private Input input;
	private Random random;
	
	public boolean markMinerals;
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
			public void onScroll(Direction d) {  }

			@Override
			public void onNewCellHovered(int cell_x, int cell_y) {
				hoverCell(cell_x, cell_y);
			}
		});
		
		this.random = new Random(System.currentTimeMillis());
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
	public void clickCell(int clickX, int clickY) {
		if (0 <= clickY && clickY < gameState.getGrid().getHeigth() && 0 <= clickX && clickX < gameState.getGrid().getWidth()) {
			Cell clickedCell = gameState.getGrid().getCellAt(clickX, clickY);
			
			if (clickedCell.containsEntity()) {
				if (clickedCell.getEntity().getPlayer() == gameState.activePlayer) {
					// Is the selected entity a worker and the entity of the clicked cell is a base, repair it
					if (this.selectedEntity instanceof IRepairer && clickedCell.getEntity() instanceof EntityBuildingBase) {
						int addHealth = ((IRepairer)this.selectedEntity).getRepairHealth();
						int cost = ((IRepairer)this.selectedEntity).getRepairCost();
						int minerals = gameState.getActivePlayer().getMinerals();
						
						if (minerals >= cost) {
							clickedCell.getEntity().health = Math.min(clickedCell.getEntity().health + addHealth, clickedCell.getEntity().maxHealth);
							gameState.getActivePlayer().setMinerals(minerals - cost);
							countMove();
						} else {
							markMinerals = true;
						}
					} else {
						// select
						selectEntity(clickedCell.getEntity());
						gameState.selectedCell = clickedCell;
					}
				} else if (selectedEntity instanceof IAttacking && selectedEntity.isCellInRange(clickX, clickY)) {
					// attack
					attackCell(clickX, clickY);
				}
			} else if (clickedCell.getType() == CellType.WALKABLE && selectedEntity != null && selectedEntity.isCellInRange(clickX, clickY)) {
				// move
				moveSelectedEntity(clickX, clickY, true, false);
			}
		}
	}

	/**
	 * This method selects a given entity for later work.
	 * @param toSelect The entity to be selected.
	 */
	private void selectEntity(Entity toSelect) {
		if(toSelect != null && toSelect instanceof EntityMovable) {
			this.selectedEntity = (EntityMovable) toSelect;
			LLC.getLLC().getCamera().focusCell(this.gameState.getGrid().getCellAt((int)toSelect.getX(), (int)toSelect.getY()), true);
		} else {
			this.selectedEntity = null;
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
			destEntity.health -= ((IAttacking) this.selectedEntity).getAttackDamage();
			moveSelectedEntity(destX, destY, false, true);
			
			if (destEntity.health <= 0) {
				Cell c = gameState.getGrid().getCellAt((int)destEntity.getX(), (int)destEntity.getY());
				c.setEntity(null);
				gameState.getActivePlayer().addMinerals(25);
				// if a base was destroyed, the game is over
				if (destEntity instanceof EntityBuildingBase) {
					gameOver(gameState.getActivePlayer());
					LLC.getLLC().openGameOverGUI(this.gameState.winner);
				}
			}
		}
		countMove();
	}

	private void gameOver(Player winner) {
		gameState.isGameOver = true;
		gameState.winner = winner;
	}

	/**
	 * This method moves an entity
	 * @param destX The new x position
	 * @param destY The new y position
	 * @param countMove Does the move count as player action.
	 */
	private void moveSelectedEntity(int destX, int destY, boolean countMove, boolean shouldReturn) {
		EntityMovable selectedEntity = this.selectedEntity;
		selectedEntity.initMoveRoutine(this, PathFinder.findPath(this.gameState.getGrid(),
				this.gameState.getGrid().getCellAt((int)selectedEntity.getX(), (int)selectedEntity.getY()), this.gameState.getGrid().getCellAt(destX, destY)), countMove, shouldReturn);
		gameState.selectedCell = null;
	}
	
	public void finishEntityMove(int origX, int origY, boolean countMove, Entity entity) {
		gameState.getGrid().getCellAt(origX, origY).setEntity(null);
		gameState.getGrid().getCellAt((int)entity.getX(), (int)entity.getY()).setEntity(entity);
		if(countMove) countMove();
		if(this.selectedEntity == entity) selectEntity(null);
	}
	
	/**
	 * This method counts a player move and sets the other player active if the action counter of the current player is
	 * equal the max action number per round.
	 */
	public void countMove() {
		gameState.moveCount++;
		if (gameState.moveCount >= subTurns) {
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
		while(spawnCell == null || spawnCell.containsEntity() || spawnCell.getType() == CellType.SOLID)
			spawnCell = this.gameState.getGrid().getCellAt(cx + random.nextInt(4) - 2, cy + random.nextInt(4) - 2);
		
		if (spawnCell != null && !spawnCell.containsEntity() && entity.getCost() > 0 && gameState.getActivePlayer().getMinerals() >= entity.getCost()) {
			gameState.getActivePlayer().removeMinerals(entity.getCost());
			entity.setPlayer(gameState.activePlayer);
			spawnCell.setEntity(entity);
			entity.setX(spawnCell.x);
			entity.setY(spawnCell.y);
			if (LLC.getLLC().getSettings().getSelectEntityOnBuy()) {
				clickCell(spawnCell.x,spawnCell.y);
			}
		} else if (gameState.getActivePlayer().getMinerals() < entity.getCost()) {
			// The entity did not spawn because the player didn't have enough minerals
			markMinerals = true;
		}
	}
	public Entity getSelectedEntity() {
		return selectedEntity;
	}
}