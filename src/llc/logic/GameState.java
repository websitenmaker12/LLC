package llc.logic;

public class GameState {

	private Grid grid;
	
	private Player player1, player2;
	
	private int activePlayer = 1;
	
	private Cell townHall1, townHall2;
	
	public GameState(Grid grid) {
		this.grid = grid;
		
		player1 = new Player(100);
		player2 = new Player(100);
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	public Player getPlayer2() {
		return player2;
	}
	public Cell getTownHallOfPlayer1() {
		return townHall1;
	}
	public Cell getTownHallOfPlayer2() {
		return townHall2;
	}
	public void setTownHall1Cell(Cell c) {
		this.townHall1 = c;
	}
	public void setTownHall2Cell(Cell c) {
		this.townHall2 = c;
	}
	public Player getActivePlayer() {
		if (activePlayer == 1) {
			return player1;
		}
		return player2;
	}
	public void setActivePlayer(Player active) {
		if (active.equals(player1)) {
			activePlayer = 1;
		}
		else if (active.equals(player2)) {
			activePlayer = 2;
		}
		else {
			throw new IllegalArgumentException("Given player was not saved!");
		}
	}
}
