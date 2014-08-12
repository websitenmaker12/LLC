package llc.loading;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import llc.entity.Entity;
import llc.logic.GameState;
import llc.logic.Grid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameLoader {
	
	private final Gson gson;
	
	public GameLoader() {
		EntityInstanceCreator creator = new EntityInstanceCreator();
		gson = new GsonBuilder().registerTypeAdapter(Entity.class, creator).create();
	}
	
	public void saveToFile(GameState f, String fileName) {
		
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
			//TODO Remove this when it works 100%!
			System.out.println(stateString);
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
			
			System.out.println(content);
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
		return null;
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
