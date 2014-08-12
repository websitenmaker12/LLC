package llc.loading;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import llc.entity.Entity;
import llc.entity.EntityBuildingBase;
import llc.logic.Cell;
import llc.logic.CellType;
import llc.logic.GameState;
import llc.logic.Grid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A IO-Util to load and save Gamestates
 * @author simolus3
 */
public class GameLoader {
	
	private final Gson gson;
	
	/**
	 * Creates a new GameLoader, make sure to only do this once!(Performance...)
	 */
	public GameLoader() {
		EntityInstanceCreator creator = new EntityInstanceCreator();
		gson = new GsonBuilder().registerTypeAdapter(Entity.class, creator).create();
	}
	
	/**
	 * Saves the given GameState into the given path
	 * @param f The GameState
	 * @param fileName The location of the file!
	 * @throws IllegalArgumentException if gamestate is null!
	 */
	public void saveToFile(GameState f, String fileName) {
		
		if (f == null) {
			throw new IllegalArgumentException("The GameState cannot be null!");
		}
		String stateString = gson.toJson(f);
		
		if (fileName == null) {
			System.out.println(stateString);
			return;
		}
		File saveTo = new File(fileName);
		
		try {
			if (!saveTo.exists()) {
				saveTo.createNewFile();
			}
			
			PrintWriter writer = new PrintWriter(saveTo, "UTF-8");
			writer.print(stateString);
			writer.close();
		}
		catch (Exception e) {
			System.err.println("Ein Fehler ist bem Speichern aufgetreten: Stacktrace:");
			e.printStackTrace(System.err);
		}
	}
	
	public GameState loadFromFile(String pathName) {
		File f = new File(pathName);
		if (!f.exists()) {
			throw new IllegalStateException("The save-file to load does not exist!");
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			
			String line = null;
			String content = "";
			
			do {
				line = in.readLine();
				if (line == null) {
					continue;
				}
				content += line;
			}
			while (line != null);
			
			
			in.close();
			
			//System.out.println(content);
			try {
				GameState loaded = gson.fromJson(content, GameState.class);
				return loaded;
			}
			catch (Exception e) {
				System.err.println("Unable to load savegame... Corrupted?");
				e.printStackTrace(System.err);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Grid getGridFromImage(BufferedImage i) {
		int height, width;
		height = i.getHeight();
		width = i.getWidth();
		
		Grid g = new Grid(height, width);
		Color co;
		Cell c;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				co = new Color(i.getRGB(x, y));
				if (co.getRed() == 255 && co.getBlue() == 0 && co.getGreen() == 0) {
					//A Townhall!
					c = new Cell(x, x, CellType.WALKABLE);
					c.setEntity(new EntityBuildingBase());
				}
				else {
					c = getCellByColorAndLocation(x, y, co);
				}
				g.setCellAt(c, x, y);
			}
		}
		return g;
	}
	public Cell getCellByColorAndLocation(int x, int y, Color c) {
		CellType type = null;
		
		if (c.getBlue() == 0 && c.getGreen() == 0 && c.getRed() == 0) {
			type = CellType.SOLID;
		}
		else if (c.getBlue() == 255 && c.getGreen() == 255 && c.getRed() == 255) {
			type = CellType.WALKABLE;
		}
		else {
			return null;
		}
		return new Cell(x, y, type);
	}
	public Grid loadMap(String fileName) {
		File f = new File(fileName);
		BufferedImage i;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return getGridFromImage(i);
	}
}
