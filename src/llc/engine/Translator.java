package llc.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Translator {

	private static Map<String, String> dictionary = new HashMap<String, String>();
	
	public static void loadLanguage(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		try {
			String line;
			while((line = reader.readLine()) != null) {
				if(!line.contains("=")) continue;
				
				String[] data = line.split("=", 2);
				dictionary.put(data[0], data[1]);
			}
		} finally {
			reader.close();
		}
	}
	
	public static void reset() {
		dictionary.clear();
	}

	public static String translate(String unlocalizedName) {
		return dictionary.containsKey(unlocalizedName) ? dictionary.get(unlocalizedName) : unlocalizedName;
	}
	
	public static String translate(String unlocalizedName, Object... inserts) {
		return dictionary.containsKey(unlocalizedName) ? String.format(dictionary.get(unlocalizedName), inserts) : unlocalizedName;
	}
	
}
