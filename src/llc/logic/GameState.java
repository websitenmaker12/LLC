package llc.logic;

public class GameState {

	private Grid grid;
	
	private Player player1;
	private Player player2;

	private Player activePlayer;
	public int moveCount = 0;
	
	public boolean isGameOver = false;
	
	public GameState(Grid grid) {
		this.grid = grid;
		
		player1 = new Player(100);
		player2 = new Player(100);
		
		setActivePlayer(player1);
	}
	
	public Grid getGrid() {
		return grid;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
	public Player getInActivePlayer() {
		if (activePlayer == player1) {
			return player2;
		} else {
			return player1;
		}
	}
}