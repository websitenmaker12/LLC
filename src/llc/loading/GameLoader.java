package llc.loading;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import llc.engine.Camera;
import llc.entity.Entity;
import llc.entity.EntityBuildingBase;
import llc.logic.Cell;
import llc.logic.GameState;
import llc.logic.Grid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A IO-Util to load and save Gamestates
 * @author simolus3
 */
public class GameLoader {
	
	private static Gson gson;
	
	static {
		EntityInstanceCreator enCreator = new EntityInstanceCreator();

		gson = new GsonBuilder().registerTypeAdapter(Entity.class, enCreator).create();
	}
	/**
	 * Creates a new GameLoader, make sure to only do this once!(Performance...)
	 */
	public GameLoader() {
//		EntityInstanceCreator enCreator = new EntityInstanceCreator();
//
//		gson = new GsonBuilder().registerTypeAdapter(Entity.class, enCreator).create();
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
			} while (line != null);
			
			
			in.close();
			
			//System.out.println(content);
			try {
				GameState loaded = gson.fromJson(content, GameState.class);
				Grid g = loaded.getGrid();
				for (Cell[] cs : g.getCells()) {
					for (Cell c : cs) {
						if (c.containsEntity()) {
							
						}
					}
				}
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
	public GameState createNewGame(String map, Camera camera) {
		return createNewGame(new File(map), camera);
	}
	
	public GameState createNewGame(File map, Camera camera) {
		GameState state = null;
		try {
			BufferedImage img = ImageIO.read(map);
			int height, width;
			height = img.getHeight();
			width = img.getWidth();
			Grid g = new Grid(height, width);
			state = new GameState(g, camera);
			Color c;
			Cell cell;
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					c = new Color(img.getRGB(x, y));
					//Check for base-cells
					if (c.getBlue() == 0 && c.getRed() == 0 && c.getGreen() > 0) {
						cell = new Cell(x, y, getHeight(c));
						Entity townHall1 = new EntityBuildingBase();
						townHall1.setPlayer(1);
						townHall1.setX(x);
						townHall1.setY(y);
						cell.setEntity(townHall1);
						state.setTownHall1Cell(cell);
					}
					else if (c.getBlue() == 0 && c.getGreen() == 0 && c.getRed() > 0) {
						cell = new Cell(x, y, getHeight(c));
						Entity townHall2 = new EntityBuildingBase();
						townHall2.setPlayer(2);
						townHall2.setX(x);
						townHall2.setY(y);
						cell.setEntity(townHall2);
						state.setTownHall2Cell(cell);
					}
					else {
						//Normal cell
						cell = new Cell(x, y, getHeight(c));
					}
					g.setCellAt(cell, x, y);
				}
			}
			
			state.setActivePlayer(state.getPlayer1());
		}
		catch (Exception e) {
			System.err.println("Konnte neues Spiel nicht laden ;(");
			e.printStackTrace(System.err);
		}
		return state;
	}
	private float getHeight(Color c) {
		if (c.getBlue() == c.getGreen() && c.getRed() == c.getBlue()) {
			//Normal cell!
			return (((float)c.getBlue())/255f)*2-1;
		}
		if (c.getBlue() == 0 && c.getRed() == 0) {
			//Base of player 1!
			return (((float)c.getGreen())/255f)*2-1;
		}
		if (c.getBlue() == 0 && c.getGreen() == 0) {
			//Base of player 2!
			return (((float)c.getRed())/255f)*2-1;
		}
		else {
			return 0;
		}
	}
//	public static String getEntityDebugInformation(Entity en) {
//		return en.getClass().getName() + gson.toJson(en);
//	}
}
