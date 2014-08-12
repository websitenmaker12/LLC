package llc.logic;

public class GameState {

	private Grid grid;
	
	private Player player1;
	private Player player2;
	
	private Player activePlayer;
	
	public GameState(Grid grid) {
		this.grid = grid;
		
		player1 = new Player(100);
		player2 = new Player(100);
	}
	
	public Grid getGrid() {
		return grid;
	}
}
