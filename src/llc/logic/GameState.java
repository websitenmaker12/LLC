package llc.logic;

public class GameState {

	private Grid grid;
	
	private Player player1;
	private Player player2;
	
	public GameState(Grid grid) {
		this.grid = grid;
		
		player1 = new Player();
		player2 = new Player();
	}
	
	public Grid getGrid() {
		return grid;
	}
}
