package llc.logic;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import llc.LLC;
import llc.input.HotkeyManager;
import llc.loading.GameLoader;
import de.teamdna.databundle.DataBundle;
import de.teamdna.databundle.ISavable;

public class GameState implements ISavable{

	private Grid grid;
	private HotkeyManager hotKeys;
	
	private List<Player> players = new ArrayList<Player>();
	public Cell hoveredCell, selectedCell;

	public Player activePlayer;
	public int moveCount = 0;

	public final String levelName;
	public final String levelPath;

	public boolean isGameOver = false;
	public Player winner;

	public GameState(Grid grid, File level, List<Cell> bases) {
		hotKeys = new HotkeyManager(this);
		this.grid = grid;
		
		for (int i = 0; i < bases.size(); i++) {
			players.add(new Player("Player: " + i, bases.get(i), i));
			grid.addEntity(bases.get(i).getEntity());
		}
		setActivePlayer(getPlayer(0));
		
		this.levelName = level.getName();
		this.levelPath = level.getParentFile().getAbsolutePath();
	}

	public GameState(DataBundle data, GameLoader gameLoader) {
		this.levelName = data.getString("levelName");
		this.levelPath = data.getString("levelPath");
		this.moveCount = data.getInt("moveCount");
		GameState newgs = gameLoader.createNewGame(levelPath + "\\" + levelName);
		this.grid = newgs.grid;
		for (int i = 0; i < data.getInt("playersSize"); i++) {
			this.players.add(new Player(data.getBundle("player" + i), newgs.getPlayer(i).getTownHall()));
		}
		setActivePlayer(getPlayer(data.getInt("activePlayerID")));
		this.hotKeys = new HotkeyManager(data.getBundle("hotkeys"), this);
		try {
			this.grid.read(data.getBundle("grid"), players);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Grid getGrid() {
		return grid;
	}

	public void setActivePlayer(Player active) {
		this.activePlayer = active;
		LLC.getLLC().getCamera().focusCell(active.getTownHall(), true);
		selectedCell = active.getTownHall();
	}

	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public List<Player> getInActivePlayers() {
		List<Player> l = new ArrayList<Player>(players);
		l.remove(activePlayer.getPlayerID());
		return l;
	}

	public Player getNextPlayer() {
		if (players.size() -1 > activePlayer.getPlayerID()) {
			return players.get(activePlayer.getPlayerID() + 1);
		} else {
			return players.get(0);
		}
	}

	public List<Player> getPlayers() {
		return players;
	}
	@Override
	public void save(DataBundle data) {
		data.setInt("moveCount", moveCount);
		data.setInt("activePlayerID", activePlayer.getPlayerID());

		data.setInt("playersSize", players.size());
		for (int i = 0; i < players.size(); i++){
			DataBundle d = new DataBundle();
			players.get(i).save(d);
			data.setBundle("player" + i, d);
		}
		
		data.setString("levelName", levelName);
		data.setString("levelPath", levelPath);

		DataBundle d = new DataBundle();
		grid.save(d);
		data.setBundle("grid", d);
		DataBundle hd = new DataBundle();
		hotKeys.save(hd);
		data.setBundle("hotkeys", hd);
	}

	public Player getPlayer(int i) {
		return players.get(i);
	}

	@Override
	public void read(DataBundle arg0) {}
}
