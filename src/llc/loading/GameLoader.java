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
	
	/**
	 * Creates a new GameLoader, make sure to only do this once!(Performance...)
	 */
	public GameLoader() {
		
	}
	
	/**
	 * Saves the given GameState into the given path
	 * @param f The GameState
	 * @param fileName The location of the file!
	 * @throws IllegalArgumentException if gamestate is null!
	 */
	public void saveToFile(GameState f, String fileName) {
		EntityInstanceCreator creator = new EntityInstanceCreator(f);
		Gson gson = new GsonBuilder().registerTypeAdapter(Entity.class, creator).create();
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
		EntityInstanceCreator creator = new EntityInstanceCreator(null);
		Gson gson = new GsonBuilder().registerTypeAdapter(Entity.class, creator).create();
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
	public GameState createNewGame(String map) {
		GameState state = null;
		try {
			BufferedImage img = ImageIO.read(new File(map));
			int height, width;
			height = img.getHeight();
			width = img.getWidth();
			Grid g = new Grid(height, width);
			state = new GameState(g);
			Color c;
			Cell cell;
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					c = new Color(img.getRGB(x, y));
					if (c.equals(Color.BLACK)) {
						cell = new Cell(x, y, CellType.SOLID);
					}
					else if (c.equals(Color.WHITE)) {
						cell = new Cell(x, y, CellType.WALKABLE);
					}
					else if (c.equals(Color.GREEN)) {
						cell = new Cell(x, y, CellType.WALKABLE);
						Entity townHall1 = new EntityBuildingBase();
						townHall1.setPlayer(state.getPlayer1());
						cell.setEntity(townHall1);
						state.setTownHall1Cell(cell);
					}
					else if (c.equals(Color.RED)) {
						cell = new Cell(x, y, CellType.WALKABLE);
						Entity townHall2 = new EntityBuildingBase();
						townHall2.setPlayer(state.getPlayer2());
						cell.setEntity(townHall2);
						state.setTownHall2Cell(cell);
					}
					else {
						//Unknown
						cell = new Cell(x, y, CellType.SOLID);
					}
					g.setCellAt(cell, x, y);
				}
			}
		}
		catch (Exception e) {
			System.err.println("Konnte neues Spiel nicht laden ;(");
			e.printStackTrace(System.err);
		}
		return state;
	}
	/**
	 * @deprecated Use createNewGame or loadFromFile instead!
	 */
	@Deprecated
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
					c = new Cell(x, x, CellType.WALKABLE);
					Entity en = new EntityBuildingBase();
					en.setPlayer(null);
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
			throw new IllegalArgumentException("Unknown Cell Color!");
		}
		return new Cell(x, y, type);
	}
	/**
	 * @deprecated Use createNewGame or loadFromFile instead!
	 */
	@Deprecated
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
