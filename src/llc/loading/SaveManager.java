package llc.loading;

import java.io.File;

public class SaveManager {
	
	private static File safePath;
	
	static {
		String homeDir = System.getProperty("user.home"); //Home of the user data on the hard drive
		
		homeDir += "/LLC/saves";
		
		safePath = new File(homeDir);
	}
	
	public static void saveToFile(SaveGame f) {
		
	}
	
	public static SaveGame loadFromFile() {
		return null;
	}
}
