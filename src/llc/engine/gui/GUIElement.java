package llc.engine.gui;

import llc.engine.GUIRenderer;

public abstract class GUIElement {
	
	public float posX;
	public float posY;

	public GUIElement(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	/**
	 * This function gets called when the GUI, the element is bound to, opens
	 */
	public void init() {  }
	
	/**
	 * Is called for input updates
	 */
	public abstract void update(int x, int y);
	
	/**
	 * Is called for render updates
	 */
	public abstract void render(GUIRenderer renderer, int x, int y);
	
}
