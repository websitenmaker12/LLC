package llc.loading;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import llc.entity.EntityBuildingBase;
import llc.logic.Cell;
import llc.logic.GameState;
import llc.logic.Grid;
import de.teamdna.databundle.DataBundle;

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
		try {
			f.writeToDataBundle().writeToFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public GameState loadFromFile(String pathName) {
		DataBundle data = new DataBundle();
		try {
			data.readFromFile(pathName);
		} catch (IOException e) {
			System.err.println("Error: Could not load save file!");
			e.printStackTrace();
		}
		return new GameState(data, this);
	}
	
	public GameState createNewGame(String mapLoc) {
		GameState state = null;
		File map = new File(mapLoc);
		try {
			BufferedImage img = ImageIO.read(map);
			
			int height, width;
			height = img.getHeight();
			width = img.getWidth();
			Grid g = new Grid(height, width);
			Color c;
			Cell cell;
			List<Cell> bases = new ArrayList<Cell>();
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					c = new Color(img.getRGB(x, height-y-1));
					//Check for base-cells
					if (c.getBlue() == 0 && c.getGreen() == 0 && c.getRed() > 0) {
						cell = new Cell(x, y, getHeight(c));
						cell.setEntity(new EntityBuildingBase(x, y));
						bases.add(cell);
					}
					else {
						//Normal cell
						cell = new Cell(x, y, getHeight(c));
					}
					g.setCellAt(cell, x, y);
				}
			}

			state = new GameState(g, map, bases);
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
	
	public DataBundle saveList(List<ISavable> l) {
		DataBundle d = new DataBundle();
		
		d.setInt("size", l.size());
		
		for (int i = 0; i < l.size(); i++) {
			d.setBundle(String.valueOf(i), l.get(i).writeToDataBundle());
		}
		
		return d;
	}
//	
//	public List<T> readList(DataBundle d, Class<T>) {
//		List<T> l = new ArrayList<T>();
//		
//		for (int i = 0; i < d.getInt("size"); i++) {
//			l.set(i,<T>.readFromDataBundle(d.getBundle(String.valueOf(i)));
//		}
//		
//		return l;
//	}
}
