package llc.loading;

import java.io.File;

public class SaveManager {
	
	private static File safePath;
	private static final Object gson;
	
	static {
		gson = null;
		String homeDir = System.getProperty("user.home"); //Home of the user data on the hard drive
		
		homeDir += "/LLC/saves";
		
		safePath = new File(homeDir);
	}
	
	/**
	 * 
	 * @param directory the directory where the files should be saved!
	 * @throws NullPointerException if <code>directory == null</code>
	 * @throws IllegalStateException if <code>!directory.isDirectory()</code>
	 */
	public static void setSafePath(File directory) {
		if (directory == null) {
			throw new NullPointerException("Directory cannot be null!");
		}
		if (!directory.isDirectory()) {
			throw new IllegalStateException("File \"directory\" must be a directory!");
		}
	}
	
	public static void saveToFile(SaveGame f) {
		
	}
	
	public static SaveGame loadFromFile() {
		return null;
	}
}
