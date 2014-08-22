package llc.loading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Represents the .settings file. Util to get and change settings
 * @author simolus3
 */
public class Settings {
	public static final String settingsPath = ".llcsettings";
	public static final String version = "0.0.2";
	public static final Settings defaultSettings = new Settings();
	
	static {
		defaultSettings.setPlayBgSound(true);
		defaultSettings.setFocusBaseOnPlayerToggle(true);
		defaultSettings.setSelectEntityOnBuy(true);
	}
	
	private HashMap<String, String> settings;
	public Settings() {
		settings = new HashMap<String, String>();
		settings.put("version", version);
	}
	
	public void setPlayBgSound(boolean playSound) {
		settings.put("playBgSound", Boolean.toString(playSound));
	}
	public boolean getPlayBgSound() {
		return Boolean.parseBoolean(settings.get("playBgSound"));
	}

	public boolean getFocusBaseOnPlayerToggle() {
		return Boolean.parseBoolean(settings.get("focusBaseOnPlayerToggle"));
	}
	public void setFocusBaseOnPlayerToggle(boolean focusBaseOnPlayerToggle) {
		settings.put("focusBaseOnPlayerToggle", Boolean.toString(focusBaseOnPlayerToggle));
	}
	public void setSelectEntityOnBuy(boolean select) {
		settings.put("selectEntityOnBuy", Boolean.toString(select));
	}
	public boolean getSelectEntityOnBuy() {
		return Boolean.parseBoolean(settings.get("selectEntityOnBuy"));
	}
	public void set(String key, String value) {
		settings.put(key, value);
	}
	public HashMap<String, String> getAllInformation() {
		return settings;
	}
	
	public static void saveSettings(Settings s) {
		File f = new File(settingsPath);
		
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			String d = "";
			String newLine = System.getProperty("line.separator");
			FileWriter w = new FileWriter(f);
			for (Entry<String, String> e : s.getAllInformation().entrySet()) {
				d += e.getKey() + "=" + e.getValue() + newLine;
			}
			d = d.substring(0, d.length()-2);
			w.write(d);
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Settings loadSettings() {
		File f = new File(settingsPath);
		Settings s = new Settings();
		try {
			if (!f.exists()) {
				//Return default settings
				return defaultSettings;
			}
			Reader r = new FileReader(f);
			
			BufferedReader br = new BufferedReader(r);
			
			String line = null;
			String[] entry;
			while ((line = br.readLine()) != null) {
				if (!line.contains("=")) continue;
				
				entry = line.split("=", 2);
				s.set(entry[0], entry[1]);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
}
