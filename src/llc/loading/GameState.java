package llc.loading;

import java.util.Date;

public class GameState {

	private Grid grid;
	private Date creation;
	
	public GameState(Grid grid, Date creation) {
		this.grid = grid;
	}
	
	public Grid getGrid() {
		return grid;
	}
	public Date getCreationDate() {
		return creation;
	}
}
