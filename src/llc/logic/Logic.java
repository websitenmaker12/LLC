package llc.logic;

import llc.loading.GameState;
import llc.loading.Grid;

public class Logic {

	private GameState gameState;

	public Logic(Grid grid) {
		this.setGameState(new GameState(grid));
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
}
