package llc.loading;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;

public class GameLoader {
	
	private File safePath;
	private final Gson gson;
	
	public GameLoader(File path) {
		gson = new Gson();
		String homeDir = System.getProperty("user.home"); //Home of the user data on the hard drive
		
		homeDir += "/LLC/saves";
		
		safePath = new File(homeDir);
	}
	
	/**
	 * @deprecated Save games path should not be changeable
	 * @param directory the directory where the files should be saved!
	 * @throws NullPointerException if <code>directory == null</code>
	 * @throws IllegalStateException if <code>!directory.isDirectory()</code>
	 */
	@Deprecated
	public void setSafePath(File directory) {
		if (directory == null) {
			throw new NullPointerException("Directory cannot be null!");
		}
		if (!directory.isDirectory()) {
			throw new IllegalStateException("File \"directory\" must be a directory!");
		}
	}
	
	public void saveToFile(GameState f) {
		System.out.println(gson.toJson(f));
		
		SimpleDateFormat d = new SimpleDateFormat();
		d.applyLocalizedPattern("dd-MM-yyyy");
		
	}
	
	/**
	 * Gets a list of all saved games, the name is based on the filename... If a savegame file is named <code>myGame 1.saveGame</code>, the name will be called <code>myGame 1</code>
	 * @return a List of Saved Grids
	 */
	public List<String> getSavedGrids() {
		
		List<String> allGames = new ArrayList<String>();
		
		return allGames;
	}
}
