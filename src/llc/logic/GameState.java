package llc.logic;

public class GameState {

	private Grid grid;
	
	private Player player1, player2;
	private Cell townHall1, townHall2;
	
	private int activePlayer;
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

	public void setActivePlayer(Player active) {
		if (player1.equals(activePlayer)) {
			activePlayer = 1;
		} else if (player2.equals(activePlayer)) {
			activePlayer = 2;
		} else {
			throw new IllegalArgumentException("Given Player argument does not exist");
		}
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	public Player getActivePlayer() {
		if (activePlayer == 1) {
			return player1;
		} else {
			return player2;
		}
	}
	public Player getInActivePlayer() {
		if (activePlayer == 1) {
			return player2;
		} else {
			return player1;
		}
	}

	public Cell getTownHall1Cell() {
		return townHall1;
	}

	public void setTownHall1Cell(Cell townHall1) {
		this.townHall1 = townHall1;
	}

	public Cell getTownHall2Cell() {
		return townHall2;
	}

	public void setTownHall2Cell(Cell townHall2) {
		this.townHall2 = townHall2;
	}
}
