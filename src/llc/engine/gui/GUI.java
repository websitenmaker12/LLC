package llc.engine.gui;

import java.util.ArrayList;
import java.util.List;

import llc.engine.GUIRenderer;

public class GUI {
	
	protected List<GUIElement> elements = new ArrayList<GUIElement>();
	
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
	 * An event which gets triggered when the GUI opens
	 */
	public void onOpen() {
		for(GUIElement element : this.elements) element.init();
	}
	
	/**
	 * An event which gets triggered when the GUI closes
	 */
	public void onClose() {
	}
	
}
