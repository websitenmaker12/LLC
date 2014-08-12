package llc.engine.res;

import java.util.HashMap;
import java.util.Map;

public class TextureLoader {

	private Map<String, Texture> textures = new HashMap<String, Texture>();
	private boolean loaded = false;
	
	/**
	 * Adds Texture for loading
	 */
	public void addTexture(String name, Texture texture) {
		if(this.loaded) return;
		else System.err.println("You can't load Textures from the Main-Loop");
		
		if(!this.textures.containsKey(name)) this.textures.put(name, texture);
		else System.err.println("Texture '" + name + "' is already defined");
	}
	
	/**
	 * Returns a registered texture for the given name
	 */
	public Texture getTexture(String name) {
		return this.textures.get(name);
	}
	
	/**
	 * Loads all registered textures
	 */
	public void loadTextures() {
		if(this.loaded) return;
		else System.err.println("You can load Textures only once");
		
		this.loaded = true;
		
		for(Texture texture : this.textures.values()) texture.load();
	}
	
}
