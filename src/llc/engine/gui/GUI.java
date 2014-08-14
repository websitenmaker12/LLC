package llc.engine.gui;

import java.util.ArrayList;
import java.util.List;

import llc.engine.GUIRenderer;
import llc.engine.audio.AudioEngine;

public class GUI {
	
	protected List<GUIElement> elements = new ArrayList<GUIElement>();
	public AudioEngine audioEngine;
	
	/**
	 * Is called to update the GUI. Use this for inputs!
	 */
	public void update(int x, int y) {
		for(GUIElement element : this.elements) element.update(x, y);
	}
	
	/**
	 * Is called to render the GUI. Use this for rendering!
	 */
	public void render(GUIRenderer renderer, int x, int y) {
		for(GUIElement element : this.elements) element.render(renderer, x, y);
	}
	
	/**
	 * An event which gets triggered when the GUI opens or when the display gets resized
	 */
	public void onOpen() {
		this.elements.clear();
	}
	
	/**
	 * An event which gets triggered when the GUI closes
	 */
	public void onClose() {
	}
	
	/**
	 * Returns a list of all GUI Elements
	 */
	public List<GUIElement> getElements() {
		return this.elements;
	}
	
}
