package llc.loading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

public class GameLoader {
	
	private final Gson gson;
	
	public GameLoader() {
		gson = new Gson();
	}
	
	public void saveToFile(GameState f, String fileName) {
		System.out.println(gson.toJson(f));

		File saveTo = new File(fileName);
		
		try {
			if (!saveTo.exists()) {
				saveTo.createNewFile();
			}
			String stateString = gson.toJson(f);
			
			PrintWriter writer = new PrintWriter(saveTo, "UTF-8");
			System.out.println("[Debug]" + stateString);
			writer.println(stateString);
			writer.close();
		}
		catch (Exception e) {
			System.err.println("Ein Fehler ist bem Speichern aufgetreten: Stacktrace:");
			e.printStackTrace(System.err);
		}
	}
	
	public void loadFromFile(String pathName) {
		File f = new File(pathName);
		if (!f.exists()) {
			throw new IllegalStateException("The save-file to load does not exist!");
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			
			String line = null;
			
			do {
				line = in.readLine();
			}
			while (line != null);
			
			in.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
