package llc.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import llc.engine.Camera;
import llc.loading.ISavable;
import de.teamdna.databundle.DataBundle;

public class GameState implements ISavable{

	private Grid grid;
	private Camera camera;

	private List<Player> players = new ArrayList<Player>();
	public Cell hoveredCell, selectedCell;

	public Player activePlayer;
	public int moveCount = 0;

	public final String levelName;
	public final String levelPath;

	public boolean isGameOver = false;
	public Player winner;

	public GameState(Grid grid, Camera camera, File level, List<Cell> bases) {
		this.grid = grid;
		this.camera = camera;
		
		for (int i = 0; i < bases.size(); i++) players.add(new Player("Player" + i, bases.get(i), i));
		setActivePlayer(getPlayer(0));

		this.levelName = level.getName();
		this.levelPath = level.getPath();
	}

	public GameState(DataBundle data) {
		this.levelName = data.getString("levelName");
		this.levelPath = data.getString("levelPath");
		readFromDataBundle(data);
	}

	public Grid getGrid() {
		return grid;
	}
	
	public void focusCell(Cell c, boolean animate) {
		this.camera.focusCell(c, animate);
	}

	public void setActivePlayer(Player active) {
		this.activePlayer = active;
		this.camera.focusCell(this.getActivePlayerTownHall(), true);
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public Player getNextPlayer() {
		if (players.size() -1 > activePlayer.getPlayerID()) {
			return players.get(activePlayer.getPlayerID() + 1);
		} else {
			return players.get(0);
		}
	}

	public Cell getActivePlayerTownHall() {
		return activePlayer.getTownHall();
	}

	@Override
	public DataBundle writeToDataBundle() {
		DataBundle data = new DataBundle();

		data.setInt("moveCount", moveCount);
		data.setInt("activePlayerID", activePlayer.getPlayerID());
//		data.setList("players", players);
		data.setString("levelName", levelName);
		data.setString("levelPath", levelPath);

		return data;
	}

	@Override
	public void readFromDataBundle(DataBundle data) {

	}

	public Player getPlayer(int i) {
		return players.get(i);
	}
}
