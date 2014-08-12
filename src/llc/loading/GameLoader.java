package llc.loading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GameLoader {
	
	private final Gson gson;
	
	public GameLoader() {
		gson = new Gson();
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
			System.out.println("[Debug]" + stateString);
			writer.println(stateString);
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
				content += line;
			}
			while (line != null);
			
			in.close();
			
			try {
				GameState loaded = gson.fromJson(content, GameState.class);
				return loaded;
			}
			catch (JsonSyntaxException e) {
				System.err.println("Unable to load savegame... Corrupted?");
				e.printStackTrace(System.err);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
